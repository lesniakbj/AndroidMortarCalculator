package com.squadfinder.brend.squadandroidcalculator.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squadfinder.brend.squadandroidcalculator.R;
import com.squadfinder.brend.squadandroidcalculator.activity.map.MapCalculatedActivity;
import com.squadfinder.brend.squadandroidcalculator.application.MortarCalculatorApplication;
import com.squadfinder.brend.squadandroidcalculator.domain.calc.MarkPoint;
import com.squadfinder.brend.squadandroidcalculator.domain.calc.MortarMathUtil;

import org.w3c.dom.Text;

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
        if(rowView == null) {
            viewHolder = new MarkPointCalculatedViewHolder();

            rowView = context.getLayoutInflater().inflate(R.layout.mark_point_calculated_list_item_layout, null);
            viewHolder.setMortarImage(rowView.findViewById(R.id.mortarCalcMarkImageView));
            viewHolder.setMortarPoint(rowView.findViewById(R.id.mortarCalcMarkPoint));
            viewHolder.setTargetText(rowView.findViewById(R.id.mortarCalcTargetText));
            // viewHolder.setTargetTextDetails(rowView.findViewById(R.id.mortarCalcTargetTextDetails));
            rowView.setTag(viewHolder);
        }

        viewHolder = (MarkPointCalculatedViewHolder)rowView.getTag();

        MarkPoint currentPoint = markPoints.get(i);
        viewHolder.getMortarImage().setImageDrawable(currentPoint.getMarkSnapshot());
        viewHolder.getMortarPoint().setText(currentPoint.getMapGrid());
        viewHolder.getTargetText().setText(buildTargetText(currentPoint));
        // viewHolder.getTargetTextDetails().setText("");
        return rowView;
    }

    private static class MarkPointCalculatedViewHolder {
        TextView mortarPoint;
        TextView targetText;
        TextView targetTextDetails;
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

        public TextView getTargetTextDetails() {
            return targetTextDetails;
        }

        void setTargetTextDetails(TextView targetTextDetails) {
            this.targetTextDetails = targetTextDetails;
        }
    }

    private String buildTargetText(MarkPoint currentPoint) {
        MortarCalculatorApplication app = (MortarCalculatorApplication) context.getApplication();
        StringBuilder b = new StringBuilder();
        List<MarkPoint> points = currentPoint.getMappedPoints();
        DecimalFormat df = new DecimalFormat("#.00");
        if(points != null) {
            String prefix = "";
            for (MarkPoint pt : currentPoint.getMappedPoints()) {
                double dist = app.getDistanceBetweenMarkPoints(currentPoint, pt);
                double angle = app.getAngleBetweenPoints(currentPoint, pt);
                double mils = MortarMathUtil.getMilsFromMeters(dist);
                b.append(prefix).append(pt.getMapGrid()).append("\n")
                        .append("\t\tDistance: ").append(df.format(dist)).append("m").append("\n")
                        .append("\t\tAngle: ").append(df.format(angle)).append("deg").append("\n")
                        .append("\t\tMils: ").append(df.format(mils)).append("mils").append("\n");
                prefix = "\n";
            }
        } else {
            b.append(context.getResources().getString(R.string.str_no_mapped_targets));
        }
        return b.toString();
    }
}
