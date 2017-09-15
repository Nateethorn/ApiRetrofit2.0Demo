package com.example.toshiba.youtubedemo;

import java.util.List;

import okhttp3.ResponseBody;

interface ApiViewTreeHouseInterface {
//    void displayHeadData(List<String> head);
    void displayAllData(List<Post> posts);
    void displayUnSuccessResponse(ResponseBody responseBody);
    void displayFailedPostData(Throwable t);
}
