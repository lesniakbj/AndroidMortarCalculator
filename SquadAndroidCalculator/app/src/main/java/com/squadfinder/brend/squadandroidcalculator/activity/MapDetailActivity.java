package com.squadfinder.brend.squadandroidcalculator.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.squadfinder.brend.squadandroidcalculator.R;
import com.squadfinder.brend.squadandroidcalculator.application.MortarCalculatorApplication;
import com.squadfinder.brend.squadandroidcalculator.domain.SquadMap;
import com.squadfinder.brend.squadandroidcalculator.listener.ImageGestureDetector;
import com.squadfinder.brend.squadandroidcalculator.listener.ImageTouchListener;
import com.squadfinder.brend.squadandroidcalculator.view.OuterHorizontalScrollView;


/**
 * Created by brend on 3/6/2018.
 */

public class MapDetailActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.map_detail_layout);

        // Load the map
        MortarCalculatorApplication app = (MortarCalculatorApplication) getApplication();
        SquadMap loadedMap = app.getCurrentMap();

        // Load the Map String Data
        TextView mapName = findViewById(R.id.mapDetailMapName);
        TextView mapDesc = findViewById(R.id.mapDetailMapDescription);
        TextView mapDimensions = findViewById(R.id.mapDetailMapDimensions);
        mapName.setText(loadedMap.getMapName());
        mapDesc.setText(loadedMap.getMapDescription());
        mapDimensions.setText(loadedMap.getDimensionString());

        // Load the Map Image View
        ImageView imageView = findViewById(R.id.mapImageView);

        // Load the Image
        Glide.with(this).load(loadedMap.getMapImageResourceId(this))
                .apply(new RequestOptions().override(MortarCalculatorApplication.getMarkImageWidth(), MortarCalculatorApplication.getMarkImageHeight()))
                .into(imageView);

        // Setup detectors needed locally and for chaining
        GestureDetector.OnGestureListener gListener = new ImageGestureDetector(this, imageView);
        GestureDetector gDetector = new GestureDetector(this, gListener);
        View.OnTouchListener tListener = new ImageTouchListener(gDetector);
        imageView.setOnTouchListener(tListener);

        // Setup the Scroll View for the image
        OuterHorizontalScrollView hScrollView = findViewById(R.id.mapHorizontalScroll);
        ScrollView vScrollView = findViewById(R.id.mapVerticalScroll);
        hScrollView.setScrollView(vScrollView);
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

    @Override
    public void onBackPressed() {
        // Clear saved points if we back out of the activity
        MortarCalculatorApplication app = (MortarCalculatorApplication) getApplication();
        app.clear();
        finish();
    }

    public enum MarkPointState {
        CREATE,
        EDIT;
    }
}
