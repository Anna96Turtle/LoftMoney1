package com.loftblog.loftmoney.skreens.web;

import android.app.Application;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoftApp extends Application {

    public static LoftApp instance = null;

    public static LoftApp getInstance() {
        if (instance == null) {
            instance = new LoftApp();
        }
        return instance;
    }

    private Retrofit retrofit;

    @Override
    public void onCreate() {
        super.onCreate();

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();

        String baseUrl = "https://loftschool.com/android-api/basic/v1/";
        retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    public Api api() { return retrofit.create(Api.class); }
    public AuthRequest getAuthRequest() { return retrofit.create(AuthRequest.class); }
    public PostApi postApi() { return retrofit.create(PostApi.class); }
}
