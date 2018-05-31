package com.hr.ms.ms_android.mvp.resources.overview

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.better.appbase.mvp.MvpPresenter
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import com.github.mikephil.charting.utils.MPPointF
import com.gongwen.marqueen.MarqueeFactory
import com.hr.ms.ms_android.R
import com.hr.ms.ms_android.base.BaseActivity
import com.hr.ms.ms_android.bean.TeacherBean
import com.hr.ms.ms_android.bean.YearPlanBean
import kotlinx.android.synthetic.main.overview_activity.*
import kotlinx.android.synthetic.main.toolbar_layout.*
import java.util.*

/**
 * Created by Dino on 2018/4/3.
 */
class OverviewActivity : BaseActivity() {
    override fun getPresenter(): MvpPresenter? {        return null     }

    private lateinit var adapter:OverViewAdapter

    override fun setViewId(): Int {
        return R.layout.overview_activity
    }

    override fun initData() {
        toolbar.addOnBackListener { onBackPressed() }
        toolbar.setStatusBarFontDark(this)
        toolbar.setTitleContent("概览")


        var beans = ArrayList<TeacherBean>()
        for(i in 0 until 10){
            var bean = TeacherBean()
            bean.name = "肖南方"
            bean.intro = "简介"
            bean.type = "专家/顾问"
            bean.mobile = "13400000000"
            bean.territory = "战略咨询"
            bean.talkType = "待恰谈"
            bean.fee = "¥50,000"
            bean.talkDate = "2018-01-20 17:38"
            beans.add(bean)
        }
        val marqueeFactory = IndexNoticeHolderView(this)
        marqueeFactory.setData(beans)
        marqueeView.setMarqueeFactory(marqueeFactory)
        marqueeView.startFlipping()


        chart.setUsePercentValues(true)
        chart.description.isEnabled = false
        chart.setExtraOffsets(5f, 10f, 5f, 5f)
        chart.dragDecelerationFrictionCoef = 0.95f
        chart.centerText = "师资类别分布"
        chart.isDrawHoleEnabled = true
        chart.setHoleColor(Color.WHITE)
        chart.setTransparentCircleColor(Color.WHITE)
        chart.setTransparentCircleAlpha(110)
        chart.holeRadius = 58f
        chart.transparentCircleRadius = 61f
        chart.setDrawCenterText(true)
        chart.rotationAngle = 0f
        chart.isRotationEnabled = true
        chart.isHighlightPerTapEnabled = true
        chart.animateY(1400, Easing.EasingOption.EaseInOutQuad)
        val l2 = chart.legend
        l2.verticalAlignment = Legend.LegendVerticalAlignment.TOP
        l2.horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
        l2.orientation = Legend.LegendOrientation.VERTICAL
        l2.setDrawInside(false)
        l2.xEntrySpace = 7f
        l2.yEntrySpace = 0f
        l2.yOffset = 0f
        // entry label styling
        chart.setEntryLabelColor(Color.WHITE)
        chart.setEntryLabelTextSize(12f)

        setData()

        adapter = OverViewAdapter(recyclerView)
        recyclerView.adapter = adapter

        var yearbeans = ArrayList<YearPlanBean>()
        for (i in 1 .. 12){
            var bean = YearPlanBean()
            bean.month = i.toString()+"月"
            bean.location = "中山"
            bean.name = "活动"+i.toString()
            bean.date = "1月09日-10日"
            bean.persons = "梁柏强，梁柏强，梁柏强，梁柏强，梁柏强，梁柏强，梁柏强，梁柏强，梁柏强，梁柏强，梁柏强，梁柏强"
            yearbeans.add(bean)
        }

        adapter.showSinglePage(yearbeans)
    }

    private var mParties: Array<String> = arrayOf("教授/学者", "专家/顾问", "企业家/高管")
    private fun setData() {

//        val mult = range
        val entries = ArrayList<PieEntry>()
        var red = 0.2f
        var yellow = 0.5f
        var green = 0.3f
        entries.add(PieEntry(red, mParties[0]))
        entries.add(PieEntry(yellow, mParties[1]))
        entries.add(PieEntry(green, mParties[2]))
//        }

        val dataSet = PieDataSet(entries, "")

        dataSet.setDrawIcons(false)

        dataSet.sliceSpace = 3f
        dataSet.iconsOffset = MPPointF(0f, 40f)
        dataSet.selectionShift = 5f

        // add a lot of colors

        val colors = ArrayList<Int>()

        colors.add(resources.getColor(R.color.main_color))
        colors.add(resources.getColor(R.color.color_839EE2))
        colors.add(resources.getColor(R.color.color_FDE136))


        colors.add(ColorTemplate.getHoloBlue())

        dataSet.colors = colors
        //dataSet.setSelectionShift(0f);

        val data = PieData(dataSet)
        data.setValueFormatter(PercentFormatter())
        data.setValueTextSize(11f)
        data.setValueTextColor(Color.WHITE)
        chart.data = data

        // undo all highlights
        chart.highlightValues(null)

        chart.invalidate()

    }

    inner class IndexNoticeHolderView(mContext: Context) : MarqueeFactory<View, TeacherBean>(mContext) {
        private val inflater: LayoutInflater = LayoutInflater.from(mContext)

        override fun generateMarqueeItemView(data: TeacherBean): View {
            val view = inflater.inflate(R.layout.overview_marq_item, null)
            (view.findViewById<View>(R.id.tv_name) as TextView).text = data.name
            (view.findViewById<View>(R.id.tv_mobile) as TextView).text = data.mobile
            (view.findViewById<View>(R.id.tv_territory) as TextView).text = data.territory
            (view.findViewById<View>(R.id.tv_date) as TextView).text = data.talkDate
            (view.findViewById<View>(R.id.tv_fee) as TextView).text = data.fee
            return view
        }
    }

}