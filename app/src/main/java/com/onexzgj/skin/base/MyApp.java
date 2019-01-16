package com.onexzgj.skin.base;

import android.app.Application;

import com.blankj.utilcode.util.Utils;
import com.onexzgj.skin.skin.SkinManager;

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //进行初始化
        Utils.init(this);
        SkinManager.getInstance().init(this);
    }
}
