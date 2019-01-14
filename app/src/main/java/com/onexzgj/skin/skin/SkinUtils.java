package com.onexzgj.skin.skin;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import java.lang.reflect.Constructor;

public class SkinUtils {

//    public static int getStatusBarColorResId(Context context){
//        context.getResources().getIdentifier("id_layout_test_image", "id", getPackageName());
//        return ;
//    }


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

}
