package com.squadfinder.brend.squadandroidcalculator.activity;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;

import com.squadfinder.brend.squadandroidcalculator.R;
import com.squadfinder.brend.squadandroidcalculator.adapter.MarkPointListViewAdapter;
import com.squadfinder.brend.squadandroidcalculator.domain.calc.MarkPoint;
import com.squadfinder.brend.squadandroidcalculator.domain.calc.PointManager;
import com.squadfinder.brend.squadandroidcalculator.domain.enums.PointType;
import com.squadfinder.brend.squadandroidcalculator.view.MaxHeightListView;

import java.util.List;

/**
 * Created by brend on 3/9/2018.
 */

public class EditPointsActivity extends BaseActivity {
    private static final int PAD = 30;
    private static int listViewMaxHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.edit_points_layout);

        // Get our Screen Height
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int height = dm.heightPixels;
        listViewMaxHeight = (height / 2) - PAD;

        // Load mortars and distance to targets
        MaxHeightListView mortarListView = findViewById(R.id.mortarMarkListView);
        loadListView(mortarListView, PointType.MORTAR);

        // Load targets
        MaxHeightListView targetListView = findViewById(R.id.targetMarkListView);
        loadListView(targetListView, PointType.TARGET);
    }

    private void loadListView(MaxHeightListView listView, PointType type) {
        listView.setMaxHeight(listViewMaxHeight);
        List<MarkPoint> points = PointManager.getInstance().getPointsByType(type);
        ArrayAdapter<MarkPoint> mpArrayAdapter = new MarkPointListViewAdapter(this, points);
        listView.setAdapter(mpArrayAdapter);
    }
}
