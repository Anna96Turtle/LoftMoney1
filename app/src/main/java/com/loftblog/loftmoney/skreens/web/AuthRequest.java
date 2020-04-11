package com.loftblog.loftmoney.skreens.web;

import com.loftblog.loftmoney.skreens.web.models.AuthResponse;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface AuthRequest{
    @GET("./auth")
    Single<AuthResponse> request(@Query("social_user_id") String userId);
}
