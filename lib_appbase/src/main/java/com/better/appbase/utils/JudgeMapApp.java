package com.better.appbase.utils;

/**
 * Created by Administrator on 2017/8/19.
 */

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.text.TextUtils;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by liyongzhao on 2017/4/20.
 */

public class JudgeMapApp {

    /**
     * 判断 手机上 是否 安装了 某个应用
     * @param context
     * @param packageName
     * @return
     */
    public static boolean isAvilible(Context context, String packageName){
        //获取packagemanager
        final PackageManager packageManager = context.getPackageManager();
        //获取所有已安装程序的包信息
        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
        //用于存储所有已安装程序的包名
        List<String> packageNames = new ArrayList<String>();
        //从pinfo中将包名字逐一取出，压入pName list中
        if(packageInfos != null){
            for(int i = 0; i < packageInfos.size(); i++){
                String packName = packageInfos.get(i).packageName;
                packageNames.add(packName);
            }
        }
        //判断packageNames中是否有目标程序的包名，有TRUE，没有FALSE
        return packageNames.contains(packageName);
    }
    //跳转到高德地图
    public static  void jumpGaodeMap(Context context , String lat , String lon ){
        Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("androidamap://navi?sourceApplication=师兄在线&lat="+lat+"&lon="+lon+"&dev=0"));
        intent.setPackage("com.autonavi.minimap");
        context.startActivity(intent);

    }
    //跳转百度地图
    public static void jumpBaiduMap(Context context,String address)
    {
        Intent i1 = new Intent();
        i1.setData(Uri.parse("baidumap://map/geocoder?src=openApiDemo&address="+address));
        context.startActivity(i1);
    }
}
