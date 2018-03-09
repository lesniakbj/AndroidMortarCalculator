package com.squadfinder.brend.squadandroidcalculator.domain;

import android.app.Activity;

import com.squadfinder.brend.squadandroidcalculator.domain.enums.MapArea;

import java.io.Serializable;
import java.util.List;

/**
 * A map that is in the game of squad
 * Created by brend on 3/6/2018.
 */

public class SquadMap implements Serializable {
    private int mapId;
    private String mapName;
    private MapArea mapArea;
    private String mapDescription;
    private String mapImage;
    private int mapWidth;
    private int mapHeight;
    private float mapScalePixesToMeters;
    private List<Layer> layerList;

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

    public int getMapWidth() {
        return mapWidth;
    }

    public void setMapWidth(int mapWidth) {
        this.mapWidth = mapWidth;
    }

    public int getMapHeight() {
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

    public float getMapScalePixesToMeters() {
        return mapScalePixesToMeters;
    }

    public void setMapScalePixesToMeters(float mapScalePixesToMeters) {
        this.mapScalePixesToMeters = mapScalePixesToMeters;
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
                ", mapScalePixesToMeters=" + mapScalePixesToMeters +
                ", layerList=" + layerList +
                '}';
    }

    public int getMapImageResourceId(Activity context) {
        String mapImg = getMapName().toLowerCase().replace("'", "").replace(" ", "_") + "_map";
        return context.getResources().getIdentifier(mapImg, "drawable", context.getPackageName());
    }

    public String getDimensionString() {
        return Integer.toString(getMapWidth()) + "m x " + Integer.toString(getMapHeight()) + "m";
    }
}
