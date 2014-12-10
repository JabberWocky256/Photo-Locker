package com.pereverzev.encryptedimage.app;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * Created by Александр on 02.11.2014.
 */
public class GridViewImageAdapter extends BaseAdapter {
    private GridViewActivity gridViewActivity;
    private Activity _activity;
    private ArrayList<String> _filePaths = new ArrayList<String>();
    private int imageWidth;

    public GridViewImageAdapter(GridViewActivity grid, Activity activity, ArrayList<String> filePaths,
                                int imageWidth) {
        this.gridViewActivity = grid;
        this._activity = activity;
        this._filePaths = filePaths;
        this.imageWidth = imageWidth;
    }

    @Override
    public int getCount() {
        return this._filePaths.size();
    }

    @Override
    public Object getItem(int position) {
        return this._filePaths.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            imageView = new ImageView(_activity);
        } else {
            imageView = (ImageView) convertView;
        }

        // get screen dimensions

        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setLayoutParams(new GridView.LayoutParams(imageWidth,
                imageWidth));


        ImageLoader imageLoader = ImageLoader.getInstance();

        imageLoader.init(ImageLoaderConfiguration.createDefault(_activity.getApplicationContext()));

        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                .cacheInMemory()
                .cacheOnDisc()
                .build();

        if(_filePaths.get(position).substring(_filePaths.get(position).length()-3, _filePaths.get(position).length()).equals("evg")){
            imageView.setBackgroundColor(Color.BLUE);
            imageView.setPadding(5,5,5,5);
        }

        imageLoader.displayImage("file:///" + _filePaths.get(position), imageView, options);


        imageView.setOnClickListener(new OnImageClickListener(position));

        imageView.setOnLongClickListener(new OnImageLongClickListener(position));

        return imageView;
    }

    class OnImageClickListener implements View.OnClickListener {

        int _postion;

        // constructor
        public OnImageClickListener(int position) {
            this._postion = position;
        }

        @Override
        public void onClick(View v) {
            // on selecting grid view image
            // launch full screen activity
            Intent i = new Intent(_activity, FullScreenViewActivity.class);
            i.putExtra("position", _postion);
            _activity.startActivity(i);
        }

    }

    class OnImageLongClickListener implements View.OnLongClickListener {

        int _postion;

        // constructor
        public OnImageLongClickListener(int position) {
            this._postion = position;
        }

        @Override
        public boolean onLongClick(View v) {
            String path = _filePaths.get(_postion);
            final AlertDialog.Builder alertBuilder = new AlertDialog.Builder(_activity);

            DialogInterface.OnClickListener okButtonListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if(_filePaths.get(_postion).substring(_filePaths.get(_postion).length() - 3, _filePaths.get(_postion).length()).equals("evg")) {
                        File file = new File(_filePaths.get(_postion));
                        File file2 = new File(_filePaths.get(_postion).substring(0, _filePaths.get(_postion).length() - 3));
                        file.renameTo(file2);
                    } else {
                        File file = new File(_filePaths.get(_postion));
                        File file2 = new File(_filePaths.get(_postion) + "evg");
                        file.renameTo(file2);
                    }

                    gridViewActivity.setAdapter();
                    _activity.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + Environment.getExternalStorageDirectory())));
                }
            };

             String message = "";
            if(path.substring(path.length()-3, path.length()).equals("evg")){
                message = "Открыть доступ к изображению?" ;
            } else {
                message = "Скрыть изображение?";
            }

            alertBuilder
                    .setTitle("Скрытие")
                    .setMessage(message)
                    .setPositiveButton("Да", okButtonListener)
                    .setNegativeButton("Нет", null)
                    .show();

            return true;
        }

    }
}
