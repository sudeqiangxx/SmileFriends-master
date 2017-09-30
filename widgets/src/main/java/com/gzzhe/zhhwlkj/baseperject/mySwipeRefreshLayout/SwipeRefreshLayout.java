package com.gzzhe.zhhwlkj.baseperject.mySwipeRefreshLayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Transformation;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 界面下拉刷新容器
 *
 * @author sudeqiang
 * @see android.support.v4.widget.SwipeRefreshLayout
 */
public class SwipeRefreshLayout extends ViewGroup {

    // private static final String TAG =
    // SwipeRefreshLayout.class.getSimpleName();

    /**
     * API Level 14, Android 3.0.
     */
    private static final boolean mHasHONEYCOMB;
    /**
     * API Level 14, Android 4.0.
     */
    private static final boolean mHasICS;

    static {
        final int version = Build.VERSION.SDK_INT;
        mHasICS = version >= Build.VERSION_CODES.ICE_CREAM_SANDWICH;
        mHasHONEYCOMB = version >= Build.VERSION_CODES.HONEYCOMB;
    }

    /** */
    private static final int INVALID_POINTER_ID = -1;

    /** */
    private static final float DRAG_RATE = 0.5f;

    private static final int HEADER_VIEW_DP_HEIGHT = 50;

    private static final int SHORT_ANIM_MILLS = 200;

    private static final int REFRESH_RESULT_SHOW_MILLS = 1000;

    /**
     * 下拉达到刷新的距离,dp值
     */
    private static final int REFRESH_SHOT_DRAG_DISTANCE = 60;

    /**
     * 当前处于活跃状态的手指ID
     */
    private int mActivePointerId = INVALID_POINTER_ID;

    /**
     * 更新UI的的最小滑动距离 </br>
     *
     * @see {@link ViewConfiguration#getScaledTouchSlop()}
     */
    private int mTouchSlop;

    /**
     * 到达释放刷新状态的最小下拉距离
     */
    private float mDistanceTriggerRefresh;

    /**
     * 当前的Header距离顶部的距离 ,向上为负，向下为正
     */
    private int mCurrentHeaderOffsetTop;

    /**
     * 是否计算过一开始HeaderView的位置
     */
    private boolean mOriginalOffsetCalculated;

    /**
     * Header开始滑动时候手指Y坐标
     */
    private float mSlideHeaderStartY;

    private float mCurrentY;

    private boolean mNotifyRefresh;

    private View mTargetView;
    private boolean mOnRefreshing;
    private boolean mOnLoadingMore;

    private int mHeaderViewWidth;
    private int mHeaderViewHeight;
    private HeaderView mHeaderView;
    private RelativeLayout mHeaderViewContainer;

    /**
     * 松开手指滑动Header的初始位置
     */
    private int mHeaderSlideFrom;

    /**
     * 松开手指滑动Header的结束位置
     */
    private int mHeaderSlideEnd;

    /**
     * 布局正在往回滑动中
     */
    private boolean mReturningToStart;

    /**
     * 是否计算过开始滑动Header时候的 Header的Y坐标
     */
    private boolean mInitYCaculated;

    private boolean mBeingDragged;

    private OnPullRefreshListener mPullRefreshListener;

    /** */
    private final DecelerateInterpolator mDecInterpolator = new DecelerateInterpolator(2);
    /**
     * 滑动动画
     */
    private final Animation mSlideAnimation = new Animation() {

        @Override
        public void applyTransformation(float interpolatedTime, Transformation t) {
            final int slideToTop = mHeaderSlideFrom + (int) ((mHeaderSlideEnd - mHeaderSlideFrom)
                    * interpolatedTime);
            int diff = slideToTop - mHeaderViewContainer.getTop();
            setHeaderOffsetTopAndBottom(diff, false /* requires update */);
        }
    };

    /**
     * 滑动动画的状态回调
     */
    private final AnimationListener mSlideAnimListener = new BaseAnimationListener() {

        @Override
        public void onAnimationStart(Animation animation) {
            mReturningToStart = true;
        }

        @Override
        public void onAnimationEnd(Animation animation) {
            if (mNotifyRefresh && mPullRefreshListener != null) {
                mHeaderView.setState(HeaderView.HeaderState.Refreshing);
                mOnRefreshing = true;
                mPullRefreshListener.onRefresh();
            } else {
                mHeaderView.setState(HeaderView.HeaderState.PullToRefresh);
                mOnRefreshing = false;
                mNotifyRefresh = false;
            }
            mReturningToStart = false;
        }
    };

    /** */
    private final Runnable mDelayToStart = new Runnable() {
        @Override
        public void run() {
            mNotifyRefresh = false;
            animHeaderToCorrectPosition();
        }
    };

    /**
     * @param context
     */
    public SwipeRefreshLayout(Context context) {
        this(context, null);
    }

    /**
     * Constructor
     *
     * @param context
     * @param attrs
     */
    public SwipeRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
        TypedArray a = context.obtainStyledAttributes(attrs, new int[]{android.R.attr.enabled});
        setEnabled(a.getBoolean(0, true));
        a.recycle();
        initWidget(context);
    }


    /**
     * @param context
     */
    private void initWidget(Context context) {
        setWillNotDraw(false);
        ViewCompat.setChildrenDrawingOrderEnabled(this, true);
        final DisplayMetrics metrics = getResources().getDisplayMetrics();
        float density = metrics.density;
        mHeaderViewWidth = metrics.widthPixels;
        mHeaderViewHeight = (int) (density * HEADER_VIEW_DP_HEIGHT + 0.5f);
        mDistanceTriggerRefresh = density * REFRESH_SHOT_DRAG_DISTANCE + 0.5f;
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        //
        createHeaderViewContainer();
    }

    /**
     *
     */
    private void createHeaderViewContainer() {
        mHeaderViewContainer = new RelativeLayout(getContext());
        // mHeaderViewContainer.setBackgroundColor(Color.parseColor("#08D8F5"));
        mHeaderView = new DefaultHeaderView(getContext());
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams
                .WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        params.bottomMargin = 10;
        mHeaderViewContainer.addView(mHeaderView, params);
        addView(mHeaderViewContainer);
    }

    /**
     *
     */
    private void animHeaderToCorrectPosition() {
        mSlideAnimation.reset();
        mSlideAnimation.setAnimationListener(mSlideAnimListener);
        mSlideAnimation.setDuration(SHORT_ANIM_MILLS);
        mSlideAnimation.setInterpolator(mDecInterpolator);
        mHeaderViewContainer.clearAnimation();
        mHeaderSlideFrom = mHeaderViewContainer.getTop();
        // 计算上滑结束时候的Header距离顶部的距离
        if (mNotifyRefresh) {
            mHeaderSlideEnd = (int) (mDistanceTriggerRefresh - mHeaderViewHeight);
        } else {
            mHeaderSlideEnd = -mHeaderViewHeight;
        }
        mHeaderViewContainer.startAnimation(mSlideAnimation);
    }

    /**
     * @param offset
     * @param invalidate
     */
    private void setHeaderOffsetTopAndBottom(int offset, boolean invalidate) {
        mHeaderViewContainer.bringToFront();
        mHeaderViewContainer.offsetTopAndBottom(offset);
        mCurrentHeaderOffsetTop = mHeaderViewContainer.getTop();
        if (invalidate && !mHasHONEYCOMB/* Android 3.0 */) {
            invalidate();
        }
        // TargetView到顶部的距离(pull down distance)
        int targetOffsetTop = mHeaderViewHeight + mCurrentHeaderOffsetTop;

        mHeaderView.onPullDownDistance(targetOffsetTop);

        HeaderView.HeaderState state = mHeaderView.getState();

        if (state == HeaderView.HeaderState.Refreshing || mReturningToStart) {
            return;
        }
        // Pull to Refresh
        if (targetOffsetTop < mDistanceTriggerRefresh && state != HeaderView.HeaderState.PullToRefresh) {
            mHeaderView.setState(HeaderView.HeaderState.PullToRefresh);
        }
        // Release to Refresh
        if (targetOffsetTop >= mDistanceTriggerRefresh && state != HeaderView.HeaderState.ReleaseToRefresh) {
            mHeaderView.setState(HeaderView.HeaderState.ReleaseToRefresh);
        }
    }

    /**
     * finish refresh ignore the reesult.
     */
    public void setRefreshCompleted() {
        setRefreshCompleted(false/* ignore */, null);
    }

    /**
     * finish refresh with result.
     *
     * @param refreshSuccess is Refresh Success.
     * @param message
     */
    public void setRefreshCompleted(boolean refreshSuccess, String message) {
        if (TextUtils.isEmpty(message)) {
            postDelayed(mDelayToStart, 0);
            return;
        }
        mHeaderView.setRefreshResult(refreshSuccess, message);
        postDelayed(mDelayToStart, REFRESH_RESULT_SHOW_MILLS);
    }

    /**
     * @return
     */
    public boolean isOnRefreshing() {
        return mOnRefreshing;
    }

    /**
     * @param refresh
     */
    public void setRefresh(boolean refresh) {
        mOnRefreshing = refresh;
    }

    /**
     *
     */
    private void ensureTarget() {
        int childCount = getChildCount();
        if (childCount > 3) {
            throw new RuntimeException(
                    getClass().getSimpleName() + "can only have one child , but current child " +
                            "count is " + childCount);
        }
        if (mTargetView == null) {
            for (int i = 0; i < childCount; i++) {
                View targetView = getChildAt(i);
                if (targetView != mHeaderViewContainer) {
                    mTargetView = targetView;
                    break;
                }
            }
        }
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (mTargetView == null) {
            ensureTarget();
        }
        mTargetView.measure(
                MeasureSpec.makeMeasureSpec(getMeasuredWidth() - getPaddingLeft() -
                        getPaddingRight(), MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(getMeasuredHeight() - getPaddingTop() -
                        getPaddingBottom(), MeasureSpec.EXACTLY));
        mHeaderViewContainer.measure(
                MeasureSpec.makeMeasureSpec(mHeaderViewWidth, MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(mHeaderViewHeight, MeasureSpec.EXACTLY));
        //
        if (!mOriginalOffsetCalculated) {
            mOriginalOffsetCalculated = true;
            mCurrentHeaderOffsetTop = -mHeaderViewContainer.getMeasuredHeight();
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        if (mTargetView == null) {
            return;
        }
        final int layoutWidth = getMeasuredWidth();
        final int layoutHeight = getMeasuredHeight();
        // layout header view
        int headViewWidth = mHeaderViewContainer.getMeasuredWidth();
        int headViewHeight = mHeaderViewContainer.getMeasuredHeight();
        mHeaderViewContainer.layout((layoutWidth / 2 - headViewWidth / 2), mCurrentHeaderOffsetTop,
                (layoutWidth / 2 + headViewWidth / 2), mCurrentHeaderOffsetTop + headViewHeight);
        //
        final int childLeft = getPaddingLeft();
        final int targetTop = mHeaderViewContainer.getBottom() + getPaddingTop();
        // final int targetTop = getPaddingTop() + distance - mDragUpDistance;
        final int childWidth = layoutWidth - getPaddingLeft() - getPaddingRight();
        final int childHeight = layoutHeight - getPaddingTop() - getPaddingBottom();
        // layout target view
        mTargetView.layout(childLeft, targetTop, childLeft + childWidth, targetTop + childHeight);
    }

    /**
     * @return
     */
    private boolean isChildOnTop() {
        return !canChildScrollUp();
    }

    /**
     * @return
     */
    private boolean canChildScrollUp() {

        if (mHasICS) {
            //ListView/GridView...
            if (AdapterView.class.isInstance(mTargetView)
                    || RecyclerView.class.isInstance(mTargetView)) {
                return ViewCompat.canScrollVertically(mTargetView, -1);
            }
            //LinearLayout/RelativeLayout/FrameLayout...
            if (ViewGroup.class.isInstance(mTargetView)) {

                ViewGroup viewGroup = (ViewGroup) mTargetView;

                for (int i = 0; i < viewGroup.getChildCount(); i++) {
                    View scrollChild = viewGroup.getChildAt(0);
                    if (AdapterView.class.isInstance(scrollChild)) {
                        return ViewCompat.canScrollVertically(scrollChild, -1);
                    }
                }
                return false;
            }
        } else {

            if (mTargetView instanceof AbsListView) {
                final AbsListView absListView = (AbsListView) mTargetView;
                return absListView.getChildCount() > 0 && (absListView.getFirstVisiblePosition() > 0
                        || absListView.getChildAt(0).getTop() < absListView.getPaddingTop());
            }

            if (RecyclerView.class.isInstance(mTargetView)) {
                return ViewCompat.canScrollVertically(mTargetView, -1)
                        || mTargetView.getScrollY() > 0;
            }

            //LinearLayout/RelativeLayout/FrameLayout...
            if (ViewGroup.class.isInstance(mTargetView)) {
                ViewGroup viewGroup = (ViewGroup) mTargetView;
                for (int i = 0; i < viewGroup.getChildCount(); i++) {
                    final AbsListView absListView = (AbsListView) viewGroup.getChildAt(i);
                    if (AdapterView.class.isInstance(absListView)) {
                        return absListView.getChildCount() > 0 && (absListView
                                .getFirstVisiblePosition() > 0
                                || absListView.getChildAt(0).getTop() < absListView.getPaddingTop
                                ());
                    }
                }
                return false;
            } else {
                return mTargetView.getScrollY() > 0;
            }
        }
        return false;
    }

    @Override
    public void requestDisallowInterceptTouchEvent(boolean b) {
        // Nope.
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {

        if (!isEnabled() || mReturningToStart || mOnRefreshing || mOnLoadingMore) {
            return false;
        }

        if (!isChildOnTop()) {
            mInitYCaculated = false;
        } else {
            if (!mInitYCaculated) {
                mInitYCaculated = true;
                mSlideHeaderStartY = getCurrentY(event, mActivePointerId);
            }
        }

        final int action = MotionEventCompat.getActionMasked(event);

        switch (action) {

            case MotionEvent.ACTION_DOWN://
                mBeingDragged = false;
                mActivePointerId = MotionEventCompat.getPointerId(event, 0);
                mSlideHeaderStartY = mCurrentY = getCurrentY(event, mActivePointerId);
                break;

            case MotionEvent.ACTION_MOVE://
                if (mActivePointerId == INVALID_POINTER_ID) {
                    return false;
                }
                mCurrentY = getCurrentY(event, mActivePointerId);
                if (mCurrentY == -1) {
                    return false;
                }
                float diffY;
                if (isChildOnTop() && mInitYCaculated) {
                    diffY = mCurrentY - mSlideHeaderStartY;
                    if (diffY > mTouchSlop && !mBeingDragged) {
                        mBeingDragged = true;
                        return true;
                    }
                }
                break;

            case MotionEventCompat.ACTION_POINTER_UP://
                onSecondaryPointerUp(event);
                break;

            case MotionEvent.ACTION_UP://
            case MotionEvent.ACTION_CANCEL://
                mBeingDragged = false;
                mActivePointerId = INVALID_POINTER_ID;
                break;
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!isEnabled() || mReturningToStart || (!isChildOnTop())) {
            return false;
        }
        if (isChildOnTop()) {
            return handleDragDownEvent(event);
        }
        return false;
    }

    /**
     * @param event
     * @param activePointerId
     * @return If running on a pre-android 2.0 (API lEVEL 5) device, does
     * nothing and returns -1.
     */
    private float getCurrentY(MotionEvent event, int activePointerId) {
        final int index = MotionEventCompat.findPointerIndex(event, activePointerId);
        if (index < 0) {
            return -1;
        }
        return MotionEventCompat.getY(event, index);
    }

    /**
     * @param event
     * @return
     */
    private boolean handleDragDownEvent(MotionEvent event) {

        final int pointerIndex;
        final float overscrollTop;

        final int action = MotionEventCompat.getActionMasked(event);

        switch (action) {

            case MotionEvent.ACTION_DOWN://
                mBeingDragged = false;
                mActivePointerId = MotionEventCompat.getPointerId(event, 0);
                break;

            case MotionEvent.ACTION_MOVE://
                pointerIndex = MotionEventCompat.findPointerIndex(event, mActivePointerId);
                if (pointerIndex < 0) {
                    return false;
                }
                //
                mCurrentY = getCurrentY(event, mActivePointerId);
                overscrollTop = (mCurrentY - mSlideHeaderStartY) * DRAG_RATE;

                //
                if (!mBeingDragged && isChildOnTop() && mInitYCaculated) {
                    float diffY = mCurrentY - mSlideHeaderStartY;
                    if (diffY > mTouchSlop) {
                        mBeingDragged = true;
                        return true;
                    }
                }

                if (mBeingDragged) {
                    float dragedPercent = overscrollTop / mDistanceTriggerRefresh;
                    if (dragedPercent < 0) {
                        return false;
                    }
                    //
                    float dragPercent = Math.min(1f, Math.abs(dragedPercent));
                    float extraOS = Math.abs(overscrollTop) - mDistanceTriggerRefresh;
                    float tensionSlingshotPercent = Math.max(0,
                            Math.min(extraOS, mDistanceTriggerRefresh * 2) /
                                    mDistanceTriggerRefresh);
                    float tensionPercent = (float) ((tensionSlingshotPercent / 4)
                            - Math.pow((tensionSlingshotPercent / 4), 2)) * 2f;
                    float extraMove = (mDistanceTriggerRefresh) * tensionPercent * 2;
                    int targetY = (int) ((mDistanceTriggerRefresh * dragPercent) + extraMove -
                            mHeaderViewHeight);
                    // 移动layout
                    setHeaderOffsetTopAndBottom(targetY - mCurrentHeaderOffsetTop, true);
                }
                break;

            case MotionEventCompat.ACTION_POINTER_DOWN://
                int index = MotionEventCompat.getActionIndex(event);
                mActivePointerId = MotionEventCompat.getPointerId(event, index);
                break;

            case MotionEventCompat.ACTION_POINTER_UP://
                onSecondaryPointerUp(event);
                break;

            case MotionEvent.ACTION_UP://
            case MotionEvent.ACTION_CANCEL://
                mInitYCaculated = false;
                if (mActivePointerId == INVALID_POINTER_ID) {
                    return false;
                }
                pointerIndex = MotionEventCompat.findPointerIndex(event, mActivePointerId);
                mCurrentY = MotionEventCompat.getY(event, pointerIndex);
                overscrollTop = (mCurrentY - mSlideHeaderStartY) * DRAG_RATE;
                mBeingDragged = false;
                mNotifyRefresh = overscrollTop >= mDistanceTriggerRefresh && mPullRefreshListener
                        != null;
                animHeaderToCorrectPosition();
                mActivePointerId = INVALID_POINTER_ID;
                return false;
        }
        return true;
    }

    /**
     * @param event
     */
    private void onSecondaryPointerUp(MotionEvent event) {
        final int pointerIndex = MotionEventCompat.getActionIndex(event);
        final int pointerId = MotionEventCompat.getPointerId(event, pointerIndex);
        if (pointerId == mActivePointerId) {
            final int newPointerIndex = pointerIndex == 0 ? 1 : 0;
            mActivePointerId = MotionEventCompat.getPointerId(event, newPointerIndex);
        }
    }

    /**
     * 设置下拉刷新的回调
     *
     * @param listener
     */

    private List<OnPullRefreshListener> mRefreshListener = new ArrayList<OnPullRefreshListener>();

    public void setOnPullRefreshListener(OnPullRefreshListener listener) {
        mPullRefreshListener = listener;
        if (!mRefreshListener.contains(listener)) {
            mRefreshListener.add(listener);
        }
    }

    //
    public interface OnPullRefreshListener {
        /**
         * Start Refresh
         */
        void onRefresh();
    }

    /**
     * Simple AnimationListener to avoid having to implement unneeded methods in
     * AnimationListeners.
     */
    class BaseAnimationListener implements AnimationListener {

        @Override
        public void onAnimationStart(Animation animation) {
        }

        @Override
        public void onAnimationEnd(Animation animation) {
        }

        @Override
        public void onAnimationRepeat(Animation animation) {
        }
    }
}
