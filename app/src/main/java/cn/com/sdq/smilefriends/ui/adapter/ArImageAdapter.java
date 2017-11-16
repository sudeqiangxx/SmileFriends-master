package cn.com.sdq.smilefriends.ui.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

import cn.com.sdq.smilefriends.R;

/**
 * Created by sudeqiang on 2017/10/17.
 */

public class ArImageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Integer> images;
    private Context mContext;
    public ArImageAdapter(Context context,List<Integer> images){
        this.mContext=context;
        this.images=images;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.ar_item_layout,null);
        ArImageHolder imageHolder=new ArImageHolder(view);
        return imageHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ArImageHolder arImageHolder= (ArImageHolder) holder;
        if (images!=null&&arImageHolder!=null){
            int imageResId=images.get(position);
            Drawable drawable=mContext.getDrawable(imageResId);
//            Glide.with(mContext).load(imageResId).into(arImageHolder.ivArImage);
            arImageHolder.ivArImage.setImageDrawable(drawable);
            arImageHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    if (onItemLongClickListener!=null){
                        onItemLongClickListener.onLongItemClick(position);
                    }
                    return true;
                }
            });

        }
    }

    @Override
    public int getItemCount() {
        return images==null?0:images.size();
    }


    class ArImageHolder extends RecyclerView.ViewHolder{
        ImageView ivArImage;
        View view;
        public ArImageHolder(View itemView) {
            super(itemView);
            ivArImage= (ImageView) itemView.findViewById(R.id.iv_ar_image);
            view=itemView.findViewById(R.id.rl_ar_item);
        }
    }

    private OnItemLongClickListener onItemLongClickListener;
    public interface  OnItemLongClickListener{
        void onLongItemClick(int position);
    }
    public void setOnItemLongClickListener(OnItemLongClickListener listener){
        onItemLongClickListener=listener;
    }
}
