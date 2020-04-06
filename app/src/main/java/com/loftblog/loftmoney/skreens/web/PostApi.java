package com.loftblog.loftmoney.skreens.web;

import com.loftblog.loftmoney.Status;
import com.loftblog.loftmoney.skreens.main.adapter.Item;

import io.reactivex.Completable;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface PostApi {
    @POST("items/add")
    Call<Status> addItem(@Body Item request, @Query("auth-token") String token);

    @FormUrlEncoded
    Completable requect(@Field("price") Integer price, @Field("name") String name,
                        @Field("type") String type);

}
