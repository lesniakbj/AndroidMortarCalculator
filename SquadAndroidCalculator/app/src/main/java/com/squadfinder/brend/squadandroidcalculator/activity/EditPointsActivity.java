package com.squadfinder.brend.squadandroidcalculator.activity;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.squadfinder.brend.squadandroidcalculator.R;
import com.squadfinder.brend.squadandroidcalculator.domain.calc.MarkPoint;
import com.squadfinder.brend.squadandroidcalculator.domain.calc.PointManager;
import com.squadfinder.brend.squadandroidcalculator.domain.enums.PointType;
import com.squadfinder.brend.squadandroidcalculator.view.MaxHeightListView;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by brend on 3/9/2018.
 */

public class EditPointsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.edit_points_layout);

        TextView layout = findViewById(R.id.mortarHeader);

        // Load Mortar Marks
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        MaxHeightListView mortarListView = findViewById(R.id.mortarMarkListView);
        mortarListView.setMaxHeight((dm.heightPixels / 2) - 30);
        List<MarkPoint> mortars = PointManager.getInstance().getPointsByType(PointType.MORTAR);
        ArrayAdapter ad = new ArrayAdapter(this, android.R.layout.simple_list_item_1, mortars.stream().map((pt) -> pt.getPointCoordinates().toString()).collect(Collectors.toList()));
        mortarListView.setAdapter(ad);

        // Load Target Marks
        MaxHeightListView targetListView = findViewById(R.id.targetMarkListView);
        targetListView.setMaxHeight((dm.heightPixels / 2) - 30);
        List<MarkPoint> targets = PointManager.getInstance().getPointsByType(PointType.TARGET);
        ArrayAdapter ad2 = new ArrayAdapter(this, android.R.layout.simple_list_item_1, targets.stream().map((pt) -> pt.getPointCoordinates().toString()).collect(Collectors.toList()));
        targetListView.setAdapter(ad2);

        // Debug
        Log.d("ACTIVITY", String.format("Mortars: %d[%s]. Targets: %d[%s].", mortars.size(), mortars, targets.size(), targets));
    }
}
