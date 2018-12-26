package com.onexzgj.skin.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.widget.Toast;

import com.onexzgj.skin.activity.MainActivity;
import com.onexzgj.skin.skin.SkinFactory;
import com.onexzgj.skin.skin.SkinManager;
import com.onexzgj.skin.utils.RxSchedulers;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Consumer;

public class BaseActivity extends AppCompatActivity {

    protected SkinFactory skinFactory;
    private String mApkName="";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        skinFactory = new SkinFactory();
        LayoutInflater.from(this).setFactory(skinFactory);
        super.onCreate(savedInstanceState);
    }


    public void changeSkin(){
        Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(ObservableEmitter<Boolean> emitter) throws Exception {
                //开始换肤
                boolean b = SkinManager.getInstance().loadSkin("skinapk.apk");
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
