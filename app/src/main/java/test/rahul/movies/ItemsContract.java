package test.rahul.movies.movies;

import android.net.Uri;

/**
 * Created by Rahul on 11/11/2017.
 */

public class ItemsContract {
    public static final String CONTENT_AUTHORITY = "test.rahul.movies.movies";
    public static final Uri BASE_URI = Uri.parse("content://test.rahul.movies.movies");

    interface ItemsColumns {

        String _ID = "_id";




        String TITLE = "org_title";


        String BODY = "description";

        String THUMB_URL = "thumb_url";

        String VOTE_COUNT = "vote_count";

        String RELEASE_DATE = "release_date";

        String ADULT = "adult";
    }

    public static class Items implements ItemsColumns {
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.test.rahul.movies.movies.MyContentProvider.movies";
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.test.rahul.movies.movies.MyContentProvider.movies";

        public static final String DEFAULT_SORT = "DESC";

        public static Uri buildDirUri() {

            return BASE_URI.buildUpon().appendPath("items").build();
        }

        public static Uri buildItemUri(long _id) {
            return BASE_URI.buildUpon().appendPath("item").appendPath(Long.toString(_id)).build();
        }

        /**
         * Read item ID item detail URI.
         */
        public static long getItemId(Uri itemUri) {
            return Long.parseLong(itemUri.getPathSegments().get(1));
        }
    }
}