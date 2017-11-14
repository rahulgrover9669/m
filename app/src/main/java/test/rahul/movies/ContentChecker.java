package test.rahul.movies.movies;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;

/**
 * Created by Rahul on 13/11/2017.
 */

public class ContentChecker implements LoaderManager.LoaderCallbacks<Cursor> {
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        CursorLoader cursorLoader = null;
        try {
            cursorLoader =  new CursorLoader(MainActivity.class.newInstance().getApplicationContext(),MyContentProvider.CONTENT_URI,null,null,null,null);
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();

        }
        return cursorLoader;

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
