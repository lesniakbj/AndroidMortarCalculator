package com.squadfinder.brend.squadandroidcalculator.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Load Map Data
        ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage(getString(R.string.str_loading));
        pDialog.show();
        simpleGetWebJSON("", (JSONObject json) -> {
            Log.d("JSON_REQUEST", String.format("Got JSON: %s", json.toString()));
            addScrollViewContent(this, new Gson().fromJson(json.toString(), SquadMap[].class));
            pDialog.dismiss();
        }, (VolleyError error) -> {
            Log.d("JSON_REQUEST", String.format("Error: %s", error.toString()));
            String json = RawResourceLoader.readRawResourceAsString(getResources().openRawResource(R.raw.maps_default));
            addScrollViewContent(this, new Gson().fromJson(json, SquadMap[].class));
            pDialog.dismiss();
        });

        // Setup our Help Button
        ImageView helpImage = findViewById(R.id.helpButton);
        helpImage.setOnTouchListener((View v, MotionEvent event) -> {
            v.performClick();
            AlertDialog dialog = buildAlertDialog();
            dialog.show();
            return false;
        });

        // Welcome to the App
        Toast.makeText(this, R.string.str_welcome, Toast.LENGTH_LONG).show();
    }

    private AlertDialog buildAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        return builder.setTitle(R.string.str_help_title)
                .setMessage(R.string.str_help)
                .setCancelable(true)
                .setNeutralButton(R.string.str_ok, null)
                .create();
    }

    private void addScrollViewContent(Activity context, SquadMap[] maps) {
        ArrayAdapter<SquadMap> mapAdapter = new ListViewMapAdapter(this, maps);
        ListView mapList = context.findViewById(R.id.mapListView);
        mapList.setAdapter(mapAdapter);
        mapList.setOnItemClickListener(new ListViewMapListener(this));
    }
}
