package com.example.toshiba.youtubedemo;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

class ApiTreeHouseModel implements ApiTreeHouseRetrofitInterface{

    @Override
    public Call<Blog> getPosts() {
        return createInterface().getPosts();
    }

    private ApiTreeHouseRetrofitInterface createInterface(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://blog.teamtreehouse.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(ApiTreeHouseRetrofitInterface.class);
    }
}
