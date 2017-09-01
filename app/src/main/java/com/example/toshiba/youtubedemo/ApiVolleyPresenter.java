package com.example.toshiba.youtubedemo;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.List;

class ApiVolleyPresenter {
    private ApiVolleyModel mApiVolleyModel;
    private ApiViewVolleyInterface mApiViewVolleyInterface;
    private String mUrl;

    ApiVolleyPresenter(ApiVolleyModel mApiVolleyModel, String mUrl) {
        this.mApiVolleyModel = mApiVolleyModel;
        this.mUrl = mUrl;
    }

    void bind(ApiViewVolleyInterface apiViewVolleyInterface){ this.mApiViewVolleyInterface = apiViewVolleyInterface; }

    void unbind(){ mApiViewVolleyInterface = null; }

    void displayAllResult() {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, mUrl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                List<VideoClip> clips = getClips(response);
                mApiViewVolleyInterface.updateUi(clips);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mApiViewVolleyInterface.updateErrorResponse(error);
            }
        });
        mApiVolleyModel.createVolleyConnection().add(jsonObjectRequest);
    }

    private List<VideoClip> getClips(JSONObject jsonObject){
        Gson gson = new Gson();
        Youtube youtube = gson.fromJson(String.valueOf(jsonObject),Youtube.class);
        return youtube.getClips();
    }
}
