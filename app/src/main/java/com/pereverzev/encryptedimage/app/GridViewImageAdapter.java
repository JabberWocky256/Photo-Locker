package com.pereverzev.encryptedimage.app;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
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
    private Activity _activity;
    private ArrayList<String> _filePaths = new ArrayList<String>();
    private int imageWidth;

    public GridViewImageAdapter(Activity activity, ArrayList<String> filePaths,
                                int imageWidth) {
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

        imageLoader.displayImage("file:///" + _filePaths.get(position), imageView, options);

/*

        Image img = new Image(_filePaths.get(position));


        Bitmap image = img.getIcon(100, 100);

        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setLayoutParams(new GridView.LayoutParams(imageWidth,
                imageWidth));
        imageView.setImageBitmap(image);*/

        // image view click listener
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
            final String path = _filePaths.get(_postion);
            final AlertDialog.Builder alertBuilder = new AlertDialog.Builder(_activity);

            DialogInterface.OnClickListener okButtonListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if(path.substring(path.length()-3, path.length()).equals("evg")) {
                        File file = new File(path);
                        File file2 = new File(path.substring(0, path.length()-3));
                        file.renameTo(file2);
                    } else {
                        File file = new File(path);
                        File file2 = new File(path + "evg");
                        file.renameTo(file2);
                    }

                    _activity.sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.parse("file://" + Environment.getExternalStorageDirectory())));
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
