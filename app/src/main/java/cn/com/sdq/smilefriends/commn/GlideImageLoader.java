package cn.com.sdq.smilefriends.commn;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.youth.banner.loader.ImageLoader;

import cn.com.sdq.smilefriends.R;

/**
 * Created by sudeqiang on 2017/6/9.
 */

public class GlideImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        Glide.with(context).load(path).placeholder(R.mipmap.ic_launcher).
                error(R.mipmap.ic_launcher).into(imageView);
    }


}
