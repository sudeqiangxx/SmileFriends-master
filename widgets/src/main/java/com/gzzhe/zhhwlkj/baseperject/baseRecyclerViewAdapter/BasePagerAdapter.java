/**
 *
 */
package com.gzzhe.zhhwlkj.baseperject.baseRecyclerViewAdapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;


import com.gzzhe.zhhwlkj.baseperject.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * ViewPager 适配器基类
 *
 * @author YinJie 2016-1-26
 * @company GCRT
 */
public abstract class BasePagerAdapter<D> extends PagerAdapter implements OnClickListener {

    protected static final int KEY_ITEM_TAG = R.id.list_item_position;

    private Context mContext;
    private List<D> mDataSet;

    private boolean mNotifyOnChange = true;

    private final Object mLock = new Object();

    protected OnPagerItemClickListener mItemClickListener;

    /**
     * @param context
     */
    public BasePagerAdapter(Context context) {
        init(context, null);
    }

    /**
     * @param context
     * @param dataSet
     */
    public BasePagerAdapter(Context context, List<D> dataSet) {
        init(context, dataSet);
    }

    /**
     * @param context
     * @param dataSet
     */
    private void init(Context context, List<D> dataSet) {
        if (dataSet == null) {
            dataSet = new ArrayList<D>();
        }
        mContext = context;
        mDataSet = dataSet;
    }

    /**
     * @param notifyOnChange
     */
    public void setNotifyOnChange(boolean notifyOnChange) {
        mNotifyOnChange = notifyOnChange;
    }

    /**
     * 用于返回一个 View 对象提供给 ViewPager 显示,子类必须实现此方法。
     *
     * @param container
     * @param position
     * @return
     */
    public abstract View getItemView(ViewGroup container, int position);

    /**
     * @return
     */
    public Context getContext() {
        return mContext;
    }

    /**
     * @return
     */
    public List<D> getDataSet() {
        return mDataSet;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = getItemView(container, position);
        ViewGroup.LayoutParams params = view.getLayoutParams();
        if (params != null) {
            container.addView(view, view.getLayoutParams());
        } else {
            container.addView(view);
        }
        return view;
    }

    @Override
    public void onClick(View view) {
        Integer position = (Integer) view.getTag(KEY_ITEM_TAG);
        if (mItemClickListener != null && position != null) {
            mItemClickListener.onPagerItemClick(position, view, getItem(position));
        }
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        if (View.class.isInstance(object)) {
            final View imageView = (View) object;
            container.removeView(imageView);
        }
    }

    @Override
    public int getCount() {
        return mDataSet.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    /**
     * 获取对应项的数据实体
     *
     * @param position
     */
    public D getItem(int position) {
        if (position >= mDataSet.size()) {
            return null;
        }
        return mDataSet.get(position);
    }

    /**
     * 清空数据集合
     */
    public void clear() {
        synchronized (mLock) {
            mDataSet.clear();
        }
        if (mNotifyOnChange)
            notifyDataSetChanged();
    }

    /**
     * 添加一个对象到末尾
     *
     * @param data
     */
    public void add(D data) {
        if (data == null) {
            return;
        }
        synchronized (mLock) {
            mDataSet.add(data);
        }
        if (mNotifyOnChange)
            notifyDataSetChanged();
    }

    /**
     * 添加一个集合到末尾
     *
     * @param collection
     */
    public void addAll(Collection<D> collection) {
        if (collection == null || collection.size() == 0) {
            return;
        }
        synchronized (mLock) {
            mDataSet.addAll(collection);
        }
        if (mNotifyOnChange)
            notifyDataSetChanged();
    }

    /**
     * @param listener the mItemClickListener to set
     */
    public void setOnPagerItemClickListener(OnPagerItemClickListener listener) {
        mItemClickListener = listener;
    }

    public interface OnPagerItemClickListener {
        /**
         * @param position
         * @param view
         * @param data
         */
        void onPagerItemClick(int position, View view, Object data);
    }

}
