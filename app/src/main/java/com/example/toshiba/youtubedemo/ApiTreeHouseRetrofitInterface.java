package com.example.toshiba.youtubedemo;

import retrofit2.Call;
import retrofit2.http.GET;

interface ApiTreeHouseRetrofitInterface {
    @GET("api/get_recent_summary/")
    Call<Blog> getPosts();
}
