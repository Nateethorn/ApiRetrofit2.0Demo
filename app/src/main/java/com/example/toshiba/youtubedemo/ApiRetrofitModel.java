package com.example.toshiba.youtubedemo;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Query;

class ApiRetrofitModel implements ApiRetrofitInterface {

    private ApiRetrofitInterface createInterface(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://codemobiles.com/adhoc/youtubes/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(ApiRetrofitInterface.class);
    }

    @Override
    public Call<Youtube> getYoutube(@Query("username") String username, @Query("password") String password, @Query("type") String type) {
        return createInterface().getYoutube(username, password, type);
    }
}
