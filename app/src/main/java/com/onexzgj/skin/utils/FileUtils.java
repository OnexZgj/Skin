package com.onexzgj.skin.utils;

import android.content.Context;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class FileUtils {




    public static String copyAssetsToCache(Context context, String name)  {

        try{
        File cacheDir = context.getCacheDir();
        if (!cacheDir.exists())
            cacheDir.mkdirs();


        File outFile = new File(cacheDir, name);
        if (!outFile.exists()){
            boolean res = outFile.createNewFile();
            if (res){
                InputStream is = context.getAssets().open(name);
                FileOutputStream fos = new FileOutputStream(outFile);
                byte[] buffer=new byte[is.available()];
                int byteCount;
                while ((byteCount=is.read(buffer))!=-1){
                    fos.write(buffer,0,byteCount);
                }
                fos.flush();
                is.close();
                fos.close();
                return outFile.getAbsolutePath();
            }

        }else{
            return outFile.getAbsolutePath();
        }
        }catch (IOException e){
            e.printStackTrace();
        }

        return "";
    }

}
