package com.example.toshiba.youtubedemo;


import okhttp3.ResponseBody;

interface ApiViewRetrofitInterface {
    void updateUi(Youtube youtube);
    void updateUnSuccessResponse(ResponseBody responseBody);
    void updateFalseResponse(Throwable t);
}
