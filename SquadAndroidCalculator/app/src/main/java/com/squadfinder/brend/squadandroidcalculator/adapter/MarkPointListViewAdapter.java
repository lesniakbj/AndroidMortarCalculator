package com.squadfinder.brend.squadandroidcalculator.adapter;

import android.app.Activity;
import android.widget.ArrayAdapter;

import com.squadfinder.brend.squadandroidcalculator.activity.EditPointsActivity;
import com.squadfinder.brend.squadandroidcalculator.domain.calc.MarkPoint;

import java.util.List;

/**
 * Created by brend on 3/9/2018.
 */

public class MarkPointListViewAdapter extends ArrayAdapter<MarkPoint> {
    private Activity context;
    private List<MarkPoint> markPoints;

    public MarkPointListViewAdapter(Activity context, List<MarkPoint> markPoints) {
        super(context, -1, markPoints);
        this.context = context;
        this.markPoints = markPoints;
    }
}
