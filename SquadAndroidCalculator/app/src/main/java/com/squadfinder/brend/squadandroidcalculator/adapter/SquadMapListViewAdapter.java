package com.squadfinder.brend.squadandroidcalculator.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squadfinder.brend.squadandroidcalculator.R;
import com.squadfinder.brend.squadandroidcalculator.domain.SquadMap;

/**
 *
 * Created by brend on 3/6/2018.
 */

public class SquadMapListViewAdapter extends ArrayAdapter<SquadMap> {
    private final Activity context;
    private final SquadMap[] maps;

    public SquadMapListViewAdapter(Activity context, SquadMap[] maps) {
        super(context, -1, maps);
        this.context = context;
        this.maps = maps;
    }

    @NonNull
    @Override
    public View getView(int i, View view, @NonNull ViewGroup viewGroup) {
        View rowView = view;
        MapViewHolder viewHolder;
        if(rowView == null) {
            viewHolder = new MapViewHolder();

            rowView = context.getLayoutInflater().inflate(R.layout.scroll_view_item, null);
            viewHolder.setMapImage(rowView.findViewById(R.id.scrollViewMapImage));
            viewHolder.setMapTitle(rowView.findViewById(R.id.scrollViewMapName));
            viewHolder.setMapDescription(rowView.findViewById(R.id.scrollViewMapDescription));

            rowView.setTag(viewHolder);
        }

        viewHolder = (MapViewHolder)rowView.getTag();
        viewHolder.getMapTitle().setText(maps[i].getMapName());
        viewHolder.getMapDescription().setText(maps[i].getMapDescription());

        // Load the image
        Glide.with(context).load(maps[i].getMapImageResourceId(context)).into(viewHolder.getMapImage());
        return rowView;
    }

    private static class MapViewHolder {
        private ImageView mapImage;
        private TextView mapTitle;
        private TextView mapDescription;

        ImageView getMapImage() {
            return mapImage;
        }

        void setMapImage(ImageView mapImage) {
            this.mapImage = mapImage;
        }

        TextView getMapTitle() {
            return mapTitle;
        }

        void setMapTitle(TextView mapTitle) {
            this.mapTitle = mapTitle;
        }

        TextView getMapDescription() {
            return mapDescription;
        }

        void setMapDescription(TextView mapDescription) {
            this.mapDescription = mapDescription;
        }
    }
}
