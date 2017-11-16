package cn.com.sdq.smilefriends.ar;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;

import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.sdq.smilefriends.R;
import cn.com.sdq.smilefriends.ar.arvideo.ArVideoActivity;
import cn.com.sdq.smilefriends.contact.Jake;
import cn.com.sdq.smilefriends.ui.activity.FaceTestActivity;
import cn.com.sdq.smilefriends.ui.adapter.ArImageAdapter;
import cn.com.sdq.smilefriends.ui.fragment.ConsultFragment;

public class ARActivity extends AppCompatActivity {

    @Bind(R.id.tv_hint)
    TextView tvHint;
    @Bind(R.id.rlv_image_list)
    RecyclerView rlvImageList;
    @Bind(R.id.tv_tiyan)
    TextView tvTiyan;

    private List<Integer> images;
    private ArImageAdapter arImageAdapter;
    private int arValue=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ar);
        ButterKnife.bind(this);
        initData();
        arValue=getIntent().getIntExtra(ConsultFragment.AR_KEY,0);
    }

    @OnClick(R.id.tv_tiyan)
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_tiyan:
                if (arValue==1){
                Intent intents=new Intent();
                intents.setClass(ARActivity.this, ArVideoActivity.class);
                startActivity(intents);
                }else {
                    Intent intent = new Intent();
                    intent.setClass(ARActivity.this, ArHelloActivity.class);
                    startActivity(intent);
                }
                break;
            default:break;
        }
    }

    private void initData(){
        images=new ArrayList<>();
        images.add(R.drawable.argame00);
        images.add(R.drawable.argame01);
        images.add(R.drawable.argame02);
        images.add(R.drawable.argame03);
        images.add(R.drawable.idback);
        images.add(R.drawable.namecard);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initRlv();
    }

    private void initRlv(){
        arImageAdapter=new ArImageAdapter(this,images);
        rlvImageList.setLayoutManager(new GridLayoutManager(ARActivity.this,3));
        rlvImageList.setAdapter(arImageAdapter);
        arImageAdapter.setOnItemLongClickListener(new ArImageAdapter.OnItemLongClickListener() {
            @Override
            public void onLongItemClick(int position) {
                //长按点击
                share(images.get(position));
            }
        });


    }



    private void share(int resId) {
        UMImage umImage=new UMImage(this,resId);
        UMShareListener umShareListener = new UMShareListener() {
            @Override
            public void onStart(SHARE_MEDIA share_media) {

            }

            @Override
            public void onResult(SHARE_MEDIA share_media) {


            }

            @Override
            public void onError(SHARE_MEDIA share_media, Throwable throwable) {


            }

            @Override
            public void onCancel(SHARE_MEDIA share_media) {

            }
        };
        new ShareAction(ARActivity.this)
                .setDisplayList(SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.QQ, SHARE_MEDIA
                        .QZONE)
                .withMedia(umImage)
                .setCallback(umShareListener).open();

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }
}
