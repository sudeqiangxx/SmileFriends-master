package cn.com.sdq.smilefriends.util;

import android.webkit.WebSettings;
import android.webkit.WebView;

/**
 * Created by sudeqiang on 2017/6/7.
 * webview 工具类
 */

public class WebViewUtils {
    /**
     *加载html网页url
     * @param webView
     * @param url
     */
    public static void openUrl(WebView webView,String url){
        webViewSet(webView);
        webView.loadUrl(url);
    }

    /**
     * 带参打开网页
     * @param webView
     * @param url
     * @param postData
     */
    public static void openUrlPostParams(WebView webView,String url,byte[] postData){
        webViewSet(webView);
        webView.postUrl(url,postData);
    }

    /**
     * 基于baseUrl加载html数据
     * @param webView
     * @param baseUrl
     * @param htmlData
     */
    public static void openHtmeDataWithBaseUrl(WebView webView,String baseUrl,String htmlData){
        webViewSet(webView);
        webView.loadDataWithBaseURL(baseUrl,htmlData,"text/html","utf-8",null);
    }

    /**
     * 基于uri加载加载html数据
     * @param webView
     * @param htmlDataUri
     */
    public static void openHtmeDataWithUri(WebView webView,String htmlDataUri){
        webViewSet(webView);
        webView.loadUrl(htmlDataUri);
    }


    /**
     * webView设置
     * @param webView
     */
    private static void webViewSet(WebView webView) {
        WebSettings settings = webView.getSettings();
        settings.setUseWideViewPort(true);
        //自适应屏幕
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setLoadWithOverviewMode(true);
        //支持js操作
        settings.setJavaScriptEnabled(true);
        //支持缩放
        settings.setSupportZoom(true);
        settings.setDomStorageEnabled(true);
    }
}
