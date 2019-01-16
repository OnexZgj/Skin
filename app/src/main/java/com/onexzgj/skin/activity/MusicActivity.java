package com.onexzgj.skin.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.blankj.utilcode.util.SPUtils;
import com.onexzgj.skin.base.BaseActivity;
import com.onexzgj.skin.skin.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * des：我的音乐Activity
 * author：onexzgj
 * time：2019/1/15
 */
public class MusicActivity extends BaseActivity {

    @BindView(R.id.rv_am_music)
    RecyclerView rvAmMusic;

    private ArrayList<MusicItem> datas;
    private MusicAdapter adapter;


    @Override
    protected void initData() {
        datas = new ArrayList<>();
        datas.add(new MusicItem("敢做敢爱", "3:24:34", "林子祥"));
        datas.add(new MusicItem("写给黄淮", "3:24:34", ""));
        datas.add(new MusicItem("老街", "3:24:34", "李荣浩"));
        datas.add(new MusicItem("光明", "3:24:34", "汪峰"));
        datas.add(new MusicItem("浪子回头", "3:24:34", "茄子蛋"));
        datas.add(new MusicItem("你在终点等我", "3:24:34", "王菲"));
        datas.add(new MusicItem("一剪梅", "3:24:34", "李健"));
        datas.add(new MusicItem("爱江山更爱美人", "3:24:34", "李健"));
        datas.add(new MusicItem("一剪梅", "3:24:34", "李健"));
        datas.add(new MusicItem("一剪梅", "3:24:34", "李健"));
        datas.add(new MusicItem("一剪梅", "3:24:34", "李健"));
        datas.add(new MusicItem("一剪梅", "3:24:34", "李健"));
        datas.add(new MusicItem("一剪梅", "3:24:34", "李健"));
        datas.add(new MusicItem("一剪梅", "3:24:34", "李健"));
        datas.add(new MusicItem("一剪梅", "3:24:34", "李健"));
        datas.add(new MusicItem("一剪梅", "3:24:34", "李健"));
        datas.add(new MusicItem("一剪梅", "3:24:34", "李健"));

        rvAmMusic.setLayoutManager(new LinearLayoutManager(this));

        adapter = new MusicAdapter(datas);
        adapter.openLoadAnimation();
        rvAmMusic.setAdapter(adapter);
    }

    @Override
    public int getContetId() {
        return R.layout.activity_music;
    }
}
