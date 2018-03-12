package com.squadfinder.brend.squadandroidcalculator.listener;

import android.graphics.Matrix;
import android.graphics.PointF;
import android.util.FloatMath;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import static com.android.volley.VolleyLog.TAG;

/**
 * Created by brend on 3/9/2018.
 */

public class ImageDelegatingTouchListener implements View.OnTouchListener {
    private float lastX;
    private float lastY;

    private GestureDetector gestureDetector;

    public ImageDelegatingTouchListener(GestureDetector gestureDetector) {
        this.gestureDetector = gestureDetector;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        v.performClick();
        gestureDetector.onTouchEvent(event);
        return true;
    }

    public float getLastX() {
        return lastX;
    }

    public void setLastX(float lastX) {
        this.lastX = lastX;
    }

    public float getLastY() {
        return lastY;
    }

    public void setLastY(float lastY) {
        this.lastY = lastY;
    }

    public GestureDetector getGestureDetector() {
        return gestureDetector;
    }

    public void setGestureDetector(GestureDetector gestureDetector) {
        this.gestureDetector = gestureDetector;
    }
}