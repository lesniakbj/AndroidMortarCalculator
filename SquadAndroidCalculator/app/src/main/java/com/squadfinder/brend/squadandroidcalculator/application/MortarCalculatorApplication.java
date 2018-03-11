package com.squadfinder.brend.squadandroidcalculator.application;

import android.app.Activity;
import android.app.Application;
import android.graphics.drawable.Drawable;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squadfinder.brend.squadandroidcalculator.domain.SquadMap;
import com.squadfinder.brend.squadandroidcalculator.domain.calc.MarkPoint;
import com.squadfinder.brend.squadandroidcalculator.domain.enums.PointType;
import com.squadfinder.brend.squadandroidcalculator.util.ImageDrawUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by brend on 3/10/2018.
 */

public class MortarCalculatorApplication extends Application {
    private static SquadMap currentMap;
    private static List<MarkPoint> currentPointList;
    private static MarkPoint currentEditMarkPoint;
    private static Drawable currentMapDrawable;
    private static int currentMarkPointId;

    // For Target Mapping
    private static Map<MarkPoint, List<MarkPoint>> targetMappings;
    private static ArrayAdapter<MarkPoint> mortarArrayAdapter;
    private static ArrayAdapter<MarkPoint> targetArrayAdapter;

    private static final int MARK_IMAGE_WIDTH = 2048;
    private static final int MARK_IMAGE_HEIGHT = 2048;

    public SquadMap getCurrentMap() {
        return currentMap;
    }

    public void setCurrentMap(SquadMap currentMap) {
        MortarCalculatorApplication.currentMap = currentMap;
    }

    public void addMarkPoint(float x, float y, PointType pointType) {
        if(currentPointList == null) {
            currentPointList = new ArrayList<>();
            currentMarkPointId = 1;
        }
        currentPointList.add(new MarkPoint(currentMarkPointId, x, y, pointType));
        currentMarkPointId++;
    }

    public List<MarkPoint> getMarkPointList() {
        return currentPointList;
    }

    public void setMarkPointToEdit(MarkPoint markPointToEdit) {
        this.currentEditMarkPoint = markPointToEdit;
    }

    public MarkPoint getMarkPointToEdit() {
        return currentEditMarkPoint;
    }

    public Drawable getCurrentMapDrawable() {
        return currentMapDrawable;
    }

    public void setCurrentMapDrawable(Drawable currentMapDrawable) {
        this.currentMapDrawable = currentMapDrawable;
    }

    public void deleteMarkPoint(MarkPoint editPoint) {
        currentPointList.remove(editPoint);
    }

    public static int getMarkImageWidth() {
        return MARK_IMAGE_WIDTH;
    }

    public static int getMarkImageHeight() {
        return MARK_IMAGE_HEIGHT;
    }

    public void fillImageViewMarkPoints(Activity activity, ImageView imageView, List<MarkPoint> points) {
        Drawable updated = ImageDrawUtil.fillImageViewMarkPoints(activity, currentMapDrawable, imageView, points);
        if(updated != null) {
            setCurrentMapDrawable(updated);
        }
    }

    public void circleMarkPoint(Activity activity, MarkPoint editPoint) {
        Drawable updated = ImageDrawUtil.circleMarkPoint(activity, currentMapDrawable, editPoint);
        if(updated != null) {
            setCurrentMapDrawable(updated);
        }
    }

    public List<MarkPoint> getMarkPointsByType(PointType type) {
        List<MarkPoint> points = new ArrayList<>();
        if(currentPointList == null) {
            return points;
        }

        for(MarkPoint p : currentPointList) {
            if(p.getPointType().equals(type)) {
                points.add(p);
            }
        }
        return points;
    }

    public void clear() {
       currentMap = null;
       currentPointList = null;
       currentEditMarkPoint = null;
       currentMapDrawable = null;
       targetMappings = null;
    }

    public boolean addMortarMapping(MarkPoint mortarMark, MarkPoint targetMark) {
        if(targetMappings == null) {
            targetMappings = new HashMap<>();
        }

        if(mortarMark.getPointType() != PointType.MORTAR) {
            return false;
        }

        List<MarkPoint> currentTargets;
        if(targetMappings.containsKey(mortarMark)) {
            currentTargets = targetMappings.get(mortarMark);
        } else {
            currentTargets = new ArrayList<>();
        }
        currentTargets.add(targetMark);
        targetMappings.put(mortarMark, currentTargets);
        return true;
    }

    public MarkPoint getMarkPointByStringId(String id) {
        for(MarkPoint mp : currentPointList) {
            if (mp.getId().equals(Integer.parseInt(id))) {
                return mp;
            }
        }
        return null;
    }

    public void setListViewAdapterForType(ArrayAdapter<MarkPoint> adapter, PointType type) {
        if(type == PointType.MORTAR) {
            mortarArrayAdapter = adapter;
        } else {
            targetArrayAdapter = adapter;
        }
    }

    public ArrayAdapter<MarkPoint> getListViewAdapterForType(PointType type) {
        if(type == PointType.MORTAR) {
            return mortarArrayAdapter;
        }
        return targetArrayAdapter;
    }

    public Map<MarkPoint, List<MarkPoint>> getTargetMappings() {
        return targetMappings;
    }
}
