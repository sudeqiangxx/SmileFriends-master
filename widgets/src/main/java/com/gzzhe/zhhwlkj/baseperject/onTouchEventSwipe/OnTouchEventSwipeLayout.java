package com.gzzhe.zhhwlkj.baseperject.onTouchEventSwipe;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * @author sudeqiang
 * @time 2016/11/4 12:55
 * @updateAuthor $Author$
 * @updateDate $Date$
 */
public class OnTouchEventSwipeLayout extends SwipeRefreshLayout {
    public OnTouchEventSwipeLayout(Context context) {
        super(context);
    }

    public OnTouchEventSwipeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return true;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return false;
    }
}
