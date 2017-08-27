package com.example.toshiba.youtubedemo;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface ApiRetrofitInterface {
    @GET("index_new.php")
    Call<Youtube> getYoutube(@Query("username") String username,@Query("password") String password,@Query("type") String type);
//    @GET
//    Call<Youtube> getYoutube(@Url String url);
}
