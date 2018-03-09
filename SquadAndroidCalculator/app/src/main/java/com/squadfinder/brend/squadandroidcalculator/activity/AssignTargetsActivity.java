package com.squadfinder.brend.squadandroidcalculator.activity;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by brend on 3/9/2018.
 */

public class AssignTargetsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }
}
