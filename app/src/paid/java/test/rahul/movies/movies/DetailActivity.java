package test.rahul.movies.movies;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {
    Toolbar toolbar;
    ImageView imageView;
    FloatingActionButton floatingActionButton;
    TextView description, movieRating, movieAdult, movieReleaseDate, movieOrignalTitle;
    int titleCOlor;
    CollapsingToolbarLayout collapsingToolbarLayout;
    String isFavt, isVisible, movie_title, release_date, votes, Description, photo_path, id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        toolbar = (Toolbar) findViewById(R.id.toolbar1);
        imageView = (ImageView) findViewById(R.id.ivParallax);
        movie_title = getIntent().getStringExtra("title");
        release_date = getIntent().getStringExtra("release_date");
        votes = getIntent().getStringExtra("votes");
        Description = getIntent().getStringExtra("description");
        photo_path = getIntent().getStringExtra("photo_path");
        isFavt = getIntent().getStringExtra("is_favt");
        isVisible = getIntent().getStringExtra("is_visible");
        id = getIntent().getStringExtra("id");
        description = (TextView) findViewById(R.id.Description);
        movieRating = (TextView) findViewById(R.id.movie_rating);
        movieAdult = (TextView) findViewById(R.id.movie_adult);
        movieReleaseDate = (TextView) findViewById(R.id.movie_release_Date);
        movieOrignalTitle = (TextView) findViewById(R.id.movie_orignal_title);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.CollapsingToolbarLayout);

        setSupportActionBar(toolbar);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.floatingActionButton2);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (isFavt.equals("1")) {
                        isFavt = "0";
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.removeFavoirite), Toast.LENGTH_SHORT).show();
                    } else {
                        isFavt = "1";
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.addFavoirite), Toast.LENGTH_SHORT).show();
                    }
                    Uri uri = Uri.withAppendedPath(MyContentProvider.CONTENT_URI, "update/" + id);
                    // uri.withAppendedPath(uri, id);

                    String[] args = new String[]{id, isFavt};
                    Cursor cursor = getApplicationContext().getContentResolver().query(uri, null, null, args, null);
                    Toast.makeText(getBaseContext(), String.valueOf(cursor.getCount()), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Log.d("TAG", "ONCLICKERROR" + e.getMessage());
                }
            }
        });


        toolbar.setTitleTextColor(Color.RED);

        if (movie_title != null & release_date != null & votes != null & Description != null & photo_path != null & id != null & isVisible != null & isFavt != null) {

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
            case R.id.menu2: {
                Toast.makeText(getBaseContext(), "REFRESH", Toast.LENGTH_SHORT).show();
                return true;
            }
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

    }


    @Override
    protected void onStop() {
        super.onStop();

    }
}
