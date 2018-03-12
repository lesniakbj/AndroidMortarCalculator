package com.squadfinder.brend.squadandroidcalculator.util;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;

import com.squadfinder.brend.squadandroidcalculator.R;
import com.squadfinder.brend.squadandroidcalculator.domain.calc.MarkPoint;
import com.squadfinder.brend.squadandroidcalculator.domain.enums.PointType;

import java.util.List;

/**
 * Created by brend on 3/10/2018.
 */

public class ImageDrawUtil {
    private static final int POINT_WIDTH = 12;

    public static Drawable fillImageViewMarkPoints(Activity context, Drawable workingDrawable, ImageView imageView, List<MarkPoint> points) {
        if(points == null) {
            return null;
        }

        // Get the bitmap
        BitmapDrawable bd = (BitmapDrawable)workingDrawable;
        if(bd != null) {
            Bitmap bitmap = bd.getBitmap();

            // Add points to the canvas
            Canvas c = new Canvas(bitmap);
            for (int i = 0; i < points.size(); i++) {
                MarkPoint p = points.get(i);
                drawSinglePoint(context, c, p);
                addSnapshotImage(context, bitmap, p);
            }

            imageView.setImageBitmap(bitmap);
            return new BitmapDrawable(context.getResources(), bitmap);
        }

        return null;
    }

    private static void addSnapshotImage(Activity context, Bitmap bitmap, MarkPoint p) {
        if(p.getMarkSnapshot() == null) {
            float offX = p.getPointCoordinates().x;
            float offY = p.getPointCoordinates().y;
            final int PAD = 75;

            Bitmap snap = Bitmap.createBitmap(bitmap, (int)(offX - PAD), (int)(offY - PAD), PAD * 2, PAD * 2);
            p.setMarkSnapshot(new BitmapDrawable(context.getResources(), snap));
        }
    }

    private static void drawSinglePoint(Activity context, Canvas c, MarkPoint point) {
        PointF p = point.getPointCoordinates();
        RectF rect = new RectF(p.x - POINT_WIDTH, p.y - POINT_WIDTH, p.x + POINT_WIDTH, p.y + POINT_WIDTH);
        Paint basePaint = getPaintForPoint(context, point);
        c.drawRect(rect, basePaint);

        Paint outline = new Paint();
        outline.setStyle(Paint.Style.STROKE);
        outline.setColor(Color.BLACK);
        outline.setStrokeWidth(2);
        c.drawRect(rect, outline);
    }

    public static Drawable circleMarkPoint(Activity context, Drawable drawable, MarkPoint point) {
        BitmapDrawable bd = (BitmapDrawable)drawable;
        if(bd != null) {
            Bitmap bitmap = bd.getBitmap();
            Canvas c = new Canvas(bitmap);
            circleSinglePoint(context, c, point);
            return new BitmapDrawable(context.getResources(), bitmap);
        }
        return null;
    }

    private static void circleSinglePoint(Activity context, Canvas c, MarkPoint point) {
        PointF p = point.getPointCoordinates();
        Paint basePaint = getPaintForPoint(context, point);
        basePaint.setStyle(Paint.Style.STROKE);
        c.drawCircle(p.x, p.y, POINT_WIDTH + 5, basePaint);
    }

    private static Paint getPaintForPoint(Activity context, MarkPoint point) {
        Paint basePaint = new Paint();
        basePaint.setColor(point.getPointType() == PointType.MORTAR ? context.getResources().getColor(R.color.colorLightGreen) : context.getResources().getColor(R.color.colorLightRed));
        basePaint.setStyle(Paint.Style.FILL);
        basePaint.setAntiAlias(true);
        basePaint.setStrokeWidth(4);
        basePaint.setTextSize(24);
        return basePaint;
    }

    public static Drawable fillImageViewGridLines(Activity context, Drawable drawable, double majorGridDistancePx, double minorGridDistancePx, int width, int height) {
        // Get the bitmap
        BitmapDrawable bd = (BitmapDrawable)drawable;
        if(bd != null) {
            Bitmap bitmap = bd.getBitmap();
            Canvas c = new Canvas(bitmap);
            drawGridLines(context, c, majorGridDistancePx, minorGridDistancePx, width, height);
            return new BitmapDrawable(context.getResources(), bitmap);
        }

        return null;
    }

    private static void drawGridLines(Activity context, Canvas c, double majorGridDistancePx, double minorGridDistancePx, int width, int height) {
        Paint paint = new Paint();
        paint.setColor(ContextCompat.getColor(context, R.color.colorBlackTransparent));
        paint.setAntiAlias(true);
        paint.setStrokeWidth(4);

        double curMajor = majorGridDistancePx;
        while(curMajor < width) {
            float fMajor = (float) curMajor;
            c.drawLine(fMajor, 0, fMajor, height, paint);
            c.drawLine(0, fMajor, width, fMajor, paint);
            curMajor += majorGridDistancePx;
        }

        paint.setColor(ContextCompat.getColor(context, R.color.colorGrayTransparent));
        paint.setStrokeWidth(2);
        double curMinor = minorGridDistancePx;
        int iter = 1;
        while(curMinor < width) {
            float fMinor = (float) curMinor;
            if(iter % 3 != 0) {
                c.drawLine(fMinor, 0, fMinor, height, paint);
                c.drawLine(0, fMinor, width, fMinor, paint);
            }
            curMinor += minorGridDistancePx;
            iter++;
        }
    }

    public static Drawable connectMarkPoints(Activity context, Drawable drawable, List<MarkPoint> markPoints) {
        // Get the bitmap
        BitmapDrawable bd = (BitmapDrawable)drawable;
        if(bd != null) {
            Bitmap bitmap = bd.getBitmap();

            // Connect our points
            Canvas c = new Canvas(bitmap);
            for (int i = 0; i < markPoints.size(); i++) {
                MarkPoint p = markPoints.get(i);
                List<MarkPoint> mpList = p.getMappedPoints();
                if(mpList != null) {
                    for(MarkPoint p2 : mpList) {
                        drawPointConnection(context, c, p, p2);
                    }
                }
            }

            return new BitmapDrawable(context.getResources(), bitmap);
        }

        return null;
    }

    private static void drawPointConnection(Activity context, Canvas c, MarkPoint p1, MarkPoint p2) {
        Paint p = getPaintForPoint(context, p1);
        p.setColor(context.getResources().getColor(R.color.colorLightGreenTransparent));
        c.drawLine(p1.getPointCoordinates().x, p1.getPointCoordinates().y, p2.getPointCoordinates().x, p2.getPointCoordinates().y, p);
    }
}
