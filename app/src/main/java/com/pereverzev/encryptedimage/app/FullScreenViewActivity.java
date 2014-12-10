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
        int position = i.getIntExtra("position", 0);

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
        viewPager.setCurrentItem(position);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}

