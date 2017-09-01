package com.example.toshiba.youtubedemo;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.List;

class ApiVolleyModel implements ApiVolleyInterface{
    private Context mContext;

    ApiVolleyModel(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public RequestQueue createVolleyConnection() {
        return Volley.newRequestQueue(mContext);
    }
}
