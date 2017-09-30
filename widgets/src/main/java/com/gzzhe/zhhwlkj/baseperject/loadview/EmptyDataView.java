/**
 * 
 */
package com.gzzhe.zhhwlkj.baseperject.loadview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gzzhe.zhhwlkj.baseperject.R;


/**
 * 加载数据为空的时候的视图
 *
 *
 * @author sudeqiang
 */
public class EmptyDataView extends LinearLayout {

	//TODO 更改成null数据时显示的
	private static final int DEF_LOGO_DRAWABLE_ID = R.mipmap.ic_launcher_round;
	private static final String DEF_LABEL_STRING_ID = "暂无数据";
	private static final int DEF_LOGO_LABEL_INTERVAL = 10;// dp value

	private ImageView mLogoImgView;
	private TextView mLabelTextView;

	private Drawable mLogoDrawable;
	private String mLabelText;
	private int mInterval;// 图标和文字之间的间隔dimension

	/**
	 * @param context
	 */
	public EmptyDataView(Context context) {
		this(context, null);
	}

	/**
	 * @param context
	 * @param attrs
	 */
	public EmptyDataView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	@RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
	public EmptyDataView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.EmptyDataView);
		mLabelText = a.getString(R.styleable.EmptyDataView_labelText);
		mLogoDrawable = a.getDrawable(R.styleable.EmptyDataView_emptyImage);
		mInterval = a.getDimensionPixelSize(R.styleable.EmptyDataView_interval,
				dp2px(context, DEF_LOGO_LABEL_INTERVAL));
		a.recycle();
		initWidget();
	}

	public static int dp2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		// return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
		// dip, ctx.getResources().getDisplayMetrics());
		return (int) (dpValue * scale + 0.5f);
	}
	/**
	 */
	private void initWidget() {
		setId(R.id.content_view_empty_layout);
		mLogoImgView = new ImageView(getContext());
		if (mLogoDrawable == null) {
			mLogoDrawable = getResources().getDrawable(DEF_LOGO_DRAWABLE_ID);
		}
		mLogoImgView.setScaleType(ScaleType.CENTER_INSIDE);
		mLogoImgView.setImageDrawable(mLogoDrawable);
		//
		mLabelTextView = new TextView(getContext());
		if (TextUtils.isEmpty(mLabelText)) {
			mLabelText = DEF_LABEL_STRING_ID;
		}
		mLabelTextView.setSingleLine();
		mLabelTextView.setTextColor(getResources().getColor(R.color.textcolor_gray));
		mLabelTextView.setText(mLabelText);
		//
		setOrientation(VERTICAL);
		setGravity(Gravity.CENTER_HORIZONTAL);
		LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		addView(mLogoImgView, params);
		params.topMargin = mInterval;
		addView(mLabelTextView, params);
	}

}
