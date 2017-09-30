package com.gzzhe.zhhwlkj.baseperject.loadview; /**
 *
 */


/**
 * 显示用户界面接口，定义了一系列 Activity 或 Fragment 加载数据的规则。
 * <p/>
 * <p>
 * 我把每个用户界面抽象成了三层：从底到顶部依次是</br>
 * 1.ContentView(用于显示页面的数据，赋值ID为：{@link R.id.content_view_data_layout}), </br>
 * <p/>
 * 2.EmptyView(用于当用户请求的页面数据为空的情况下显示的布局，赋值ID:
 * {@link R.id.content_view_empty_layout} ),</br>
 * <p/>
 * 3.LoadingView(用于在加载数据等耗时操作的等待布局，赋值ID:{@link R.id.loading_view_root_layout}
 * )</br>
 * <p/>
 * 所有的ID定义请见res/values/ids.xml中
 * <p/>
 * 其中LoadingView中包含了LoadErrorView,当请求的数据失败时，LoadErrorView提供给用户一个重新加载的接口执行重新加载
 * </p>
 * <p/>
 * <p/>
 * 如果要使用这种机制，请在布局文件中严格按照我所定义的Id来给你的布局id赋值
 *
 * @author sudeqiang
 * @see LoadingView
 */
public interface UserInterface {

    /**
     * 显示内容布局
     *
     * @param animate 是否执行动画
     */
    void showContentView(boolean animate);

    /**
     * 显示加载布局
     *
     * @param animate 是否执行动画
     */
    void showLoadingView(boolean animate);

    /**
     * 显示空数据布局
     *
     * @param animate 是否执行动画
     */
    void showEmptyView(boolean animate);

    /**
     * 显示加载错误布局
     */
    void showLoadingErrorView();

    /**
     * 首次进入界面的时候是否显示 ContentView
     *
     * @return
     */
    boolean showContentViewWhenFirstEnter();

    /**
     * 首次进入界面的时候是否显示 ContentView
     *
     * @return
     */
    boolean showLoadingViewWhenFirstEnter();

    /**
     * 取消LoadingView的显示
     *
     * @param animate 是否执行动画
     */
    void dismissLoadingView(boolean animate);

}
