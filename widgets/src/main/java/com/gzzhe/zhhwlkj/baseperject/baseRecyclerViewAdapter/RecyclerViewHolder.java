package com.gzzhe.zhhwlkj.baseperject.baseRecyclerViewAdapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gzzhe.zhhwlkj.baseperject.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

/**
 * @author sudeqiang
 * @time 2016/9/7 20:09
 * @updateAuthor $Author$
 * @updateDate $Date$
 */
public class RecyclerViewHolder extends RecyclerView.ViewHolder
        implements HoldAble, View.OnClickListener, View.OnLongClickListener {

    private View mItemView;
    private SparseArray<View> mViewArray;
    private OnRecyclerViewItemClickListener mItemClickListener;
    private OnRecyclerViewItemLongClickListener mItemLongClickListener;
    private Context mContext;


    /**
     * @param itemView
     */
    public RecyclerViewHolder(View itemView, Context context) {
        super(itemView);
        mItemView = itemView;
        mViewArray = new SparseArray<View>();
        mContext=context;
    }

    /**
     * @param itemClickListener the recyclerViewItemClickListener to set
     */
    public void setOnRecyclerViewItemClickListener(OnRecyclerViewItemClickListener
                                                           itemClickListener) {
        this.mItemClickListener = itemClickListener;
        mItemView.setOnClickListener(this);
    }

    /**
     * @param listener
     */
    public void setOnRecyclerViewItemLongClickListener(OnRecyclerViewItemLongClickListener
                                                               listener) {
        this.mItemLongClickListener = listener;
        mItemView.setOnLongClickListener(this);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends View> T getView(int viewId) {
        View view = mViewArray.get(viewId);
        if (view == null) {
            view = mItemView.findViewById(viewId);
            mViewArray.put(viewId, view);
        }
        return (T) view;
    }

    @Override
    public RecyclerViewHolder setText(int viewId, CharSequence text) {
        TextView view = getView(viewId);
        view.setText(text);
        return this;
    }

    @Override
    public RecyclerViewHolder setImageResource(int viewId, int drawableId) {
        ImageView view = getView(viewId);
        view.setImageResource(drawableId);
        return this;
    }

    @Override
    public RecyclerViewHolder setImageBitmap(int viewId, Bitmap bitmap) {
        ImageView view = getView(viewId);
        view.setImageBitmap(bitmap);
        return this;
    }

    @Override
    public RecyclerViewHolder setVisibility(int viewId, int visibility) {
        View view = getView(viewId);
        view.setVisibility(visibility);
        return this;
    }

    @Override
    public RecyclerViewHolder setImageUri(int viewId, String url) {
        ImageView imageView = getView(viewId);

        //替换为Glide图片加载框架
        Glide.with(mContext).load(url).placeholder(R.drawable.default_img).error(R.drawable.default_img).into(imageView);
        return this;
    }



    @Override
    public HoldAble setImageUri(int viewId, String uri, @DrawableRes int defDrawable) {
        ImageView imageView = getView(viewId);
        //替换图片加载框架
        Glide.with(mContext).load(uri).into(imageView);
        return this;
    }

    /**
     * 指定宽高加载图片
     *
     * @param viewId
     * @param uri
     * @param width  指定缩略图的宽（px）
     * @param height 指定图片的高（px）
     * @return
     */
    @Override
    public HoldAble setImageUri(int viewId, String uri, int width, int height) {
        String newUrl = uri + "@" + width + "w_" + height + "h" + "_1l";
        return setImageUri(viewId, newUrl);
    }


    @Override
    public RecyclerViewHolder setOnClickListener(int viewId, View.OnClickListener clickListener) {
        View view = getView(viewId);
        view.setOnClickListener(clickListener);
        return this;
    }

    @Override
    public boolean onLongClick(View view) {
        if (mItemLongClickListener != null) {
            mItemLongClickListener.onRecyclerViewItemLongClick(view, getLayoutPosition());
        }
        return false;
    }

    @Override
    public void onClick(View view) {
        if (mItemClickListener != null) {
            mItemClickListener.onRecyclerViewItemClick(view, getLayoutPosition());
        }
    }


    @Override
    public View getConvertView() {
        return mItemView;
    }

    @Override
    public HoldAble setText(int viewId, int textResId) {
        TextView view = getView(viewId);
        view.setText(textResId);
        return this;
    }

    @Override
    public HoldAble setImageDrawable(int viewId, Drawable drawable) {
        ImageView view = getView(viewId);
        view.setImageDrawable(drawable);
        return this;
    }

    @Override
    public HoldAble setEnabled(int viewId, boolean enable) {
        getView(viewId).setEnabled(enable);
        return this;
    }

    @Override
    public HoldAble setBackgroundColor(int viewId, int color) {
        //        View view = getView(viewId);
        //        view.setBackgroundColor(color);
        return this;
    }

    @Override
    public HoldAble setBackgroundResource(int viewId, int drawable) {
        getView(viewId).setBackgroundResource(drawable);
        return this;
    }

    @SuppressWarnings("deprecation")
    @Override
    public HoldAble setBackgroundDrawable(int viewId, Drawable drawable) {
        getView(viewId).setBackgroundDrawable(drawable);
        return this;
    }

    @SuppressWarnings("deprecation")
    @SuppressLint("NewApi")
    @Override
    public HoldAble setBackground(int viewId, Drawable drawable) {
        View view = getView(viewId);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            view.setBackground(drawable);
        } else {
            view.setBackgroundDrawable(drawable);
        }
        return this;
    }

    @Override
    public HoldAble setViewTag(int viewId, Object tag) {
        getView(viewId).setTag(tag);
        return this;
    }

    @Override
    public HoldAble setViewTag(int viewId, int key, Object tag) {
        getView(viewId).setTag(key, tag);
        return this;
    }

    @Override
    public HoldAble setChecked(int viewId, boolean checked) {
        View view = getView(viewId);
        if (Checkable.class.isInstance(view)) {
            ((Checkable) view).setChecked(checked);
        }
        return this;
    }

    @Override
    public boolean isChecked(int viewId) {
        View view = getView(viewId);
        if (Checkable.class.isInstance(view)) {
            return ((Checkable) view).isChecked();
        }
        return false;
    }

    @Override
    public HoldAble toggle(int viewId) {
        View view = getView(viewId);
        if (Checkable.class.isInstance(view)) {
            setChecked(viewId, !isChecked(viewId));
        }
        return this;
    }

    @Override
    public HoldAble setTextColor(int viewId, @ColorInt int colorRes) {
        TextView view = getView(viewId);
        view.setTextColor(colorRes);
        return this;
    }

    @Override
    public HoldAble setTextSize(int viewId, int textSize) {
        TextView view = getView(viewId);
        view.setTextSize(textSize);
        return this;
    }


    /**
     * Item点击事件回调接口
     */
    public interface OnRecyclerViewItemClickListener {

        /**
         * @param view
         * @param position
         */
        void onRecyclerViewItemClick(View view, int position);

    }

    /**
     * Item长按事件回调接口
     */
    public interface OnRecyclerViewItemLongClickListener {

        /**
         * @param view
         * @param position
         */
        void onRecyclerViewItemLongClick(View view, int position);
    }

}
