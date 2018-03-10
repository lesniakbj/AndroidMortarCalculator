package com.squadfinder.brend.squadandroidcalculator.domain.calc;

import android.graphics.PointF;
import android.util.Log;

import com.squadfinder.brend.squadandroidcalculator.domain.enums.PointType;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by brend on 3/8/2018.
 */

public class PointManager {
    private List<MarkPoint> pointList;
    private float metersPerPixel;

    private static PointManager instance;

    private PointManager() {}

    public static PointManager getInstance() {
        if(instance == null) {
            instance = new PointManager();
        }
        return instance;
    }

    public void addPoint(float x, float y, PointType pointType) {
        if(pointList == null) {
            pointList = new ArrayList<>(1);
        }
        pointList.add(new MarkPoint(x, y, pointType));
    }

    public double getDistanceBetweenMarkPoints(MarkPoint m1, MarkPoint m2) {
        PointF p1 = m1.getPointCoordinates();
        PointF p2 = m2.getPointCoordinates();
        float x1 = p1.x - p2.x;
        float y1 = p1.y - p2.y;
        return getScaledValue(Math.hypot(x1, y1));
    }

    public double getScaledValue(double value) {
        return value / metersPerPixel;
    }

    public List<MarkPoint> getPointsByType(PointType type) {
        List<MarkPoint> points = new ArrayList<>();
        for(MarkPoint p : pointList) {
            if(p.getPointType().equals(type)) {
                points.add(p);
            }
        }
        return points;
    }

    public List<MarkPoint> getPointList() {
        return pointList;
    }

    public void setPointList(List<MarkPoint> pointList) {
        this.pointList = pointList;
    }

    public float getMetersPerPixel() {
        return metersPerPixel;
    }

    public void setMetersPerPixel(float metersPerPixel) {
        this.metersPerPixel = metersPerPixel;
    }

    public void clearPoints() {
        pointList = new ArrayList<>(0);
    }

    @Override
    public String toString() {
        return "PointManager{" +
                "pointList=" + pointList +
                '}';
    }
}
