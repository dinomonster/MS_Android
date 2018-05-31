package com.hr.ms.ms_android.mvp.search

import android.app.Activity
import android.content.Intent
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import com.better.appbase.mvp.MvpPresenter
import com.hr.ms.ms_android.R
import com.hr.ms.ms_android.base.BaseActivity
import com.hr.ms.ms_android.constants.CommonConstants
import kotlinx.android.synthetic.main.search_activity.*


class SearchActivity : BaseActivity(), SearchContract.View {
    private lateinit var presenter: SearchPresenter

    private lateinit var type: String

    private lateinit var adapter: SearchAdapter

    override fun getPresenter(): MvpPresenter? {
        return presenter
    }

    override fun setViewId(): Int {
        return R.layout.search_activity
    }

    override fun initData() {
        window.setBackgroundDrawableResource(R.color.transparent_color)
        presenter = SearchPresenter(this)
        type = intent.getStringExtra(CommonConstants.TYPE)
        status_bar.setStatusBarDark(this)
        et_search_content.hint = intent.getStringExtra(CommonConstants.MSG)
        et_search_content.setText(intent.getStringExtra(CommonConstants.KEY))
        adapter = SearchAdapter(recyclerView)
        recyclerView.adapter = adapter
        adapter.setOnItemClickListener { adapter, view, position ->
            et_search_content.setText(adapter.data[position] as String)
            search()
        }
        adapter.setOnItemChildClickListener { adapter, view, position ->
            when (view.id) {
                R.id.x_ll -> {
                    presenter.removeSearchHistory(type,adapter.data[position] as String)
                }
            }
        }
        delete_ll.setOnClickListener { presenter.removeAllSearchHistory(type) }

        tv_cancel.setOnClickListener {
            var intent = Intent()
            intent.putExtra(CommonConstants.ID, "")
            setResult(Activity.RESULT_OK, intent)
            onBackPressed()
        }
        search_ll.setOnClickListener {
            search()
        }

        et_search_content.setOnEditorActionListener { textView, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEND
                    || actionId == EditorInfo.IME_ACTION_DONE
                    || (event != null && KeyEvent.KEYCODE_ENTER == event.keyCode && KeyEvent.ACTION_DOWN == event.action)) {
                search()
            }
            false
        }
    }

    private fun search(){
        presenter.saveSearchHistory(type,et_search_content.text.toString())
        var intent = Intent()
        intent.putExtra(CommonConstants.ID, et_search_content.text.toString())
        setResult(Activity.RESULT_OK, intent)
        onBackPressed()
    }

    override fun onResume() {
        super.onResume()
        presenter.getSearchHistory(type)
    }

    override fun refresh() {
        presenter.getSearchHistory(type)
    }

    override fun showSearchHistory(list: MutableList<String>?) {
        adapter.showMultiPage(list,1)
    }

    override fun onBackPressed() {
        supportFinishAfterTransition()
    }
}
