package test.rahul.movies.movies;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import org.json.JSONArray;

/**
 * Created by Rahul on 28/10/2017.
 */

public abstract class MovieAsyncTaskLoader extends AsyncTaskLoader {
    public MovieAsyncTaskLoader(Context context) {
        super(context);
    }

    @Override
    public JSONArray loadInBackground() {
        return null;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
    }
}
