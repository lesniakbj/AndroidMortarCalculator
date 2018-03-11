package com.squadfinder.brend.squadandroidcalculator.application;

import android.app.Activity;
import android.app.Application;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.squadfinder.brend.squadandroidcalculator.domain.SquadMap;
import com.squadfinder.brend.squadandroidcalculator.domain.calc.MarkPoint;
import com.squadfinder.brend.squadandroidcalculator.domain.enums.PointType;
import com.squadfinder.brend.squadandroidcalculator.util.ImageDrawUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by brend on 3/10/2018.
 */

public class MortarCalculatorApplication extends Application {
    private SquadMap currentMap;
    private List<MarkPoint> currentPointList;
    private MarkPoint currentEditMarkPoint;
    private Drawable currentMapDrawable;

    private static final int MARK_IMAGE_WIDTH = 2048;
    private static final int MARK_IMAGE_HEIGHT = 2048;

    public SquadMap getCurrentMap() {
        return currentMap;
    }

    public void setCurrentMap(SquadMap currentMap) {
        this.currentMap = currentMap;
    }

    public void addMarkPoint(float x, float y, PointType pointType) {
        if(currentPointList == null) {
            currentPointList = new ArrayList<>();
        }
        currentPointList.add(new MarkPoint(x, y, pointType));
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
        currentMapDrawable = null;
        currentEditMarkPoint = null;
    }
}
