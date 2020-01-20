package com.oranle.es.module.ui.innovation;

import android.os.Bundle;

import com.oranle.es.R;
import com.oranle.es.databinding.ActivityLogicBinding;
import com.oranle.es.module.base.BaseActivity;

import org.jetbrains.annotations.Nullable;

public class LogicActivity extends BaseActivity<ActivityLogicBinding> {

    private LogicViewModel logicViewModel;

    @Override
    public int getLayoutId() {
        return R.layout.activity_logic;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        logicViewModel = new LogicViewModel(this);
        getDataBinding().setViewModel(logicViewModel);
        getDataBinding().setLifecycleOwner(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        logicViewModel = null;
    }
}
