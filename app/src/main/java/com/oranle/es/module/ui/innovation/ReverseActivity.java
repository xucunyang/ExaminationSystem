package com.oranle.es.module.ui.innovation;

import android.os.Bundle;

import com.oranle.es.R;
import com.oranle.es.databinding.ActivityReverseBinding;
import com.oranle.es.module.base.BaseActivity;

import org.jetbrains.annotations.Nullable;

public class ReverseActivity extends BaseActivity<ActivityReverseBinding> {

    private ReverseViewModel reverseViewModel;

    @Override
    public int getLayoutId() {
        return R.layout.activity_reverse;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        reverseViewModel = new ReverseViewModel(this);
        getDataBinding().setViewModel(reverseViewModel);
        getDataBinding().setLifecycleOwner(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        reverseViewModel = null;
    }
}
