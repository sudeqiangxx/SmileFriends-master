package com.gzzhe.zhhwlkj.baseperject.baseRecyclerViewAdapter;

import android.text.TextUtils;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.download.ImageDownloader;
import com.nostra13.universalimageloader.core.imageaware.ImageAware;
import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;

/**
 * @author sudeqiang
 * @time 2016/9/7 20:18
 * @updateAuthor $Author$
 * @updateDate $Date$
 */
public class ImageRequest {

    private static final String TAG = ImageRequest.class.getSimpleName();

    /**
     * @param uri
     * @return
     */
    private static String wrapUri(String uri) {
        if (TextUtils.isEmpty(uri)) {
            return "";
        }
        //LogUtil.d(TAG, "Image Uri = " + uri);//
        switch (ImageDownloader.Scheme.ofUri(uri)) {
            case ASSETS:
            case CONTENT:
            case DRAWABLE:
            case FILE:
            case HTTP:
            case HTTPS:
                return uri;

            default:
                //默认加载图片
                return  null;
//                return ApiConstant.BASE_URL +uri;
        }
    }

    /**
     * @param imageAware
     */
    public static void cancelDisplayTask(ImageAware imageAware) {
        ImageLoader.getInstance().cancelDisplayTask(imageAware);
    }

    /**
     * @param imageView
     */
    public static void cancelDisplayTask(ImageView imageView) {
        cancelDisplayTask(new ImageViewAware(imageView));
    }

    /**
     * @param uri
     * @param imageView
     */
    public static void displayImage(String uri, ImageView imageView) {
        ImageLoader.getInstance().displayImage(wrapUri(uri), imageView);
    }

    /**
     * @param uri
     * @param imageView
     * @param targetImageSize
     */
    public void displayImage(String uri, ImageView imageView, ImageSize targetImageSize) {
        ImageLoader.getInstance().displayImage(wrapUri(uri), imageView, targetImageSize);
    }

    /**
     * @param uri
     * @param imageView
     * @param options
     */
    public static void displayImage(String uri, ImageView imageView, DisplayImageOptions options) {
        ImageLoader.getInstance().displayImage(wrapUri(uri), imageView, options);
    }

    /**
     * @param uri
     * @param imageView
     * @param listener
     */
    public static void displayImage(String uri, ImageView imageView, ImageLoadingListener
            listener) {
        ImageLoader.getInstance().displayImage(wrapUri(uri), new ImageViewAware(imageView),
                listener);
    }

    /**
     * @param uri
     * @param imageView
     * @param options
     * @param listener
     * @param progressListener
     */
    public static void displayImage(String uri, ImageView imageView, DisplayImageOptions options,
                                    ImageLoadingListener listener, ImageLoadingProgressListener
                                            progressListener) {
        ImageLoader.getInstance().displayImage(wrapUri(uri), new ImageViewAware(imageView),
                options, listener,
                progressListener);
    }

    /**
     * @param uri
     * @param imageAware
     */
    public static void displayImage(String uri, ImageAware imageAware) {
        ImageLoader.getInstance().displayImage(wrapUri(uri), imageAware);
    }

    /**
     * @param uri
     * @param imageAware
     * @param listener
     */
    public static void displayImage(String uri, ImageAware imageAware, ImageLoadingListener
            listener) {
        ImageLoader.getInstance().displayImage(wrapUri(uri), imageAware, listener);
    }

    /**
     * @param uri
     * @param imageAware
     * @param options
     */
    public static void displayImage(String uri, ImageAware imageAware, DisplayImageOptions
            options) {
        ImageLoader.getInstance().displayImage(wrapUri(uri), imageAware, options);
    }

    /**
     * @param uri
     * @param imageAware
     * @param options
     * @param listener
     */
    public static void displayImage(String uri, ImageAware imageAware, DisplayImageOptions options,
                                    ImageLoadingListener listener) {
        ImageLoader.getInstance().displayImage(wrapUri(uri), imageAware, options, listener);
    }

    /**
     * @param uri
     * @param imageAware
     * @param options
     * @param listener
     * @param progressListener
     */
    public static void displayImage(String uri, ImageAware imageAware, DisplayImageOptions options,
                                    ImageLoadingListener listener, ImageLoadingProgressListener
                                            progressListener) {
        ImageLoader.getInstance().displayImage(wrapUri(uri), imageAware, options, listener,
                progressListener);
    }
}
