package com.hr.ms.ms_android.base;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.better.appbase.intef.BaseViewInterface;
import com.better.appbase.view.empty.BetterEmptyView;
import com.hr.ms.ms_android.R;
import com.socks.library.KLog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * 赖加载fragment
 */

public abstract class BaseLaiLoadingFragment extends Fragment implements BaseViewInterface {

    @BindView(R.id.base_fragment_empty_layout)
    BetterEmptyView baseFragmentEmptyLayout;
    private FrameLayout baseFragmentContainerLayout;

    //是否第一次运行
    protected boolean mIsFirst = true;
    //View是否显示
    private boolean mIsVisible = false;
    //是否准备好加载数据
    private boolean mIsPrepared = false;
    //handler.what->隐藏加载动画
    private final int HIDE_LOADING = 1002;

    protected boolean showLoading = true;

    protected abstract int getLayoutId();

    @SuppressLint("HandlerLeak")
    private Handler myHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case HIDE_LOADING:
                    try {
                        baseFragmentEmptyLayout.hide();
                        baseFragmentContainerLayout.animate().alpha(1).start();
                    } catch (Exception e) {
                        KLog.e(e.getMessage());
                    }
                    break;
            }
            super.handleMessage(msg);
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_base_layout, null);

        View view = inflater.inflate(getLayoutId(), null);
        baseFragmentContainerLayout = rootView.findViewById(R.id.base_fragment_container_layout);
        baseFragmentContainerLayout.addView(view);

        ButterKnife.bind(this, rootView);

        if (!isShowLoading()){
            baseFragmentEmptyLayout.hide();
            baseFragmentContainerLayout.animate().alpha(1).start();
        }

        //初始化数据
        mIsFirst = true;
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mIsPrepared = true;
        loadData();
    }

    /**
     * 在这里实现Fragment数据的缓加载.
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            mIsVisible = true;
            onVisible();
        } else if (!getUserVisibleHint()) {
            mIsVisible = false;
            onInvisible();
        }
    }

    //结束页面展示的加载动画
    protected void stopLoadingAnima() {
        myHandler.sendEmptyMessage(HIDE_LOADING);
    }

    //结束页面展示的加载动画
    protected void stopLoadingAnima(int delay) {
        myHandler.sendEmptyMessageDelayed(HIDE_LOADING, delay);
    }

    /**
     * 显示时加载数据
     */
    protected void onVisible() {
        loadData();
    }

    protected void loadData() {
        if (!mIsPrepared || !mIsVisible || !mIsFirst) {
            return;
        }

        initRecyclerView();

        init();

        mIsFirst = false;
    }

    public boolean isShowLoading() {
        return showLoading;
    }

    public void setShowLoading(boolean showLoading) {
        this.showLoading = showLoading;
    }

    /**
     * 不显示时调用
     */
    protected void onInvisible() {
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EventNull eventNull) {
    }
}
