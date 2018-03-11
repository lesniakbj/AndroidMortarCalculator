package com.squadfinder.brend.squadandroidcalculator.listener;

import android.app.Activity;
import android.content.ClipData;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;

import com.squadfinder.brend.squadandroidcalculator.application.MortarCalculatorApplication;
import com.squadfinder.brend.squadandroidcalculator.domain.calc.MarkPoint;
import com.squadfinder.brend.squadandroidcalculator.domain.enums.PointType;

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
        switch(event.getAction()) {
            case DragEvent.ACTION_DRAG_ENTERED:
                viewBgDrawable = v.getBackground();
                v.setBackgroundColor(Color.GREEN);
                v.invalidate();
                return true;
            case DragEvent.ACTION_DRAG_EXITED:
                v.setBackground(viewBgDrawable);
                v.invalidate();
                return true;
            case DragEvent.ACTION_DROP:
                // Get the clip item
                ClipData.Item item = event.getClipData().getItemAt(0);

                // Add mapping for the event here
                MortarCalculatorApplication app = (MortarCalculatorApplication) activity.getApplication();
                MarkPoint target = app.getMarkPointByStringId(item.getText().toString());
                boolean success = app.addMortarMapping(point, target);

                // If we were successful, move the item visually
                if(success) {
                    app.getListViewAdapterForType(PointType.TARGET).remove(target);
                    app.getListViewAdapterForType(PointType.TARGET).notifyDataSetChanged();
                    Log.d("LISTENER", String.format("Assignments: %s", app.getTargetMappings()));
                }

                v.setBackground(viewBgDrawable);
                v.invalidate();
                return true;
        }
        return true;
    }
}
