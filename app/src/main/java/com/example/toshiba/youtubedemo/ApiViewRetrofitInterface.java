package com.example.toshiba.youtubedemo;


import java.util.List;

import okhttp3.ResponseBody;

interface ApiViewRetrofitInterface {
    void updateUi(List<VideoClip> clips);
    void updateUnSuccessResponse(ResponseBody responseBody);
    void updateFalseResponse(Throwable t);
}
