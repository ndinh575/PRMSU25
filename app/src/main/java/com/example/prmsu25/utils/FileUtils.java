package com.example.prmsu25.utils;


import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.File;
import java.io.FileOutputStream;

public class FileUtils {

    public static File getFileFromUri(Context context, Uri uri) {
        File file = new File(uri.getPath());
        return file;
    }

    public static Uri saveBitmapToCache(Context context, Bitmap bitmap) {
        File file = new File(context.getCacheDir(), "captured_image.jpg");
        try (FileOutputStream out = new FileOutputStream(file)) {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            return Uri.fromFile(file);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

