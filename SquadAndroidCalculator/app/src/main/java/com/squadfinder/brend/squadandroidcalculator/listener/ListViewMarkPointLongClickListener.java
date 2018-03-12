package com.squadfinder.brend.squadandroidcalculator.listener;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipDescription;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.widget.AdapterView;

import com.squadfinder.brend.squadandroidcalculator.application.MortarCalculatorApplication;
import com.squadfinder.brend.squadandroidcalculator.domain.calc.MarkPoint;

/**
 * Created by brend on 3/11/2018.
 */

public class ListViewMarkPointLongClickListener implements AdapterView.OnItemLongClickListener {
    private final Activity activity;

    public ListViewMarkPointLongClickListener(Activity activity) {
        this.activity = activity;
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        // Build the drag data
        MarkPoint markPointToDrag = (MarkPoint)parent.getItemAtPosition(position);
        MortarCalculatorApplication app = (MortarCalculatorApplication) activity.getApplication();
        app.setDraggedMarkPoint(markPointToDrag);
        String markPointId = markPointToDrag.getId().toString();
        ClipData.Item item = new ClipData.Item(markPointId);
        ClipData data = new ClipData(markPointId, new String[]{ ClipDescription.MIMETYPE_TEXT_PLAIN }, item);

        // Start the drag
        View.DragShadowBuilder shadow = new ListViewMarkPointShadowBuilder(activity, view);
        ViewCompat.startDragAndDrop(view, data, shadow, null, 0);
        return true;
    }
}
