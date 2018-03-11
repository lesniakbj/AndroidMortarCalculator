package com.squadfinder.brend.squadandroidcalculator.listener;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.squadfinder.brend.squadandroidcalculator.activity.MapDetailActivity;
import com.squadfinder.brend.squadandroidcalculator.activity.MapDetailEditActivity;
import com.squadfinder.brend.squadandroidcalculator.application.MortarCalculatorApplication;
import com.squadfinder.brend.squadandroidcalculator.domain.calc.MarkPoint;

/**
 * Created by brend on 3/10/2018.
 */

public class ListViewMarkPointListener implements AdapterView.OnItemClickListener {
    private final Activity activity;

    public ListViewMarkPointListener(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        MarkPoint clickedMarkPoint = (MarkPoint)parent.getItemAtPosition(position);
        Log.d("LISTENER", String.format("onItemClick: %s", clickedMarkPoint));

        // Begin the transition to the clicked map
        MortarCalculatorApplication app = (MortarCalculatorApplication) activity.getApplication();
        app.setMarkPointToEdit(clickedMarkPoint);
        activity.startActivity(new Intent(activity.getApplicationContext(), MapDetailEditActivity.class));
    }
}
