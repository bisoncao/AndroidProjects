package com.bisoncao.bccommonutil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import android.os.Environment;

/**
 * A util for handle TXT file.
 * @author Bison Cao
 * @created 19:02 06/30/2016
 */
public class BCTxtFileUtil {

    /**
     * @return null if an error occurred
     */
    public static String read(String completePath) {
        try {
            File file = new File(completePath);
            FileInputStream is = new FileInputStream(file);
            byte[] b = new byte[is.available()];
            is.read(b);
            return new String(b);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Save in the root path of SD card.
     * @param filename with txt extension
     */
    public static boolean save(String text, String filename) {
        return save(text, Environment.getExternalStorageDirectory().getPath(), filename);
    }

    public static boolean save(String text, String sdPath, String filename) {
        try {
            File file = new File(sdPath, filename);
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(text.getBytes());
            fos.close();
            return true;
        } catch (Exception e) {
            System.err.println("BCTxtFileUtil: error occured when save text to file.");
            e.printStackTrace();
            return false;
        }
    }
}
