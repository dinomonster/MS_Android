package com.hr.ms.ms_android.network;

import android.content.Intent;

import com.google.gson.Gson;
import com.hr.ms.ms_android.AppConfig;
import com.hr.ms.ms_android.base.AppManager;
import com.hr.ms.ms_android.bean.BaseResponseReturnValue;
import com.hr.ms.ms_android.constants.CommonConstants;
import com.hr.ms.ms_android.data.AccountHelper;
import com.hr.ms.ms_android.mvp.login.LoginActivity;

import java.io.IOException;
import java.nio.charset.Charset;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

/**
 * Created by Dino on 2017/11/16.
 */

public class TokenInterceptor implements Interceptor {
    private static final Charset UTF8 = Charset.forName("UTF-8");
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        // try the request
        Response originalResponse = chain.proceed(request);

        /**通过如下的办法曲线取到请求完成的数据
         *
         * 原本想通过  originalResponse.body().string()
         * 去取到请求完成的数据,但是一直报错,不知道是okhttp的bug还是操作不当
         *
         * 然后去看了okhttp的HttpLoggingInterceptor源码,找到了这个曲线方法,取到请求完成的数据后,根据特定的判断条件去判断token过期
         */
        ResponseBody responseBody = originalResponse.body();
        BufferedSource source = responseBody.source();
        source.request(Long.MAX_VALUE); // Buffer the entire body.
        Buffer buffer = source.buffer();
        Charset charset = UTF8;
        MediaType contentType = responseBody.contentType();
        if (contentType != null) {
            charset = contentType.charset(UTF8);
        }

        String bodyString = buffer.clone().readString(charset);

        /***************************************/

        BaseResponseReturnValue returnValue = new Gson().fromJson(bodyString, BaseResponseReturnValue.class);
        if(999 == returnValue.getCode()){
            originalResponse.body().close();
            AccountHelper.logout();
//            SPUtils.putBoolean(AppKey.IS_AUTO_LOGIN,false);
            AppManager.getAppManager().finishAllActivity();
            Intent intent = new Intent(AppConfig.getInstance(),LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra(CommonConstants.MSG,"登录超时,请重新登录！");
            AppConfig.getInstance().getApplicationContext().startActivity(intent);
        }

        // otherwise just pass the original response on
        return originalResponse;
    }
}
