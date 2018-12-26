package com.onexzgj.skin.skin;

import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toolbar;

import java.lang.reflect.Method;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView tbAmTitle;

    SkinFactory skinFactory;

    @SuppressLint("PrivateApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        skinFactory=new SkinFactory();
        LayoutInflater.from(this).setFactory(skinFactory);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tbAmTitle=findViewById(R.id.tb_am_title);

        Button btnAmChangeSkin=findViewById(R.id.btn_am_change_skin);
        Button btnAmChangeDefault=findViewById(R.id.btn_am_change_default);
        btnAmChangeSkin.setOnClickListener(this);
        btnAmChangeDefault.setOnClickListener(this);

    }



    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.btn_am_change_skin:
                //开始换肤
                SkinManager.getInstance().loadSkin("skinapk.apk");
                skinFactory.apply();
                break;
            case R.id.btn_am_change_default:
                //恢复默认
                SkinManager.getInstance().loadSkin("");
                skinFactory.apply();
                break;
        }
    }
}
