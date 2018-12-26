package com.onexzgj.skin.skin;

import android.app.Application;

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //进行初始化
        SkinManager.getInstance().init(this);
    }
}
