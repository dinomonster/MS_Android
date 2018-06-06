package com.hr.ms.ms_android.mvp.map

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.ArrayAdapter
import android.widget.TextView
import com.afollestad.materialdialogs.MaterialDialog
import com.amap.api.location.AMapLocation
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.amap.api.location.AMapLocationListener
import com.amap.api.maps.AMap
import com.amap.api.maps.CameraUpdateFactory
import com.amap.api.maps.model.BitmapDescriptorFactory
import com.amap.api.maps.model.LatLng
import com.amap.api.maps.model.Marker
import com.amap.api.maps.model.MarkerOptions
import com.amap.api.services.core.PoiItem
import com.amap.api.services.help.Inputtips
import com.amap.api.services.help.InputtipsQuery
import com.amap.api.services.help.Tip
import com.amap.api.services.poisearch.PoiResult
import com.amap.api.services.poisearch.PoiSearch
import com.better.appbase.mvp.MvpPresenter
import com.hr.ms.ms_android.R
import com.hr.ms.ms_android.base.BaseActivity
import com.hr.ms.ms_android.bean.LocationBean
import com.hr.ms.ms_android.constants.CommonConstants
import com.socks.library.KLog
import com.tbruyelle.rxpermissions2.RxPermissions
import io.reactivex.observers.DisposableObserver
import kotlinx.android.synthetic.main.mapsearch_activity.*
import java.util.*


/**
 * AMapV2地图中简单介绍poisearch搜索
 */
class MapSearchActivity : BaseActivity(), PoiSearch.OnPoiSearchListener, Inputtips.InputtipsListener, AMapLocationListener, AMap.OnMarkerClickListener, AMap.InfoWindowAdapter {
    var aMap: AMap? = null
    var query: PoiSearch.Query? = null
    var mLocationClient: AMapLocationClient? = null
    var mLocationOption: AMapLocationClientOption? = null
    var cityCode: String? = null
    override fun getPresenter(): MvpPresenter? {
        return null
    }

    override fun setViewId(): Int {
        return R.layout.mapsearch_activity
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        map.onCreate(savedInstanceState)
        aMap = map.map
        aMap?.setOnMarkerClickListener(this)
        aMap?.setInfoWindowAdapter(this)
        mLocationClient = AMapLocationClient(applicationContext)
        mLocationClient?.setLocationListener(this)
        mLocationOption = AMapLocationClientOption()
        mLocationOption?.locationMode = AMapLocationClientOption.AMapLocationMode.Hight_Accuracy
        mLocationOption?.isOnceLocation = true
        mLocationClient?.setLocationOption(mLocationOption)
        checkExternalStoragePermission()

        back_ll.setOnClickListener { onBackPressed() }
        status_bar.setStatusBarDark(this)
        tv_search.setOnClickListener { doSearchQuery() }
        keyWord.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val newText = s?.toString()?.trim()
                val inputquery = InputtipsQuery(newText, cityCode)
                inputquery.cityLimit = true//是否限制在当前城市
                val inputTips = Inputtips(this@MapSearchActivity, inputquery)
                inputTips.setInputtipsListener(this@MapSearchActivity)
                inputTips.requestInputtipsAsyn()
            }
        })

    }

    override fun onLocationChanged(amapLocation: AMapLocation?) {
        if (amapLocation != null) {
            cityCode = amapLocation.cityCode
            when (amapLocation.errorCode) {
                0 -> {
                    cityCode = amapLocation.cityCode
                    val latLng = LatLng(amapLocation.latitude, amapLocation.longitude)
                    aMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 11f))
                }
                12 -> {
                    KLog.e("缺少定位权限")
                }
            }
        } else {
            //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
            KLog.e("AmapError", "location Error, ErrCode:"
                    + amapLocation?.errorCode + ", errInfo:"
                    + amapLocation?.errorInfo)
        }
    }

    override fun onPoiItemSearched(p0: PoiItem?, p1: Int) {
    }

    override fun onPoiSearched(poiResult: PoiResult?, rCode: Int) {
        dialogHelper.dismissProgressDialog()
        if (rCode == 1000) {
            if (poiResult?.query == query) {// 是否是同一条
                // 取得搜索到的poiitems有多少页
                val poiItems = poiResult?.pois// 取得第一页的poiitem数据，页数从数字0开始
                val suggestionCities = poiResult?.searchSuggestionCitys// 当搜索不到poiitem数据时，会返回含有搜索关键字的城市信息

                if (poiItems != null && poiItems!!.size > 0) {
                    aMap?.clear()// 清理之前的图标
                    for (poiItem in poiItems) {
                        drawmaker(poiItem.latLonPoint.latitude, poiItem.latLonPoint.longitude, poiItem.title, poiItem.snippet)
                    }
                    val latLng = LatLng(poiItems!![0].latLonPoint.latitude, poiItems!![0].latLonPoint.longitude)
                    aMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 11f))
                }
            }
        }
    }

    override fun onGetInputtips(tipList: MutableList<Tip>?, rCode: Int) {
        if (tipList == null) return
        if (rCode == 1000) {// 正确返回
            val listString = ArrayList<String>()
            for (i in tipList!!.indices) {
                listString.add(tipList[i].name)
            }
            val aAdapter = ArrayAdapter(
                    applicationContext,
                    R.layout.route_inputs_layout, listString)
            keyWord.setAdapter(aAdapter)
            aAdapter.notifyDataSetChanged()
        }
    }

    /**
     * 开始进行poi搜索
     */
    fun doSearchQuery() {
        dialogHelper.showProgressDialog("搜索中...")
        var currentPage = 0
        //keyWord表示搜索字符串，
        //第二个参数表示POI搜索类型，二者选填其一，选用POI搜索类型时建议填写类型代码，码表可以参考下方（而非文字）
        //cityCode表示POI搜索区域，可以是城市编码也可以是城市名称，也可以传空字符串，空字符串代表全国在全国范围内进行搜索
        query = PoiSearch.Query(keyWord?.text?.toString(), "", cityCode)
        query?.pageSize = 20// 设置每页最多返回多少条poiitem
        query?.pageNum = currentPage// 设置查第一页

        var poiSearch = PoiSearch(this, query)
        poiSearch.setOnPoiSearchListener(this)
        poiSearch.searchPOIAsyn()// 异步搜索
    }

    fun drawmaker(latitude: Double, longitude: Double, title: String, snippet: String) {
        //绘制marker
        val marker = aMap?.addMarker(MarkerOptions()
                .position(LatLng(latitude, longitude))
                .icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                        .decodeResource(resources, R.drawable.marker)))
                .draggable(true))
        marker?.title = title
        marker?.snippet = snippet
//        if (marker != null) {
//            val animation = RotateAnimation(marker.rotateAngle, marker.rotateAngle + 180, 0f, 0f, 0f)
//            val duration = 1000L
//            animation.setDuration(duration)
//            animation.setInterpolator(LinearInterpolator())
//            marker.setAnimation(animation)
//            marker.startAnimation()
//        }
    }

    override fun getInfoContents(p0: Marker?): View? {
        return null
    }

    override fun getInfoWindow(marker: Marker): View {
        val view = layoutInflater.inflate(R.layout.poikeywordsearch_uri,
                null)
        val title = view.findViewById(R.id.title) as TextView
        title.text = marker.title

        val snippet = view.findViewById(R.id.snippet) as TextView
        snippet.text = marker.snippet
        val button = view.findViewById(R.id.start_amap_app) as TextView
        button.setOnClickListener {
            var intent = Intent()
            var locationBean = LocationBean()
            locationBean.address = marker.title
            locationBean.latitude = marker.position.latitude
            locationBean.longitude = marker.position.longitude
            intent.putExtra(CommonConstants.BEAN, locationBean)
            setResult(Activity.RESULT_OK,intent)
            onBackPressed()
        }
        return view
    }

    override fun onMarkerClick(marker: Marker?): Boolean {
        marker?.showInfoWindow()
        return false
    }


    fun checkExternalStoragePermission() {
        val externalStoragePermissions = arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION)
        val rxPermissions = RxPermissions(this)
        rxPermissions.request(*externalStoragePermissions)
                .subscribeWith(object : DisposableObserver<Boolean>() {
                    override fun onNext(value: Boolean) {
                        if (value) {
                            mLocationClient?.startLocation()
                        } else {
                            MaterialDialog.Builder(this@MapSearchActivity)
                                    .title("帮助")
                                    .content("当前应用缺少定位权限。\n请点击“设置”-“权限”-打开所需权限。\n最后点击两次后退按钮即可返回。")
                                    .positiveText("设置")
                                    .negativeText("取消")
                                    .onPositive { dialog, which ->
                                        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                                        intent.data = Uri.parse("package:$packageName")
                                        startActivity(intent)
                                    }
                                    .show()
                        }
                    }

                    override fun onError(e: Throwable) {
                        KLog.e("检测限抛异常了：" + e.message)
                    }

                    override fun onComplete() {

                    }
                })
    }

}
