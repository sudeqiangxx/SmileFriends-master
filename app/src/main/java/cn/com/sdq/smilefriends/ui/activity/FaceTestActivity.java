package cn.com.sdq.smilefriends.ui.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.mikephil.charting.data.PieData;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.view.CropImageView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.AbsCallback;
import com.lzy.okgo.callback.StringCallback;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.ShareContent;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.UploadBatchListener;
import cn.com.sdq.smilefriends.R;
import cn.com.sdq.smilefriends.base.BaseActivity;
import cn.com.sdq.smilefriends.bean.FaceJson;
import cn.com.sdq.smilefriends.commn.APIService;
import cn.com.sdq.smilefriends.commn.PicassoImageLoader;
import cn.com.sdq.smilefriends.commn.okhttp.JsonCallback;
import cn.com.sdq.smilefriends.widgte.PieChartView;
import okhttp3.Call;
import okhttp3.Response;

public class FaceTestActivity extends BaseActivity implements View.OnClickListener {

    private static final int IMAGE_PICKER = 0;
    private static final int REQUEST_CODE_SELECT = 1;
    private int index = 0;
    ImageView ivIamge1;

    ImageView ivImage2;
    LinearLayout llAdd;

    TextView tvTest;

    RelativeLayout rlContent;
    private TextView tvTestResult;

    private PieChartView pieChartView;
    private TextView tvCancel;
    private TextView tvShare;
    private String imageUrl1;
    private String imageUrl2;


    private HashMap<String, String> paths;
    private List<PieChartView.PieceDataHolder> mPieDataList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_face_test);
        initView();
    }

    private void initData() {
        for (int i = 0; i < 2; i++) {
            if (i == 0) {
                PieChartView.PieceDataHolder pieData = new PieChartView.PieceDataHolder(56f, 12, "相似度");
                mPieDataList.add(pieData);
            } else if (i == 1) {
                PieChartView.PieceDataHolder pieData = new PieChartView.PieceDataHolder(44f, 34, "不相似");
                mPieDataList.add(pieData);
            }

        }
    }

    private void initView() {
        ivIamge1 = (ImageView) findViewById(R.id.iv_iamge1);
        ivImage2 = (ImageView) findViewById(R.id.iv_image2);
        tvTest = (TextView) findViewById(R.id.tv_test);
        llAdd = (LinearLayout) findViewById(R.id.ll_add);
        rlContent = (RelativeLayout) findViewById(R.id.rl_content);
        pieChartView = (PieChartView) findViewById(R.id.pieChart);
        tvTestResult = (TextView) findViewById(R.id.tv_test_result);
        tvCancel = (TextView) findViewById(R.id.tv_cancel);
        tvShare = (TextView) findViewById(R.id.tv_share);
        ivIamge1.setOnClickListener(this);
        ivImage2.setOnClickListener(this);
        tvTest.setOnClickListener(this);
        llAdd.setOnClickListener(this);
        rlContent.setOnClickListener(this);
        initImagePicker();
        paths = new HashMap<>();

        initData();
        pieChartView.setPieChartCircleRadius(200);
        pieChartView.setData(mPieDataList);
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tvShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                share();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_iamge1:
                index = 0;
                showDialog();
                break;
            case R.id.iv_image2:
                index = 1;
                showDialog();
                break;
            case R.id.ll_add:
                break;
            case R.id.tv_test:
                loadTest();
                break;
            case R.id.rl_content:
                break;
        }
    }

    private ProgressDialog progressDialog;


    private void showProgressDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("测夫妻相");
        progressDialog.setMessage("正在人脸检测中，请稍后......");
        progressDialog.show();
    }

    private void dissProDialog() {
        if (progressDialog != null)
            progressDialog.dismiss();
    }

    private void loadTest() {
        showProgressDialog();
        if (paths.size() == 0) {
            Toast.makeText(this, "请选择图片", Toast.LENGTH_SHORT).show();
            return;
        }
        if (paths.size() == 1) {
            Toast.makeText(this, "亲，两张图片才能检查喔", Toast.LENGTH_SHORT).show();
            return;
        }
        final String path1 = paths.get("0");
        String path2 = paths.get("1");
        String[] pt = new String[]{path1, path2};
        BmobFile.uploadBatch(pt, new UploadBatchListener() {
            @Override
            public void onSuccess(List<BmobFile> list, List<String> list1) {
                Log.i("Face", "上传成功");
                if (list1 != null && list1.size() == paths.size()) {
                    faceAddTest(list1.get(0), list1.get(1));
                    imageUrl1=list1.get(0);
                    imageUrl2=list1.get(1);
                }
            }

            @Override
            public void onProgress(int i, int i1, int i2, int i3) {

            }

            @Override
            public void onError(int i, String s) {
                Toast.makeText(FaceTestActivity.this, "检测失败，请重试", Toast.LENGTH_SHORT).show();
                dissProDialog();
            }
        });

    }

    private void faceAddTest(String url1, String url2) {
        OkGo.post(APIService.FACEADD_TEST)
                .params("api_key", APIService.FACE_APP_KEY)
                .params("api_secret", APIService.FACE_APP_SELECT)
                .params("image_url1", url1)
                .params("image_url2", url2)
                .execute(new JsonCallback<FaceJson>() {
                    @Override
                    public void onSuccess(FaceJson faceJson, Call call, Response response) {
                        Log.i("face++", "检测结果：" + faceJson);
                        if (faceJson == null) {
                            tvTestResult.setText("相似度达到:" + "      " + 0 + "%");
                            return;
                        }
                        if (faceJson.getConfidence() == null) {
                            tvTestResult.setText("相似度达到:" + "      " + 0 + "%");
                            return;
                        }
                        tvTestResult.setText("相似度达到:" + "      " + faceJson.getConfidence() + "%");
                    }

                    @Override
                    public void onAfter(FaceJson faceJson, Exception e) {
                        super.onAfter(faceJson, e);
                        dissProDialog();
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        if (response != null) {
                            Log.i("error----------->", response.body() + "");
                        }
                    }
                });


    }

    int flag = 0;

    private void showDialog() {
        String[] items = new String[]{"相册", "相机"};
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("请选择");
        dialog.setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (i == 0) {

                    flag = 0;
                } else if (i == 1) {
                    flag = 1;

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
                if (flag == 0) {
                    //
                    //打开选择,本次允许选择的数量
                    ImagePicker.getInstance().setSelectLimit(1);
                    Intent intent1 = new Intent(FaceTestActivity.this, ImageGridActivity.class);
                                /* 如果需要进入选择的时候显示已经选中的图片，
                                 * 详情请查看ImagePickerActivity
                                 * */
//                                intent1.putExtra(ImageGridActivity.EXTRAS_IMAGES,images);
                    startActivityForResult(intent1, IMAGE_PICKER);
                } else {
                    ImagePicker.getInstance().setSelectLimit(1);
                    Intent intent = new Intent(FaceTestActivity.this, ImageGridActivity.class);
                    intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS, true); // 是否是直接打开相机
                    startActivityForResult(intent, REQUEST_CODE_SELECT);

                }
            }
        });

        dialog.show();
    }

    private void initImagePicker() {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new PicassoImageLoader());   //设置图片加载器
        imagePicker.setShowCamera(true);                      //显示拍照按钮
        imagePicker.setCrop(true);                           //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(true);                   //是否按矩形区域保存
        imagePicker.setSelectLimit(1);              //选中数量限制
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
        imagePicker.setFocusWidth(800);                       //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(800);                      //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(1000);                         //保存文件的宽度。单位像素
        imagePicker.setOutPutY(1000);                         //保存文件的高度。单位像素
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == IMAGE_PICKER) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                if (images != null && images.size() > 0) {
                    if (index == 0) {
                        Glide.with(FaceTestActivity.this).load(images.get(0).path).into(ivIamge1);
                        paths.put("0", images.get(0).path);
                    } else if (index == 1) {
                        paths.put("1", images.get(0).path);
                        Glide.with(FaceTestActivity.this).load(images.get(0).path).into(ivImage2);

                    }
                }
            } else if (data != null && requestCode == REQUEST_CODE_SELECT) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                if (images != null && images.size() > 0) {
                    if (index == 0) {
                        paths.put("0", images.get(0).path);
                        Glide.with(FaceTestActivity.this).load(images.get(0).path).into(ivIamge1);
                    } else if (index == 1) {
                        paths.put("1", images.get(0).path);
                        Glide.with(FaceTestActivity.this).load(images.get(0).path).into(ivImage2);

                    }
                }
            }
        }
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    private void share() {
//        UMImage umImage = new UMImage(this, imageUrl1);
//        UMImage umImage1 = new UMImage(this, imageUrl2);
//        ShareContent shareContent = new ShareContent();
//        shareContent.mText = "快来看看我们的相似度把！！！！！！！！！";
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

        new ShareAction(FaceTestActivity.this)
                .setDisplayList(SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.QQ, SHARE_MEDIA
                        .QZONE)
//                .withMedia(umImage)
                .withText("快来看看我们的相似度吧")
//                .withMedia(umImage1)
//                .setShareContent(shareContent)
                .setCallback(umShareListener).open();

    }
}