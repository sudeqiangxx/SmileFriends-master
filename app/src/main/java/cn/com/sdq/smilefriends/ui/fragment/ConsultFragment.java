package cn.com.sdq.smilefriends.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.request.BaseRequest;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.sdq.smilefriends.R;
import cn.com.sdq.smilefriends.ar.ARActivity;
import cn.com.sdq.smilefriends.base.BaseTwoFragment;
import cn.com.sdq.smilefriends.bean.PhotoJson;
import cn.com.sdq.smilefriends.commn.AppConfig;
import cn.com.sdq.smilefriends.commn.AppConstans;
import cn.com.sdq.smilefriends.commn.okhttp.JsonCallback;
import cn.com.sdq.smilefriends.commn.okhttp.StringUtils;
import cn.com.sdq.smilefriends.commn.okhttp.WrapUrl;
import cn.com.sdq.smilefriends.ui.adapter.PhotoAdapter;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/2/11.
 */

public class ConsultFragment extends BaseTwoFragment implements AppConstans {

    @Bind(R.id.rlv_photo)
    RecyclerView rlvPhoto;
    @Bind(R.id.srl_photo)
    SmartRefreshLayout srlPhoto;
    private PhotoAdapter mPhotoAdapter;
    private List<PhotoJson.Data> mPhotoData;
    private int currentPage=0;

    @Override
    protected boolean hasTitleBar() {
        return false;
    }

    @Override
    protected int setContentViewId() {
        return R.layout.consult_fragmentlayout;
    }

    @Override
    protected void initView() {
        srlPhoto.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshLayout) {
                currentPage++;
                getPhoto(currentPage,false,true);
            }

            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                currentPage=1;
                getPhoto(currentPage,true,false);
            }
        });
        getPhoto(1,false,false);
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



    private void initRlv(List<PhotoJson.Data> data){
        mPhotoData=data;
        mPhotoAdapter=new PhotoAdapter(R.layout.item_photo_layout,mPhotoData,getActivity());
        rlvPhoto.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        rlvPhoto.setAdapter(mPhotoAdapter);
    }
    public static final String AR_KEY = "arvideo";
    public static final int AR_VALUE = 1;

    @Override
    public void onPause() {
        super.onPause();
        OkGo.getInstance().cancelTag("photo");
    }

    private void getPhoto(int page, final boolean isRefresh, final boolean isMore){
        String url= WrapUrl.wrapG(AppConfig.GRILD_URL);
        String pages= page+"";
        StringBuffer sb=new StringBuffer();
        sb.append(url);
        sb.append(pages);

        OkGo.get(sb.toString())
                .tag("photo")
                .execute(new JsonCallback<PhotoJson>() {
                    @Override
                    public void onSuccess(PhotoJson photoJson, Call call, Response response) {
                        if (photoJson!=null&&!photoJson.isError()){
                            List<PhotoJson.Data> datas=photoJson.getResults();
                            if (datas!=null&&datas.size()>0){
                                if (isRefresh){
                                    initRlv(datas);
                                }else if (isMore){
                                    if (mPhotoData==null){
                                        mPhotoData=new ArrayList<PhotoJson.Data>();
                                    }
                                    if (rlvPhoto!=null){
                                        mPhotoData.addAll(mPhotoData.size(),datas);
                                    }

                                }else {
                                    if (rlvPhoto!=null){
                                        initRlv(datas);
                                    }
                                }
                            }

                        }


                    }

                    @Override
                    public void onAfter(PhotoJson photoJson, Exception e) {
                        super.onAfter(photoJson, e);
                        if (srlPhoto!=null){
                            srlPhoto.finishLoadmore();
                            srlPhoto.finishRefresh();
                        }
                    }

                    @Override
                    public void onBefore(BaseRequest request) {
                        super.onBefore(request);
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                    }
                });

    }
}
