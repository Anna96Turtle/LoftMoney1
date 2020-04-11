package com.loftblog.loftmoney.skreens.web;

import com.loftblog.loftmoney.Status;
import com.loftblog.loftmoney.skreens.main.adapter.Item;
import com.loftblog.loftmoney.skreens.web.models.ItemRemote;

import java.util.List;

import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Api {
    @GET ("./items")
    Single<List<ItemRemote>> request(@Query("type") String string, @Query("auth-token") String authToken);
    Call<List<Item>> getItems(String string, String token);

    @GET("./auth")
    Call<Status> auth(@Query("social_user_id") String userId);


}
