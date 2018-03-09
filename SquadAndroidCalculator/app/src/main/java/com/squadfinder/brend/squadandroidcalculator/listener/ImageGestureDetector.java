package com.squadfinder.brend.squadandroidcalculator.listener;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.content.ContextCompat;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.ImageView;

import com.squadfinder.brend.squadandroidcalculator.R;
import com.squadfinder.brend.squadandroidcalculator.domain.calc.MarkPoint;
import com.squadfinder.brend.squadandroidcalculator.domain.calc.PointManager;
import com.squadfinder.brend.squadandroidcalculator.domain.enums.PointType;

/**
 * Created by brend on 3/9/2018.
 */

public class ImageGestureDetector extends GestureDetector.SimpleOnGestureListener {
    private static final int POINT_WIDTH = 12;

    private final ImageView imageView;
    private final Activity activity;
    private Bitmap bitmap;

    public ImageGestureDetector(Activity activity, ImageView imageView) {
        this.activity = activity;
        this.imageView = imageView;
        checkBitmap();
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        super.onDoubleTap(e);

        // Ensure we have a filled bitmap
        checkBitmap();

        // Get the location of the long press
        float touchX = e.getX();
        float touchY = e.getY();

        // Ask the user what type of point they are placing, handle drawing there.
        AlertDialog alert = buildAlertDialog(touchX, touchY);
        alert.show();

        return true;
    }

    private AlertDialog buildAlertDialog(float x, float y) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity, android.R.style.Theme_Material_Dialog);
        return builder.setTitle("What are you marking...")
                .setItems(new String[]{"Mortar", "Target"}, (dialog, which) -> {
                    PointManager.getInstance().addPoint(x, y, which == 0 ? PointType.MORTAR : PointType.TARGET);
                    drawPoints();
                }).create();
    }

    private void checkBitmap() {
        if(bitmap == null) {
            BitmapDrawable bd = (BitmapDrawable)imageView.getDrawable();
            if(bd != null) {
                this.bitmap = bd.getBitmap();
            }
        }
    }

    private void drawPoints() {
        // Create our working and mutable bitmaps
        Bitmap working = Bitmap.createBitmap(bitmap);
        Bitmap mutable = working.copy(Bitmap.Config.ARGB_8888, true);

        // Add points to the canvas
        Canvas c = new Canvas(mutable);
        int i = 1;
        for(MarkPoint point : PointManager.getInstance().getPointList()) {
            drawSinglePoint(c, point);
            i++;
        }

        // Set our image view to the mutated bitmap
        imageView.setImageBitmap(mutable);
    }

    private void drawSinglePoint(Canvas c, MarkPoint point) {
        PointF p = point.getPointCoordinates();
        RectF rect = new RectF(p.x - POINT_WIDTH, p.y - POINT_WIDTH, p.x + POINT_WIDTH, p.y + POINT_WIDTH);
        Paint basePaint = new Paint();
        basePaint.setColor(point.getPointType() == PointType.MORTAR ? ContextCompat.getColor(activity, R.color.colorLightGreen) : ContextCompat.getColor(activity, R.color.colorLightRed));
        basePaint.setStyle(Paint.Style.FILL);
        basePaint.setAntiAlias(true);
        basePaint.setStrokeWidth(4);
        basePaint.setTextSize(24);

        c.drawRect(rect, basePaint);
        // c.drawText(Integer.toString(i), p.x + 20, p.y + 20, basePaint);

        Paint outline = new Paint();
        outline.setStyle(Paint.Style.STROKE);
        outline.setColor(Color.BLACK);
        outline.setStrokeWidth(2);

        c.drawRect(rect, outline);
        // c.drawText(Integer.toString(i), p.x + 20, p.y + 20, outline);
    }
}