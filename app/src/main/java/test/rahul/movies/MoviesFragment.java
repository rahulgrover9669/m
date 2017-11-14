package test.rahul.movies.movies;

import android.app.Fragment;
import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;

/**
 * Created by Rahul on 27/10/2017.
 */

@RequiresApi(api = Build.VERSION_CODES.M)
public class MoviesFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getContext(),MyContentProvider.CONTENT_URI,null,null,null,null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    RecyclerView recyclerView;

    MovieAsyncTaskLoader movieAsyncTaskLoader;
    GridLayoutManager gridLayoutManager;
    JSONArray jsonArray,NewJsonArray;
    JSONObject jsonObject;
    private StaggeredGridLayoutManager staggeredGridLayoutManager;
    private LinearLayoutManager linearLayoutManager;
    private RecyclerView.LayoutManager layoutManager;
    private int pastVisibleItems, visibleItemCount, totalItemCount;
    private int PageCount;
    MovieAdapter adapter;
    private boolean isLoading;

    private EndlessScrollListener scrollListener =
            new EndlessScrollListener(new EndlessScrollListener.RefreshList() {
                @Override public void onRefresh(int pageNumber) {
                    //Here you can execute server connection or anything else to update data and present with Recycler view
                    // Notice : It is sysn method\
                    Log.d("PAGENUMBER","NUMBER"+String.valueOf(pageNumber));

                try{

                    StartLoaderAgain();
                    Log.d("TAG","STARTLOAD"+String.valueOf(PageCount));

                    scrollListener.notifyMorePages();

                }
                catch (Exception e){
                    Log.d("TAG","CRASH"+e.toString());
                }



                }
            });
    public void StartLoaderAgain(){
       // getLoaderManager().restartLoader(1,null,this).forceLoad();
    }

    View view;
    Toolbar toolbar;
    MenuInflater menuInflater;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
       view = inflater.inflate(R.layout.activity_main_fragment,container,false);
      final   int loaderId = getResources().getInteger(R.integer.mainActivityLoaderId);
recyclerView = (RecyclerView)view.findViewById(R.id.recyclerView);
        if(isConnected()){
            getLoaderManager().initLoader(1,null,this);
        }
        else {

            Snackbar.make(view,"Check Internet Connection",Snackbar.LENGTH_LONG).show();
        }
return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu,menu);
        super.onCreateOptionsMenu(menu, inflater);

    }


    public boolean isConnected(){
        ConnectivityManager connMgr = (ConnectivityManager)getActivity().getSystemService(getActivity().CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }


}
