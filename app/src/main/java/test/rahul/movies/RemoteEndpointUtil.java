package test.rahul.movies.movies;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;

import java.io.IOException;
import java.net.URL;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Rahul on 28/10/2017.
 */

public class RemoteEndpointUtil {
    private static final String TAG = "RemoteEndpointUtil";
    private RemoteEndpointUtil() {
    }
    public static String fetchJsonArray() {
        String itemsJson = null;
        try {
            itemsJson = fetchPlainText(Config.BASE_URL);
            Log.d("TAG","JSONDATA" + itemsJson);
        } catch (IOException e) {
            Log.e(TAG, "Error fetching items JSON", e);
            return null;
        }

        // Parse JSON


        return itemsJson;
    }
    public   static String fetchNextArray(  int PageNumber){

        String itemsJson = null;
        try {
            itemsJson = fetchPlainText(Config.nextPage(PageNumber));
            Log.d("TAG","JSONDATA" + itemsJson);
        } catch (IOException e) {
            Log.e(TAG, "Error fetching items JSON", e);
            return null;
        }

        // Parse JSON


        return itemsJson;
    }

    public static String fetchPlainText(URL url) throws IOException {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }
}
