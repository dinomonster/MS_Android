package com.better.appbase.base;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.better.appbase.utils.NetworkUtils;


public abstract class NetWorkService extends Service {

    private NetWorkStateReceiver mReceiver;

    @Override
    public void onCreate() {
        super.onCreate();
        startNetReceiver(this);
    }

    //动态启动网络监听广播
    private void startNetReceiver(Context context) {
        IntentFilter mFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        mReceiver = new NetWorkStateReceiver();
        context.registerReceiver(mReceiver, mFilter);
    }

    @Override
    public void onDestroy() {
        if (mReceiver != null) {
            unregisterReceiver(mReceiver);
        }
        super.onDestroy();
    }

    abstract protected void notConnectNet();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
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
