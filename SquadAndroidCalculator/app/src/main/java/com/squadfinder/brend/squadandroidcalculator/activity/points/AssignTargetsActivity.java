package com.squadfinder.brend.squadandroidcalculator.activity.points;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.squadfinder.brend.squadandroidcalculator.R;
import com.squadfinder.brend.squadandroidcalculator.activity.base.BaseActivity;
import com.squadfinder.brend.squadandroidcalculator.activity.map.MapCalculatedActivity;
import com.squadfinder.brend.squadandroidcalculator.adapter.MarkPointTargetListViewAdapter;
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

        // Button Assignments
        Button confirmAssignmentButton = findViewById(R.id.confirmAssignButton);
        confirmAssignmentButton.setOnClickListener(v -> {
            Log.d("ACTIVITY", "Start new intent for Edit");
            Intent calulateActivity = new Intent(this, MapCalculatedActivity.class);
            this.startActivity(calulateActivity);
        });
    }


    @Override
    public void onBackPressed() {
        MortarCalculatorApplication app = (MortarCalculatorApplication) getApplication();
        app.removeAllMarkedAssignments();
        super.onBackPressed();
    }

    private void loadListView(MortarCalculatorApplication app, MaxHeightListView listView, PointType type) {
        listView.setMaxHeight(listViewMaxHeight);
        List<MarkPoint> points = app.getMarkPointsByType(type);
        ArrayAdapter<MarkPoint> mpArrayAdapter = new MarkPointTargetListViewAdapter(this, points);
        listView.setAdapter(mpArrayAdapter);
        listView.setLongClickable(true);
        listView.setOnItemClickListener(new ListViewMarkPointListener(this));
        listView.setOnItemLongClickListener(new ListViewMarkPointLongClickListener(this));
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listView.setSelector(R.color.colorAccentTransparent);
        app.setListViewAdapterForType(mpArrayAdapter, type);
    }
}
