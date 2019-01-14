package com.onexzgj.skin.skin;

import android.content.Context;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import java.util.ArrayList;

/**
 * 自定义View的填充factory
 *
 * @author onexzgj
 */
public class SkinFactory implements LayoutInflater.Factory {

    private AppCompatActivity mActivity;
    private ArrayList<SkinViewItem> viewItems = new ArrayList<>();

    private static final String[] sClassPrefixList = {
            "android.widget.",
            "android.view.",
            "android.webkit.",
            "android.support.v7.widget."
    };

    public SkinFactory(AppCompatActivity activity) {
        this.mActivity = activity;
    }

    @Override
    public View onCreateView(String name, Context context, AttributeSet attributeSet) {

        View view = createView(name, context, attributeSet);

        if (view != null) {
            passSkinViewAttr(view, context, attributeSet);
        }
        return view;
    }


    /**
     * 开始换肤的方法
     */
    public void apply() {

        for (int i = 0; i < viewItems.size(); i++) {
            SkinViewItem skinViewItem = viewItems.get(i);
            skinViewItem.apply();
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            int color = SkinManager.getInstance().getColorPrimaryDark();
            StatusBarBackground statusBarBackground = new StatusBarBackground(
                    mActivity, color);
            if (color != -1)
                statusBarBackground.setStatusBarbackColor();
        }
    }


    /**
     * 2,解析反射生成的View的属性
     *
     * @param view
     * @param context
     * @param attributeSet android:background=@drawable/bg
     */
    private void passSkinViewAttr(View view, Context context, AttributeSet attributeSet) {
        ArrayList<SkinViewAttr> viewAttrs = new ArrayList<>();
        for (int i = 0; i < attributeSet.getAttributeCount(); i++) {
            //获取到属性的name,value
            String attrName = attributeSet.getAttributeName(i);   //background
            String attrValue = attributeSet.getAttributeValue(i);  //@12346622

            if (attrValue.contains("@")) {
                int id = Integer.parseInt(attrValue.substring(1));   // 即R资源中对应的Id @12346622
                String typeName = context.getResources().getResourceTypeName(id);  //drawable
                String entryName = context.getResources().getResourceEntryName(id);  //bg

                Log.e("TAG----->", "passSkinViewAttr:  id :" + id + " |typeName : " + typeName + " | entryName : " + entryName);

                SkinViewAttr attr = new SkinViewAttr(id, attrName, typeName, entryName);
                viewAttrs.add(attr);
            }

            if (!viewAttrs.isEmpty()) {
                //假如说属性不为空
                viewItems.add(new SkinViewItem(view, viewAttrs));
            }

        }
    }

    /**
     * 1,通过反射创建View
     *
     * @param name         view的名称
     * @param context
     * @param attributeSet view的属性
     * @return
     */
    private View createView(String name, Context context, AttributeSet attributeSet) {
        View view = null;
        if (name.contains(".")) {  //view的名称中带点，说明是自定义View
            view = SkinUtils.crateView(name, context, attributeSet);
        } else {  //系统的View,直接通过反射创建
            for (int i = 0; i < sClassPrefixList.length; i++) { //比如android.widget.Button
                String viewName = sClassPrefixList[i] + name;
                view = SkinUtils.crateView(viewName, context, attributeSet);
                if (view != null) {
                    break;
                }
            }
        }
        return view;
    }
}
