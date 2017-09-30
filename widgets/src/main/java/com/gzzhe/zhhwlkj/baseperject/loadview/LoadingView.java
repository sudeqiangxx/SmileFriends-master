package com.gzzhe.zhhwlkj.baseperject.loadview; /**
 *
 */


import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.gzzhe.zhhwlkj.baseperject.R;


/**
 *
 * @author sudeqiang
 * 页面加载进度显示
 */
public class LoadingView extends FrameLayout {

    private View mLoadWaitingLayout; // 显示加载等待
    private View mLoadErrorLayout;// 显示加载失败
    private TextView mReloadButton;// 重新加载

    private LoadingViewCallBack mCallBack;
    private ImageView mLoadingErrImage;

    /**
     * @param context
     */
    public LoadingView(Context context) {
        this(context, null);
    }

    /**
     * @param context
     * @param attrs
     */
    public LoadingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /**
     * @param context
     * @param attrs
     * @param defStyle
     */
    public LoadingView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initWidget();
    }

    /**
     *
     */
    private void initWidget() {
        LayoutInflater.from(getContext()).inflate(R.layout.layout_custom_view_loading_view, this, true);
        if (isInEditMode()) {
            return;
        }
        mLoadWaitingLayout = findViewById(R.id.loading_view_waiting_layout);
        mLoadErrorLayout = findViewById(R.id.loading_view_error_layout);
        mLoadingErrImage = (ImageView) findViewById(R.id.iv_loading_errow);


        mReloadButton = (TextView) findViewById(R.id.btn_reload);
        mReloadButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mLoadWaitingLayout.setVisibility(View.VISIBLE);
                mLoadErrorLayout.setVisibility(View.GONE);
                if (mCallBack != null) {
                    mCallBack.onReload(LoadingView.this);
                }
            }
        });

        TextView goToHomeBt = (TextView) findViewById(R.id.btn_go_home_page);//去首页按钮
        TextView contactServiceBt = (TextView) findViewById(R.id.btn_contact_customer_service);//联系客服按钮
        goToHomeBt.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mCallBack!=null){
                    mCallBack.onGoToHomePage();
                }
            }
        });
        contactServiceBt.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mCallBack!=null){
                    mCallBack.onContactService();
                }
            }
        });
    }

    public void setLoadError(Activity activity) {
        mLoadWaitingLayout.setVisibility(View.GONE);
//        boolean networkAvailable = .isNetworkConnected(activity);
////        无网络和服务器错误显示不同的图片
//        if(!networkAvailable){
////            mLoadingErrImage.setImageResource(R.mipmap.no_net);
//        }else{
////            mLoadingErrImage.setImageResource(R.mipmap.net_error);
//        }
        mLoadErrorLayout.setVisibility(View.VISIBLE);
    }

    /**
     * @param callBack
     */
    public void setOnLoadingViewCallBack(LoadingViewCallBack callBack) {
        mCallBack = callBack;
    }

    public interface LoadingViewCallBack {
        void onReload(LoadingView view);
        void onGoToHomePage();
        void onContactService();
    }
}
