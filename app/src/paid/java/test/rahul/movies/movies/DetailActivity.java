package test.rahul.movies.movies;

import android.app.AlarmManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import java.util.Timer;
import java.util.TimerTask;

public class DetailActivity extends AppCompatActivity {
    Toolbar toolbar;
    ImageView imageView;

    String title;

    TextView description, movieRating, movieAdult, movieReleaseDate, movieOrignalTitle;


    int titleCOlor;
    CollapsingToolbarLayout collapsingToolbarLayout;

    AlarmManager alarmManager;
    Timer timer;
    TimerTask timerTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        toolbar = (Toolbar) findViewById(R.id.toolbar1);
        imageView = (ImageView) findViewById(R.id.ivParallax);
        String movie_title = getIntent().getStringExtra("title");
        String release_date = getIntent().getStringExtra("release_date");
        String votes = getIntent().getStringExtra("votes");
        String Description = getIntent().getStringExtra("description");
        String photo_path = getIntent().getStringExtra("photo_path");
        String id = getIntent().getStringExtra("id");
        description = (TextView) findViewById(R.id.Description);
        movieRating = (TextView) findViewById(R.id.movie_rating);
        movieAdult = (TextView) findViewById(R.id.movie_adult);
        movieReleaseDate = (TextView) findViewById(R.id.movie_release_Date);
        movieOrignalTitle = (TextView) findViewById(R.id.movie_orignal_title);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.CollapsingToolbarLayout);
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        setSupportActionBar(toolbar);

        toolbar.setTitleTextColor(Color.RED);


        if (movie_title != null & release_date != null & votes != null & Description != null & photo_path != null & id != null) {

            description.setText(Description);
            movieRating.setText(votes);
            movieReleaseDate.setText(release_date);
            movieOrignalTitle.setText(movie_title);
            getSupportActionBar().setTitle(movie_title);
            Picasso.with(this).load("https://image.tmdb.org/t/p/w500/" + photo_path).into(imageView);

        }


        try {
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            ImageLoader.ImageCache imageCache = new ImageLoader.ImageCache() {
                @Override
                public Bitmap getBitmap(String url) {
                    return null;
                }

                @Override
                public void putBitmap(String url, Bitmap bitmap) {

                }
            };
            ImageLoader imageLoader = new ImageLoader(requestQueue, imageCache);
            imageLoader.get("https://image.tmdb.org/t/p/w500/" + photo_path, new ImageLoader.ImageListener() {
                @Override
                public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                    Bitmap bitmap = response.getBitmap();
                    if (bitmap != null) {
                        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
                            @Override
                            public void onGenerated(Palette palette) {
                                //work with the palette here
                                int defaultValue = 0x000000;
                                int vibrant = palette.getVibrantColor(defaultValue);
                                int vibrantLight = palette.getLightVibrantColor(defaultValue);
                                int vibrantDark = palette.getDarkVibrantColor(defaultValue);
                                int muted = palette.getMutedColor(defaultValue);
                                int mutedLight = palette.getLightMutedColor(defaultValue);
                                int mutedDark = palette.getDarkMutedColor(defaultValue);

                                titleCOlor = vibrantDark;
                                collapsingToolbarLayout.setExpandedTitleColor(vibrant);
                            }
                        });

                        Log.d("IMAGELOADER", "IMAGELOADER" + String.valueOf(titleCOlor));
                        // getSupportActionBar().setTitle("RAHUL");


                    }
                }


                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });

        } catch (Exception e) {
            Log.e("EXP", "EXPEXP" + e.getMessage());
            Log.d("EXP", "EXPEXP" + e.getMessage());
        }


    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return true;
    }

    @Override
    public void setSupportActionBar(@Nullable Toolbar toolbar) {
        super.setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.RED);

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        CancelTimer();
    }

    public void CancelTimer() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        CancelTimer();
    }
}
