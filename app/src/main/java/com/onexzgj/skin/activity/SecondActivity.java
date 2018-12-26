package com.onexzgj.skin.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.onexzgj.skin.base.BaseActivity;
import com.onexzgj.skin.skin.R;

public class SecondActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        changeSkin();
    }
}
