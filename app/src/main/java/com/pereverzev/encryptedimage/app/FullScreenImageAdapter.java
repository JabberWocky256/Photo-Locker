package com.pereverzev.encryptedimage.app;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.ArrayList;

/**
 * Created by Александр on 02.11.2014.
 */
public class FullScreenImageAdapter extends PagerAdapter  {
    private Activity activity;
    private ArrayList<String> imagePaths;
    private LayoutInflater inflater;
    private int viewMode = 0;
    private boolean enabled = true;

    // constructor
    public FullScreenImageAdapter(Activity activity,
                                  ArrayList<String> imagePaths) {
        this.activity = activity;
        this.imagePaths = imagePaths;
    }

    @Override
    public int getCount() {
        return this.imagePaths.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((RelativeLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

            ImageView imgDisplay;
            Button btnClose;

            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View viewLayout = inflater.inflate(R.layout.layout_fullscreen_image, container,
                    false);
            imgDisplay = (TouchImageView) viewLayout.findViewById(R.id.imgDisplay);
            btnClose = (Button) viewLayout.findViewById(R.id.btnClose);

            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            Bitmap bitmap = BitmapFactory.decodeFile(imagePaths.get(position), options);
            imgDisplay.setImageBitmap(bitmap);

            // close button click event
            btnClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.finish();
                }
            });

            ((ViewPager) container).addView(viewLayout);
             viewMode = 1;
            return viewLayout;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((RelativeLayout) object);

    }

    public void setPagingEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
