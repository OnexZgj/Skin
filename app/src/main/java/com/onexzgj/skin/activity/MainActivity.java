package com.onexzgj.skin.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.onexzgj.skin.base.BaseActivity;
import com.onexzgj.skin.skin.R;
import com.onexzgj.skin.skin.SkinFactory;
import com.onexzgj.skin.skin.SkinManager;
import com.onexzgj.skin.utils.RxSchedulers;

import java.lang.reflect.Method;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Consumer;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    TextView tbAmTitle;


    @SuppressLint("PrivateApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tbAmTitle = findViewById(R.id.tb_am_title);

        Button btnAmChangeSkin = findViewById(R.id.btn_am_change_skin);
        Button btnAmChangeDefault = findViewById(R.id.btn_am_change_default);
        Button btnAmChangeActivity = findViewById(R.id.btn_am_change_activity);
        btnAmChangeSkin.setOnClickListener(this);
        btnAmChangeDefault.setOnClickListener(this);
        btnAmChangeActivity.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_am_change_skin:
                changeSkin();

//                Observable.create(new ObservableOnSubscribe<Boolean>() {
//                    @Override
//                    public void subscribe(ObservableEmitter<Boolean> emitter) throws Exception {
//                        //开始换肤
//                        boolean b = SkinManager.getInstance().loadSkin("skinapk.apk");
//                        emitter.onNext(b);
//                    }
//                }).compose(RxSchedulers.<Boolean>applySchedulers())
//                        .subscribe(new Consumer<Boolean>() {
//                            @Override
//                            public void accept(Boolean aBoolean) throws Exception {
//                                if (aBoolean) {
//                                    skinFactory.apply();
//                                } else {
//                                    Toast.makeText(MainActivity.this, "换肤失败了，are you sure apkName is exict!", Toast.LENGTH_SHORT).show();
//                                }
//                            }
//                        }, new Consumer<Throwable>() {
//                            @Override
//                            public void accept(Throwable throwable) throws Exception {
//                                Toast.makeText(MainActivity.this, "换肤失败了，are you sure apkName is exict!", Toast.LENGTH_SHORT).show();
//                            }
//                        });
                break;
            case R.id.btn_am_change_default:
                //恢复默认
                SkinManager.getInstance().loadSkin("");
                skinFactory.apply();
                break;
            case R.id.btn_am_change_activity:
                startActivity(new Intent(this,SecondActivity.class));
                break;
        }
    }
}
