/**
 * 
 */
package com.gzzhe.zhhwlkj.baseperject.mySwipeRefreshLayout;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.gzzhe.zhhwlkj.baseperject.R;


/**
 * 自定义刷新头，箭头+文字。
 * 
 * @company GCRT
 * 
 * @author YinJie 2016-1-21
 */
public class DefaultHeaderView extends HeaderView {

	// 刷新头中的三个组件
	private ProgressBar mProgressBar;
	private TextView mTextView;
	private ImageView mImageView;
	private FrameLayout mArrowContainer;
	private Animation mRotateAnimation;
	private Animation mReversedAnimation;

	/**
	 * 
	 * @param context
	 */
	public DefaultHeaderView(Context context) {
		super(context);
		initWidget();
	}

	/**
	 *
	 */
	private void initWidget() {
		LayoutInflater.from(getContext()).inflate(
				R.layout.layout_refresh_def_header_view, this, true);
		mProgressBar = (ProgressBar) findViewById(R.id.pb_refreshing);
		mArrowContainer = (FrameLayout) findViewById(R.id.container_for_arrow);
		mImageView = (ImageView) findViewById(R.id.imgv_refresh_tip);
		mTextView = (TextView) findViewById(R.id.tv_refresh_tips);
		//
		mRotateAnimation = AnimationUtils.loadAnimation(getContext(),
				R.anim.anim_rotate_0_to_180);
		mReversedAnimation = AnimationUtils.loadAnimation(getContext(),
				R.anim.anim_rotate_180_to_0);
	}

	@Override
	public void onPullToRefresh() {
		mArrowContainer.setVisibility(View.VISIBLE);
		mProgressBar.setVisibility(View.INVISIBLE);
		mTextView.setText("下拉刷新");
		mImageView.setVisibility(View.VISIBLE);
		mImageView.clearAnimation();
		mImageView.startAnimation(mReversedAnimation);
	}

	@Override
	public void onReleaseToRefresh() {
		mProgressBar.setVisibility(View.INVISIBLE);
		mImageView.clearAnimation();
		mTextView.setText("释放刷新");
		mImageView.setVisibility(View.VISIBLE);
		mImageView.clearAnimation();
		mImageView.startAnimation(mRotateAnimation);
	}

	@Override
	public void onRefreshing() {
		mProgressBar.setVisibility(View.VISIBLE);
		mImageView.setVisibility(View.INVISIBLE);
		mTextView.setText("正在刷新");
		mImageView.clearAnimation();
	}

	@Override
	public void onPullDownDistance(int distance) {
	}

	@Override
	public void setRefreshResult(boolean success, String message) {
		mArrowContainer.setVisibility(View.GONE);
		if (!TextUtils.isEmpty(message)) {
			mTextView.setText(message);
		}
	}
}
