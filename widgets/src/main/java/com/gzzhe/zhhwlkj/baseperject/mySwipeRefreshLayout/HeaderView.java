/**
 * 
 */
package com.gzzhe.zhhwlkj.baseperject.mySwipeRefreshLayout;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * 
 *
 * @author sudeqiang
 */
public abstract class HeaderView extends FrameLayout {

	private HeaderState mCurrentState = HeaderState.PullToRefresh;//
	private final Object mStateLock = new Object();

	/**
	 * 
	 * @param context
	 */
	public HeaderView(Context context) {
		super(context);
	}

	/**
	 * 
	 * @param context
	 * @param attrs
	 */
	public HeaderView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	/**
	 * 
	 * @param context
	 * @param attrs
	 * @param defStyle
	 */
	public HeaderView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	protected abstract void onPullToRefresh();

	protected abstract void onReleaseToRefresh();

	protected abstract void onRefreshing();

	protected abstract void onPullDownDistance(int distance);

	/**
	 * 
	 * 
	 * @param success
	 * @param message
	 */
	protected abstract void setRefreshResult(boolean success, String message);

	/**
	 * 
	 * @return the mCurrentState
	 */
	public HeaderState getState() {
		synchronized (mStateLock) {
			return mCurrentState;
		}
	}

	/**
	 * @param state
	 */
	public void setState(HeaderState state) {

		if (mCurrentState == state) {
			return;
		}

		mCurrentState = state;

		synchronized (mStateLock) {

			switch (mCurrentState) {

			case PullToRefresh:
				onPullToRefresh();
				break;

			case ReleaseToRefresh:
				onReleaseToRefresh();
				break;

			case Refreshing:
				onRefreshing();
				break;

			default:
				break;
			}
		}
	}

	public enum HeaderState {
		PullToRefresh, ReleaseToRefresh, Refreshing
	}

}
