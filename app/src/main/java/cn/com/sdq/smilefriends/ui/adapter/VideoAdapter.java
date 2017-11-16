package cn.com.sdq.smilefriends.ui.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cn.com.sdq.smilefriends.R;
import cn.com.sdq.smilefriends.bean.entity.video.Data;
import cn.com.sdq.smilefriends.bean.entity.video.Item;

/**
 * Created by sudeqiang on 2017/11/14.
 * 邮箱：1320909689@qq.com
 * 博客地址：https://sudeqiangxx.github.io
 */

public class VideoAdapter extends BaseQuickAdapter<Item,VideoHolder> {
    private Context mContext;
    public VideoAdapter(@LayoutRes int layoutResId, @Nullable List<Item> data,Context context) {
        super(layoutResId, data);
        this.mContext=context;
    }

    public VideoAdapter(@Nullable List<Item> data,Context context) {
        super(data);
        this.mContext=context;
    }

    public VideoAdapter(@LayoutRes int layoutResId,Context context) {
        super(layoutResId);
        this.mContext=context;
    }


    @Override
    protected void convert(VideoHolder baseViewHolder, Item item) {
        if (item!=null){
            if (item.getData()!=null){
                Data data=item.getData();
                if (data!=null){
                    if ("video".equals(item.getType())){
                        ((TextView)baseViewHolder.getView(R.id.tv_title)).setText(data.getTitle());

                        baseViewHolder.onBind(getParentPosition(item),data,mContext);

                    }else {
//                        Glide.with(mContext).load(data.getImage()).into((ImageView) baseViewHolder.getView(R.id.iv_video_header));
                    }
                }
            }
        }
    }
}
