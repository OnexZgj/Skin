package com.onexzgj.skin.activity;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.onexzgj.skin.skin.R;

import java.util.List;

/**
 * des：adapter
 * author：onexzgj
 * time：2019/1/15
 */
public class MusicAdapter extends BaseQuickAdapter<MusicItem ,BaseViewHolder> {

    public MusicAdapter( @Nullable List<MusicItem> data) {
        super(R.layout.item_music, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MusicItem item) {
        helper.setText(R.id.tv_music_name,item.getName());
        helper.setText(R.id.tv_music_time,item.getTime());
        helper.setText(R.id.tv_music_author,item.getAuthor());
    }
}
