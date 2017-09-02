package com.example.toshiba.youtubedemo;

import android.support.annotation.NonNull;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class ApiRetrofitPresenter {
    private ApiRetrofitModel mApiModel;
    private ApiViewRetrofitInterface mApiViewRetrofitInterface;

    ApiRetrofitPresenter(ApiRetrofitModel mApiModel) {
        this.mApiModel = mApiModel;
    }

    void bindView(ApiViewRetrofitInterface apiViewRetrofitInterface) {
        this.mApiViewRetrofitInterface = apiViewRetrofitInterface;
    }

    void unbindView(){
        mApiViewRetrofitInterface = null;
    }

    void displayResult() {
        mApiModel.getYoutube("admin","password","foods")
                .enqueue(new Callback<Youtube>() {
                    @Override
                    public void onResponse(@NonNull Call<Youtube> call, @NonNull Response<Youtube> response) {
                        if (response.isSuccessful()) {
                            mApiViewRetrofitInterface.updateUi(getClips(response.body()));
                        } else {
                            mApiViewRetrofitInterface.updateUnSuccessResponse(response.errorBody());
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<Youtube> call, @NonNull Throwable t) {
                        mApiViewRetrofitInterface.updateFalseResponse(t);
                    }
                });
    }

    private List<VideoClip> getClips(Youtube youtube){
        return youtube.getClips();
    }
}
