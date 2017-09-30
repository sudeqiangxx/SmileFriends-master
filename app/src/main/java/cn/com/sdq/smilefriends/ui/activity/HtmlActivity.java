package cn.com.sdq.smilefriends.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.com.sdq.smilefriends.R;
import cn.com.sdq.smilefriends.base.BaseActivity;
import cn.com.sdq.smilefriends.ui.fragment.NewsFragment;
import cn.com.sdq.smilefriends.util.StringUtils;
import cn.com.sdq.smilefriends.util.WebViewUtils;
import cn.com.sdq.smilefriends.util.utils.StringUtil;

public class HtmlActivity extends BaseActivity {


    WebView wvHtml;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_html);
        wvHtml= (WebView) findViewById(R.id.wv_html);
    }

    @Override
    protected void onStart() {
        super.onStart();
        String url=getIntent().getStringExtra(NewsFragment.HTML_URL);
        if (cn.com.sdq.smilefriends.commn.okhttp.StringUtils.isNotEmpty(url)){
            WebViewUtils.openUrl(wvHtml,url);
        }else {
            Toast.makeText(this,"解析错误，请返回",Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onClick(View view) {

    }
}
