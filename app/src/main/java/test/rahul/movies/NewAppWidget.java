package test.rahul.movies.movies;

import android.app.LoaderManager;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ContentUris;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RemoteViews;

import com.android.volley.toolbox.ImageLoader;
import com.squareup.picasso.Picasso;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;


/**
 * Implementation of App Widget functionality.
 */
public class NewAppWidget extends AppWidgetProvider implements LoaderManager.LoaderCallbacks<Cursor> {
     static Context context1;
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Uri uri = Uri.withAppendedPath(MyContentProvider.CONTENT_URI,"1");
        return new CursorLoader(context1, uri, null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    private static final String TAG = "TAG";
    public static String SWITCH_WIDGET_UPDATE = "MainActivity.Switch";
     static Uri uri;
    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {


        context1 = context;

        CharSequence widgetText = context.getString(R.string.appwidget_text);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);
        //views.setTextViewText(R.id.appwidget_text, widgetText);

        ImageView imageView = new ImageView(context);
        imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.example_appwidget_preview));



        Uri uri = MyContentProvider.CONTENT_URI.withAppendedPath(MyContentProvider.CONTENT_URI,"favourite");
        Uri uri1 =  uri.withAppendedPath(uri,"1");


        Log.d("TAG","MULTIROW"+String.valueOf(uri1));
        String[] projection = new String[]{"ID"};
        String[] args = new String[]{"0"};
        Cursor cursor = context.getContentResolver().query(uri1,projection,null,args,null);
        cursor.moveToPosition(0);
        String posterPath = "https://image.tmdb.org/t/p/w500/"+cursor.getString(5);
        Log.d("POSTER","POSTER"+posterPath);
        Picasso.with(context)
                .load(posterPath)
                .into(views, R.id.widgetImage, new int[] {appWidgetId});

        //views.setImageViewResource(R.id.widgetImage,R.drawable.googleg_standard_color_18);
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }


    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);

        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        Log.d("TAG","WIDGET11");
        RemoteViews remoteViews;

        AppWidgetManager appWidgetManager = AppWidgetManager
                .getInstance(context);

    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

