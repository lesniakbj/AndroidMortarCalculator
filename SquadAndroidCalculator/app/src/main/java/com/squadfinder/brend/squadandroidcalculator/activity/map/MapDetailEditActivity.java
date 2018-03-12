package com.squadfinder.brend.squadandroidcalculator.activity.map;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.GestureDetector;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.squadfinder.brend.squadandroidcalculator.R;
import com.squadfinder.brend.squadandroidcalculator.activity.base.BaseActivity;
import com.squadfinder.brend.squadandroidcalculator.application.MortarCalculatorApplication;
import com.squadfinder.brend.squadandroidcalculator.domain.SquadMap;
import com.squadfinder.brend.squadandroidcalculator.domain.calc.MarkPoint;
import com.squadfinder.brend.squadandroidcalculator.listener.ImageDelegatingTouchListener;
import com.squadfinder.brend.squadandroidcalculator.listener.image.SquadMapMarkingImageGestureDetector;
import com.squadfinder.brend.squadandroidcalculator.view.OuterHorizontalScrollView;
import com.squadfinder.brend.squadandroidcalculator.view.image.BaseClickableImageView;

/**
 * Created by brend on 3/10/2018.
 */

public class MapDetailEditActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.map_detail_edit_layout);

        // Load data
        MortarCalculatorApplication app = (MortarCalculatorApplication) getApplication();
        SquadMap loadedMap = app.getCurrentMap();
        MarkPoint editPoint = app.getMarkPointToEdit();

        // Load the Map String Data
        TextView mapName = findViewById(R.id.mapDetailMapName);
        TextView mapDesc = findViewById(R.id.mapDetailMapDescription);
        TextView mapDimensions = findViewById(R.id.mapDetailMapDimensions);
        mapName.setText(loadedMap.getMapName());
        mapDesc.setText(loadedMap.getMapDescription());
        mapDimensions.setText(loadedMap.getDimensionString());

        // Load the Map Image View
        BaseClickableImageView imageView = findViewById(R.id.mapImageView);
        app.circleMarkPoint(this, editPoint);
        Glide.with(this).load(app.getCurrentMapDrawable())
            .apply(new RequestOptions().override(MortarCalculatorApplication.getMarkImageWidth(), MortarCalculatorApplication.getMarkImageHeight()))
            .into(imageView);

        // Setup detectors needed locally and for chaining
        GestureDetector.OnGestureListener gListener = new SquadMapMarkingImageGestureDetector(this, imageView);
        GestureDetector gDetector = new GestureDetector(this, gListener);
        View.OnTouchListener tListener = new ImageDelegatingTouchListener(gDetector);
        imageView.setOnTouchListener(tListener);

        // Setup the Scroll View for the image
        OuterHorizontalScrollView hScrollView = findViewById(R.id.mapHorizontalScroll);
        ScrollView vScrollView = findViewById(R.id.mapVerticalScroll);
        hScrollView.setScrollView(vScrollView);
        vScrollView.requestDisallowInterceptTouchEvent(true);
        hScrollView.requestDisallowInterceptTouchEvent(true);

        // Setup button handling here
        Button deletePointButton = findViewById(R.id.deletePointButton);
        deletePointButton.setOnClickListener(v -> {
            Log.d("ACTIVITY", "Start new intent for Delete");

            // Delete the mark point
            app.deleteMarkPoint(editPoint);

            // Return to the Map Detail Activity
            Toast.makeText(getApplicationContext(), "Point deleted, returning...", Toast.LENGTH_LONG).show();
            new Handler().postDelayed(() -> {
                startActivity(new Intent(getApplicationContext(), MapDetailActivity.class));
            }, Toast.LENGTH_LONG);
        });
    }
}
