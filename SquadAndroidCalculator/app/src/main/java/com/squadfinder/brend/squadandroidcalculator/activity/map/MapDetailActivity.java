package com.squadfinder.brend.squadandroidcalculator.activity.map;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.squadfinder.brend.squadandroidcalculator.R;
import com.squadfinder.brend.squadandroidcalculator.activity.base.BaseActivity;
import com.squadfinder.brend.squadandroidcalculator.activity.points.AssignTargetsActivity;
import com.squadfinder.brend.squadandroidcalculator.activity.points.EditPointsActivity;
import com.squadfinder.brend.squadandroidcalculator.application.MortarCalculatorApplication;
import com.squadfinder.brend.squadandroidcalculator.domain.SquadMap;
import com.squadfinder.brend.squadandroidcalculator.listener.ImageDelegatingTouchListener;
import com.squadfinder.brend.squadandroidcalculator.listener.StartIntentListener;
import com.squadfinder.brend.squadandroidcalculator.listener.image.SquadMapMarkingImageGestureDetector;
import com.squadfinder.brend.squadandroidcalculator.view.OuterHorizontalScrollView;
import com.squadfinder.brend.squadandroidcalculator.view.image.BaseClickableImageView;


/**
 * Created by brend on 3/6/2018.
 */

public class MapDetailActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.map_detail_layout);

        // Load our application and relevant data
        MortarCalculatorApplication app = (MortarCalculatorApplication) getApplication();
        SquadMap loadedMap = app.getCurrentMap();

        // Load the Map Meta Data
        TextView mapName = findViewById(R.id.mapDetailMapName);
        TextView mapDesc = findViewById(R.id.mapDetailMapDescription);
        TextView mapDimensions = findViewById(R.id.mapDetailMapDimensions);
        mapName.setText(loadedMap.getMapName());
        mapDesc.setText(loadedMap.getMapDescription());
        mapDimensions.setText(loadedMap.getDimensionString());

        // Load the Map Image View
        BaseClickableImageView imageView = findViewById(R.id.mapDetailImageView);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), loadedMap.getMapImageResourceId(this));
        app.setCurrentMapBitmap(this, bitmap);
        app.fillImageViewMarkPoints(this, imageView, app.getMarkPointList());
        int width = MortarCalculatorApplication.getMarkImageWidth();
        int height = MortarCalculatorApplication.getMarkImageHeight();
        RequestOptions req = new RequestOptions().override(width, height);
        Glide.with(this).load(app.getCurrentMapBitmap()).apply(req).into(imageView);

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

        // Setup our button presses
        Button editPointsButton = findViewById(R.id.editPointsButton);
        Button assignTargetsButton = findViewById(R.id.assignTargetsButton);
        editPointsButton.setOnClickListener(new StartIntentListener(this, EditPointsActivity.class));
        assignTargetsButton.setOnClickListener(new StartIntentListener(this, AssignTargetsActivity.class));
    }

    @Override
    public void onBackPressed() {
        MortarCalculatorApplication app = (MortarCalculatorApplication) getApplication();
        app.clear();
        super.onBackPressed();
    }
}
