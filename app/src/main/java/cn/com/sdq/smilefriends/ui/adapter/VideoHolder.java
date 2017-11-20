package cn.com.sdq.smilefriends.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

import cn.com.sdq.smilefriends.R;
import cn.com.sdq.smilefriends.bean.entity.video.Data;

/**
 * Created by sudeqiang on 2017/11/16.
 * 邮箱：1320909689@qq.com
 * 博客地址：https://sudeqiangxx.github.io
 */

public class VideoHolder extends BaseViewHolder {


    public final static String TAG = "RecyclerView2List";

    protected Context context = null;


    StandardGSYVideoPlayer gsyVideoPlayer;

    ImageView imageView;
     View itemview;

    GSYVideoOptionBuilder gsyVideoOptionBuilder;
    public VideoHolder(View view) {
        super(view);
        gsyVideoPlayer= (StandardGSYVideoPlayer) view.findViewById(R.id.listvideo_view);
    }
    public void onBind(final int position, Data data,Context context) {
        this.context=context;
        imageView = new ImageView(this.context);

        gsyVideoOptionBuilder = new GSYVideoOptionBuilder();
        //增加封面
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        if (data.getCover() != null && data.getCover().getFeed() != null) {
        Glide.with(context).load(data.getCover().getFeed()).into(imageView);
        }
        if (imageView.getParent() != null) {
            ViewGroup viewGroup = (ViewGroup) imageView.getParent();
            viewGroup.removeView(imageView);
        }
        String url=null;
        String title=null;
        if (data.getCover() != null && data.getCover().getFeed() != null) {
            url=data.getPlayUrl();
            title=data.getTitle();
        }

        gsyVideoOptionBuilder
                .setIsTouchWiget(false)
                .setThumbImageView(imageView)
                .setUrl(url)
                .setVideoTitle(title)
                .setCacheWithPlay(true)
                .setRotateViewAuto(true)
                .setLockLand(true)
                .setPlayTag(TAG)
                .setShowFullAnimation(true)
                .setNeedLockFull(true)
                .setPlayPosition(position)
                .setStandardVideoAllCallBack(new SampleListener() {
                    @Override
                    public void onPrepared(String url, Object... objects) {
                        super.onPrepared(url, objects);
//                        if (!gsyVideoPlayer.isIfCurrentIsFullscreen()) {
//                            //静音
//                            GSYVideoManager.instance().setNeedMute(true);
//                        }
                        GSYVideoManager.instance().setNeedMute(false);

                    }

                    @Override
                    public void onQuitFullscreen(String url, Object... objects) {
                        super.onQuitFullscreen(url, objects);
                        //全屏不静音
                        GSYVideoManager.instance().setNeedMute(false);
                    }

                    @Override
                    public void onEnterFullscreen(String url, Object... objects) {
                        super.onEnterFullscreen(url, objects);
                        GSYVideoManager.instance().setNeedMute(false);
                    }
                }).build(gsyVideoPlayer);


        //增加title
        gsyVideoPlayer.getTitleTextView().setVisibility(View.GONE);

        //设置返回键
        gsyVideoPlayer.getBackButton().setVisibility(View.GONE);

        //设置全屏按键功能
        gsyVideoPlayer.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resolveFullBtn(gsyVideoPlayer);
            }
        });
    }
    /**
     * 全屏幕按键处理
     */
    private void resolveFullBtn(final StandardGSYVideoPlayer standardGSYVideoPlayer) {
        standardGSYVideoPlayer.startWindowFullscreen(context, true, true);
    }
}
