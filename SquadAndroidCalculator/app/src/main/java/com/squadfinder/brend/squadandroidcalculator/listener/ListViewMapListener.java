package com.squadfinder.brend.squadandroidcalculator.listener;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.squadfinder.brend.squadandroidcalculator.activity.MapDetailActivity;
import com.squadfinder.brend.squadandroidcalculator.domain.SquadMap;

/**
 * Created by brend on 3/6/2018.
 */

public class ListViewMapListener implements AdapterView.OnItemClickListener {
    private final Activity activity;

    public ListViewMapListener(Activity activity) {
        this.activity = activity;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        SquadMap clickedMap = (SquadMap)parent.getItemAtPosition(position);
        Log.d("LISTENER", String.format("onItemClick: %s", clickedMap));

        // Begin the transition to the clicked map
        Intent mapDetailActivity = new Intent(activity.getApplicationContext(), MapDetailActivity.class);
        mapDetailActivity.putExtra("map", clickedMap);
        activity.startActivity(mapDetailActivity);
    }
}
