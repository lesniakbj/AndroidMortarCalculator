package com.squadfinder.brend.squadandroidcalculator.application;

import android.app.Activity;
import android.app.Application;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squadfinder.brend.squadandroidcalculator.domain.SquadMap;
import com.squadfinder.brend.squadandroidcalculator.domain.calc.MapGridUtil;
import com.squadfinder.brend.squadandroidcalculator.domain.calc.MarkPoint;
import com.squadfinder.brend.squadandroidcalculator.domain.enums.PointType;
import com.squadfinder.brend.squadandroidcalculator.util.ImageDrawUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
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

    // For Dragging
    private static MarkPoint draggedMarkPoint;

    private static final int MARK_IMAGE_WIDTH = 2048;
    private static final int MARK_IMAGE_HEIGHT = 2048;

    public void clear() {
        currentMap = null;
        currentPointList = null;
        currentEditMarkPoint = null;
        currentMapDrawable = null;
        currentMarkPointId = 0;
        targetMappings = null;
        mortarArrayAdapter = null;
        targetArrayAdapter = null;
        draggedMarkPoint = null;
    }

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
        MarkPoint mp = new MarkPoint(currentMarkPointId, x, y, pointType);
        String gridX = MapGridUtil.getHorizontalGridMajor(currentMap.getMapScalePixelsPerMeter(), x);
        String gridY = MapGridUtil.getVerticalGridMajor(currentMap.getMapScalePixelsPerMeter(), y);
        String gridKeypad = MapGridUtil.getGridKeypad(currentMap.getMapScalePixelsPerMeter(), x, y);
        String gridSubKeypadKeypad = MapGridUtil.getGridSubKeypad(currentMap.getMapScalePixelsPerMeter(), x, y);
        mp.setMapGrid(String.format(Locale.getDefault(), "%s%s-%s-%s", gridX, gridY, gridKeypad, gridSubKeypadKeypad));
        currentPointList.add(mp);
        currentMarkPointId++;
    }

    public List<MarkPoint> getMarkPointList() {
        return currentPointList;
    }

    public void setMarkPointToEdit(MarkPoint markPointToEdit) {
        MortarCalculatorApplication.currentEditMarkPoint = markPointToEdit;
    }

    public MarkPoint getMarkPointToEdit() {
        return currentEditMarkPoint;
    }

    public Drawable getCurrentMapDrawable() {
        return currentMapDrawable;
    }

    public void setCurrentMapDrawable(Activity activity, Drawable currentMapDrawable) {
        boolean firstSet = false;
        if(MortarCalculatorApplication.currentMapDrawable == null) {
            firstSet = true;
        }

        MortarCalculatorApplication.currentMapDrawable = currentMapDrawable;

        if(firstSet) {
            Bitmap bitmap = Bitmap.createScaledBitmap(((BitmapDrawable) MortarCalculatorApplication.currentMapDrawable).getBitmap(), MARK_IMAGE_WIDTH, MARK_IMAGE_HEIGHT, true);
            MortarCalculatorApplication.currentMapDrawable = new BitmapDrawable(getResources(), bitmap);
            addGridLinesToMap(activity);
        }
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
            setCurrentMapDrawable(activity, updated);
        }
    }

    public void circleMarkPoint(Activity activity, MarkPoint editPoint) {
        Drawable updated = ImageDrawUtil.circleMarkPoint(activity, currentMapDrawable, editPoint);
        if(updated != null) {
            setCurrentMapDrawable(activity, updated);
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

    public boolean addMortarMapping(MarkPoint mortarMark, MarkPoint targetMark) {
        if(targetMappings == null) {
            targetMappings = new HashMap<>();
        }

        if(mortarMark.getPointType() != PointType.MORTAR) {
            return false;
        }

        if(getDistanceBetweenMarkPoints(mortarMark, targetMark) > getMaxMortarDistance()) {
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
        mortarMark.addMappedPoint(targetMark);
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

    public void addGridLinesToMap(Activity activity) {
        double scale = currentMap.getMapScalePixelsPerMeter();
        double majorGridDistancePx = MapGridUtil.getMajorGridPixelDistance(scale);
        double minorGridDistancePx = MapGridUtil.getMinorGridPixelDistanceFromMajorGridPixelScale(majorGridDistancePx);

        Drawable updated = ImageDrawUtil.fillImageViewGridLines(activity, currentMapDrawable, majorGridDistancePx, minorGridDistancePx, MARK_IMAGE_WIDTH, MARK_IMAGE_HEIGHT);
        if(updated != null) {
            setCurrentMapDrawable(activity, updated);
        }
    }

    public double getDistanceBetweenMarkPoints(MarkPoint currentPoint, MarkPoint pt) {
        double dX = currentPoint.getPointCoordinates().x - pt.getPointCoordinates().x;
        double dY = currentPoint.getPointCoordinates().y - pt.getPointCoordinates().y;
        double pxDist = Math.hypot(dX, dY);
        return pxDist / currentMap.getMapScalePixelsPerMeter();
    }

    public void connectMarkPoints(Activity context) {
        Drawable updated = ImageDrawUtil.connectMarkPoints(context, currentMapDrawable, getMarkPointsByType(PointType.MORTAR));
        if(updated != null) {
            setCurrentMapDrawable(context, updated);
        }
    }

    public void removeAllMarkedAssignments() {
        targetMappings = null;
        for(MarkPoint mp : currentPointList) {
            mp.setMappedPoints(null);
        }
    }

    public void setDraggedMarkPoint(MarkPoint draggedMarkPoint) {
        MortarCalculatorApplication.draggedMarkPoint = draggedMarkPoint;
    }

    public MarkPoint getDraggedMarkPoint() {
        return draggedMarkPoint;
    }

    public double getMaxMortarDistance() {
        return 1300.0;
    }

    public double getAngleBetweenPoints(MarkPoint mortar, MarkPoint target) {
        float dY = target.getPointCoordinates().y - mortar.getPointCoordinates().y;
        float dX = target.getPointCoordinates().x - mortar.getPointCoordinates().x;
        double angle = Math.atan2(dY, dX);
        double degAngle = Math.toDegrees(angle) + 90;
        degAngle = (degAngle > 0) ? degAngle : degAngle + 360;
        Log.d("ACTIVITY", String.format("Angle: %f", degAngle));
        return degAngle;
    }
}
