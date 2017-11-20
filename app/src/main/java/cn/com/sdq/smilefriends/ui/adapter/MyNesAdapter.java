package cn.com.sdq.smilefriends.ui.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cn.com.sdq.smilefriends.R;
import cn.com.sdq.smilefriends.bean.entity.Story;

/**
 * Created by sudeqiang on 2017/11/17.
 * 邮箱：1320909689@qq.com
 * 博客地址：https://sudeqiangxx.github.io
 */

public class MyNesAdapter extends BaseQuickAdapter<Story,BaseViewHolder> {
    private Context mContext;
    public MyNesAdapter(@LayoutRes int layoutResId, @Nullable List<Story> data, Context context) {
        super(layoutResId, data);
        this.mContext=context;
    }

    public MyNesAdapter(@Nullable List<Story> data) {
        super(data);
    }

    public MyNesAdapter(@LayoutRes int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, Story story) {
        if (story!=null){
            if (story.getImages()!=null&&story.getImages().size()>0){
                Glide.with(mContext).load(story.getImages().get(0)).into((ImageView) baseViewHolder.getView(R.id.video_preview));
            }
            ((TextView)baseViewHolder.getView(R.id.video_title)).setText(story.getTitle());
        }

    }
}
