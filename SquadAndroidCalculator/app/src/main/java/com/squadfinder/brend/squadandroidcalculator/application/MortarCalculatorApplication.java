package com.squadfinder.brend.squadandroidcalculator.application;

import android.app.Activity;
import android.app.Application;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
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
    private SquadMap currentMap;
    private List<MarkPoint> currentPointList;
    private MarkPoint currentEditMarkPoint;
    private int currentMarkPointId;
    private static Bitmap currentMapBitmap;

    // For Target Mapping
    private Map<MarkPoint, List<MarkPoint>> targetMappings;
    private ArrayAdapter<MarkPoint> mortarArrayAdapter;
    private ArrayAdapter<MarkPoint> targetArrayAdapter;

    // For Dragging
    private MarkPoint draggedMarkPoint;

    private static final int MARK_IMAGE_WIDTH = 2048;
    private static final int MARK_IMAGE_HEIGHT = 2048;

    public void clear() {
        currentMap = null;
        currentPointList = null;
        currentEditMarkPoint = null;
        currentMapBitmap = null;
        currentMarkPointId = 0;
        targetMappings = null;
        mortarArrayAdapter = null;
        targetArrayAdapter = null;
        draggedMarkPoint = null;
        Glide.get(this).clearMemory();
    }

    public SquadMap getCurrentMap() {
        return currentMap;
    }

    public void setCurrentMap(SquadMap currentMap) {
        this.currentMap = currentMap;
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
        this.currentEditMarkPoint = markPointToEdit;
    }

    public MarkPoint getMarkPointToEdit() {
        return currentEditMarkPoint;
    }

    public Bitmap getCurrentMapBitmap() {
        return currentMapBitmap;
    }

    public void setCurrentMapBitmap(Activity activity, Bitmap bitmap) {
        boolean firstSet = false;
        if(currentMapBitmap == null) {
            firstSet = true;
        }
        currentMapBitmap = bitmap;

        if(firstSet) {
            currentMapBitmap = Bitmap.createScaledBitmap(currentMapBitmap, MARK_IMAGE_WIDTH, MARK_IMAGE_HEIGHT, true);
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
        Bitmap updated = ImageDrawUtil.fillImageViewMarkPoints(activity, currentMapBitmap, imageView, points);
        if(updated != null) {
            // setCurrentMapDrawable(activity, updated);
            setCurrentMapBitmap(activity, updated);
        }
    }

    public void circleMarkPoint(Activity activity, MarkPoint editPoint) {
        Bitmap updated = ImageDrawUtil.circleMarkPoint(activity, currentMapBitmap, editPoint);
        if(updated != null) {
            setCurrentMapBitmap(activity, updated);
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

        Bitmap updated = ImageDrawUtil.fillImageViewGridLines(activity, currentMapBitmap, majorGridDistancePx, minorGridDistancePx, MARK_IMAGE_WIDTH, MARK_IMAGE_HEIGHT);
        if(updated != null) {
            setCurrentMapBitmap(activity, updated);
        }
    }

    public double getDistanceBetweenMarkPoints(MarkPoint currentPoint, MarkPoint pt) {
        double dX = currentPoint.getPointCoordinates().x - pt.getPointCoordinates().x;
        double dY = currentPoint.getPointCoordinates().y - pt.getPointCoordinates().y;
        double pxDist = Math.hypot(dX, dY);
        return pxDist / currentMap.getMapScalePixelsPerMeter();
    }

    public void connectMarkPoints(Activity context) {
        Bitmap updated = ImageDrawUtil.connectMarkPoints(context, currentMapBitmap, getMarkPointsByType(PointType.MORTAR));
        if(updated != null) {
            setCurrentMapBitmap(context, updated);
        }
    }

    public void removeAllMarkedAssignments() {
        targetMappings = null;
        if(currentPointList != null) {
            for (MarkPoint mp : currentPointList) {
                mp.setMappedPoints(null);
            }
        }
    }

    public void setDraggedMarkPoint(MarkPoint draggedMarkPoint) {
        this.draggedMarkPoint = draggedMarkPoint;
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

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        Glide.get(this).clearMemory();
    }
}
