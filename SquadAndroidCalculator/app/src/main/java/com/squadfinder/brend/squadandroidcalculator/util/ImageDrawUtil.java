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
            circleSinglePoint(context, drawable, c, point);

            return new BitmapDrawable(context.getResources(), mutable);
        }

        return null;
    }

    private static void circleSinglePoint(Activity context, Drawable drawable, Canvas c, MarkPoint point) {
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
}
