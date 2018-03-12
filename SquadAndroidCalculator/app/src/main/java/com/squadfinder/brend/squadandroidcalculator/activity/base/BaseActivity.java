package com.squadfinder.brend.squadandroidcalculator.activity.base;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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

    public AlertDialog buildAlertDialog(int titleResource, int bodyResource, int neutralButtonResource) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, android.R.style.Theme_Holo_Dialog);
        return builder.setTitle(titleResource)
                .setMessage(bodyResource)
                .setCancelable(true)
                .setNeutralButton(neutralButtonResource, null)
                .create();
    }

    public ListView addListView(int listViewResource, ArrayAdapter<?> adapter, AdapterView.OnItemClickListener clickListener) {
        ListView listView = this.findViewById(listViewResource);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(clickListener);
        return listView;
    }

    public void simpleGetWebJSON(String url, Response.Listener<JSONObject> responseListener, Response.ErrorListener errorListener) {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, responseListener, errorListener);
        simpleRequestQueue.add(request);
    }

    public void simplePostWebJSON(String url, JSONObject json, Response.Listener<JSONObject> responseListener, Response.ErrorListener errorListener) {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, json, responseListener, errorListener);
        simpleRequestQueue.add(request);
    }

    /**
     * EXAMPLE
     *
     *
     *
     *          simpleGetWebJSON("", (JSONObject json) -> {
                    Log.d("JSON_REQUEST", String.format("Got JSON: %s", json.toString()));
                    addScrollViewContent(this, new Gson().fromJson(json.toString(), new TypeToken<List<SquadMap>>(){}.getType()));
                    pDialog.dismiss();
                }, (VolleyError error) -> {
                    Log.d("JSON_REQUEST", String.format("Error: %s", error.toString()));
                    String json = RawResourceLoader.readRawResourceAsString(getResources().openRawResource(R.raw.maps_default));
                    addScrollViewContent(this, new Gson().fromJson(json, new TypeToken<List<SquadMap>>(){}.getType()));
                    pDialog.dismiss();
                });
     */
}
