package com.loftblog.loftmoney.skreens.web;

import io.reactivex.Completable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface PostApi {
    @POST("./item/add")
    @FormUrlEncoded
    Completable requect(@Field("price") Integer price, @Field("name") String name,
                        @Field("type") String type);

}
