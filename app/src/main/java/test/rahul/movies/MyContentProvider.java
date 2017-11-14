package test.rahul.movies.movies;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.util.Log;

public class MyContentProvider extends ContentProvider {
    private MovieDatabase movieDatabase;
    private static final String AUTHORITY = "test.rahul.movies.movies.MyContentProvider";
    public static final Uri CONTENT_URI =
            Uri.parse("content://" + AUTHORITY + "/movies");

    private static final UriMatcher uriMatcher;
    private static final int ALL_MOVIES= 1;
    private static final int SINGLE_MOVIE = 2;
    private SQLiteOpenHelper mOpenHelper;
    private   SQLiteDatabase mDB;
    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, "movies", ALL_MOVIES);
        uriMatcher.addURI(AUTHORITY, "movies/#", SINGLE_MOVIE);
    }

    public MyContentProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        switch (uriMatcher.match(uri)){
            case ALL_MOVIES:
                return "vnd.android.cursor.dir/vnd.test.rahul.movies.movies.MyContentProvider.movies";
            case SINGLE_MOVIE:
                return "vnd.android.cursor.item/vnd.test.rahul.movies.movies.MyContentProvider.movies";
            default:
                throw new UnsupportedOperationException("Not yet implemented");
        }

    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.
        SQLiteDatabase db = movieDatabase.getWritableDatabase();
        switch (uriMatcher.match(uri)) {
            case ALL_MOVIES:
                //do nothing
                break;
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
        long id = db.insert(MovieDatabase.TABLE_NAME, null, values);
        getContext().getContentResolver().notifyChange(uri, null);
        return Uri.parse(CONTENT_URI + "/" + id);
    }

    @Override
    public boolean onCreate() {
        // TODO: Implement this to initialize your content provider on startup.



        try {
            movieDatabase = new MovieDatabase(getContext());
        mDB =   movieDatabase.getWritableDatabase();


            Log.d("ONCREATE","ONCREATE1");
        }
        catch (Exception e){
            e.getMessage();
        }
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        // TODO: Implement this to handle query requests from clients.
          Log.d("TAG","GETCURSOR5");
        if(uriMatcher.match(uri)== ALL_MOVIES){

            movieDatabase.getAllRows(mDB);
          //  int count = movieDatabase.getAllRows().getCount();
           // Log.d("TAG","GETCURSOR11"+String.valueOf(count));
           // return movieDatabase.getAllRows();
            return null;
        }
        else return null;

   //     throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }



}
