package cn.com.sdq.smilefriends.ui.adapter;

import android.content.Context;
import android.view.View;

import com.gzzhe.zhhwlkj.baseperject.baseRecyclerViewAdapter.BaseRecyclerViewAdapter;
import com.gzzhe.zhhwlkj.baseperject.baseRecyclerViewAdapter.RecyclerViewHolder;

import java.util.List;


import cn.com.sdq.smilefriends.R;
import cn.com.sdq.smilefriends.bean.NewsJson;

/**
 * Created by sudeqiang on 2017/8/3.
 */

public class NewsAdapter extends BaseRecyclerViewAdapter<NewsJson.Result.Data> {
    private List<NewsJson.Result.Data> data;

    public NewsAdapter(Context context, List<NewsJson.Result.Data> dataSet, int itemViewLayoutId) {
        super(context, dataSet, itemViewLayoutId);
        data=dataSet;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        if (data!=null){
            NewsJson.Result.Data newdata=data.get(position);
            if (newdata!=null){
                holder.setImageUri(R.id.iv_news_image,newdata.getThumbnail_pic_s());
                holder.setText(R.id.tv_new_title,newdata.getTitle());
                holder.setText(R.id.tv_information,newdata.getDate()+"  "+newdata.getAuthor_name());
                holder.setOnRecyclerViewItemClickListener(new RecyclerViewHolder.OnRecyclerViewItemClickListener() {
                    @Override
                    public void onRecyclerViewItemClick(View view, int position) {
                        if (listener!=null){
                            listener.onItemClick(position);
                        }
                    }
                });

            }
        }
    }


    public interface  onItemClickListener{
        void onItemClick(int position);
    }
    private onItemClickListener listener;

    public void setOnItemClickListener(onItemClickListener onItemClickListener){
        listener=onItemClickListener;
    }

}
