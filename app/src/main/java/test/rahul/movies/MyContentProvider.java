package test.rahul.movies.movies;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.util.Log;

import org.json.JSONException;


public class MyContentProvider extends ContentProvider {
    private MovieDatabase movieDatabase;
    private static final String AUTHORITY = "test.rahul.movies.movies.MyContentProvider";
    public static final Uri CONTENT_URI =
            Uri.parse("content://" + AUTHORITY + "/movies");
    public static final Uri CONTENT_URI_ID =
            Uri.parse("content://" + AUTHORITY + "/movies"+"/*");

    private static final UriMatcher uriMatcher;
    private static final int ALL_MOVIES = 1;
    private static final int SINGLE_MOVIE = 2;
    private static final int FAVT_MOVIES = 3;
    private static final int FAVT_SINGLE = 4;
    private static final int UPDATE_MOVIES = 5;
    private static final int UPDATE_MOVIE = 6;
    private SQLiteOpenHelper mOpenHelper;
    private SQLiteDatabase mDB;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, "movies", ALL_MOVIES);
        uriMatcher.addURI(AUTHORITY, "movies/#", SINGLE_MOVIE);
        uriMatcher.addURI(AUTHORITY, "movies/favourite", FAVT_MOVIES);
        uriMatcher.addURI(AUTHORITY, "movies/favourite/#", FAVT_SINGLE);
        uriMatcher.addURI(AUTHORITY, "movies/update/", UPDATE_MOVIES);
        uriMatcher.addURI(AUTHORITY, "movies/update/#", UPDATE_MOVIE);

    }

    Cursor cursor = null;

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
        switch (uriMatcher.match(uri)) {
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
            mDB = movieDatabase.getWritableDatabase();


            Log.d("ONCREATE", "ONCREATE1");
        } catch (Exception e) {
            e.getMessage();

        }
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        // TODO: Implement this to handle query requests from clients.
        Log.d("TAG", "GETCURSOR5");

        if (uriMatcher.match(uri) == ALL_MOVIES) {


            cursor = movieDatabase.getAllRows(mDB);
            if (cursor.getCount() > 1) {
                cursor = movieDatabase.getAllRows(mDB);
            }


            Log.d("TAG", "CURSORCOUNT" + String.valueOf(cursor.getCount()));
            //  int count = movieDatabase.getAllRows().getCount();
            // Log.d("TAG","GETCURSOR11"+String.valueOf(count));
            // return movieDatabase.getAllRows();
            return cursor;
        } else if(uriMatcher.match(uri)==SINGLE_MOVIE){
            Log.d("TAG","SINGLE_ROW"+String.valueOf(uri));

            Log.d("TAG","PROJECTION"+projection[0]);
            Log.d("TAG","ARGS"+selectionArgs[0]);
            return cursor;

        }

        else if(uriMatcher.match(uri) == FAVT_MOVIES){
            Log.d("TAG","favt_row"+String.valueOf(uri));
            cursor = movieDatabase.AllFavtMovies(mDB);
            return cursor;
        }
        else if(uriMatcher.match(uri) == FAVT_SINGLE){
            Log.d("TAG","favt_row_single"+String.valueOf(uri));
            cursor = movieDatabase.SingleFavtMovie(mDB,Integer.parseInt(selectionArgs[0]));
            return cursor;
        }
        else if(uriMatcher.match(uri) == UPDATE_MOVIES){
            Log.d("TAG","update_query"+String.valueOf(uri));
            try {
                cursor = movieDatabase.updateMovies(mDB,selectionArgs[0]);
            } catch (JSONException e) {
                e.printStackTrace();
                Log.d("TAG","UPDATE_ERROR"+e.getMessage());
            }
            return cursor;
        }
        else if(uriMatcher.match(uri)==UPDATE_MOVIE){
            Log.d("TAG","update_query_single"+String.valueOf(uri));
            Log.d("TAG","ARG1"+selectionArgs[0]);
            Log.d("TAG","ARG1"+selectionArgs[1]);
            cursor = movieDatabase.updateRecord(mDB,selectionArgs[0],selectionArgs[1]);
            return cursor;
        }

            return null;

        //     throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }


}
