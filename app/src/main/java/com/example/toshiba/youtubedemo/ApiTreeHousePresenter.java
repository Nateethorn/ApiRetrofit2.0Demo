package com.example.toshiba.youtubedemo;

import android.content.Context;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class ApiTreeHousePresenter {
    ApiViewTreeHouseInterface apiViewTreeHouseInterface;
    ApiTreeHouseModel apiTreeHouseModel;
    Context context;

    ApiTreeHousePresenter(Context context) {
        this.context = context;
    }

    void bindView(ApiViewTreeHouseInterface apiViewTreeHouseInterface){
        this.apiViewTreeHouseInterface = apiViewTreeHouseInterface;
    }

    public void setApiTreeHouseModel(ApiTreeHouseModel apiTreeHouseModel) {
        this.apiTreeHouseModel = apiTreeHouseModel;
    }

    void unbindView(){
        apiViewTreeHouseInterface = null;
    }

    void getData(){
        apiTreeHouseModel.getPosts().enqueue(new Callback<Blog>() {
            @Override
            public void onResponse(@NonNull Call<Blog> call, @NonNull Response<Blog> response) {
                if (response.isSuccessful()) {
//                    apiViewTreeHouseInterface.displayHeadData(getHeadPosts(getPosts(response.body())));
                    apiViewTreeHouseInterface.displayAllData(getPosts(response.body()));
                } else {
                    apiViewTreeHouseInterface.displayUnSuccessResponse(response.errorBody());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Blog> call, @NonNull Throwable t) {
                apiViewTreeHouseInterface.displayFailedPostData(t);
            }
        });
    }

    private List<String> getHeadPosts(List<Post> list){
        List<String> head = new ArrayList<>();
        for(int i=0;i<list.size();i++){
            head.add(list.get(i).getTitle());
        }
        return head;
    }

    private List<Post> getPosts(Blog blog){
        return blog.getPosts();
    }
}
