package cn.com.sdq.smilefriends.ui.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cn.com.sdq.smilefriends.R;
import cn.com.sdq.smilefriends.bean.PhotoJson;

/**
 * Created by sudeqiang on 2017/11/14.
 * 邮箱：1320909689@qq.com
 * 博客地址：https://sudeqiangxx.github.io
 */

public class PhotoAdapter extends BaseQuickAdapter<PhotoJson.Data,BaseViewHolder> {
    private Context mContext;
    public PhotoAdapter(@LayoutRes int layoutResId, @Nullable List<PhotoJson.Data> data,Context context) {
        super(layoutResId, data);
        this.mContext=context;
    }

    public PhotoAdapter(@Nullable List<PhotoJson.Data> data,Context context) {
        super(data);
        this.mContext=context;
    }

    public PhotoAdapter(@LayoutRes int layoutResId,Context context) {
        super(layoutResId);
        this.mContext=context;
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, PhotoJson.Data photoJson) {
        if (photoJson==null){
            return;
        }
        Glide.with(mContext).load(photoJson.getUrl()).into((ImageView) baseViewHolder.getView(R.id.iv_photo));
    }
}
