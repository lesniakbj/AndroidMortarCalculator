package com.squadfinder.brend.squadandroidcalculator.domain.calc;

import com.squadfinder.brend.squadandroidcalculator.domain.enums.PointType;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by brend on 3/8/2018.
 */

public class PointManager {
    private List<MarkPoint> pointList;
    private float distanceScale;

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

    public List<MarkPoint> getPointsByType(PointType type) {
        return pointList.stream().filter((mp) -> mp.getPointType() == type).collect(Collectors.toList());
    }

    public List<MarkPoint> getPointList() {
        return pointList;
    }

    public void setPointList(List<MarkPoint> pointList) {
        this.pointList = pointList;
    }

    public float getDistanceScale() {
        return distanceScale;
    }

    public void setDistanceScale(float distanceScale) {
        this.distanceScale = distanceScale;
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
