package com.sakshammathur25web.xphotoeditor;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Utils {

    public static Uri getOutPutMeidaFileUri(Context c,int mediaType,String filePath){
        //using Environment.getExternalStorageState() before doing this
        if (isExternalStorageIsAvailable()){
            //get the URI
            //1. Get the external storage directory
            String appName = c.getString(R.string.app_name);
            File mediaStorageDir = new File(Environment.getExternalStorageDirectory(),appName+"/"+filePath);
            //2. Create our subdirectory
            if (!mediaStorageDir.exists()) {
                mediaStorageDir.mkdirs();
            }
            //3. Create a file name
            //4. Create the file
            File mediaFile;
            Date now = new Date();


            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss",Locale.US).format(now);
            String path = mediaStorageDir.getPath()+File.separator;
            if (mediaType==Constants.MEDIA_TYPE_IMAGE){
                mediaFile = new File(path+filePath+timestamp+".jpg");
            }else {
                return null;
            }
            //5. Return the file Uri
            return Uri.fromFile(mediaFile);

        }
        else {
            return null;
        }
    }

    private static boolean isExternalStorageIsAvailable() {
        String state = Environment.getExternalStorageState();
        return state.equals(Environment.MEDIA_MOUNTED);
    }
}