package com.example.toshiba.youtubedemo;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

class ApiVolleyModel implements ApiVolleyInterface{
    private Context ctx;

    ApiVolleyModel(Context ctx) {
        this.ctx = ctx;
    }

    @Override
    public RequestQueue createVolleyConnection() { return Volley.newRequestQueue(ctx); }
}
