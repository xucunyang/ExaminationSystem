package com.oranle.es.module.ui.innovation;

import android.os.Bundle;

import com.oranle.es.R;
import com.oranle.es.databinding.ActivityDivergentThinkBinding;
import com.oranle.es.module.base.BaseActivity;

import org.jetbrains.annotations.Nullable;

public class DivergentThinkActivity extends BaseActivity<ActivityDivergentThinkBinding> {

    private DivergentThinkViewModel divergentThinkViewModel;

    @Override
    public int getLayoutId() {
        return R.layout.activity_divergent_think;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        divergentThinkViewModel = new DivergentThinkViewModel(this);
        getDataBinding().setViewModel(divergentThinkViewModel);
        getDataBinding().setLifecycleOwner(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        divergentThinkViewModel = null;
    }
}
