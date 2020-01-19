package com.oranle.es.module.ui.innovation;

import android.os.Bundle;

import com.oranle.es.R;
import com.oranle.es.databinding.ActivityImageBinding;
import com.oranle.es.module.base.BaseActivity;

import org.jetbrains.annotations.Nullable;

public class ImageActivity extends BaseActivity<ActivityImageBinding> {

    private ImageViewModel imageViewModel;

    @Override
    public int getLayoutId() {
        return R.layout.activity_image;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        imageViewModel = new ImageViewModel(this);
        getDataBinding().setViewModel(imageViewModel);
        getDataBinding().setLifecycleOwner(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        imageViewModel = null;
    }
}
