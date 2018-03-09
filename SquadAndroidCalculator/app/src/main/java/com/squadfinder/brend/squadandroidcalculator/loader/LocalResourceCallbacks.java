package com.squadfinder.brend.squadandroidcalculator.loader;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;

/**
 * Created by brend on 3/6/2018.
 */

public class LocalResourceCallbacks implements LoaderManager.LoaderCallbacks {
    private Activity activity;

    public LocalResourceCallbacks(Activity activity) {
        this.activity = activity;
    }

    @Override
    public Loader onCreateLoader(int i, Bundle bundle) {
        // return new CursorLoader(activity);
        return null;
    }

    @Override
    public void onLoadFinished(Loader loader, Object o) {

    }

    @Override
    public void onLoaderReset(Loader loader) {

    }
}
