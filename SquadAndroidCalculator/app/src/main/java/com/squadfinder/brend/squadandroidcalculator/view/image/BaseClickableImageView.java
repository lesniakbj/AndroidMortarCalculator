package com.squadfinder.brend.squadandroidcalculator.view.image;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

/**
 * Created by brend on 3/12/2018.
 */

public class BaseClickableImageView extends android.support.v7.widget.AppCompatImageView {
    public BaseClickableImageView(Context context) {
        super(context);
    }

    public BaseClickableImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseClickableImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean performClick() {
        super.performClick();
        return true;
    }
}
