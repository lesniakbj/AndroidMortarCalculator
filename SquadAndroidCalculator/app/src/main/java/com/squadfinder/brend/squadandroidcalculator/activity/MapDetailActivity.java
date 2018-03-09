package com.squadfinder.brend.squadandroidcalculator.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squadfinder.brend.squadandroidcalculator.R;
import com.squadfinder.brend.squadandroidcalculator.domain.SquadMap;
import com.squadfinder.brend.squadandroidcalculator.listener.ImageGestureDetector;
import com.squadfinder.brend.squadandroidcalculator.listener.ImageTouchListener;


/**
 * Created by brend on 3/6/2018.
 */

public class MapDetailActivity extends Activity {

    @SuppressLint({"SetTextI18n", "ClickableViewAccessibility"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.map_detail_layout);

        // Make sure we loaded this activity correctly
        SquadMap loadedMap = (SquadMap)getIntent().getSerializableExtra("map");
        if(loadedMap == null) {
            Log.e("ACTIVITY", "Error switching to Map Detail View");
            finish();
            return;
        }

        // Load the Map String Data
        TextView mapName = findViewById(R.id.mapDetailMapName);
        TextView mapDesc = findViewById(R.id.mapDetailMapDescription);
        TextView mapDimensions = findViewById(R.id.mapDetailMapDimensions);
        mapName.setText(loadedMap.getMapName());
        mapDesc.setText(loadedMap.getMapDescription());
        mapDimensions.setText("Dimensions: " + loadedMap.getDimensionString());

        // Load the Map Image View
        ImageView imageView = findViewById(R.id.mapImageView);

        // Load the Image
        Glide.with(this).load(loadedMap.getMapImageResourceId(this)).into(imageView);

        // Setup detectors needed locally and for chaining
        imageView.setOnTouchListener(new ImageTouchListener(new GestureDetector(this, new ImageGestureDetector(this, imageView))));
        ScrollView vScrollView = findViewById(R.id.mapVerticalScroll);
        HorizontalScrollView hScrollView = findViewById(R.id.mapHorizontalScroll);
        vScrollView.requestDisallowInterceptTouchEvent(true);
        hScrollView.requestDisallowInterceptTouchEvent(true);

        // Setup our button presses
        Button editPointsButton = findViewById(R.id.editPointsButton);
        Button assignTargetsButton = findViewById(R.id.assignTargetsButton);
        editPointsButton.setOnClickListener(v -> {
            Log.d("ACTIVITY", "Start new intent for Edit");
            Intent editIntent = new Intent(this, EditPointsActivity.class);
            this.startActivity(editIntent);
        });
        assignTargetsButton.setOnClickListener(v -> {
            Log.d("ACTIVITY", "Start new intent for Assign");
            Intent assignIntent = new Intent(this, AssignTargetsActivity.class);
            this.startActivity(assignIntent);
        });
    }
}
