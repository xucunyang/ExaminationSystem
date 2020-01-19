package com.oranle.es.module.ui.innovation;

import android.os.Bundle;

import com.oranle.es.R;
import com.oranle.es.databinding.ActivityMathematicalBinding;
import com.oranle.es.module.base.BaseActivity;

import org.jetbrains.annotations.Nullable;

public class MathematicalActivity extends BaseActivity<ActivityMathematicalBinding> {

    private MathematicalViewModel mathematicalViewModel;

    @Override
    public int getLayoutId() {
        return R.layout.activity_mathematical;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        mathematicalViewModel = new MathematicalViewModel(this);
        getDataBinding().setViewModel(mathematicalViewModel);
        getDataBinding().setLifecycleOwner(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mathematicalViewModel = null;
    }
}
