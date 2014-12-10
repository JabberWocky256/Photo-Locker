package com.pereverzev.encryptedimage.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.pereverzev.encryptedimage.app.exceptions.DirectoryPassIsNotValid;
import com.pereverzev.encryptedimage.app.exceptions.EmptyDirectoryException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Александр on 02.11.2014.
 */
public class FullScreenViewActivity extends Activity {
    private Utils utils;
    private FullScreenImageAdapter adapter;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen_view);

        viewPager = (ViewPager) findViewById(R.id.pager);

        utils = new Utils();

        Intent i = getIntent();
        try {
            adapter = new FullScreenImageAdapter(FullScreenViewActivity.this,
                    utils.getFilePaths());
        } catch (EmptyDirectoryException e) {
            e.printStackTrace();
        } catch (DirectoryPassIsNotValid directoryPassIsNotValid) {
            directoryPassIsNotValid.printStackTrace();
        }

      /*  try {
            viewPager.setAdapter(new TouchImageAdapter(this, utils.getFilePaths()));
        } catch (EmptyDirectoryException e) {
            e.printStackTrace();
        } catch (DirectoryPassIsNotValid directoryPassIsNotValid) {
            directoryPassIsNotValid.printStackTrace();
        }*/

        viewPager.setAdapter(adapter);

    }

    static class TouchImageAdapter extends PagerAdapter {

        private Activity activity;
        private static List<String> images ;
        private LayoutInflater inflater;

        public TouchImageAdapter(Activity activity, List<String> images) {
            this.activity = activity;
            this.images = images;
        }

        @Override
        public int getCount() {
            return images.size();
        }

        @Override
        public View instantiateItem(ViewGroup container, int position) {
            TouchImageView imageView = new TouchImageView(container.getContext());
            Button btnClose;
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View viewLayout = inflater.inflate(R.layout.layout_fullscreen_image, container,
                    false);

            imageView = (TouchImageView) viewLayout.findViewById(R.id.imgDisplay);
            btnClose = (Button) viewLayout.findViewById(R.id.btnClose);

            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            Bitmap bitmap = BitmapFactory.decodeFile(images.get(position), options);

            imageView.setImageBitmap(bitmap);
            btnClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.finish();
                }
            });

            container.addView(imageView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

    }
}

