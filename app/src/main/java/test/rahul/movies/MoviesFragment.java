package test.rahul.movies.movies;

import android.app.Fragment;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.Toast;

import com.google.firebase.crash.FirebaseCrash;

import org.json.JSONObject;


/**
 * Created by Rahul on 27/10/2017.
 */

@RequiresApi(api = Build.VERSION_CODES.M)
public class MoviesFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    Toolbar toolbar;
    private Parcelable mListState = null;
    private static Bundle mBundleRecyclerViewState;
    private final String KEY_RECYCLER_STATE = "recycler_state";

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getContext(), MyContentProvider.CONTENT_URI, null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        Toast.makeText(getContext(), String.valueOf(data.getCount()), Toast.LENGTH_SHORT).show();
        adapter = new MovieAdapter(getContext(), data);
        int columnCount = getResources().getInteger(R.integer.columnCount);

        gridLayoutManager = new GridLayoutManager(getContext(), 2, GridLayout.VERTICAL, false);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(gridLayoutManager);


    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    RecyclerView recyclerView;

    MovieAsyncTaskLoader movieAsyncTaskLoader;
    GridLayoutManager gridLayoutManager;

    private int PageCount;
    MovieAdapter adapter;
    private boolean isLoading;

    private EndlessScrollListener scrollListener =
            new EndlessScrollListener(new EndlessScrollListener.RefreshList() {
                @Override
                public void onRefresh(int pageNumber) {


                    try {

                        StartLoaderAgain();


                        scrollListener.notifyMorePages();

                    } catch (Exception e) {

                        FirebaseCrash.log(e.getMessage());
                    }


                }
            });

    public void StartLoaderAgain() {
        // getLoaderManager().restartLoader(1,null,this).forceLoad();
    }

    View view;

    MenuInflater menuInflater;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_main_fragment, container, false);
        final int loaderId = getResources().getInteger(R.integer.mainActivityLoaderId);
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        if (isConnected()) {
            getLoaderManager().initLoader(1, null, this);
        } else {

            Snackbar.make(view, getResources().getString(R.string.internetConnectionMessage), Snackbar.LENGTH_LONG).show();
        }
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);

    }


    public boolean isConnected() {
        ConnectivityManager connMgr = (ConnectivityManager) getActivity().getSystemService(getActivity().CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu2: {

                if (isConnected()) {
                    new NewResults().execute();
                    recyclerView.setAdapter(null);
                } else {
                    Toast.makeText(getContext(), getContext().getResources().getString(R.string.internetConnectionMessage), Toast.LENGTH_SHORT).show();
                }

                return true;
            }
            case R.id.favtMovies: {

                getContext().startActivity(new Intent(getContext(), FavouriteMovies.class));
                return true;
            }

            default:
                return true;
        }
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {

        super.onViewStateRestored(savedInstanceState);
       try {
           if(savedInstanceState!=null) {
               Parcelable parcelable = savedInstanceState.getParcelable("Layout");
               recyclerView.getLayoutManager().onRestoreInstanceState(parcelable);


           }
       }
       catch (Exception e){
           Log.d("TAG","ERRMSG"+e.getMessage());
       }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

        super.onSaveInstanceState(outState);
        Parcelable parcelable = recyclerView.getLayoutManager().onSaveInstanceState();
       outState.putString("saveData","rahul");
        outState.putParcelable("Layout",parcelable);


    }

    public class NewResults extends AsyncTask<String, String, String> {

        String resultData = null;
        JSONObject jsonObject = null;

        @Override
        protected String doInBackground(String... params) {
            resultData = RemoteEndpointUtil.fetchJsonArray().toString();
            return resultData;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Uri uri = MyContentProvider.CONTENT_URI.withAppendedPath(MyContentProvider.CONTENT_URI, "update");

            String[] args = new String[]{s};
            Cursor cursor = getContext().getContentResolver().query(uri, null, null, args, null);
            adapter = new MovieAdapter(getContext(), cursor);
            recyclerView.setAdapter(adapter);

        }

    }
}
