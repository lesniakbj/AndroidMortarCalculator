package com.squadfinder.brend.squadandroidcalculator.activity.points;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;

import com.squadfinder.brend.squadandroidcalculator.R;
import com.squadfinder.brend.squadandroidcalculator.activity.base.BaseActivity;
import com.squadfinder.brend.squadandroidcalculator.adapter.MarkPointListViewAdapter;
import com.squadfinder.brend.squadandroidcalculator.application.MortarCalculatorApplication;
import com.squadfinder.brend.squadandroidcalculator.domain.calc.MarkPoint;
import com.squadfinder.brend.squadandroidcalculator.domain.enums.PointType;
import com.squadfinder.brend.squadandroidcalculator.listener.ListViewMarkPointListener;
import com.squadfinder.brend.squadandroidcalculator.listener.ListViewMarkPointLongClickListener;
import com.squadfinder.brend.squadandroidcalculator.view.MaxHeightListView;

import java.util.List;

/**
 * Created by brend on 3/9/2018.
 */

public class AssignTargetsActivity extends BaseActivity {
    private static final int PAD = 30;
    private static int listViewMaxHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.assign_points_layout);

        // Load our application
        MortarCalculatorApplication app = (MortarCalculatorApplication) getApplication();

        // Get our Screen Height
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        listViewMaxHeight = (dm.heightPixels / 2) - PAD;

        // Load mortars
        MaxHeightListView mortarListView = findViewById(R.id.mortarMarkListView);
        loadListView(app, mortarListView, PointType.MORTAR);

        // Load targets
        MaxHeightListView targetListView = findViewById(R.id.targetMarkListView);
        loadListView(app, targetListView, PointType.TARGET);
    }

    private void loadListView(MortarCalculatorApplication app, MaxHeightListView listView, PointType type) {
        listView.setMaxHeight(listViewMaxHeight);
        List<MarkPoint> points = app.getMarkPointsByType(type);
        ArrayAdapter<MarkPoint> mpArrayAdapter = new MarkPointListViewAdapter(this, points);
        listView.setAdapter(mpArrayAdapter);
        listView.setLongClickable(true);
        listView.setOnItemClickListener(new ListViewMarkPointListener(this));
        listView.setOnItemLongClickListener(new ListViewMarkPointLongClickListener(this));
        app.setListViewAdapterForType(mpArrayAdapter, type);
    }
}
