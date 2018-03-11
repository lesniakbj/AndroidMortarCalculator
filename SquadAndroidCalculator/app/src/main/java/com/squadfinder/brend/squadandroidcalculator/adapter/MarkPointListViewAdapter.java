package com.squadfinder.brend.squadandroidcalculator.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.squadfinder.brend.squadandroidcalculator.R;
import com.squadfinder.brend.squadandroidcalculator.domain.calc.MarkPoint;

import java.util.List;

/**
 * Created by brend on 3/9/2018.
 */

public class MarkPointListViewAdapter extends ArrayAdapter<MarkPoint> {
    private Activity context;
    private List<MarkPoint> markPoints;

    public MarkPointListViewAdapter(Activity context, List<MarkPoint> markPoints) {
        super(context, -1, markPoints);
        this.context = context;
        this.markPoints = markPoints;
    }

    @NonNull
    @Override
    public View getView(int i, View view, @NonNull ViewGroup viewGroup) {
        View rowView = view;
        MarkPointViewHolder viewHolder;

        if(rowView == null) {
            viewHolder = new MarkPointViewHolder();

            rowView = context.getLayoutInflater().inflate(R.layout.mark_point_list_item_layout, null);
            viewHolder.setMarkPointCoordinatesTextView(rowView.findViewById(R.id.markPointCoordinatesTextView));
            viewHolder.setMarkPointGridTextView(rowView.findViewById(R.id.markPointGridTextView));
            rowView.setTag(viewHolder);
        }

        viewHolder = (MarkPointViewHolder)rowView.getTag();
        viewHolder.getMarkPointCoordinatesTextView().setText(String.format("Image Coordinates: %s", markPoints.get(i).getPointCoordinates().toString()));
        viewHolder.getMarkPointGridTextView().setText(String.format("Grid: %s", "NO GRID YET"));

        return rowView;
    }

    private static class MarkPointViewHolder {
        private TextView markPointCoordinatesTextView;
        private TextView markPointGridTextView;

        void setMarkPointCoordinatesTextView(TextView markPointCoordinatesTextView) {
            this.markPointCoordinatesTextView = markPointCoordinatesTextView;
        }

        TextView getMarkPointCoordinatesTextView() {
            return markPointCoordinatesTextView;
        }

        public TextView getMarkPointGridTextView() {
            return markPointGridTextView;
        }

        void setMarkPointGridTextView(TextView markPointGridTextView) {
            this.markPointGridTextView = markPointGridTextView;
        }
    }
}
