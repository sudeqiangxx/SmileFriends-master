package com.gzzhe.zhhwlkj.baseperject.baseRecyclerViewAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @author sudeqiang
 * @time 2016/9/7 20:07
 * @updateAuthor $Author$
 * @updateDate $Date$
 */
public abstract class BaseRecyclerViewAdapter<D> extends RecyclerView.Adapter<RecyclerViewHolder> {

    private List<D> mDataSet;
    private List<D> mOriginalDatas;
    private boolean mNotifyOnChange = true;
    private final Object mLock = new Object();
    private ListViewItemMultiTypeSupport<D> mMultiViewTypeSupport;
    private int mItemLayoutId;
    private LayoutInflater mInflater;
    private Context mContext;

    /**
     * @param context
     */
    public BaseRecyclerViewAdapter(Context context) {
        init(context, 0, new ArrayList<D>());
    }

    /**
     * @param context
     * @param itemViewLayoutId
     */
    public BaseRecyclerViewAdapter(Context context, int itemViewLayoutId) {
        init(context, itemViewLayoutId, new ArrayList<D>());
    }

    /**
     * @param context
     * @param dataSet
     */
    public BaseRecyclerViewAdapter(Context context, D[] dataSet) {
        init(context, 0, Arrays.asList(dataSet));
    }

    /**
     * @param context
     * @param dataSet
     * @param itemViewLayoutId
     */
    public BaseRecyclerViewAdapter(Context context, D[] dataSet, int itemViewLayoutId) {
        init(context, itemViewLayoutId, Arrays.asList(dataSet));
    }

    /**
     * @param context
     * @param dataSet
     */
    public BaseRecyclerViewAdapter(Context context, List<D> dataSet) {
        init(context, 0, dataSet);
    }

    /**
     * @param context
     * @param dataSet
     * @param itemViewLayoutId
     */
    public BaseRecyclerViewAdapter(Context context, List<D> dataSet, int itemViewLayoutId) {
        init(context, itemViewLayoutId, dataSet);
    }

    /**
     * @param context
     * @param itemViewLayoutId
     * @param dataSet
     */
    private void init(Context context, int itemViewLayoutId, List<D> dataSet) {
        mContext=context;
        if (dataSet == null) {
            dataSet = new ArrayList<D>();
        }
        mDataSet = dataSet;
        mItemLayoutId = itemViewLayoutId;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(mItemLayoutId, parent, false);
        return new RecyclerViewHolder(itemView,mContext);
    }

    @Override
    public int getItemViewType(int position) {
        if (mMultiViewTypeSupport != null) {
            return mMultiViewTypeSupport.getItemViewType(position, mDataSet.get(position));
        }
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    /**
     * @param position
     * @return
     */
    public D getItem(int position) {
        return mDataSet.get(position);
    }

    /**
     * @return
     */
    public List<D> getDateSet() {
        return mDataSet;
    }

    // Operate Internal DataSet Methods START*****/

    /**
     * 添加一个对象到末尾
     *
     * @param object
     */
    public void add(D object) {
        synchronized (mLock) {
            if (mOriginalDatas != null) {
                mOriginalDatas.add(object);
            } else {
                mDataSet.add(object);
            }
        }
        if (mNotifyOnChange) {
            notifyDataSetChanged();
        }
    }

    /**
     * 添加一个集合到末尾
     *
     * @param collection
     */
    public void addAll(Collection<? extends D> collection) {
        synchronized (mLock) {
            if (mOriginalDatas != null) {
                mOriginalDatas.addAll(collection);
            } else {
                mDataSet.addAll(collection);
            }
        }
        if (mNotifyOnChange) {
            notifyDataSetChanged();
        }
    }

    /**
     * 添加一些数据到结尾
     *
     * @param items
     */
    public void addAll(D... items) {
        synchronized (mLock) {
            if (mOriginalDatas != null) {
                Collections.addAll(mOriginalDatas, items);
            } else {
                Collections.addAll(mDataSet, items);
            }
        }
        if (mNotifyOnChange) {
            notifyDataSetChanged();
        }
    }

    /**
     * 向数据集合中指定的序号插入对象
     *
     * @param object The object to insert into the array.
     * @param index  The index at which the object must be inserted.
     */
    public void insert(D object, int index) {
        synchronized (mLock) {
            if (mOriginalDatas != null) {
                mOriginalDatas.add(index, object);
            } else {
                mDataSet.add(index, object);
            }
        }
        if (mNotifyOnChange)
            notifyItemInserted(index);
    }

    /**
     * 移除数据集合中指定的对象
     *
     * @param object The object to remove.
     */
    public void remove(D object) {
        synchronized (mLock) {
            if (mOriginalDatas != null) {
                mOriginalDatas.remove(object);
            } else {
                mDataSet.remove(object);
            }
        }
        if (mNotifyOnChange) {
            notifyDataSetChanged();
        }
    }

    /**
     * 移除数据集合中指定的对象
     *
     * @param position
     */
    public void remove(int position) {
        synchronized (mLock) {
            if (mOriginalDatas != null && mOriginalDatas.size() > position) {
                mOriginalDatas.remove(position);
            } else {
                if (mDataSet.size() > position) {
                    mDataSet.remove(position);
                }
            }
        }
        if (mNotifyOnChange) {
            notifyItemRemoved(position);
        }
    }

    /**
     * 清空数据集合
     */
    public void clear() {
        synchronized (mLock) {
            if (mOriginalDatas != null) {
                mOriginalDatas.clear();
            } else {
                mDataSet.clear();
            }
        }
        if (mNotifyOnChange)
            notifyDataSetChanged();
    }

    public abstract void onBindViewHolder(RecyclerViewHolder holder, int position);

    // Operate Internal DataSet Methods END *****/

}
