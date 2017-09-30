package cn.com.sdq.smilefriends.commn;

import android.app.Activity;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.File;

import cn.com.sdq.smilefriends.R;


/**
 * Created by sudeqiang on 2017/8/15.
 */

public class PicassoImageLoader implements com.lzy.imagepicker.loader.ImageLoader {
    @Override
    public void displayImage(Activity activity, String s, ImageView imageView, int i, int i1) {

//        Picasso.with(activity)//
//                .load(Uri.fromFile(new File(s)))//
//                .placeholder(R.mipmap.default_image)//
//                .error(R.mipmap.default_image)//
//                .resize(240, 100)//
//                .centerInside()//
//                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)//
//                .into(imageView);
        Glide.with(activity)
                .load(Uri.fromFile(new File(s)))
                .placeholder(R.mipmap.default_image)
                .error(R.mipmap.default_image)
                .skipMemoryCache(true)
                .into(imageView);
    }

    @Override
    public void clearMemoryCache() {

    }
}
