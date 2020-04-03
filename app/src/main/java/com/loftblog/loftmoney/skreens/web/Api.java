package com.loftblog.loftmoney.skreens.web;

import com.loftblog.loftmoney.skreens.web.models.GetItemsResponseModel;

import io.reactivex.Single;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Api {
    @GET ("./items")
    Single<GetItemsResponseModel> request(@Query("Type") String string);
}
