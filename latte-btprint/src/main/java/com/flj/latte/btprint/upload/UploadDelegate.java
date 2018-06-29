package com.flj.latte.btprint.upload;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.flj.latte.app.Latte;
import com.flj.latte.btprint.R;
import com.flj.latte.btprint.utils.Constants;
import com.flj.latte.delegates.LatteDelegate;
import com.flj.latte.net.RestClient;
import com.flj.latte.ui.image.GlideApp;
import com.flj.latte.util.callback.CallbackManager;
import com.flj.latte.util.callback.CallbackType;
import com.flj.latte.util.callback.IGlobalCallback;
import com.flj.latte.util.log.LatteLogger;
import com.flj.latte.util.storage.LattePreference;

public class UploadDelegate extends LatteDelegate implements View.OnClickListener {
    private AppCompatImageView imageView = null;
    private Uri selectPic = null;
    private boolean selected = false;
    private String userid = null;

    @Override
    public Object setLayout() {
        return R.layout.delegate_upload;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        $(R.id.btn_upload_select).setOnClickListener(this);
        imageView = $(R.id.img_upload);
        userid = LattePreference.getCustomAppProfile("userid");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initImg();
    }

    private void initImg() {
        Latte.getHandler().postDelayed(() -> {
            RestClient.builder()
                    .url(Constants.PICTURE)
                    .params("userid", userid)
                    .success((response) -> {
                        selected = false;
                        ((TextView) $(R.id.btn_upload_select)).setText("选择新图片上传");
                        GlideApp.with(getContext())
                                .load(response)
                                .placeholder(R.drawable.placeholder)
                                .centerInside()
                                .into(imageView);
                    })
                    .build().get();
        }, 0);
    }

    @Override
    public void onClick(View v) {
        if (selected) {
            doUpload();
        } else {
            //开始照相机或选择图片
            CallbackManager.getInstance()
                    .addCallback(CallbackType.ON_CROP, new IGlobalCallback<Uri>() {
                        @Override
                        public void executeCallback(Uri args) {
                            selectPic = args;
                            LatteLogger.d("ON_CROP", args);
                            Glide.with(getContext())
                                    .load(args)
                                    .into(imageView);
                            selected = true;
                            ((TextView) $(R.id.btn_upload_select)).setText("点击上传此图片");
                        }
                    });

            startCameraWithCheck();
        }
    }


    private void doUpload() {
        RestClient.builder()
                .url(Constants.PICTURE)
                .params("userid", userid)
                .loader(getContext())
                .file(selectPic.getPath())
                .success((response1) -> {
                    if ("1".equals(response1)) {
                        selected = false;
                        ((TextView) $(R.id.btn_upload_select)).setText("选择新图片上传");

                        Toast.makeText(_mActivity, "图片上传成功", Toast.LENGTH_SHORT).show();
                    }
                })
                .error(((code, msg) -> {
                    Toast.makeText(_mActivity, code + " " + msg, Toast.LENGTH_SHORT).show();
                }))
                .build()
                .upload();
    }
}
