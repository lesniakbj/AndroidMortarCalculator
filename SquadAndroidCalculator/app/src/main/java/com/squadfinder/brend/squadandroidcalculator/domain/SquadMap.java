package com.squadfinder.brend.squadandroidcalculator.domain;

import android.app.Activity;
import android.os.Parcel;
import android.os.Parcelable;

import com.squadfinder.brend.squadandroidcalculator.domain.enums.MapArea;

import java.util.List;

/**
 * A map that is in the game of squad
 * Created by brend on 3/6/2018.
 */

public class SquadMap implements Parcelable {
    private int mapId;
    private String mapName;
    private MapArea mapArea;
    private String mapDescription;
    private String mapImage;
    private int mapWidth;
    private int mapHeight;
    private double mapScalePixelsPerMeter;
    private List<Layer> layerList;

    /**
     * Parcel Constructor
     * @param in Parcel
     */
    private SquadMap(Parcel in) {
        mapId = in.readInt();
        mapName = in.readString();
        mapDescription = in.readString();
        mapImage = in.readString();
        mapWidth = in.readInt();
        mapHeight = in.readInt();
        mapScalePixelsPerMeter = in.readDouble();
    }

    public int getId() {
        return mapId;
    }

    public void setId(int id) {
        this.mapId = id;
    }

    public String getMapName() {
        return mapName;
    }

    public void setMapName(String mapName) {
        this.mapName = mapName;
    }

    private int getMapWidth() {
        return mapWidth;
    }

    public void setMapWidth(int mapWidth) {
        this.mapWidth = mapWidth;
    }

    private int getMapHeight() {
        return mapHeight;
    }

    public void setMapHeight(int mapHeight) {
        this.mapHeight = mapHeight;
    }

    public List<Layer> getLayerList() {
        return layerList;
    }

    public void setLayerList(List<Layer> layerList) {
        this.layerList = layerList;
    }

    public String getMapImage() {
        return mapImage;
    }

    public void setMapImage(String mapImageResource) {
        this.mapImage = mapImageResource;
    }

    public void setMapDescription(String mapDescription) {
        this.mapDescription = mapDescription;
    }

    public String getMapDescription() {
        return mapDescription;
    }

    public int getMapId() {
        return mapId;
    }

    public void setMapId(int mapId) {
        this.mapId = mapId;
    }

    public MapArea getMapArea() {
        return mapArea;
    }

    public void setMapArea(MapArea mapArea) {
        this.mapArea = mapArea;
    }

    public double getMapScalePixelsPerMeter() {
        return mapScalePixelsPerMeter;
    }

    public void setMapScalePixelsPerMeter(double mapScalePixelsPerMeter) {
        this.mapScalePixelsPerMeter = mapScalePixelsPerMeter;
    }

    @Override
    public String toString() {
        return "SquadMap{" +
                "mapId=" + mapId +
                ", mapName='" + mapName + '\'' +
                ", mapArea=" + mapArea +
                ", mapDescription='" + mapDescription + '\'' +
                ", mapImage='" + mapImage + '\'' +
                ", mapWidth=" + mapWidth +
                ", mapHeight=" + mapHeight +
                ", mapScalePixesPerMeters=" + mapScalePixelsPerMeter +
                ", layerList=" + layerList +
                '}';
    }

    public int getMapImageResourceId(Activity context) {
        String mapImg = getMapName().toLowerCase().replace("'", "").replace(" ", "_") + "_map_min";
        return context.getResources().getIdentifier(mapImg, "drawable", context.getPackageName());
    }

    public String getDimensionString() {
        return Integer.toString(getMapWidth()) + "m x " + Integer.toString(getMapHeight()) + "m";
    }

    // ==============================
    //      PARCELABLE IMP
    // ==============================
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mapId);
        dest.writeString(mapName);
        dest.writeString(mapDescription);
        dest.writeString(mapImage);
        dest.writeInt(mapWidth);
        dest.writeInt(mapHeight);
        dest.writeDouble(mapScalePixelsPerMeter);
    }

    public static final Creator<SquadMap> CREATOR = new Creator<SquadMap>() {
        @Override
        public SquadMap createFromParcel(Parcel in) {
            return new SquadMap(in);
        }

        @Override
        public SquadMap[] newArray(int size) {
            return new SquadMap[size];
        }
    };
}
