package com.pereverzev.encryptedimage.app;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.view.Display;
import android.view.WindowManager;
import com.pereverzev.encryptedimage.app.constants.AppConstant;
import com.pereverzev.encryptedimage.app.exceptions.DirectoryPassIsNotValid;
import com.pereverzev.encryptedimage.app.exceptions.EmptyDirectoryException;

import java.io.File;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by Александр on 02.11.2014.
 */
public class Utils {

    // Reading file paths from SDCard
    public ArrayList<String> getFilePaths() throws EmptyDirectoryException, DirectoryPassIsNotValid {
        ArrayList<String> filePaths = new ArrayList<String>();

        File directory = new File(
                android.os.Environment.getExternalStorageDirectory()
                        + File.separator + AppConstant.PHOTO_ALBUM);

        /*File directory = new File("mnt/sdcard/EncryptedImage");*/

        // check for directory
        if (directory.isDirectory()) {
            filePaths = getFilesList(directory);

        } else {
            throw new DirectoryPassIsNotValid("directory path is not valid! Please set the image directory name AppConstant.java class");
        }

        return filePaths;
    }

    // getting list of file paths
    private  ArrayList<String> getFilesList(File directory) throws EmptyDirectoryException {
        ArrayList<String> filePaths = new ArrayList<String>();
        File[] listFiles = directory.listFiles();

        // Check for count
        if (listFiles.length > 0) {

            // loop through all files
            for (int i = 0; i < listFiles.length; i++) {
                // get file path
                String filePath = listFiles[i].getAbsolutePath();

                // check for supported file extension
                if (IsSupportedFile(filePath)) {
                    // Add image path to array list
                    filePaths.add(filePath);
                }
            }
        } else {
            // image directory is empty
            throw new EmptyDirectoryException("image directory is empty");
        }

        return filePaths;
    }

    // Check supported file extensions
    public boolean IsSupportedFile(String filePath) {
        String ext = filePath.substring((filePath.lastIndexOf(".") + 1),
                filePath.length());

        if (AppConstant.FILE_EXTN
                .contains(ext.toLowerCase(Locale.getDefault())))
            return true;
        else
            return false;

    }

    public boolean isSpecialFile(String filePath){
        String ext = filePath.substring((filePath.lastIndexOf(".") + 1),
                filePath.length());

        if (AppConstant.HIDE_FILE_EXTN
                .equals(ext.toLowerCase(Locale.getDefault())))
            return true;
        else
            return false;
    }

    /*
     * getting screen width
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public int getScreenWidth(Context _context) {
        int columnWidth;
        WindowManager wm = (WindowManager) _context
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();

        final Point point = new Point();
        try {
            display.getSize(point);
        } catch (java.lang.NoSuchMethodError ignore) { // Older device
            point.x = display.getWidth();
            point.y = display.getHeight();
        }
        columnWidth = point.x;
        return columnWidth;
    }
}
