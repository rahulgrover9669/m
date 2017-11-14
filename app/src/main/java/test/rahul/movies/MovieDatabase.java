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

    public static final String DATABASE_NAME = "MOVIESDB";
    public static final String TABLE_NAME = "MOVIES";
    private static final int DB_VERSION = 1;
    private static final String CREATE_TABLE_TUTORIALS = "create table " + TABLE_NAME
            + " (" + ID + " integer primary key autoincrement, " + TITLE
            + " text not null, " + RELEASE_DATE + " text not null, " + VOTE_COUNT + " text not null, " + DESCRIPTION + "text not null, " + THUMB_URL + "text not null );";
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


    public void getAllRows(SQLiteDatabase sq){

        Log.d("ONCREATE","ONCREATE3");
        if(sq!=null){
            sq = getWritableDatabase();
            Log.d("ONCREATE","ONCREATE4");

            String[] columns = new String[] { "ID" };

            Cursor  cursor = sq.rawQuery("select * from movies",null);
           if(cursor.getCount() == 0){
               ContentValues contentValues = new ContentValues();
               contentValues.put("TITLE","1");
               contentValues.put("RELEASE_DATE","1");
               contentValues.put("VOTE_COUNT","1");
               contentValues.put("DESCRIPTIONtext","1");
               contentValues.put("THUMB_URLtext","1");
               sq.insert(TABLE_NAME,null,contentValues);
               Log.d("ONCREATE","ONCREATE5"+String.valueOf(cursor.getCount()));
           }
           else {
               String array = RemoteEndpointUtil.fetchJsonArray().toString();
               try {
                   JSONObject jsonObject = new JSONObject(array);
                   if(array!=null){
                       Log.d("TAG","ONCREATE6"+String.valueOf(jsonObject.getJSONArray("results").length()));
                       ContentValues contentValues = new ContentValues();
                       for(int i=0;i <jsonObject.getJSONArray("results").length(); i++){
                           contentValues.put("TITLE","1");
                           contentValues.put("RELEASE_DATE","1");
                           contentValues.put("VOTE_COUNT","1");
                           contentValues.put("DESCRIPTIONtext","1");
                           contentValues.put("THUMB_URLtext","1");
                           sq.insert(TABLE_NAME,null,contentValues);
                       }

                       Cursor  cursor1 = sq.rawQuery("select * from movies",null);
                       Log.d("TAG","ONCREATE6"+String.valueOf(cursor1.getCount()));
                   }
               } catch (JSONException e) {
                   e.printStackTrace();
               }
           }

        }
       // sq = getWritableDatabase();


//try {


        //sq.query(TABLE_NAME,new String[]{ID},null,null,null,null,"asc");

//}
//catch (Exception e){
  //  Log.d("TAG","ONCREATE4"+e.getMessage());
//}


    }
}
