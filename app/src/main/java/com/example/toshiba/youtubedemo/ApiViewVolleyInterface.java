package com.example.toshiba.youtubedemo;

import com.android.volley.VolleyError;

import org.json.JSONObject;

import java.util.List;

interface ApiViewVolleyInterface {
    void updateUi(List<VideoClip> clips);
    void updateErrorResponse(VolleyError volleyError);
}
