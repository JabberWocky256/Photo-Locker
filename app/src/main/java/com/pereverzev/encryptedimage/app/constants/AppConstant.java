package com.pereverzev.encryptedimage.app.constants;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Александр on 02.11.2014.
 */
public class AppConstant {
    // Number of columns of Grid View
    public static final int NUM_OF_COLUMNS = 3;

    // Gridview image padding
    public static final int GRID_PADDING = 8; // in dp

    // SD card image directory
    public static final String PHOTO_ALBUM = "ScreenCapture";

    // supported file formats
    public static final List<String> FILE_EXTN = Arrays.asList("jpg", "jpeg", "png", "jpgevg", "jpegevg", "pngevg");
    public static final String HIDE_FILE_EXTN = ".evg";
}