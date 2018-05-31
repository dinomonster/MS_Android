package com.hr.ms.ms_android.network;


import com.hr.ms.ms_android.constants.UrlConstants;
import com.hr.ms.ms_android.network.api.ServiceDataApi;
import com.socks.library.KLog;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Dino on 4/17 0017.
 */

public class NetWork {
    private static Converter.Factory gsonConverterFactory = GsonConverterFactory.create();
    private static RxJava2CallAdapterFactory rxJavaCallAdapterFactory = RxJava2CallAdapterFactory.create();
    private static final int DEFAULT_TIMEOUT = 30;
    private static ServiceDataApi serviceDataApi;


    private static OkHttpClient getOkHttpClient() {

        //新建log拦截器
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                //打印retrofit日志
                KLog.e("RetrofitLog", "OkHttp = " + message);
            }
        });

        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        //定制OkHttp
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(new AddQueryMapInterceptor())
                .addInterceptor(new TokenInterceptor())
                .addInterceptor(loggingInterceptor)
                .build();
        return client;
    }


    public static ServiceDataApi getServiceDataApi() {
        Retrofit retrofit = new Retrofit.Builder()
                .client(getOkHttpClient())
                .baseUrl(UrlConstants.DOMAIN)
                .addConverterFactory(gsonConverterFactory)
                .addCallAdapterFactory(rxJavaCallAdapterFactory)
                .build();
        serviceDataApi = retrofit.create(ServiceDataApi.class);
        return serviceDataApi;
    }


    private static OkHttpClient getDownloadOkHttpClient(final ProgressResponseBody.ProgressListener progressListener) {
        Interceptor interceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Response originalResponse = chain.proceed(chain.request());
                return originalResponse.newBuilder()
                        .body(new ProgressResponseBody(originalResponse.body(), progressListener))
                        .build();
            }
        };

        OkHttpClient downloadOkHttpClient = new OkHttpClient.Builder()
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .build();
        return downloadOkHttpClient;
    }

}
