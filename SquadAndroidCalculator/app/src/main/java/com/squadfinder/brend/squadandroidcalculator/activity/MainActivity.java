package com.squadfinder.brend.squadandroidcalculator.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.squadfinder.brend.squadandroidcalculator.R;
import com.squadfinder.brend.squadandroidcalculator.adapter.ListViewMapAdapter;
import com.squadfinder.brend.squadandroidcalculator.domain.SquadMap;
import com.squadfinder.brend.squadandroidcalculator.listener.ListViewMapListener;
import com.squadfinder.brend.squadandroidcalculator.util.RawResourceLoader;

import org.json.JSONObject;

public class MainActivity extends BaseActivity {
    // Progress Dialog for user feedback of requests
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inflate the view so we can add dynamic elements
        View mainView = getLayoutInflater().inflate(R.layout.activity_main, null);

        // Load Map Data and set our View
        loadMapData(mainView);
        setContentView(mainView);

        // Setup our Help Button
        ImageView helpImage = mainView.findViewById(R.id.helpButton);
        helpImage.setOnTouchListener((View v, MotionEvent event) -> true);

        // Toast!
        Toast.makeText(this, "Welcome to Squad Finder's Mortar Calculator!", Toast.LENGTH_LONG).show();
    }

    @SuppressLint("ResourceType")
    private void addScrollViewContent(View view, SquadMap[] maps) {
        ArrayAdapter<SquadMap> mapAdapter = new ListViewMapAdapter(this, maps);
        ListView mapList = view.findViewById(R.id.mapListView);
        mapList.setAdapter(mapAdapter);
        mapList.setOnItemClickListener(new ListViewMapListener(this));
    }

    private void loadMapData(View view) {
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.show();
        simpleGetWebJSON("", (JSONObject json) -> {
            Log.d("JSON_REQUEST", String.format("Got JSON: %s", json.toString()));
            addScrollViewContent(view, new Gson().fromJson(json.toString(), SquadMap[].class));
            pDialog.dismiss();
        }, (VolleyError error) -> {
            Log.d("JSON_REQUEST", String.format("Error: %s", error.toString()));
            String json = RawResourceLoader.readRawResourceAsString(getResources().openRawResource(R.raw.maps_default));
            addScrollViewContent(view, new Gson().fromJson(json, SquadMap[].class));
            pDialog.dismiss();
        });
    }
}
