package com.onexzgj.skin.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.SpanUtils;
import com.onexzgj.skin.base.BaseActivity;
import com.onexzgj.skin.skin.R;
import com.onexzgj.skin.skin.SkinManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @BindView(R.id.tv_am_change_skin_blue)
    TextView tvAmChangeSkinBlue;
    @BindView(R.id.tv_am_change_skin_green)
    TextView tvAmChangeSkinGreen;
    @BindView(R.id.tv_am_change_default)
    TextView tvAmChangeDefault;
    @BindView(R.id.ll_am_music)
    LinearLayout llAmMusic;


    private String TAG="MainActivity";


    @Override
    protected void initData() {

    }

    @Override
    public int getContetId() {
        return R.layout.activity_main;
    }

    @OnClick({R.id.ll_am_music, R.id.tv_am_change_skin_blue, R.id.tv_am_change_skin_green, R.id.tv_am_change_default})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_am_music:
                //TODO 验证RecycleView/ListView的换肤是否支持
                startActivity(new Intent(MainActivity.this,MusicActivity.class));
                break;
            case R.id.tv_am_change_skin_blue:
                SkinManager.getInstance().loadSkin("skinblueapk.apk");
                skinFactory.apply();
                SPUtils.getInstance().put(Constant.CURRENT_SKIN,"skinblueapk.apk");
                break;
            case R.id.tv_am_change_skin_green:
                SkinManager.getInstance().loadSkin("skingreen.apk");
                skinFactory.apply();
                SPUtils.getInstance().put(Constant.CURRENT_SKIN,"skingreen.apk");
                break;
            case R.id.tv_am_change_default:
                SkinManager.getInstance().loadSkin("");
                skinFactory.apply();
                SPUtils.getInstance().put(Constant.CURRENT_SKIN,"");
                break;
        }
    }
}
