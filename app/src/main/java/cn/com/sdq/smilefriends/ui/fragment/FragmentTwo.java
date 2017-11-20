package cn.com.sdq.smilefriends.ui.fragment;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lzy.okgo.OkGo;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.com.sdq.smilefriends.R;
import cn.com.sdq.smilefriends.base.BaseFragment;
import cn.com.sdq.smilefriends.base.BaseTwoFragment;
import cn.com.sdq.smilefriends.bean.JakeBean;
import cn.com.sdq.smilefriends.bean.entity.BeforeNews;
import cn.com.sdq.smilefriends.bean.entity.Story;
import cn.com.sdq.smilefriends.commn.AppConfig;
import cn.com.sdq.smilefriends.commn.okhttp.JsonCallback;
import cn.com.sdq.smilefriends.commn.okhttp.WrapUrl;
import cn.com.sdq.smilefriends.contact.Jake;
import cn.com.sdq.smilefriends.ui.activity.WebActivity;
import cn.com.sdq.smilefriends.ui.adapter.JakeGifAdapter;
import cn.com.sdq.smilefriends.ui.adapter.MyNesAdapter;
import cn.com.sdq.smilefriends.util.DateFormatUtil;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Jack on 16/10/27.
 */
public class FragmentTwo extends BaseTwoFragment  {
    @Bind(R.id.gif_fragment_recycler_view)
    RecyclerView mRecyclerView;
    @Bind(R.id.srl_video)
    SmartRefreshLayout srlVideo;
    //view和model的桥梁
    private Jake.Prestener prestener;
    //请求标记[0表示请求第一页，1表示请求更多]
    private int requestCode = 0;
    private View view;
    private JakeGifAdapter mAdapter;
    private List<JakeBean> mDatas = new ArrayList<>();
    private LinearLayoutManager linearLayoutManager;
    private MyNesAdapter myNesAdapter;
    private static final int ADD_DATA = 0;
    private Date date;
    private List<Story> mNews;
    private long day=24*60*60*1000;
    private int currentpage=1;

    @Override
    public boolean onBackPressed() {
        return false;
    }




    @Override
    protected boolean hasTitleBar() {
        return false;
    }

    @Override
    protected int setContentViewId() {
        return R.layout.fragment_two;
    }





    private void initRlv(List<Story> data) {
        mNews = data;
        if (mRecyclerView == null) {
            mRecyclerView = (RecyclerView) view.findViewById(R.id.gif_fragment_recycler_view);
        }
        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
//        mAdapter = new JakeGifAdapter(getActivity(), mDatas);
        myNesAdapter = new MyNesAdapter(R.layout.item_mynews_layout, mNews,getActivity());
        mRecyclerView.setAdapter(myNesAdapter);
        myNesAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                Story story=mNews.get(i);
                if (story!=null){
                    Intent intent=new Intent();
                    intent.setClass(getActivity(), WebActivity.class);
                    intent.putExtra("contentId",story.getId()+"");
                    startActivity(intent);
                }
            }
        });

    }

    private void getNews(String date, final boolean isMore) {
        String parms = date;
        String url = WrapUrl.wrapZhiHU(AppConfig.ZHIHU_URL);
        StringBuffer sb = new StringBuffer();
        sb.append(url);
        sb.append(parms);
        OkGo.get(sb.toString())
                .execute(new JsonCallback<BeforeNews>() {
                    @Override
                    public void onSuccess(BeforeNews beforeNews, Call call, Response response) {
                        if (beforeNews!=null){
                            List<Story> stories=beforeNews.getStories();
                            if (stories!=null&&stories.size()>0){
                                if (isMore){
                                    if (mNews==null){
                                        mNews=new ArrayList<Story>();
                                    }
                                    if (mRecyclerView!=null){
                                        mNews.addAll(mNews.size(),stories);
                                        myNesAdapter.notifyDataSetChanged();
                                    }
                                }else {
                                    if (mRecyclerView!=null){
                                        initRlv(stories);
                                    }
                                }
                            }else {
                                Toast.makeText(getActivity(),"无数据",Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                             @Override
                             public void onAfter(BeforeNews beforeNews, Exception e) {
                                 super.onAfter(beforeNews, e);
                                 if (srlVideo!=null){
                                     srlVideo.finishLoadmore();
                                     srlVideo.finishRefresh();
                                 }
                             }
                         }
                );

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    protected void initView() {
        srlVideo.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshLayout) {
                currentpage++;
                long time=System.currentTimeMillis()-(currentpage*day);
                getNews(DateFormatUtil.dateFormat(new Date(time)),true);


            }

            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                currentpage=1;
                long time=System.currentTimeMillis()-(currentpage*day);
                getNews(DateFormatUtil.dateFormat(new Date(time)),true);
            }
        });
        currentpage=1;
        long time=System.currentTimeMillis()-(currentpage*day);
        getNews(DateFormatUtil.dateFormat(new Date(time)),false);
    }

}
