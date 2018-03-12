package com.squadfinder.brend.squadandroidcalculator.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squadfinder.brend.squadandroidcalculator.R;
import com.squadfinder.brend.squadandroidcalculator.application.MortarCalculatorApplication;
import com.squadfinder.brend.squadandroidcalculator.domain.calc.MarkPoint;
import com.squadfinder.brend.squadandroidcalculator.domain.calc.MortarMathUtil;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by brend on 3/11/2018.
 */

public class MarkPointCalculatedListViewAdapter extends ArrayAdapter<MarkPoint> {
    private Activity context;
    private List<MarkPoint> markPoints;

    public MarkPointCalculatedListViewAdapter(Activity context, List<MarkPoint> markPoints) {
        super(context, -1, markPoints);
        this.context = context;
        this.markPoints = markPoints;
    }

    @NonNull
    @Override
    public View getView(int i, View view, @NonNull ViewGroup viewGroup) {
        View rowView = view;
        MarkPointCalculatedViewHolder viewHolder;
        MarkPoint currentPoint = markPoints.get(i);
        if(rowView == null) {
            viewHolder = new MarkPointCalculatedViewHolder();

            rowView = context.getLayoutInflater().inflate(R.layout.mark_point_calculated_list_item_layout, null);
            viewHolder.setMortarImage(rowView.findViewById(R.id.mortarCalcMarkImageView));
            viewHolder.setMortarPoint(rowView.findViewById(R.id.mortarCalcMarkPoint));
            viewHolder.setTargetTextDetails(rowView.findViewById(R.id.mortarCalcTargetLinearLayout));

            // Only populate target details the first time, so we don't duplicate
            for (MarkPoint pt : currentPoint.getMappedPoints()) {
                View v = buildTargetText(currentPoint, pt);
                viewHolder.getTargetTextDetails().addView(v);
            }

            rowView.setTag(viewHolder);
        }

        viewHolder = (MarkPointCalculatedViewHolder)rowView.getTag();

        viewHolder.getMortarImage().setImageDrawable(currentPoint.getMarkSnapshot());
        viewHolder.getMortarPoint().setText(currentPoint.getMapGrid());
        return rowView;
    }

    private static class MarkPointCalculatedViewHolder {
        TextView mortarPoint;
        TextView targetText;
        LinearLayout targetTextDetails;
        ImageView mortarImage;

        TextView getMortarPoint() {
            return mortarPoint;
        }

        void setMortarPoint(TextView mortarPoint) {
            this.mortarPoint = mortarPoint;
        }

        ImageView getMortarImage() {
            return mortarImage;
        }

        void setMortarImage(ImageView mortarImage) {
            this.mortarImage = mortarImage;
        }


        TextView getTargetText() {
            return targetText;
        }

        void setTargetText(TextView targetText) {
            this.targetText = targetText;
        }

        public LinearLayout getTargetTextDetails() {
            return targetTextDetails;
        }

        void setTargetTextDetails(LinearLayout targetTextDetails) {
            this.targetTextDetails = targetTextDetails;
        }
    }

    private View buildTargetText(MarkPoint mortar, MarkPoint target) {
        MortarCalculatorApplication app = (MortarCalculatorApplication) context.getApplication();

        View item = context.getLayoutInflater().inflate(R.layout.mortar_calculation_item_layout, null);
        TextView grid = item.findViewById(R.id.calcItemGrid);
        TextView distance = item.findViewById(R.id.calcItemDistance);
        TextView angle = item.findViewById(R.id.calcItemAngle);
        TextView mils = item.findViewById(R.id.calcItemMils);

        DecimalFormat df = new DecimalFormat("#.00");
        grid.setText(target.getMapGrid());
        distance.setText("Distance: " + df.format(app.getDistanceBetweenMarkPoints(mortar, target)) + "m");
        angle.setText("Angle: " + df.format(app.getAngleBetweenPoints(mortar, target)) + "degs");
        mils.setText("Mils: " + df.format(MortarMathUtil.getMilsFromMeters(app.getDistanceBetweenMarkPoints(mortar, target))));

        return item;
    }
}
