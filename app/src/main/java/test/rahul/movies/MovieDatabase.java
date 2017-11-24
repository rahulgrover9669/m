package test.rahul.movies.movies;

import android.content.ContentProviderOperation;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Rahul on 13/11/2017.
 */

public class MovieDatabase extends SQLiteOpenHelper {
    public SQLiteDatabase myDB = null;
    public static final String ID = "ID";
    public static final String TITLE = "TITLE";
    public static final String RELEASE_DATE = "RELEASE_DATE";
    public static final String VOTE_COUNT = "VOTE_COUNT";
    public static final String DESCRIPTION = "DESCRIPTION";
    public static final String THUMB_URL = "THUMB_URL";
    public static final String IS_FAVT = "IS_FAVT";
    public static final String IS_VISIBLE = "IS_VISIBLE";


    public static final String DATABASE_NAME = "MOVIESDB";
    public static final String TABLE_NAME = "MOVIES";
    private static final int DB_VERSION = 1;
    private static final String CREATE_TABLE_TUTORIALS = "create table " + TABLE_NAME
            + " (" + ID + " integer primary key autoincrement, "
            + TITLE + " text not null, "
            + RELEASE_DATE + " text not null, "
            + VOTE_COUNT + " text not null, "
            + DESCRIPTION + " text not null, "
            + THUMB_URL + " text not null, "
            + IS_FAVT + " integer, "
            + IS_VISIBLE + " integer );";
    private static final String DB_SCHEMA = CREATE_TABLE_TUTORIALS;

    public MovieDatabase(Context context) {

        super(context, DATABASE_NAME, null, DB_VERSION);
        Log.d("CREATEDB", "ONCREATE2");
  //      try {
//


    //        Log.d("CREATEDB", "ONCREATE2");
     //   }
      //  catch ( Exception e){
        //    Log.d("TAG","ONCREATE2"+e.getMessage());
       // }
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("ONCREATE","ONCREATE3");
  db.execSQL(DB_SCHEMA);
        Log.d("ONCREATE","ONCREATE3CREATED");
     //   db = getWritableDatabase();
     //   Log.d("ONCREATE","ONCREATE3");


       // try {

      //  }
     //   catch (Exception e){
     //       Log.d("ONCREATE","ONCREATE3"+e.getMessage());
     //   }
     //   this.myDB = db;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
       // try {
         //   db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
           // onCreate(db);
      //  }
      //  catch (Exception e){
        //    Log.d("TAG","ONCREATE5"+e.getMessage());
       // }
    }


    public Cursor getAllRows(SQLiteDatabase sq) {
        Cursor cursor = null;
        Log.d("ONCREATE", "ONCREATE3");
        if (sq != null) {
            sq = getWritableDatabase();
            Log.d("ONCREATE","ONCREATE4");

            cursor = sq.rawQuery("SELECT * FROM movies where is_visible=1",null);
            if(cursor.getCount() == 0){
                String array = RemoteEndpointUtil.fetchJsonArray().toString();
                try {
                    JSONObject jsonObject = new JSONObject(array);
                    if(array!=null){
                        Log.d("TAG","ONCREATE6"+String.valueOf(jsonObject.getJSONArray("results").length()));
                        ContentValues contentValues = new ContentValues();
                        for(int i=0;i <jsonObject.getJSONArray("results").length(); i++){
                            String posterPath = jsonObject.getJSONArray("results").getJSONObject(i).getString("poster_path");
                            String releaseDate = jsonObject.getJSONArray("results").getJSONObject(i).getString("release_date");
                            String votes = jsonObject.getJSONArray("results").getJSONObject(i).getString("vote_average");
                            String orignalTitle = jsonObject.getJSONArray("results").getJSONObject(i).getString("original_title");
                            String overview = jsonObject.getJSONArray("results").getJSONObject(i).getString("overview");
                            contentValues.put(MovieDatabase.TITLE,orignalTitle);
                            contentValues.put(MovieDatabase.RELEASE_DATE,releaseDate);
                            contentValues.put(MovieDatabase.VOTE_COUNT,votes);
                            contentValues.put(MovieDatabase.DESCRIPTION,overview);
                            contentValues.put(MovieDatabase.THUMB_URL,posterPath);
                            contentValues.put(MovieDatabase.IS_FAVT,0);
                            contentValues.put(MovieDatabase.IS_VISIBLE,1);

                            sq.insert(TABLE_NAME,null,contentValues);
                           final Cursor cursor1 = sq.rawQuery("SELECT * FROM movies where is_visible=1",null);
                            Log.d("RETURN","RETURN"+String.valueOf(cursor1.getCount()));

                        }

                        Log.d("RETURN","RETURN");
                        cursor = sq.rawQuery("SELECT * FROM movies where is_visible=1",null);
                        Log.d("RETURN","RETURN1"+String.valueOf(cursor.getCount()));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    return  null;
                }

            }
            else {


                 cursor = sq.rawQuery("SELECT * FROM movies where is_visible=1",null);

            }

        }


        return cursor;
    }

    public Cursor SingleFavtMovie(SQLiteDatabase sqLiteDatabase, int id){
        Cursor cursor = null;

        if(sqLiteDatabase!=null){

            if(id==0){
                cursor = sqLiteDatabase.rawQuery("select * from movies limit 1",null);
            }
            else {

            }

        }


        return cursor;
    }
public Cursor updateMovies(SQLiteDatabase sqLiteDatabase, String data) throws JSONException{
    Cursor cursor = null;
    JSONObject jsonObject = null;
    JSONArray jsonArray = null;
    if(sqLiteDatabase!=null){
        sqLiteDatabase = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(MovieDatabase.IS_VISIBLE, 0);

        sqLiteDatabase.update(TABLE_NAME, values," is_visible = 1", null);
        jsonObject = new JSONObject(data);
        jsonArray = jsonObject.getJSONArray("results");
        ContentValues contentValues = new ContentValues();
        for(int i=0;i <jsonObject.getJSONArray("results").length(); i++){

            String posterPath = jsonObject.getJSONArray("results").getJSONObject(i).getString("poster_path");
            String releaseDate = jsonObject.getJSONArray("results").getJSONObject(i).getString("release_date");
            String votes = jsonObject.getJSONArray("results").getJSONObject(i).getString("vote_average");
            String orignalTitle = jsonObject.getJSONArray("results").getJSONObject(i).getString("original_title");
            String overview = jsonObject.getJSONArray("results").getJSONObject(i).getString("overview");
            contentValues.put(MovieDatabase.TITLE,orignalTitle);
            contentValues.put(MovieDatabase.RELEASE_DATE,releaseDate);
            contentValues.put(MovieDatabase.VOTE_COUNT,votes);
            contentValues.put(MovieDatabase.DESCRIPTION,overview);
            contentValues.put(MovieDatabase.THUMB_URL,posterPath);
            contentValues.put(MovieDatabase.IS_FAVT,0);
            contentValues.put(MovieDatabase.IS_VISIBLE,1);

            sqLiteDatabase.insert(TABLE_NAME,null,contentValues);
        }
        cursor = sqLiteDatabase.rawQuery("SELECT * FROM movies where is_visible=1",null);

    }
    return cursor;
}
public Cursor updateRecord(SQLiteDatabase sqLiteDatabase, String id, String isFavt){
    Cursor cursor = null;
    if(sqLiteDatabase!=null){
        sqLiteDatabase = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(MovieDatabase.IS_FAVT, Integer.parseInt(isFavt));
        sqLiteDatabase.update(TABLE_NAME, values," id = " + id + "", null);
        cursor = sqLiteDatabase.rawQuery("select * from movies where is_favt=1",null);
    }
    return cursor;

}
public Cursor AllFavtMovies(SQLiteDatabase sqLiteDatabase){
    Cursor cursor = null;
        if(sqLiteDatabase!=null){
            sqLiteDatabase = getWritableDatabase();
            cursor = sqLiteDatabase.rawQuery("select * from movies where is_favt=1 and is_visible=1",null);
        }
        Log.d("TAG","FAVT_METHOD");
    return cursor;
}
}
