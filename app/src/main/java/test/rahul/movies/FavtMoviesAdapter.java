package test.rahul.movies.movies;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.List;

/**
 * Created by Rahul on 23/11/2017.
 */


public class FavtMoviesAdapter extends RecyclerView.Adapter<FavtMoviesAdapter.MyHolder> {
    private final static int FADE_DURATION = 500;

    private Cursor cursor;




    private Context context;

    public class MyHolder extends RecyclerView.ViewHolder {


        private ImageView imageView;
        private TextView MovieName, Rating;

        public MyHolder(View view) {
            super(view);

            //titleImage = (ImageView)view.findViewById(R.id.titleimage);
            imageView = (ImageView) view.findViewById(R.id.networkImageView);
            MovieName = (TextView) view.findViewById(R.id.movieName);
            Rating = (TextView) view.findViewById(R.id.Rating);
        }
    }


    public FavtMoviesAdapter(Context context, Cursor cursor) {

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
        try {
            cursor.moveToPosition(position);
            ImgName = cursor.getString(5);
            holder.MovieName.setText(cursor.getString(1));
            holder.Rating.setText(cursor.getString(3));
            holder.imageView.setTag(cursor.getString(0));
        } catch (Exception e) {

        }


        if (!ImgName.equals("null")) {
            ImgName = ImgName.substring(1, ImgName.length());

            Picasso.with(context).load(ImgUrl + ImgName).placeholder(android.R.drawable.btn_radio).error(android.R.drawable.stat_notify_error).into(holder.imageView, null);

        }

        Log.d("TAG", "MOVIENAME:" + ImgUrl + ImgName);


    }

    @Override
    public void onViewAttachedToWindow(MyHolder holder) {
        super.onViewAttachedToWindow(holder);
        Log.d("TAG", "ATACh");
        setScaleAnimation(holder.itemView);
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position, List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);


    }

    @Override
    public void onViewDetachedFromWindow(MyHolder holder) {
        super.onViewDetachedFromWindow(holder);
        Log.d("TAG", "DETACH");
        holder.itemView.clearAnimation();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }




    @Override
    public int getItemCount() {

        return cursor.getCount();
    }

    public void addNewItems(JSONArray nextJsonArray) throws JSONException {

        Log.d("ADDNEW", "ADDNEWITEM");


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
