package com.onexzgj.skin.skin;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;

public class FileUtils {


    /**
     * 通过view的类名和属性返回该View
     * @param name
     * @param context
     * @param attributeSet
     * @return
     */
    public static View crateView(String name, Context context, AttributeSet attributeSet){
        View view=null;
        try {
            Class<?> aClass = context.getClassLoader().loadClass(name);
            Constructor<?> constructor = aClass.getConstructor(Context.class, AttributeSet.class);
            view = (View) constructor.newInstance(context, attributeSet);
        } catch (Exception e) {
            return  null;
        }
        return view;
    }

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
