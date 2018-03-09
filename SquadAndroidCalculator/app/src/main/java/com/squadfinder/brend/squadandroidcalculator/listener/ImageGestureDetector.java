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
import android.util.Pair;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.ImageView;

import com.squadfinder.brend.squadandroidcalculator.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by brend on 3/9/2018.
 */

public class ImageGestureDetector extends GestureDetector.SimpleOnGestureListener {
    private final ImageView imageView;
    private final Activity activity;
    private Bitmap bitmap;
    private List<Pair<PointF, Paint>> paintedPoints;

    public ImageGestureDetector(Activity activity, ImageView imageView) {
        this.activity = activity;
        this.imageView = imageView;
        this.paintedPoints = new ArrayList<>();
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
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        return builder.setTitle("What are you marking...")
                .setItems(new String[]{"Mortar", "Target"}, (dialog, which) -> {
                    // Add a point based on where we touched
                    addPointToDraw(x, y, which == 0 ? R.color.colorLightGreen : R.color.colorLightRed);
                    // Draw the point list
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

    private void addPointToDraw(float x, float y, int colorId) {
        // Create a point for that location
        PointF point = new PointF(x, y);
        Paint paint = new Paint();
        paint.setColor(ContextCompat.getColor(activity, colorId));
        paint.setStrokeWidth(4);
        paint.setAntiAlias(true);
        Pair<PointF, Paint> pPair = new Pair<>(point, paint);

        // Add it to the list of known points
        if (!paintedPoints.contains(pPair)) {
            paintedPoints.add(pPair);
        }
    }

    private void drawPoints() {
        // Create our working and mutable bitmaps
        Bitmap working = Bitmap.createBitmap(bitmap);
        Bitmap mutable = working.copy(Bitmap.Config.ARGB_8888, true);

        // Add points to the canvas
        Canvas c = new Canvas(mutable);
        int i = 1;
        for(Pair<PointF, Paint> point : paintedPoints) {
            PointF p = point.first;
            Paint p2 = point.second;
            Paint workingPaint = new Paint(p2);
            workingPaint.setStyle(Paint.Style.FILL);
            RectF rect = new RectF(p.x - 12, p.y - 12, p.x + 12, p.y + 12);
            c.drawRect(rect, workingPaint);
            workingPaint.setTextSize(24);
            c.drawText(Integer.toString(i), p.x + 20, p.y + 20, workingPaint);
            workingPaint.setStyle(Paint.Style.STROKE);
            workingPaint.setColor(Color.BLACK);
            c.drawRect(rect, workingPaint);
            workingPaint.setStrokeWidth(1);
            c.drawText(Integer.toString(i), p.x + 20, p.y + 20, workingPaint);
            i++;
        }

        // Set our image view to the mutated bitmap
        imageView.setImageBitmap(mutable);
    }
}