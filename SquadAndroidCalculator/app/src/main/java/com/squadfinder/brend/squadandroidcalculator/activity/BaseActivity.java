package com.squadfinder.brend.squadandroidcalculator.activity;

import android.app.Activity;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

/**
 * Created by brend on 3/7/2018.
 */

public abstract class BaseActivity extends Activity {
    public RequestQueue simpleRequestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        simpleRequestQueue = Volley.newRequestQueue(this);
    }

    public void simpleGetWebJSON(String url, Response.Listener<JSONObject> responseListener, Response.ErrorListener errorListener) {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, responseListener, errorListener);
        simpleRequestQueue.add(request);
    }

    public void simplePostWebJSON(String url, JSONObject json, Response.Listener<JSONObject> responseListener, Response.ErrorListener errorListener) {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, json, responseListener, errorListener);
        simpleRequestQueue.add(request);
    }
}
