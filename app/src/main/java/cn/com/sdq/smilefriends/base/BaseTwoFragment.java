package cn.com.sdq.smilefriends.base;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;


import java.lang.ref.WeakReference;

import butterknife.ButterKnife;
import cn.com.sdq.smilefriends.R;
import cn.com.sdq.smilefriends.ui.activity.MainActivity;


/**
 * Created by sudeqiang on 2017/6/6.
 */

public abstract class BaseTwoFragment extends Fragment {
    private BaseActivity mParentActivity;
    private MainActivity thisParent;
    protected View mDataRootView;
    protected View mContentView;

    private Animation mFadeInAnimation;
    private Animation mFadeOutAnimation;
    protected WeakReference<View> mRootView;

    protected String CatetoryId;

    public String getCatetoryId() {
        return CatetoryId;
    }

    public void setCatetoryId(String catetoryId) {
        CatetoryId = catetoryId;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (getActivity() instanceof MainActivity) {
            thisParent= (MainActivity) getActivity();
        } else {
            mParentActivity = (BaseActivity) getActivity();
        }
        View view = onContentView();
        ButterKnife.bind(this, view);
        //布局层次
        if (thisParent==null){
            mFadeInAnimation = AnimationUtils.loadAnimation(mParentActivity, android.R.anim.fade_in);
            mFadeOutAnimation = AnimationUtils.loadAnimation(mParentActivity, android.R.anim.fade_out);
        }else {
            mFadeInAnimation = AnimationUtils.loadAnimation(thisParent, android.R.anim.fade_in);
            mFadeOutAnimation = AnimationUtils.loadAnimation(thisParent, android.R.anim.fade_out);
        }


        return view;
    }
//


    public View onContentView() {
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context
                .LAYOUT_INFLATER_SERVICE);
        LinearLayout layout = new LinearLayout(getActivity());
        layout.setOrientation(LinearLayout.VERTICAL);

        if (setContentViewId() != 0) {
            View view = inflater.inflate(setContentViewId(), layout, false);
            layout.addView(view);
        }

        return layout;
    }


    protected abstract boolean hasTitleBar();

    protected abstract int setContentViewId();

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);

    }


    protected void init() {
        initView();
    }

    protected abstract void initView();


}
