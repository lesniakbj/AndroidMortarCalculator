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

import java.util.List;

/**
 *
 * Created by brend on 3/6/2018.
 */

public class SquadMapListViewAdapter extends ArrayAdapter<SquadMap> {
    private final Activity context;
    private final List<SquadMap> maps;

    public SquadMapListViewAdapter(Activity context, List<SquadMap> maps) {
        super(context, -1, maps);
        this.context = context;
        this.maps = maps;
    }

    @NonNull
    @Override
    public View getView(int i, View view, @NonNull ViewGroup viewGroup) {
        View rowView = view;
        MapViewHolder viewHolder;
        SquadMap map = maps.get(i);
        if(rowView == null) {
            viewHolder = new MapViewHolder();

            rowView = context.getLayoutInflater().inflate(R.layout.map_selection_list_item_layout, null);
            viewHolder.setMapImage(rowView.findViewById(R.id.scrollViewMapImage));
            viewHolder.setMapTitle(rowView.findViewById(R.id.scrollViewMapName));
            viewHolder.setMapDescription(rowView.findViewById(R.id.scrollViewMapDescription));

            rowView.setTag(viewHolder);
        }

        viewHolder = (MapViewHolder)rowView.getTag();

        viewHolder.getMapTitle().setText(map.getMapName());
        viewHolder.getMapDescription().setText(map.getMapDescription());
        Glide.with(context).load(map.getMapImageResourceId(context)).into(viewHolder.getMapImage());
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
