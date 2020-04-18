package com.loftblog.loftmoney.screens.web;

import com.loftblog.loftmoney.Status;
import com.loftblog.loftmoney.common.money.adapter.MoneyModel;

import io.reactivex.Completable;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface PostApi {
    @POST("items/add")
    @FormUrlEncoded
    Completable requect(@Field("price") Integer price, @Field("name") String name,
                        @Field("type") String type,@Query("auth-token") String authToken);
    Call<Status> addItem(@Body MoneyModel request, @Query("auth-token") String authToken);
}
