package com.oranle.es.module.ui.innovation;

import android.os.Bundle;

import com.oranle.es.R;
import com.oranle.es.databinding.ActivityDigitalBinding;
import com.oranle.es.module.base.BaseActivity;

import org.jetbrains.annotations.Nullable;

public class DigitalActivity extends BaseActivity<ActivityDigitalBinding> {

    private DigitalViewModel digitalViewModel;

    @Override
    public int getLayoutId() {
        return R.layout.activity_digital;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        digitalViewModel = new DigitalViewModel(this);
        getDataBinding().setViewModel(digitalViewModel);
        getDataBinding().setLifecycleOwner(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        digitalViewModel = null;
    }
}
