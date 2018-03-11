package com.squadfinder.brend.squadandroidcalculator.activity.map;

import android.os.Bundle;
import android.view.GestureDetector;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ScrollView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.squadfinder.brend.squadandroidcalculator.R;
import com.squadfinder.brend.squadandroidcalculator.activity.base.BaseActivity;
import com.squadfinder.brend.squadandroidcalculator.application.MortarCalculatorApplication;
import com.squadfinder.brend.squadandroidcalculator.listener.ImageGestureDetector;
import com.squadfinder.brend.squadandroidcalculator.listener.ImageTouchListener;
import com.squadfinder.brend.squadandroidcalculator.view.OuterHorizontalScrollView;

/**
 * Created by brend on 3/11/2018.
 */

public class MapCalculatedActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.map_detail_calculated_layout);

        // Get our App
        MortarCalculatorApplication app = (MortarCalculatorApplication) getApplication();

        // Load our image
        ImageView imageView = findViewById(R.id.mapImageView);
        app.connectMarkPoints(this);
        Glide.with(this).load(app.getCurrentMapDrawable())
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
    }
}
