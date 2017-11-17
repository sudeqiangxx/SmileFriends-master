package cn.com.sdq.smilefriends.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.gzzhe.zhhwlkj.baseperject.pullLoadMoreRecyclerView.PullLoadMoreRecyclerView;
import com.gzzhe.zhhwlkj.baseperject.pullLoadMoreRecyclerView.RefreshRecyclerView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.request.BaseRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.com.sdq.smilefriends.R;
import cn.com.sdq.smilefriends.base.BaseTwoFragment;
import cn.com.sdq.smilefriends.bean.NewsJson;
import cn.com.sdq.smilefriends.commn.APIService;
import cn.com.sdq.smilefriends.commn.AppConfig;
import cn.com.sdq.smilefriends.commn.AppConstans;
import cn.com.sdq.smilefriends.commn.okhttp.JsonCallback;
import cn.com.sdq.smilefriends.commn.okhttp.StringUtils;
import cn.com.sdq.smilefriends.commn.okhttp.WrapUrl;
import cn.com.sdq.smilefriends.ui.activity.HtmlActivity;
import cn.com.sdq.smilefriends.ui.adapter.NewsAdapter;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by sudeqiang on 2017/8/1.
 */

public class NewsFragment extends BaseTwoFragment {

    public static final String HTML_URL = "html_url";
    @Bind(R.id.plv_new_data)
    PullLoadMoreRecyclerView plvNewData;

    private List<NewsJson.Result.Data> dataList;
    private NewsAdapter mNewsAdapter;


    @Override
    protected boolean hasTitleBar() {
        return false;
    }

    @Override
    protected int setContentViewId() {
        return R.layout.news_fragment_layout;
    }

    @Override
    protected void initView() {
        getNews();
    }


    @Override
    public boolean onBackPressed() {
        return false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        OkGo.getInstance().cancelTag("new");
    }

    @Override
    public void onPause() {
        super.onPause();
        OkGo.getInstance().cancelTag("new");
    }

    private void initRlv(List<NewsJson.Result.Data> newsdata) {
        if (newsdata == null) {
            dataList = new ArrayList<>();
        } else {
            dataList = newsdata;
        }
        if (plvNewData == null)
            return;
        mNewsAdapter = new NewsAdapter(getActivity(), dataList, R.layout.item_new_layout);
        plvNewData.setLinearLayout();
        plvNewData.setAdapter(mNewsAdapter);
        plvNewData.setOnPullLoadMoreListener(new PullLoadMoreRecyclerView.PullLoadMoreListener() {
            @Override
            public void onRefresh() {
                getNews();
            }

            @Override
            public void onLoadMore() {
                //
                Toast.makeText(getActivity(), "没有更多", Toast.LENGTH_SHORT).show();
                plvNewData.setPullLoadMoreCompleted();
            }
        });
        mNewsAdapter.setOnItemClickListener(new NewsAdapter.onItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if (dataList != null) {
                    NewsJson.Result.Data data = dataList.get(position);
                    if (data != null) {
                        String htmlUrl = data.getUrl();
                        if (StringUtils.isNotEmpty(htmlUrl)) {
                            Intent intent = new Intent();
                            intent.putExtra(HTML_URL, htmlUrl);
                            intent.setClass(getActivity(), HtmlActivity.class);
                            startActivity(intent);
                        }
                    }
                }
            }
        });


    }

    private void getNews() {
        String url = WrapUrl.wrap(AppConfig.LIST_PA);


        OkGo.post(url)
                .tag("grild")
                .cacheKey("grild")
                .execute(new JsonCallback<NewsJson>() {
                    @Override
                    public void onBefore(BaseRequest request) {
                        super.onBefore(request);
                    }

                    @Override
                    public void onAfter(NewsJson newsJson, Exception e) {
                        super.onAfter(newsJson, e);
                        if (plvNewData != null)
                            plvNewData.setPullLoadMoreCompleted();
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                    }

                    @Override
                    public void onCacheSuccess(NewsJson newsJson, Call call) {
                        super.onCacheSuccess(newsJson, call);
                        if (newsJson != null) {
                            NewsJson.Result result = newsJson.getResult();
                            if (result != null) {
                                if ("1".equals(result.getStat())) {
                                    List<NewsJson.Result.Data> data = result.getData();
                                    if (data != null && data.size() > 0) {
                                        initRlv(data);
                                    }
                                }
                            }
                        }
                    }

                    @Override
                    public void onSuccess(NewsJson newsJson, Call call, Response response) {
                        if (newsJson != null) {
                            NewsJson.Result result = newsJson.getResult();
                            if (result != null) {
                                if ("1".equals(result.getStat())) {
                                    List<NewsJson.Result.Data> data = result.getData();
                                    if (data != null && data.size() > 0) {
                                        initRlv(data);
                                    }
                                }
                            }
                        }
                    }
                });
    }


}
