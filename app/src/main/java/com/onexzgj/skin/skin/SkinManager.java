package com.onexzgj.skin.skin;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;

import java.lang.reflect.Method;

public class SkinManager {
    private static final SkinManager ourInstance = new SkinManager();
    /**
     * 皮肤包的包名
     */
    private String mPackageName;

    public static SkinManager getInstance() {
        return ourInstance;
    }


    private Context mContext;

    public Resources mSkinResources;

    private String apkPath;

    private SkinManager() {
    }

    public void init(Context context){
        mContext=context.getApplicationContext();
    }


    public void loadSkin(String apkName){

        String apkPath = FileUtils.copyAssetsToCache(mContext, apkName);
        try {
            AssetManager manager = AssetManager.class.newInstance();
            Method method = AssetManager.class.getDeclaredMethod("addAssetPath", String.class);
            method.invoke(manager,apkPath);
            Resources res = mContext.getResources();

            mSkinResources = new Resources(manager, res.getDisplayMetrics(), res.getConfiguration());

            mPackageName = mContext.getPackageManager().getPackageArchiveInfo(apkPath,
                    PackageManager.GET_ACTIVITIES | PackageManager.GET_SERVICES).packageName;

        } catch (Exception e) {

            e.printStackTrace();
        }
    }


    public Drawable getDrawable(int id ){

        Drawable drawable = mContext.getResources().getDrawable(id);
        if (mSkinResources!=null){
            String name = mContext.getResources().getResourceEntryName(id);
            int resId = mSkinResources.getIdentifier(name, "drawable", mPackageName);
            if (resId>0){
                return mSkinResources.getDrawable(resId);
            }
        }
        return drawable ;
    }


    public int getColor(int id ){

        int color = mContext.getResources().getColor(id);
        if (mSkinResources!=null){
            String name = mContext.getResources().getResourceEntryName(id);
            int resId = mSkinResources.getIdentifier(name, "color", mPackageName);
            if (resId>0){
                return mSkinResources.getColor(resId);
            }
        }
        return color ;
    }


}
