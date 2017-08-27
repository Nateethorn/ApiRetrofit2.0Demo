package com.example.toshiba.youtubedemo;

import android.support.annotation.NonNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class ApiRetrofitPresenter {
    private ApiRetrofitModel apiModel;
    private ApiViewRetrofitInterface apiViewRetrofitInterface;

    ApiRetrofitPresenter(ApiRetrofitModel apiModel) {
        this.apiModel = apiModel;
    }

    void bindView(ApiViewRetrofitInterface apiViewRetrofitInterface) {
        this.apiViewRetrofitInterface = apiViewRetrofitInterface;
    }

    void unbindView(){
        apiViewRetrofitInterface = null;
    }

    void displayResult() {
        apiModel.getYoutube("admin","password","foods")
                .enqueue(new Callback<Youtube>() {
                    @Override
                    public void onResponse(@NonNull Call<Youtube> call, @NonNull Response<Youtube> response) {
                        if (response.isSuccessful()){
                            apiViewRetrofitInterface.updateUi(response.body());
                        } else {
                            apiViewRetrofitInterface.updateUnSuccessResponse(response.errorBody());
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<Youtube> call, @NonNull Throwable t) {
                        apiViewRetrofitInterface.updateFalseResponse(t);
                    }
                });
    }
}
