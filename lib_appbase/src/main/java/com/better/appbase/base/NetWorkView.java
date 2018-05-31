package com.better.appbase.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.better.appbase.utils.NetworkUtils;


public abstract class NetWorkView extends FrameLayout {
    abstract protected void notConnectNet();

    private Context context;
    private NetWorkStateReceiver mReceiver;

    public NetWorkView(Context context) {
        super(context);
        startNetReceiver(context);
    }

    public NetWorkView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        startNetReceiver(context);
    }

    public NetWorkView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        startNetReceiver(context);
    }

    //动态启动网络监听广播
    private void startNetReceiver(Context context) {
        this.context = context;
        IntentFilter mFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        mReceiver = new NetWorkStateReceiver();
        context.registerReceiver(mReceiver, mFilter);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mReceiver != null) {
            context.unregisterReceiver(mReceiver);
        }
    }

    public class NetWorkStateReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (!NetworkUtils.isConnected(context)) {
                notConnectNet();
            }
        }
    }
}
