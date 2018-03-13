package com.squadfinder.brend.squadandroidcalculator.activity.map;

import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ScrollView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.squadfinder.brend.squadandroidcalculator.R;
import com.squadfinder.brend.squadandroidcalculator.activity.MainActivity;
import com.squadfinder.brend.squadandroidcalculator.activity.base.BaseActivity;
import com.squadfinder.brend.squadandroidcalculator.adapter.MarkPointCalculatedListViewAdapter;
import com.squadfinder.brend.squadandroidcalculator.application.MortarCalculatorApplication;
import com.squadfinder.brend.squadandroidcalculator.domain.calc.MarkPoint;
import com.squadfinder.brend.squadandroidcalculator.domain.enums.PointType;
import com.squadfinder.brend.squadandroidcalculator.listener.ImageDelegatingTouchListener;
import com.squadfinder.brend.squadandroidcalculator.listener.image.SquadMapMarkingImageGestureDetector;
import com.squadfinder.brend.squadandroidcalculator.view.OuterHorizontalScrollView;
import com.squadfinder.brend.squadandroidcalculator.view.image.BaseClickableImageView;

import java.util.List;

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
        BaseClickableImageView imageView = findViewById(R.id.mapImageView);
        app.connectMarkPoints(this);
        Glide.with(this).load(app.getCurrentMapBitmap())
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

        // Setup our listview
        List<MarkPoint> points = app.getMarkPointsByType(PointType.MORTAR);
        ArrayAdapter<MarkPoint> pointAdapter = new MarkPointCalculatedListViewAdapter(this, points);
        addListView(R.id.mortarCalcMortarList, pointAdapter, null);

        Button retButton = findViewById(R.id.mortarCalcReset);
        retButton.setOnClickListener(v -> {
            app.clear();
            Intent reset = new Intent(this, MainActivity.class);
            this.startActivity(reset);
        });
    }
}
