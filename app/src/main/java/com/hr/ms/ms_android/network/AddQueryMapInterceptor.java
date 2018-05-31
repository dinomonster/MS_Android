package com.hr.ms.ms_android.network;

import com.hr.ms.ms_android.data.AccountHelper;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Dino on 2017/11/16.
 */

public class AddQueryMapInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();
        HttpUrl originalHttpUrl = originalRequest.url();
        HttpUrl.Builder builder = originalHttpUrl.newBuilder();
//        HttpUrl url = originalHttpUrl.newBuilder()
//                .addQueryParameter("token", AccountHelper.getToken())
//                .build();
        if(!originalHttpUrl.url().toString().contains("api/login/n/apiLogin")){
            builder.addQueryParameter("token", AccountHelper.getToken());
        }
        Request request = originalRequest.newBuilder()
                .url(builder.build())
                .method(originalRequest.method(), originalRequest.body())
                .build();

        return chain.proceed(request);
    }
}
