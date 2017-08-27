package com.example.toshiba.youtubedemo;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import org.json.JSONObject;

class ApiVolleyPresenter {
    private ApiVolleyModel apiVolleyModel;
    private ApiViewVolleyInterface apiViewVolleyInterface;
    private JSONObject jsonObject;
    private VolleyError volleyError;
    private String url;

    public ApiVolleyPresenter(ApiVolleyModel apiVolleyModel, ApiViewVolleyInterface apiVolleyInterface) {
        this.apiVolleyModel = apiVolleyModel;
        this.apiViewVolleyInterface = apiVolleyInterface;
    }

    ApiVolleyPresenter(JSONObject jsonObject) { this.jsonObject = jsonObject; }

    ApiVolleyPresenter(VolleyError volleyError) { this.volleyError = volleyError; }

    ApiVolleyPresenter(ApiVolleyModel apiVolleyModel,String url) {
        this.apiVolleyModel = apiVolleyModel;
        this.url = url;
    }

    void bind(ApiViewVolleyInterface apiViewVolleyInterface){ this.apiViewVolleyInterface = apiViewVolleyInterface; }

    void unbind(){ apiViewVolleyInterface = null; }

    void displayAllResult() {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                apiViewVolleyInterface.updateUi(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                apiViewVolleyInterface.updateErrorResponse(error);
            }
        });
        apiVolleyModel.createVolleyConnection().add(jsonObjectRequest);
    }
}
