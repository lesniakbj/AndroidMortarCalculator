package com.squadfinder.brend.squadandroidcalculator.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.HorizontalScrollView;
import android.widget.ScrollView;

/**
 * Created by brend on 3/9/2018.
 */

public class OuterHorizontalScrollView extends HorizontalScrollView {
    public ScrollView scrollView;

    public OuterHorizontalScrollView(Context context) {
        super(context);
    }

    public OuterHorizontalScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public OuterHorizontalScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setScrollView(ScrollView view) {
        this.scrollView = view;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        super.onInterceptTouchEvent(ev);
        scrollView.onInterceptTouchEvent(MotionEvent.obtain(ev.getDownTime(), ev.getEventTime(), ev.getAction(), ev.getX() + getScrollX(), ev.getY(), ev.getMetaState()));
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if(ev.getPointerCount() > 1) {
            return true;
        }

        super.onTouchEvent(ev);
        scrollView.dispatchTouchEvent(MotionEvent.obtain(ev.getDownTime(), ev.getEventTime(), ev.getAction(), ev.getX() + getScrollX(), ev.getY(), ev.getMetaState()));
        return true;
    }
}