package com.squadfinder.brend.squadandroidcalculator.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
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
            viewHolder.setMarkPointImageView(rowView.findViewById(R.id.markPointImageView));
            viewHolder.setMarkPointGridTextView(rowView.findViewById(R.id.markPointGridTextView));
            rowView.setTag(viewHolder);
        }

        viewHolder = (MarkPointViewHolder)rowView.getTag();

        MarkPoint currentPoint = markPoints.get(i);
        viewHolder.getMarkPointImageView().setImageDrawable(currentPoint.getMarkSnapshot());
        viewHolder.getMarkPointGridTextView().setText(currentPoint.getMapGrid());
        return rowView;
    }

    private static class MarkPointViewHolder {
        private ImageView markPointImageView;
        private TextView markPointGridTextView;

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
    }
}
