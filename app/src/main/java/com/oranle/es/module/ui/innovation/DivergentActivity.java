package com.oranle.es.module.ui.innovation;

import android.os.Bundle;

import com.oranle.es.R;
import com.oranle.es.databinding.ActivityDivergentBinding;
import com.oranle.es.module.base.BaseActivity;

import org.jetbrains.annotations.Nullable;

public class DivergentActivity extends BaseActivity<ActivityDivergentBinding> {

    private DivergentViewModel divergentViewModel;

    @Override
    public int getLayoutId() {
        return R.layout.activity_divergent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        divergentViewModel = new DivergentViewModel(this);
        getDataBinding().setViewModel(divergentViewModel);
        getDataBinding().setLifecycleOwner(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        divergentViewModel = null;
    }
}
