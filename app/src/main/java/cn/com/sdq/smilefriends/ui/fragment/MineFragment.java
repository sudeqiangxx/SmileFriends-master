package cn.com.sdq.smilefriends.ui.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.sdq.smilefriends.HelloArActivity;
import cn.com.sdq.smilefriends.R;
import cn.com.sdq.smilefriends.ar.ARActivity;
import cn.com.sdq.smilefriends.base.BaseTwoFragment;
import cn.com.sdq.smilefriends.commn.AppConstans;
import cn.com.sdq.smilefriends.ui.activity.DeveloperActivity;
import cn.com.sdq.smilefriends.ui.activity.FaceTestActivity;
import cn.com.sdq.smilefriends.ui.activity.FaceTestOneActivity;
import cn.com.sdq.smilefriends.ui.activity.MyDaShangActivity;
import cn.com.sdq.smilefriends.ui.activity.TaoLunActivity;

/**
 * Created by Administrator on 2017/2/11.
 */

public class MineFragment extends BaseTwoFragment implements IWXAPIEventHandler,AppConstans {
    @Bind(R.id.rl_login)
    RelativeLayout rlLogin;
    @Bind(R.id.rl_my_life)
    RelativeLayout rlMyLife;
    @Bind(R.id.rl_my_dian)
    RelativeLayout rlMyDian;
    @Bind(R.id.rl_my_fabiao)
    RelativeLayout rlMyFabiao;
    @Bind(R.id.rl_fankui)
    RelativeLayout rlFankui;
    public static final String AR_KEY = "arvideo";
    public static final int AR_VALUE = 1;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    protected boolean hasTitleBar() {
        return false;
    }

    @Override
    protected int setContentViewId() {
        return R.layout.mine_fragmentlayout;
    }

    @Override
    protected void initView() {

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp baseResp) {
        if (baseResp != null) {
            Log.i(getTag(), "------->" + baseResp.toString());
        }
    }

    @OnClick({R.id.rl_login, R.id.rl_my_life, R.id.rl_my_dian, R.id.rl_my_fabiao, R.id.rl_fankui,R.id.rl_ar, R.id.rl_ar_video})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_login:
                break;
            case R.id.rl_my_life:
                showToastDialog();
                break;
            case R.id.rl_my_dian:
//                Intent intent=new Intent();
//                intent.setClass(getActivity(), HelloArActivity.class);
//                startActivity(intent);
                Intent intent=new Intent();
                intent.setClass(getActivity(), MyDaShangActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_my_fabiao:
                Intent intent1=new Intent();
                intent1.setClass(getActivity(), DeveloperActivity.class);
                startActivity(intent1);
                break;
            case R.id.rl_fankui:
                Intent intens=new Intent();
                intens.setClass(getActivity(), TaoLunActivity.class);
                startActivity(intens);
                break;
            case R.id.rl_ar:
                Intent intent2 = new Intent();
                intent2.setClass(getActivity(), ARActivity.class);
                startActivity(intent2);

                break;
            case R.id.rl_ar_video:
                Intent intents = new Intent();
                intents.putExtra(AR_KEY, AR_VALUE);
                intents.setClass(getActivity(), ARActivity.class);
                startActivity(intents);
                break;
        }
    }
     int flag=0;
    /**
     * 弹框显示
     */
    private void showToastDialog() {

        String[] items=new String[]{"人脸检测相似度"};
        AlertDialog.Builder dialog=new AlertDialog.Builder(getActivity());
        dialog.setTitle("请选择测试内容");
        dialog.setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (i==0){
                    Log.i(getTag(),"人脸");
                    flag=0;
                }else if (i==1){
                    Log.i(getTag(),"相似度");
                    flag=1;

                }
            }
        });
        dialog.setPositiveButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
            }
        });
        dialog.setNegativeButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                if (flag==0){
                    //
                    Intent intent=new Intent(getActivity(), FaceTestActivity.class);
                    startActivity(intent);
                }else {
                    Intent intent=new Intent(getActivity(), FaceTestOneActivity.class);
                    startActivity(intent);

                }
            }
        });

        dialog.show();


    }
}
