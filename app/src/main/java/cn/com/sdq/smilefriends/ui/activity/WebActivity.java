package cn.com.sdq.smilefriends.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.com.sdq.smilefriends.R;
import cn.com.sdq.smilefriends.base.BaseActivity;
import cn.com.sdq.smilefriends.bean.entity.NewsDetail;
import cn.com.sdq.smilefriends.commn.okhttp.JsonCallback;
import cn.com.sdq.smilefriends.commn.okhttp.WrapUrl;
import cn.com.sdq.smilefriends.util.StringUtils;
import cn.com.sdq.smilefriends.util.WebViewUtils;
import cn.com.sdq.smilefriends.util.utils.StringUtil;
import okhttp3.Call;
import okhttp3.Response;

public class WebActivity extends BaseActivity {


    WebView webView;
    ImageView ivHeaderImg;
    TextView tvHeaderTitle;
    TextView tvImgSource;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        webView= (WebView) findViewById(R.id.webView);
        ivHeaderImg=(ImageView) findViewById(R.id.iv_header_img);
        tvHeaderTitle= (TextView) findViewById(R.id.tv_header_title);
        tvImgSource= (TextView) findViewById(R.id.tv_img_source);
        Intent intent=getIntent();
        String contentId=intent.getStringExtra("contentId");
        if (contentId!=null&&!StringUtil.isNullOrEmpty(contentId)){
            getContent(contentId);
        }
    }

    private void getContent(String contentId){
        String url= WrapUrl.wrapZhiHU("api/4/news/");
        OkGo.get(url+contentId)
                .tag("web")
                .execute(new JsonCallback<NewsDetail>() {
                    @Override
                    public void onSuccess(NewsDetail newsDetail, Call call, Response response) {
                        if (newsDetail!=null){
//                            WebViewUtils.openHtmeDataWithUri(webView,newsDetail.getBody());
                            String htmlData = "<link rel=\"stylesheet\" type=\"text/css\" href=\"zhihu.css\" />" + newsDetail.getBody();
                            webView.loadDataWithBaseURL("file:///android_asset/", htmlData, "text/html", "utf-8", null);
                            tvHeaderTitle.setText(newsDetail.getTitle());
                            tvImgSource.setText(newsDetail.getImage_source());
                            Picasso.with(WebActivity.this).load(newsDetail.getImage()).into(ivHeaderImg);
                        }
                    }
                });
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    protected void onPause() {
        super.onPause();
        OkGo.getInstance().cancelTag("web");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
