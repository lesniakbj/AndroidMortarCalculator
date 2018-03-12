package com.squadfinder.brend.squadandroidcalculator.listener;

import android.app.Activity;
import android.content.ClipData;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.widget.TextView;

import com.squadfinder.brend.squadandroidcalculator.R;
import com.squadfinder.brend.squadandroidcalculator.application.MortarCalculatorApplication;
import com.squadfinder.brend.squadandroidcalculator.domain.calc.MarkPoint;
import com.squadfinder.brend.squadandroidcalculator.domain.enums.PointType;

import java.text.DecimalFormat;

/**
 * Created by brend on 3/11/2018.
 */

public class ListViewMarkPointDragListener implements View.OnDragListener {
    private final Activity activity;
    private final MarkPoint point;

    private Drawable viewBgDrawable;

    public ListViewMarkPointDragListener(Activity activity, MarkPoint point) {
        this.activity = activity;
        this.point = point;
    }

    @Override
    public boolean onDrag(View v, DragEvent event) {
        MortarCalculatorApplication app = (MortarCalculatorApplication) activity.getApplication();
        ClipData.Item item;
        switch(event.getAction()) {
            case DragEvent.ACTION_DRAG_ENTERED:
                viewBgDrawable = v.getBackground();

                // Draw the distance to the mortar here
                DecimalFormat df = new DecimalFormat("#.00");
                TextView tv = v.findViewById(R.id.markPointDistanceOverlay);
                double distance = app.getDistanceBetweenMarkPoints(point, app.getDraggedMarkPoint());
                tv.setText(String.format("Distance: %sm", df.format(distance)));

                int colorId;
                if(distance <= app.getMaxMortarDistance()) {
                    colorId = activity.getResources().getColor(R.color.colorAccentTransparent);
                } else {
                    colorId = activity.getResources().getColor(R.color.colorLightRed);
                }
                v.setBackgroundColor(colorId);

                v.invalidate();
                return true;
            case DragEvent.ACTION_DRAG_EXITED:
                resetView(v);
                return true;
            case DragEvent.ACTION_DROP:
                // Get the clip item
                if(event.getClipData() != null) {
                    item = event.getClipData().getItemAt(0);

                    // Add mapping for the event here
                    MarkPoint target = app.getMarkPointByStringId(item.getText().toString());
                    boolean success = app.addMortarMapping(point, target);

                    // If we were successful, move the item visually
                    if(success) {
                        app.getListViewAdapterForType(PointType.TARGET).remove(target);
                        app.getListViewAdapterForType(PointType.TARGET).notifyDataSetChanged();
                        app.getListViewAdapterForType(PointType.MORTAR).notifyDataSetChanged();
                        Log.d("LISTENER", String.format("Assignments: %s", app.getTargetMappings()));
                    }

                    resetView(v);
                    app.setDraggedMarkPoint(null);
                }

                return true;
        }
        return true;
    }

    private void resetView(View v) {
        v.setBackground(viewBgDrawable);
        TextView tv = v.findViewById(R.id.markPointDistanceOverlay);
        tv.setText(null);
        v.invalidate();
    }
}
