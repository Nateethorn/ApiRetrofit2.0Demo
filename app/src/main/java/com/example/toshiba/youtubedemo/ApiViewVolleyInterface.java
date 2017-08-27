package com.example.toshiba.youtubedemo;

import com.android.volley.VolleyError;

import org.json.JSONObject;

/**
 * Created by Toshiba on 27/08/2017.
 */

interface ApiViewVolleyInterface {
    void updateUi(JSONObject jsonObject);
    void updateErrorResponse(VolleyError volleyError);
}
