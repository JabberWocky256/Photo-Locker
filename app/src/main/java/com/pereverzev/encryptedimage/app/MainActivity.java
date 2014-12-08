package com.pereverzev.encryptedimage.app;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.*;

public class MainActivity extends Activity {
    private Button btnGetImage;
    private ImageView imgView;
    private static final int REQUEST = 1;
    private String imagePath = "";
    private File file;
    private Image image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnGetImage = (Button)findViewById(R.id.btnGetImage);
        imgView = (ImageView) findViewById(R.id.image);

        btnGetImage.setOnClickListener(btnGetImageOnClickListener());
    }

    private View.OnClickListener btnGetImageOnClickListener(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent imageGallery = new Intent(Intent.ACTION_PICK);
                imageGallery.setType("image/*");
                startActivityForResult(imageGallery, REQUEST);
            }
        };
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST && resultCode == RESULT_OK) {
            Uri selectedImage = data.getData();
            imagePath = getRealPathFromURI(selectedImage);

            image = new Image(imagePath);

            Bitmap bitmap = image.getImage();
            imgView.setImageBitmap(bitmap);

            image.saveImage();
            doYouWantToDeleteImageFromGallery();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private byte[] downloadImage(Uri selectedImage) {
        InputStream in = null;
        imagePath = getRealPathFromURI(selectedImage);

        try {
            in = new FileInputStream(imagePath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        file = new File(imagePath);
        int fileSize = Integer.parseInt(String.valueOf(file.length()));

        byte[] image = new byte[fileSize];
        try {
            in.read(image);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return image;
    }

    private void doYouWantToDeleteImageFromGallery(){
        final AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);

        DialogInterface.OnClickListener okButtonListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                file.delete();
                sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.parse("file://" + Environment.getExternalStorageDirectory())));
                Toast.makeText(getApplicationContext(), "Изображение удалено из галереи", Toast.LENGTH_SHORT);
            }
        };
        DialogInterface.OnClickListener cancelButtonListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        };
        alertBuilder
                .setTitle("Удаление")
                .setMessage("Удалить изображение с галереи?")
                .setPositiveButton("Да", okButtonListener)
                .setNegativeButton("Нет", cancelButtonListener)
                .show();
    }

    private String getRealPathFromURI(Uri contentUri)
    {
        String[] proj = { MediaStore.Video.Media.DATA };
        Cursor cursor = managedQuery(contentUri, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }
}
