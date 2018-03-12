package com.squadfinder.brend.squadandroidcalculator.listener.image;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.Toast;

import com.squadfinder.brend.squadandroidcalculator.activity.base.BaseActivity;
import com.squadfinder.brend.squadandroidcalculator.activity.map.MapDetailActivity;
import com.squadfinder.brend.squadandroidcalculator.activity.map.MapDetailEditActivity;
import com.squadfinder.brend.squadandroidcalculator.application.MortarCalculatorApplication;
import com.squadfinder.brend.squadandroidcalculator.domain.calc.MarkPoint;
import com.squadfinder.brend.squadandroidcalculator.domain.enums.PointType;
import com.squadfinder.brend.squadandroidcalculator.domain.enums.SquadMapMarkPointState;

/**
 * Created by brend on 3/12/2018.
 */

public class SquadMapMarkingImageGestureDetector extends GestureDetector.SimpleOnGestureListener {
    private final BaseActivity context;
    private final ImageView imageView;
    private SquadMapMarkPointState state;

    public SquadMapMarkingImageGestureDetector(BaseActivity context, ImageView imageView) {
        this.context = context;
        this.imageView = imageView;

        // Since we reuse this class, we need to know what mode we are in
        if(context instanceof MapDetailEditActivity) {
            state = SquadMapMarkPointState.EDIT;
        } else {
            state = SquadMapMarkPointState.CREATE;
        }
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        super.onDoubleTap(e);

        // Get the location of the long press
        float touchX = e.getX();
        float touchY = e.getY();

        // Ask the user what type of point they are placing, handle drawing there.
        if(state == SquadMapMarkPointState.CREATE) {
            AlertDialog alert = buildAlertDialog(touchX, touchY);
            alert.show();
        }

        if(state == SquadMapMarkPointState.EDIT) {
            Log.d("DETECTOR", "Edit state in detector");

            // Do the edit
            MortarCalculatorApplication app = (MortarCalculatorApplication) context.getApplication();
            MarkPoint editPoint = app.getMarkPointToEdit();
            app.deleteMarkPoint(editPoint);
            app.addMarkPoint(touchX, touchY, editPoint.getPointType());

            // Return to the Map Detail Activity
            Toast.makeText(context, "Point edited, returning...", Toast.LENGTH_LONG).show();
            new Handler().postDelayed(() -> {
                Intent assignIntent = new Intent(context, MapDetailActivity.class);
                context.startActivity(assignIntent);
            }, Toast.LENGTH_LONG);
        }

        return true;
    }

    private AlertDialog buildAlertDialog(float x, float y) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, android.R.style.Theme_Dialog);
        return builder.setTitle("What are you marking...")
                .setItems(new String[]{"Mortar", "Target"}, (dialog, which) -> {
                    MortarCalculatorApplication app = (MortarCalculatorApplication) context.getApplication();
                    app.addMarkPoint(x, y, which == 0 ? PointType.MORTAR : PointType.TARGET);
                    app.fillImageViewMarkPoints(context, imageView, app.getMarkPointList());
                }).create();
    }
}
