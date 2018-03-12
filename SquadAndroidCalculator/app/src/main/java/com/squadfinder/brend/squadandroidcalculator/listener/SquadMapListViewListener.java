package com.squadfinder.brend.squadandroidcalculator.listener;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import com.squadfinder.brend.squadandroidcalculator.activity.map.MapDetailActivity;
import com.squadfinder.brend.squadandroidcalculator.application.MortarCalculatorApplication;
import com.squadfinder.brend.squadandroidcalculator.domain.SquadMap;

/**
 * Created by brend on 3/6/2018.
 */

public class SquadMapListViewListener implements AdapterView.OnItemClickListener {
    private final Activity context;

    public SquadMapListViewListener(Activity context) {
        this.context = context;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        MortarCalculatorApplication app = (MortarCalculatorApplication)context.getApplication();
        SquadMap clickedMap = (SquadMap)parent.getItemAtPosition(position);
        app.setCurrentMap(clickedMap);
        context.startActivity(new Intent(context.getApplicationContext(), MapDetailActivity.class));
    }
}
