package com.example.toshiba.youtubedemo;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import org.json.JSONObject;

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
                mApiViewVolleyInterface.updateUi(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mApiViewVolleyInterface.updateErrorResponse(error);
            }
        });
        mApiVolleyModel.createVolleyConnection().add(jsonObjectRequest);
    }
}
