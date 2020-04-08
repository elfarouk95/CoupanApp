package com.coupanapp;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("t/CoupuanApi.php")
    Call<List<Model>> CoupuanApi();

    @GET("t/add.php")
    Call<Long> add(@Query("email") String email,
                                @Query("phone") String phone,
                                @Query("address") String address);

}
