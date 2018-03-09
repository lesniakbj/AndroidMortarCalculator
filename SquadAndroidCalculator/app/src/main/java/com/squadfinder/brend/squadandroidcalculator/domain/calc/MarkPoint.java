package com.squadfinder.brend.squadandroidcalculator.domain.calc;

import android.graphics.PointF;

import com.squadfinder.brend.squadandroidcalculator.domain.enums.PointType;

/**
 * Created by brend on 3/9/2018.
 */
public class MarkPoint {
    private Integer id;
    private PointF pointCoordinates;
    private PointType pointType;

    public MarkPoint(PointF pointCoordinates, PointType pointType) {
        this.pointCoordinates = pointCoordinates;
        this.pointType = pointType;
    }

    public MarkPoint(float x, float y, PointType pointType) {
        this.pointCoordinates = new PointF(x, y);
        this.pointType = pointType;
    }

    public PointF getPointCoordinates() {
        return pointCoordinates;
    }

    public void setPointCoordinates(PointF pointCoordinates) {
        this.pointCoordinates = pointCoordinates;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public PointType getPointType() {
        return pointType;
    }

    public void setPointType(PointType pointType) {
        this.pointType = pointType;
    }

    @Override
    public String toString() {
        return "MarkPoint{" +
                "id=" + id +
                ", pointCoordinates=" + pointCoordinates +
                ", pointType=" + pointType +
                '}';
    }
}
