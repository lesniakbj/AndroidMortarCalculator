package com.squadfinder.brend.squadandroidcalculator.listener.image;

import android.view.MotionEvent;
import android.view.View;

import com.squadfinder.brend.squadandroidcalculator.R;
import com.squadfinder.brend.squadandroidcalculator.activity.base.BaseActivity;

public class HelpImageTouchListener implements View.OnTouchListener {
    private BaseActivity context;

    public HelpImageTouchListener(BaseActivity context) {
        this.context = context;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        v.performClick();
        context.buildAlertDialog(R.string.str_help_title, R.string.str_help, R.string.str_ok).show();
        return true;
    }
}