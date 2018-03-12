package com.squadfinder.brend.squadandroidcalculator.listener;

import android.content.Intent;
import android.view.View;

import com.squadfinder.brend.squadandroidcalculator.activity.base.BaseActivity;

/**
 * Created by brend on 3/12/2018.
 */

public class StartIntentListener implements View.OnClickListener {
    private BaseActivity context;
    private Class<?> clazz;

    public StartIntentListener(BaseActivity context, Class<?> clazz) {
        super();
        this.context = context;
        this.clazz = clazz;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(context, clazz);
        context.startActivity(intent);
    }
}
