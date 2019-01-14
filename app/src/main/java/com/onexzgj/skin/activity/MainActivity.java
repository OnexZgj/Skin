package com.onexzgj.skin.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.onexzgj.skin.base.BaseActivity;
import com.onexzgj.skin.skin.R;
import com.onexzgj.skin.skin.SkinManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @BindView(R.id.tb_am_title)
    TextView tbAmTitle;
    @BindView(R.id.btn_am_change_skin)
    Button btnAmChangeSkin;
    @BindView(R.id.btn_am_change_default)
    Button btnAmChangeDefault;
    @BindView(R.id.btn_am_change_activity)
    Button btnAmChangeActivity;
    @BindView(R.id.tv_am_change_skin_blue)
    TextView tvAmChangeSkinBlue;
    @BindView(R.id.tv_am_change_skin_red)
    TextView tvAmChangeSkinRed;
    @BindView(R.id.tv_am_change_default)
    TextView tvAmChangeDefault;
    private String TAG="MainActivity";


    @SuppressLint("PrivateApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        int identifier = getResources().getIdentifier("status_bar_height", "dimen", "android");
        int dimensionPixelSize = getResources().getDimensionPixelSize(identifier);
        Log.d(TAG, "onCreate: " +dimensionPixelSize);

    }

    @OnClick({R.id.btn_am_change_skin, R.id.btn_am_change_default, R.id.btn_am_change_activity, R.id.tv_am_change_skin_blue, R.id.tv_am_change_skin_red, R.id.tv_am_change_default})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_am_change_skin:

                changeSkin();
                break;
            case R.id.btn_am_change_default:
                //恢复默认
//                SkinManager.getInstance().loadSkin("skinred.apk");
//                skinFactory.apply();

                SkinManager.getInstance().loadSkin("");
                skinFactory.apply();
                break;
            case R.id.btn_am_change_activity:
                startActivity(new Intent(this, SecondActivity.class));
                break;
            case R.id.tv_am_change_skin_blue:
                break;
            case R.id.tv_am_change_skin_red:
                break;
            case R.id.tv_am_change_default:
                break;
        }
    }
}
