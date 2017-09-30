package com.gzzhe.zhhwlkj.baseperject.baseRecyclerViewAdapter;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.view.View;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author sudeqiang
 * @time 2016/9/7 20:10
 * @updateAuthor $Author$
 * @updateDate $Date$
 */
public interface HoldAble {

    /**
     * @return
     */
    View getConvertView();

    /**
     * @param viewId
     * @return
     */
    <T extends View> T getView(int viewId);

    /**
     * @param viewId
     * @param text
     * @return
     * @see TextView#setText(CharSequence)
     */
    HoldAble setText(int viewId, CharSequence text);

    /**
     * @param viewId
     * @param textResId
     * @return
     * @see TextView#setText(int)
     */
    HoldAble setText(int viewId, @StringRes int textResId);

    /**
     * @param viewId
     * @param drawableId
     * @return
     * @see ImageView#setImageResource(int)
     */
    HoldAble setImageResource(int viewId, @DrawableRes int drawableId);

    /**
     * @param viewId
     * @param drawable
     * @return
     * @see ImageView#setImageDrawable(Drawable)
     */
    HoldAble setImageDrawable(int viewId, Drawable drawable);

    /**
     * @param viewId
     * @param bitmap
     * @return
     * @see ImageView#setImageBitmap(Bitmap)
     */
    HoldAble setImageBitmap(int viewId, Bitmap bitmap);

    /**
     * load a image from uri , it can be a network image or a local image.
     *
     * @param viewId
     * @param uri
     * @return
     */
    HoldAble setImageUri(int viewId, String uri);

    /**
     * @param viewId
     * @param uri
     * @param defDrawable
     * @return
     */
    HoldAble setImageUri(int viewId, String uri, @DrawableRes int defDrawable);

    /**
     * 指定宽高加载图片
     *
     * @param viewId
     * @param uri
     * @param width  指定缩略图的宽（px）
     * @param height 指定图片的高（px）
     * @return
     */
    HoldAble setImageUri(int viewId, String uri, int width, int height);


    /**
     * @param viewId
     * @param visibility visibility One of {@link View#VISIBLE}, {@link View#INVISIBLE}
     *                   , or {@link View#GONE}.
     * @return
     * @see View#setVisibility(int)
     */
    HoldAble setVisibility(int viewId, int visibility);

    /**
     * @param viewId
     * @param enabled
     * @return
     * @see View#setEnabled(boolean)
     */
    HoldAble setEnabled(int viewId, boolean enabled);

    /**
     * @param viewId
     * @param colorResId
     * @return
     * @see View#setBackgroundColor(int)
     */
    HoldAble setBackgroundColor(int viewId, @ColorRes int colorResId);

    /**
     * @param viewId
     * @param drawable
     * @return
     * @see View#setBackgroundResource(int)
     */
    HoldAble setBackgroundResource(int viewId, @DrawableRes int drawable);

    /**
     * @param viewId
     * @param drawable
     * @return
     * @see View#setBackgroundDrawable(Drawable)
     * @deprecated use {@link #setBackground(int, Drawable)} instead
     */
    @Deprecated
    HoldAble setBackgroundDrawable(int viewId, Drawable drawable);

    /**
     * @param viewId
     * @param drawable
     * @return
     * @see View#setBackground(Drawable)
     */
    HoldAble setBackground(int viewId, Drawable drawable);

    /**
     * @param viewId
     * @param clickListener
     * @return
     */
    HoldAble setOnClickListener(int viewId, View.OnClickListener clickListener);

    /**
     * @param viewId
     * @param tag
     * @return
     * @see View#setTag(Object)
     */
    HoldAble setViewTag(int viewId, Object tag);

    /**
     * @param viewId
     * @param key    The key identifying the tag
     * @param tag
     * @return
     * @see View#setTag(int, Object)
     */
    HoldAble setViewTag(int viewId, int key, Object tag);

    /**
     * @param viewId
     * @param checked
     * @see Checkable#setChecked(boolean)
     */
    HoldAble setChecked(int viewId, boolean checked);

    /**
     * @param viewId
     * @return
     * @see Checkable#isChecked()
     */
    boolean isChecked(int viewId);

    /**
     * @param viewId
     * @see Checkable#toggle()
     */
    HoldAble toggle(int viewId);

    /**
     * @param viewId
     * @param colorRes
     * @return
     */
    HoldAble setTextColor(int viewId, @ColorInt int colorRes);

    /**
     *
     * @param viewId
     * @param textSize
     * @return
     */
    HoldAble setTextSize(int viewId, int textSize);

}

