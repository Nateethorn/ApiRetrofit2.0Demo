package com.example.toshiba.youtubedemo;

import retrofit2.Call;
import retrofit2.http.GET;

public interface TreeHouseApiInterface {
    @GET("api/get_recent_summary/")
    Call<Blog> getBlog();
}
