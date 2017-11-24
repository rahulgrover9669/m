package test.rahul.movies.movies;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Cache;
import com.android.volley.ExecutorDelivery;
import com.android.volley.Network;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Rahul on 28/10/2017.
 */

@TargetApi(Build.VERSION_CODES.M)
public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MyHolder> implements RecyclerView.OnScrollChangeListener {
    private final static int FADE_DURATION = 500;
    private String[] moviesList;
    int previousCount ;
    private Cursor cursor;
    @Override
    public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

        Log.d("SCROLL","OLD:"+String.valueOf(oldScrollY));
    }

    private RequestQueue mRequestQueue;

    private float mAspectRatio = 1.5f;
    private ImageLoader imageLoader;
    private Context context;

    public class MyHolder extends RecyclerView.ViewHolder {

        public ImageView titleImage;
        public NetworkImageView networkImageView;
        private ImageView imageView;
        private TextView MovieName,Rating;

        public MyHolder(View view) {
            super(view);

            //titleImage = (ImageView)view.findViewById(R.id.titleimage);
            imageView = (ImageView) view.findViewById(R.id.networkImageView);
            MovieName = (TextView)view.findViewById(R.id.movieName);
            Rating  = (TextView)view.findViewById(R.id.Rating);
        }
    }


    public MovieAdapter(Context context, Cursor cursor) {

        this.context = context;

        this.cursor = cursor;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movieitem, parent, false);

        return new MyHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyHolder holder, final int position) {

        //    holder.title.setText(moviesList[position]);
        String ImgUrl = "https://image.tmdb.org/t/p/w500/";
        String ImgName = "";
        try{
        cursor.moveToPosition(position);
            ImgName = cursor.getString(5);
            holder.MovieName.setText(cursor.getString(1));
            holder.Rating.setText(cursor.getString(3));
            holder.imageView.setTag(cursor.getString(0));
        }
        catch (Exception e){

        }
      //  try {
          //  Log.d("TAG","POSTER" + ImgUrl+jsonArray.getJSONObject(position).getString("poster_path"));
          //  ImgName = jsonArray.getJSONObject(position).getString("poster_path");
       // } catch (JSONException e) {
     //       e.printStackTrace();
      //  }

   if(!ImgName.equals("null")){
       ImgName = ImgName.substring(1,ImgName.length());
  //     try {
        //   holder.MovieName.setText(jsonArray.getJSONObject(position).getString("original_title"));
          /// holder.Rating.setText(jsonArray.getJSONObject(position).getString("vote_average"));
    //   } catch (JSONException e) {
      //     e.printStackTrace();
      // }
       Picasso.with(context).load(ImgUrl+ImgName).placeholder(android.R.drawable.btn_radio).error(android.R.drawable.stat_notify_error).into(holder.imageView ,new Callback(){

           @Override
           public void onSuccess() {


           }

           @Override
           public void onError() {

           }

       });

   }

        Log.d("TAG","MOVIENAME:" +ImgUrl+ImgName);

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    int pos  = Integer.parseInt((String) v.getTag());
                    Toast.makeText(context,     String.valueOf( v.getTag()), Toast.LENGTH_SHORT).show();
                    cursor.moveToPosition(position);
                    Intent intent = new Intent(context.getApplicationContext(),DetailActivity.class);
                    intent.putExtra("title",cursor.getString(1));
                    intent.putExtra("release_date",cursor.getString(2));
                    intent.putExtra("votes",cursor.getString(3));
                    intent.putExtra("description",cursor.getString(4));
                    intent.putExtra("photo_path",cursor.getString(5));
                    intent.putExtra("id",cursor.getString(0));
                    intent.putExtra("is_visible",cursor.getString(7));
                    intent.putExtra("is_favt",cursor.getString(6));
                    context.startActivity(intent);
                  //  Toast.makeText(context,cursor.getString(4),Toast.LENGTH_SHORT).show();
                }
                catch (Exception  e){
                    Log.d("TAG","ONCLICK"+e.getMessage());
                }


            }
        });
    }

    @Override
    public void onViewAttachedToWindow(MyHolder holder) {
        super.onViewAttachedToWindow(holder);
        Log.d("TAG","ATACh");
        setScaleAnimation(holder.itemView);
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position, List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);


    }

    @Override
    public void onViewDetachedFromWindow(MyHolder holder) {
        super.onViewDetachedFromWindow(holder);
        Log.d("TAG","DETACH");
        holder.itemView.clearAnimation();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            Cache cache = new DiskBasedCache(context.getCacheDir(), 10 * 1024 * 1024);
            Network network = new BasicNetwork(new HurlStack());
            mRequestQueue = new RequestQueue(cache, network);
            // Don't forget to start the volley request queue
            mRequestQueue.start();
        }
        return mRequestQueue;
    }

    public ImageLoader getImageLoader() {
        return imageLoader;
    }

    @Override
    public int getItemCount() {

        return cursor.getCount();
    }

    public void addNewItems(JSONArray nextJsonArray) throws JSONException {

        Log.d("ADDNEW","ADDNEWITEM");



      //  for(int i =0;i < nextJsonArray.length();i++) {

       //     jsonArray.put(nextJsonArray.getJSONObject(i));
        //    Log.d("NEWJSON", "POSTERPATH" + nextJsonArray.getJSONObject(i).getString("poster_path"));

      //  }

      //  try{
      //      notifyDataSetChanged();
     //   }
     //   catch (Exception e){
       //     Log.d("ERROR","ERROR"+e.toString());
      //  }
      //  Log.d("NEWCOUNT",String.valueOf(jsonArray.length()));

    }
    private void setFadeAnimation(View view) {
        AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(FADE_DURATION);
        view.startAnimation(anim);

    }
    private void setScaleAnimation(View view) {
        ScaleAnimation anim = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        anim.setDuration(FADE_DURATION);
        view.startAnimation(anim);
    }
}
