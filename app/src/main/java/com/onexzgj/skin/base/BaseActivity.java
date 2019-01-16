package com.onexzgj.skin.base;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.widget.Toast;

import com.blankj.utilcode.util.SPUtils;
import com.onexzgj.skin.activity.Constant;
import com.onexzgj.skin.skin.SkinFactory;
import com.onexzgj.skin.skin.SkinManager;
import com.onexzgj.skin.utils.RxSchedulers;

import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Consumer;

public abstract class BaseActivity extends AppCompatActivity {

    protected SkinFactory skinFactory;
    private String mApkName = "";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        skinFactory = new SkinFactory(this);
        LayoutInflater.from(this).setFactory(skinFactory);

        String apkPath = SPUtils.getInstance().getString(Constant.CURRENT_SKIN);
        changeSkin(apkPath);
        super.onCreate(savedInstanceState);
        setContentView(getContetId());
        ButterKnife.bind(this);

        initData();

    }

    protected abstract void initData();

    public abstract int getContetId();


    @SuppressLint("CheckResult")
    public void changeSkin(final String name) {
        Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(ObservableEmitter<Boolean> emitter) throws Exception {
                //开始换肤
                boolean b = SkinManager.getInstance().loadSkin(name);
                emitter.onNext(b);
            }
        }).compose(RxSchedulers.<Boolean>applySchedulers())
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            skinFactory.apply();
                        } else {
                            Toast.makeText(BaseActivity.this, "换肤失败了，are you sure apkName is exict!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Toast.makeText(BaseActivity.this, "换肤失败了，are you sure apkName is exict!", Toast.LENGTH_SHORT).show();
                    }
                });
    }


}
