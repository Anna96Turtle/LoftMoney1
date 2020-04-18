package com.loftblog.loftmoney;

import android.app.Application;

import com.loftblog.loftmoney.screens.web.AuthRequest;
import com.loftblog.loftmoney.screens.web.GetApi;
import com.loftblog.loftmoney.screens.web.PostApi;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoftApp extends Application {

    private Retrofit retrofit;

    @Override
    public void onCreate() {
        super.onCreate();

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

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

    public GetApi api() { return retrofit.create(GetApi.class); }
    public AuthRequest getAuthRequest() { return retrofit.create(AuthRequest.class); }
    public PostApi postApi() { return retrofit.create(PostApi.class); }
}
