package com.squadfinder.brend.squadandroidcalculator.listener;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;

/**
 * Created by brend on 3/11/2018.
 */

public class ListViewMarkPointShadowBuilder extends View.DragShadowBuilder {
    private static Drawable shadow;

    ListViewMarkPointShadowBuilder(View v) {
        super(v);
        shadow = new ColorDrawable(Color.LTGRAY);
    }

    @Override
    public void onProvideShadowMetrics (Point size, Point touch) {
        int width = getView().getWidth() / 2;
        int height = getView().getHeight() / 2;
        shadow.setBounds(0, 0, width, height);

        // Sets the size parameter's width and height values. These get back to the system through the size parameter.
        size.set(width, height);

        // Sets the touch point's position to be in the middle of the drag shadow
        touch.set(width / 2, height / 2);
    }

    @Override
    public void onDrawShadow(Canvas canvas) {
        shadow.draw(canvas);
    }
}
