package com.pereverzev.encryptedimage.app;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.pereverzev.encryptedimage.app.constants.AppConstant;

import java.io.*;

/**
 * Created by Александр on 03.11.2014.
 */
public class Image {
    private File imageFile;
    private String imagePath;
    byte[] imageBytes = null;
    private static Utils utils = new Utils();

    public Image(String imagePath){
        this.imagePath = imagePath;
    }


    public Bitmap getImage(){
        if(imageBytes == null)
            imageBytes = downloadImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);

        return bitmap;
    }

    private byte[] downloadImage() {
        InputStream in = null;

        try {
            in = new FileInputStream(imagePath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        imageFile = new File(imagePath);
        int fileSize = Integer.parseInt(String.valueOf(imageFile.length()));

        byte[] image = new byte[fileSize];
        try {
            in.read(image);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(utils.IsSupportedFile(imagePath))
            return image;
        else if(utils.isSpecialFile(imagePath))
            return image;
        return null;
    }

    public void saveImage() {
        String localePath = android.os.Environment.getExternalStorageDirectory()
                + File.separator + AppConstant.PHOTO_ALBUM  + File.separator +  imageFile.getName();

        File sd = Environment.getExternalStorageDirectory();
        if (sd.canWrite()) {
            String path = localePath.substring(localePath.length() - 4) + ".evg";
            File destination = new File(path);

            OutputStream out = null;
            try {
                out = new FileOutputStream(destination);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            try {
                out.write(imageBytes, 0, imageBytes.length);
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public Bitmap getIcon(int WIDTH, int HIGHT) {



        BitmapFactory.Options o = null;
        final int REQUIRED_WIDTH = WIDTH;
        final int REQUIRED_HIGHT = HIGHT;
        int scale = 1;

        if(imageBytes == null)
            imageBytes = downloadImage();

        o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;
        BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length, o);

        while (o.outWidth / scale / 2 >= REQUIRED_WIDTH
                && o.outHeight / scale / 2 >= REQUIRED_HIGHT)
            scale *= 2;

        BitmapFactory.Options o2 = new BitmapFactory.Options();
        o2.inSampleSize = scale;
        return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length, o2);

    }

}
