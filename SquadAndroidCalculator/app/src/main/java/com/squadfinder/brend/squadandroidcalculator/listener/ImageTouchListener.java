package com.squadfinder.brend.squadandroidcalculator.listener;

import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by brend on 3/9/2018.
 */

public class ImageTouchListener implements View.OnTouchListener {
    private float lastX;
    private float lastY;

    private GestureDetector gestureDetector;

    public ImageTouchListener(GestureDetector gestureDetector) {
        this.gestureDetector = gestureDetector;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch(event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                lastX = event.getX();
                lastY = event.getY();
                break;
            }
            case MotionEvent.ACTION_UP: {
                v.performClick();
                break;
            }
            default:
                break;
        }
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