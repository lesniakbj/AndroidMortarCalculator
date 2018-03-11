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

            // Create our working and mutable bitmaps
            Bitmap working = Bitmap.createBitmap(bitmap);
            Bitmap mutable = working.copy(Bitmap.Config.ARGB_8888, true);

            // Add points to the canvas
            Canvas c = new Canvas(mutable);
            for (int i = 0; i < points.size(); i++) {
                drawSinglePoint(context, c, points.get(i));
            }

            // Set our image view to the mutated bitmap
            imageView.setImageBitmap(mutable);
            return new BitmapDrawable(context.getResources(), mutable);
        }

        return null;
    }

    private static void drawSinglePoint(Activity context, Canvas c, MarkPoint point) {
        PointF p = point.getPointCoordinates();
        RectF rect = new RectF(p.x - POINT_WIDTH, p.y - POINT_WIDTH, p.x + POINT_WIDTH, p.y + POINT_WIDTH);
        Paint basePaint = getPaintForPoint(context, point);
        c.drawRect(rect, basePaint);
        // c.drawText(Integer.toString(i), p.x + 20, p.y + 20, basePaint);

        Paint outline = new Paint();
        outline.setStyle(Paint.Style.STROKE);
        outline.setColor(Color.BLACK);
        outline.setStrokeWidth(2);

        c.drawRect(rect, outline);
        // c.drawText(Integer.toString(i), p.x + 20, p.y + 20, outline);
    }

    public static Drawable circleMarkPoint(Activity context, Drawable drawable, MarkPoint point) {
        // Get the bitmap
        BitmapDrawable bd = (BitmapDrawable)drawable;
        if(bd != null) {
            Bitmap bitmap = bd.getBitmap();

            // Create our working and mutable bitmaps
            Bitmap working = Bitmap.createBitmap(bitmap);
            Bitmap mutable = working.copy(Bitmap.Config.ARGB_8888, true);

            Canvas c = new Canvas(mutable);
            circleSinglePoint(context, c, point);

            return new BitmapDrawable(context.getResources(), mutable);
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
        basePaint.setColor(point.getPointType() == PointType.MORTAR ? ContextCompat.getColor(context, R.color.colorLightGreen) : ContextCompat.getColor(context, R.color.colorLightRed));
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

            // Create our working and mutable bitmaps
            Bitmap working = Bitmap.createBitmap(bitmap);
            Bitmap mutable = working.copy(Bitmap.Config.ARGB_8888, true);

            Canvas c = new Canvas(mutable);
            drawGridLines(context, c, majorGridDistancePx, minorGridDistancePx, width, height);

            return new BitmapDrawable(context.getResources(), mutable);
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
}
