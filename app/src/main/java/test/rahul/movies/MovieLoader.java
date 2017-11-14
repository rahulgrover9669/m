package test.rahul.movies.movies;

import android.content.Context;
import android.content.CursorLoader;
import android.net.Uri;

/**
 * Created by Rahul on 11/11/2017.
 */

public class MovieLoader extends CursorLoader {

    public static MovieLoader newAllArticlesInstance(Context context) {
        return new MovieLoader(context, ItemsContract.Items.buildDirUri());
    }

    public static MovieLoader newInstanceForItemId(Context context, long itemId) {
        return new MovieLoader(context, ItemsContract.Items.buildItemUri(itemId));
    }

    private MovieLoader(Context context, Uri uri) {
        super(context, uri, null, null, null, ItemsContract.Items.DEFAULT_SORT);
    }
    public MovieLoader(Context context) {
        super(context);
    }

    public MovieLoader(Context context, Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        super(context, uri, projection, selection, selectionArgs, sortOrder);
    }
    public interface Query{

      //  String[] PROJECTION = {MyContentProvider.MovieDatabase.ID,MyContentProvider.MovieDatabase.THUMB_URL,MyContentProvider.MovieDatabase.DESCRIPTION,MyContentProvider.MovieDatabase.RELEASE_DATE,MyContentProvider.MovieDatabase.TITLE};
    }

}
