package com.squadfinder.brend.squadandroidcalculator.activity;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.squadfinder.brend.squadandroidcalculator.R;
import com.squadfinder.brend.squadandroidcalculator.activity.base.BaseActivity;
import com.squadfinder.brend.squadandroidcalculator.adapter.SquadMapListViewAdapter;
import com.squadfinder.brend.squadandroidcalculator.domain.SquadMap;
import com.squadfinder.brend.squadandroidcalculator.listener.SquadMapListViewListener;
import com.squadfinder.brend.squadandroidcalculator.listener.image.HelpImageTouchListener;
import com.squadfinder.brend.squadandroidcalculator.util.GsonUtil;
import com.squadfinder.brend.squadandroidcalculator.util.RawResourceLoader;
import com.squadfinder.brend.squadandroidcalculator.view.image.BaseClickableImageView;

import java.util.List;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.main_layout);

        // Load Map Data
        String json = RawResourceLoader.readRawResourceAsString(getResources().openRawResource(R.raw.maps_default));
        List<SquadMap> squadMapList = GsonUtil.toType(json, new TypeToken<List<SquadMap>>(){});
        ArrayAdapter<SquadMap> squadMapAdapter = new SquadMapListViewAdapter(this, squadMapList);
        AdapterView.OnItemClickListener squadMapListener = new SquadMapListViewListener(this);
        addListView(R.id.mapListView, squadMapAdapter, squadMapListener);

        // Setup our Help Button
        BaseClickableImageView helpImage = findViewById(R.id.helpButton);
        helpImage.setOnTouchListener(new HelpImageTouchListener(this));

        // Welcome to the App
        Toast.makeText(this, R.string.str_welcome, Toast.LENGTH_LONG).show();
    }
}
