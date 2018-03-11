package com.squadfinder.brend.squadandroidcalculator.listener;

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;

/**
 * Created by brend on 3/11/2018.
 */

public class ListViewMarkPointShadowBuilder extends View.DragShadowBuilder {
    private static final double scaleFactor = .75;
    private static Drawable shadow;

    ListViewMarkPointShadowBuilder(Activity activity, View v) {
        super(v);
        v.buildDrawingCache();
        shadow = new BitmapDrawable(activity.getResources(), v.getDrawingCache());
    }

    @Override
    public void onProvideShadowMetrics (Point size, Point touch) {
        int width = (int)(getView().getWidth() * scaleFactor);
        int height = (int)(getView().getHeight() * scaleFactor);
        shadow.setBounds(0, 0, width, height);
        size.set(width, height);
        touch.set(width / 2, height / 2);
    }

    @Override
    public void onDrawShadow(Canvas canvas) {
        shadow.draw(canvas);
    }
}
