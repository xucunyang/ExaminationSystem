package com.oranle.es.module.ui.innovation;

import android.os.Bundle;

import com.oranle.es.R;
import com.oranle.es.databinding.ActivityLogicThinkBinding;
import com.oranle.es.module.base.BaseActivity;

import org.jetbrains.annotations.Nullable;

public class LogicThinkActivity extends BaseActivity<ActivityLogicThinkBinding> {

    private LogicThinkViewModel logicThinkViewModel;

    @Override
    public int getLayoutId() {
        return R.layout.activity_logic_think;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        logicThinkViewModel = new LogicThinkViewModel(this);
        getDataBinding().setViewModel(logicThinkViewModel);
        getDataBinding().setLifecycleOwner(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        logicThinkViewModel = null;
    }
}
