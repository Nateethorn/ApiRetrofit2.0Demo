package com.example.toshiba.youtubedemo;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

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
