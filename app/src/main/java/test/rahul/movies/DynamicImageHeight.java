package test.rahul.movies.movies;

import android.content.Context;
import android.util.AttributeSet;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

/**
 * Created by Rahul on 19/10/2017.
 */

public class DynamicImageHeight extends NetworkImageView {
    private float mAspectRatio = 1.5f;

    public DynamicImageHeight(Context context) {
        super(context);
    }

    public DynamicImageHeight(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DynamicImageHeight(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void setImageUrl(String url, ImageLoader imageLoader) {
        super.setImageUrl(url, imageLoader);
    }

    @Override
    public void setDefaultImageResId(int defaultImage) {
        super.setDefaultImageResId(defaultImage);
    }

    @Override
    public void setErrorImageResId(int errorImage) {
        super.setErrorImageResId(errorImage);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    @Override
    protected void drawableStateChanged() {
        super.drawableStateChanged();
    }
    public void setAspectRatio(float aspectRatio) {
        mAspectRatio = aspectRatio;
        requestLayout();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
       super.onMeasure(widthMeasureSpec, heightMeasureSpec);




    }
}
