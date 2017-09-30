package cn.com.sdq.smilefriends.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.bumptech.glide.Glide;

import cn.com.sdq.smilefriends.R;
import cn.com.sdq.smilefriends.base.BaseActivity;
import pl.droidsonroids.gif.GifImageView;

public class GifActivity extends BaseActivity {
    private GifImageView gifImageView;
    String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gif);
        gifImageView= (GifImageView) findViewById(R.id.gif_iv);
    }

    @Override
    protected void onStart() {
        super.onStart();
        url=getIntent().getStringExtra("imageurl");
        Glide
                .with(this)
                .load(url)
                .placeholder(R.drawable.qraved_bg_default)
                .into(gifImageView);
    }


    @Override
    public void onClick(View view) {

    }
}
