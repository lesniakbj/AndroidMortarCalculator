package com.squadfinder.brend.squadandroidcalculator.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squadfinder.brend.squadandroidcalculator.R;
import com.squadfinder.brend.squadandroidcalculator.application.MortarCalculatorApplication;
import com.squadfinder.brend.squadandroidcalculator.domain.calc.MarkPoint;
import com.squadfinder.brend.squadandroidcalculator.domain.enums.PointType;
import com.squadfinder.brend.squadandroidcalculator.listener.ListViewMarkPointDragListener;

import java.text.DecimalFormat;
import java.util.List;

public class MarkPointTargetListViewAdapter extends ArrayAdapter<MarkPoint> {
    private Activity context;
    private List<MarkPoint> markPoints;

    public MarkPointTargetListViewAdapter(Activity context, List<MarkPoint> markPoints) {
        super(context, -1, markPoints);
        this.context = context;
        this.markPoints = markPoints;
    }

    @NonNull
    @Override
    public View getView(int i, View view, @NonNull ViewGroup viewGroup) {
        View rowView = view;
        MarkPointWithTargetViewHolder viewHolder;

        if(rowView == null) {
            viewHolder = new MarkPointWithTargetViewHolder();

            rowView = context.getLayoutInflater().inflate(R.layout.mark_point_list_item_layout, null);
            viewHolder.setMarkPointImageView(rowView.findViewById(R.id.markPointImageView));
            viewHolder.setMarkPointGridTextView(rowView.findViewById(R.id.markPointGridTextView));
            viewHolder.setMarkPointMappedPointsTextView(rowView.findViewById(R.id.markPointMappedPointsTextView));
            rowView.setTag(viewHolder);
        }

        viewHolder = (MarkPointWithTargetViewHolder)rowView.getTag();

        MarkPoint currentPoint = markPoints.get(i);
        viewHolder.getMarkPointImageView().setImageDrawable(currentPoint.getMarkSnapshot());
        viewHolder.getMarkPointGridTextView().setText(currentPoint.getMapGrid());
        if(currentPoint.getPointType() == PointType.MORTAR) {
            viewHolder.getMarkPointMappedPointsTextView().setText(buildTargetText(currentPoint));
        }

        rowView.setOnDragListener(new ListViewMarkPointDragListener(context, currentPoint));
        return rowView;
    }

    private String buildTargetText(MarkPoint currentPoint) {
        MortarCalculatorApplication app = (MortarCalculatorApplication) context.getApplication();
        StringBuilder b = new StringBuilder();
        List<MarkPoint> points = currentPoint.getMappedPoints();
        DecimalFormat df = new DecimalFormat("#.00");
        if(points != null) {
            for (MarkPoint pt : currentPoint.getMappedPoints()) {
                b.append(pt.getMapGrid())
                        .append(", Distance: ")
                        .append(df.format(app.getDistanceBetweenMarkPoints(currentPoint, pt)))
                        .append("m\n");
            }
        } else {
            b.append(context.getResources().getString(R.string.str_no_mapped_targets));
        }
        return b.toString();
    }

    private static class MarkPointWithTargetViewHolder {
        private ImageView markPointImageView;
        private TextView markPointGridTextView;
        private TextView markPointMappedPointsTextView;

        public ImageView getMarkPointImageView() {
            return markPointImageView;
        }

        public void setMarkPointImageView(ImageView markPointImageView) {
            this.markPointImageView = markPointImageView;
        }

        TextView getMarkPointGridTextView() {
            return markPointGridTextView;
        }

        void setMarkPointGridTextView(TextView markPointGridTextView) {
            this.markPointGridTextView = markPointGridTextView;
        }

        public TextView getMarkPointMappedPointsTextView() {
            return markPointMappedPointsTextView;
        }

        void setMarkPointMappedPointsTextView(TextView markPointMappedPointsTextView) {
            this.markPointMappedPointsTextView = markPointMappedPointsTextView;
        }
    }
}
