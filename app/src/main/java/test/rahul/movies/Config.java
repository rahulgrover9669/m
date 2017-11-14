package test.rahul.movies.movies;

import android.util.Log;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Rahul on 28/10/2017.
 */

public class Config {
    public static final URL BASE_URL;
    private static String TAG = Config.class.toString();
    public static URL NEXT_URL;

    static {
        URL url = null;
        try {
            url = new URL("https://api.themoviedb.org/3/movie/popular?api_key=85157def69f59b0b6cf92de28def66c3&language=en-Hi&region=IN" );
        } catch (MalformedURLException ignored) {
            // TODO: throw a real error
            Log.e(TAG, "Please check your internet connection.");
        }

        BASE_URL = url;
    }
    public static   URL nextPage(int pageNo) throws MalformedURLException {


        URL url = null;
        try {
            url = new URL("https://api.themoviedb.org/3/movie/popular?api_key=85157def69f59b0b6cf92de28def66c3&language=hi&page="+ String.valueOf(pageNo)+"&region=IN");
        } catch (MalformedURLException ignored) {
            // TODO: throw a real error
            Log.e(TAG, "Please check your internet connection.");
        }

        NEXT_URL = url;

        return NEXT_URL;
    }
}
