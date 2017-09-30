package cn.com.sdq.smilefriends.ui.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;
import android.widget.TextView;
import com.tencent.connect.dataprovider.Constants;

import java.util.List;

import cn.com.sdq.smilefriends.R;
import cn.com.sdq.smilefriends.base.BaseActivity;
import cn.com.sdq.smilefriends.commn.AppConfig;
import cn.com.sdq.smilefriends.interf.PermissionListener;
import cn.com.sdq.smilefriends.manager.AppManager;

/**
 * 应用启动界面
 *
 * @author sdq
 * @created 2017.2.10
 */
public class AppStart extends BaseActivity{
    //private SplashAD splashAD;
    private ViewGroup container;
    private TextView skipView;
    private ImageView splashHolder;
    private static final String SKIP_TEXT = "点击跳过 %d";

    public boolean canJump = false;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 防止第三方跳转时出现双实例，安全性考虑
        setStatusBar(true);
        Activity aty = AppManager.getActivity(MainActivity.class);
        if (aty != null && !aty.isFinishing()) {
            finish();
        }
        // SystemTool.gc(this); //针对性能好的手机使用，加快应用相应速度

        final View view = View.inflate(this, R.layout.app_startlayout, null);
        setContentView(view);
        // 渐变展示启动屏
        AlphaAnimation aa = new AlphaAnimation(0.5f, 1.0f);
        aa.setDuration(3000);
        view.startAnimation(aa);
        aa.setAnimationListener(new AnimationListener() {
            @Override
            public void onAnimationEnd(Animation arg0) {
                redirectTo();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationStart(Animation animation) {
            }
        });

        container = (ViewGroup) this.findViewById(R.id.splash_container);
        skipView = (TextView) findViewById(R.id.skip_view);
        splashHolder = (ImageView) findViewById(R.id.splash_holder);

        // 如果targetSDKVersion >= 23，就要申请好权限。如果您的App没有适配到Android6.0（即targetSDKVersion < 23），那么只需要在这里直接调用fetchSplashAD接口。
        if (Build.VERSION.SDK_INT >= 23) {
//                checkAndRequestPermission();
//            requestRuntimePermission(new String[]{Manifest.permission.READ_PHONE_STATE
//                    , Manifest.permission.WRITE_EXTERNAL_STORAGE
//                    , Manifest.permission.ACCESS_FINE_LOCATION}, new PermissionListener() {
//                @Override
//                public void geanted() {
////                    fetchSplashAD(AppStart.this, container, skipView, AppConfig.APP_ID, Constants.APPID, AppStart.this, 0);
//                }
//
//                @Override
//                public void denied(List<String> deniedPermission) {
//
//                }
//            });
        } else {
            // 如果是Android6.0以下的机器，默认在安装时获得了所有权限，可以直接调用SDK
//            fetchSplashAD(this, container, skipView, AppConfig.APP_ID, Constants.APPID, this, 0);
        }

    }
    public void setStatusBar(boolean navi){
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
            Window window=getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            if (navi){
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN
                        |View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        |View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                );

                window.setStatusBarColor(Color.TRANSPARENT);
            }else {
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        |View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                );

            }
        }else if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT){
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
    }

    @Override
    protected void onResume() {
        super.onResume();
//        if (canJump) {
//            next();
//        }
//        canJump = true;
    }

    private void cleanImageCache() {

    }

    /**
     * 跳转到...
     */
    private void redirectTo() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

//    /**
//     * 拉取开屏广告，开屏广告的构造方法有3种，详细说明请参考开发者文档。
//     *
//     * @param activity        展示广告的activity
//     * @param adContainer     展示广告的大容器
//     * @param skipContainer   自定义的跳过按钮：传入该view给SDK后，SDK会自动给它绑定点击跳过事件。SkipView的样式可以由开发者自由定制，其尺寸限制请参考activity_splash.xml或者接入文档中的说明。
//     * @param appId           应用ID
//     * @param posId           广告位ID
//     * @param adListener      广告状态监听器
//     * @param fetchDelay      拉取广告的超时时长：取值范围[3000, 5000]，设为0表示使用广点通SDK默认的超时时长。
//     */
//    private void fetchSplashAD(Activity activity, ViewGroup adContainer, View skipContainer,
//                               String appId, String posId, SplashADListener adListener, int fetchDelay) {
//        splashAD = new SplashAD(activity, adContainer, skipContainer, appId, posId, adListener, fetchDelay);
//    }
//
//    @Override
//    public void onADPresent() {
//        Log.i("AD_DEMO", "SplashADPresent");
//        splashHolder.setVisibility(View.INVISIBLE); // 广告展示后一定要把预设的开屏图片隐藏起来
//    }
//
//    @Override
//    public void onADClicked() {
//        Log.i("AD_DEMO", "SplashADClicked");
//    }
//
//    /**
//     * 倒计时回调，返回广告还将被展示的剩余时间。
//     * 通过这个接口，开发者可以自行决定是否显示倒计时提示，或者还剩几秒的时候显示倒计时
//     *
//     * @param millisUntilFinished 剩余毫秒数
//     */
//    @Override
//    public void onADTick(long millisUntilFinished) {
//        Log.i("AD_DEMO", "SplashADTick " + millisUntilFinished + "ms");
//        skipView.setText(String.format(SKIP_TEXT, Math.round(millisUntilFinished / 1000f)));
//    }
//
//    @Override
//    public void onADDismissed() {
//        Log.i("AD_DEMO", "SplashADDismissed");
//        next();
//    }
//
//    @Override
//    public void onNoAD(AdError error) {
//        Log.i(
//                "AD_DEMO",
//                String.format("LoadSplashADFail, eCode=%d, errorMsg=%s", error.getErrorCode(),
//                        error.getErrorMsg()));
//        /** 如果加载广告失败，则直接跳转 */
//        this.startActivity(new Intent(this, MainActivity.class));
//        this.finish();
//    }
//
//    /**
//     * 设置一个变量来控制当前开屏页面是否可以跳转，当开屏广告为普链类广告时，点击会打开一个广告落地页，此时开发者还不能打开自己的App主页。当从广告落地页返回以后，
//     * 才可以跳转到开发者自己的App主页；当开屏广告是App类广告时只会下载App。
//     */
//    private void next() {
//        if (canJump) {
//            this.startActivity(new Intent(this, MainActivity.class));
//            this.finish();
//        } else {
//            canJump = true;
//        }
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        canJump = false;
//    }
//
//
//
//    /** 开屏页一定要禁止用户对返回按钮的控制，否则将可能导致用户手动退出了App而广告无法正常曝光和计费 */
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK || keyCode == KeyEvent.KEYCODE_HOME) {
//            return true;
//        }
//        return super.onKeyDown(keyCode, event);
//    }

    @Override
    public void onClick(View view) {

    }
}
