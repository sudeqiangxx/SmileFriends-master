package com.gzzhe.zhhwlkj.baseperject.baseRecyclerViewAdapter;

/**
 * @author sudeqiang
 * @time 2016/9/7 20:15
 * @updateAuthor $Author$
 * @updateDate $Date$
 */
public interface ListViewItemMultiTypeSupport<D> {

    /**
     *
     * 获取对应的 Item 数据的布局 ID
     *
     * @param position
     * @param data
     * @return
     */
    int getItemLayoutId(int position, D data);

    /**
     * Returns the number of types of Views.
     *
     * @return
     * @see android.widget.Adapter#getViewTypeCount()
     */
    int getViewTypeCount();

    /**
     * Get the type of View for the specified item.
     *
     * @param position
     * @param data
     * @return
     * @see android.widget.Adapter#getItemViewType(int)
     */
    int getItemViewType(int position, D data);

}
