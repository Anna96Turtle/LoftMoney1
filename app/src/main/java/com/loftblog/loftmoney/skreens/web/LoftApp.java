package com.loftblog.loftmoney.skreens.web;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoftApp {

    public static LoftApp instance = null;
    public static LoftApp getInstance(){
        if (instance == null){
            instance = new LoftApp();
        }
        return instance;
    }

    private Retrofit retrofit;
    private OkHttpClient okHttpClient;

    private LoftApp(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);

        okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();

        Retrofit.Builder builder = new Retrofit.Builder();
        builder.client(okHttpClient);
        builder.baseUrl("http://verdant-violet.glithch.me/");
        builder.addConverterFactory(GsonConverterFactory.create());
        builder.addCallAdapterFactory(RxJava2CallAdapterFactory.create());
        retrofit = builder
                .build();
    }

    public Api api(){
        return retrofit.create(Api.class);
    }

    public PostApi postApi(){
        return retrofit.create(PostApi.class);
    }
}
