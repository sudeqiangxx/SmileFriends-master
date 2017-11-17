package cn.com.sdq.smilefriends.ui.fragment;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lzy.okgo.OkGo;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.utils.CommonUtil;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;
import com.shuyu.gsyvideoplayer.video.base.GSYVideoPlayer;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.com.sdq.smilefriends.R;
import cn.com.sdq.smilefriends.base.BaseTwoFragment;
import cn.com.sdq.smilefriends.bean.JakeBean;
import cn.com.sdq.smilefriends.bean.entity.video.Feature;
import cn.com.sdq.smilefriends.bean.entity.video.Issue;
import cn.com.sdq.smilefriends.bean.entity.video.Item;
import cn.com.sdq.smilefriends.commn.AppConfig;
import cn.com.sdq.smilefriends.commn.okhttp.JsonCallback;
import cn.com.sdq.smilefriends.commn.okhttp.WrapUrl;
import cn.com.sdq.smilefriends.ui.adapter.MyRecyclerAdapter;
import cn.com.sdq.smilefriends.ui.adapter.VideoAdapter;
import cn.com.sdq.smilefriends.util.utils.ScrollCalculatorHelper;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Jack on 16/10/27.
 */
public class FragmentThree extends BaseTwoFragment {

    @Bind(R.id.rlv_video)
    RecyclerView rlvVideo;
    @Bind(R.id.srl_video)
    SmartRefreshLayout srlVideo;
    private String nextPageUrl;
    private List<Item> mItems;
    private ScrollCalculatorHelper scrollCalculatorHelper;
    private LinearLayoutManager linearLayoutManager;
    private boolean mFull=false;

    @Override
    public boolean onBackPressed() {
        if (mFull){

            return true;
        }else {
            return false;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("FragmentOne", "onCreate");
    }

    private VideoAdapter mVideoAdapter;

    @Override
    protected boolean hasTitleBar() {
        return false;
    }

    @Override
    protected int setContentViewId() {
        return R.layout.fragment_three;
    }



    private void initData() {
        getVideos(false);
    }

    @Override
    public void initView() {

        srlVideo.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshLayout) {
                getVideos(true);
            }
        });
        initData();
    }

    private void initRlv(List<Item> items){
        mItems=items;
        if (mItems==null){
            mItems=new ArrayList<>();
        }
        //限定范围为屏幕一半的上下偏移180
        int playTop = CommonUtil.getScreenHeight(getActivity()) / 2 - CommonUtil.dip2px(getActivity(), 180);
        int playBottom = CommonUtil.getScreenHeight(getActivity()) / 2 + CommonUtil.dip2px(getActivity(), 180);
        //自定播放帮助类
        scrollCalculatorHelper = new ScrollCalculatorHelper(R.id.video_item_player, playTop, playBottom);
        mVideoAdapter=new VideoAdapter(R.layout.item_video_layout,mItems,getActivity());
        linearLayoutManager=new LinearLayoutManager(getActivity());
        rlvVideo.setLayoutManager(linearLayoutManager);
        rlvVideo.setAdapter(mVideoAdapter);
        rlvVideo.addOnScrollListener(new RecyclerView.OnScrollListener() {
            int firstVisibleItem, lastVisibleItem;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                scrollCalculatorHelper.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                firstVisibleItem = linearLayoutManager.findFirstVisibleItemPosition();
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();

                //这是滑动自动播放的代码
                if (!mFull) {
                    scrollCalculatorHelper.onScroll(rlvVideo, firstVisibleItem, lastVisibleItem, lastVisibleItem - firstVisibleItem);
                }
            }
        });
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //如果旋转了就全屏
        if (newConfig.orientation != ActivityInfo.SCREEN_ORIENTATION_USER) {
            mFull = false;
        } else {
            mFull = true;
        }

    }



    private void getVideos(final boolean isNextPage) {
        String url = WrapUrl.wrapVideo(AppConfig.JING_XUAN);
        if (isNextPage){
            url=nextPageUrl;
        }
        OkGo.get(url)
                .execute(new JsonCallback<Feature>() {
                    @Override
                    public void onSuccess(Feature feature, Call call, Response response) {
                        if (feature!=null){
                            List<Issue> issues=feature.getIssueList();
                            nextPageUrl=feature.getNextPageUrl();
                            if (issues!=null&&issues.size()>0){
                                List<Item> items=issues.get(0).getItemList();
                                if (items!=null&&items.size()>0){

                                    if (isNextPage){
                                        if (mItems==null){
                                            mItems=new ArrayList<Item>();
                                        }
                                        mItems.addAll(mItems.size(),items);
                                    }else {
                                        initRlv(items);
                                    }
                                }
                            }

                        }
                    }

                    @Override
                    public void onAfter(Feature feature, Exception e) {
                        super.onAfter(feature, e);
                        srlVideo.finishLoadmore();
                    }
                });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        GSYVideoPlayer.releaseAllVideos();
    }


}
