package com.squadfinder.brend.squadandroidcalculator.listener;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.Toast;

import com.squadfinder.brend.squadandroidcalculator.activity.AssignTargetsActivity;
import com.squadfinder.brend.squadandroidcalculator.activity.MapDetailActivity;
import com.squadfinder.brend.squadandroidcalculator.activity.MapDetailEditActivity;
import com.squadfinder.brend.squadandroidcalculator.application.MortarCalculatorApplication;
import com.squadfinder.brend.squadandroidcalculator.domain.calc.MarkPoint;
import com.squadfinder.brend.squadandroidcalculator.domain.enums.PointType;

/**
 * Created by brend on 3/9/2018.
 */

public class ImageGestureDetector extends GestureDetector.SimpleOnGestureListener {
    private final ImageView imageView;
    private final Activity activity;
    private MapDetailActivity.MarkPointState state;

    public ImageGestureDetector(Activity activity, ImageView imageView) {
        this.activity = activity;
        this.imageView = imageView;

        // Since we reuse this class, we need to know what mode we are in
        if(activity instanceof MapDetailEditActivity) {
            state = MapDetailActivity.MarkPointState.EDIT;
        } else {
            state = MapDetailActivity.MarkPointState.CREATE;
        }
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        super.onDoubleTap(e);

        // Get the location of the long press
        float touchX = e.getX();
        float touchY = e.getY();

        // Ask the user what type of point they are placing, handle drawing there.
        if(state == MapDetailActivity.MarkPointState.CREATE) {
            AlertDialog alert = buildAlertDialog(touchX, touchY);
            alert.show();
        }

        if(state == MapDetailActivity.MarkPointState.EDIT) {
            Log.d("DETECTOR", "Edit state in detector");

            // Do the edit
            MortarCalculatorApplication app = (MortarCalculatorApplication) activity.getApplication();
            MarkPoint editPoint = app.getMarkPointToEdit();
            app.deleteMarkPoint(editPoint);
            app.addMarkPoint(touchX, touchY, editPoint.getPointType());

            // Return to the Map Detail Activity
            Toast.makeText(activity, "Point edited, returning...", Toast.LENGTH_LONG).show();
            new Handler().postDelayed(() -> {
                Intent assignIntent = new Intent(activity, MapDetailActivity.class);
                activity.startActivity(assignIntent);
            }, Toast.LENGTH_LONG);
        }

        return true;
    }

    private AlertDialog buildAlertDialog(float x, float y) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity, android.R.style.Theme_Dialog);
        return builder.setTitle("What are you marking...")
                .setItems(new String[]{"Mortar", "Target"}, (dialog, which) -> {
                    MortarCalculatorApplication app = (MortarCalculatorApplication) activity.getApplication();
                    app.addMarkPoint(x, y, which == 0 ? PointType.MORTAR : PointType.TARGET);
                    app.fillImageViewMarkPoints(activity, imageView, app.getMarkPointList());
                }).create();
    }
}