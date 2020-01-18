package com.oranle.es.module.ui.innovation;

import android.os.Bundle;

import com.oranle.es.R;
import com.oranle.es.databinding.ActivityDialecticalBinding;
import com.oranle.es.module.base.BaseActivity;

import org.jetbrains.annotations.Nullable;

public class DialecticalActivity extends BaseActivity<ActivityDialecticalBinding> {

    private DialecticalViewModel dialecticalViewModel;

    @Override
    public int getLayoutId() {
        return R.layout.activity_dialectical;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        dialecticalViewModel = new DialecticalViewModel(this);
        getDataBinding().setViewModel(dialecticalViewModel);
        getDataBinding().setLifecycleOwner(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dialecticalViewModel = null;
    }
}
