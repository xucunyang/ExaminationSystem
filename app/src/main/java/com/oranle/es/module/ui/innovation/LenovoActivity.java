package com.oranle.es.module.ui.innovation;

import android.os.Bundle;

import com.oranle.es.R;
import com.oranle.es.databinding.ActivityLenovoBinding;
import com.oranle.es.module.base.BaseActivity;

import org.jetbrains.annotations.Nullable;

public class LenovoActivity extends BaseActivity<ActivityLenovoBinding> {

    private LenovoViewModel lenovoViewModel;

    @Override
    public int getLayoutId() {
        return R.layout.activity_lenovo;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        lenovoViewModel = new LenovoViewModel(this);
        getDataBinding().setViewModel(lenovoViewModel);
        getDataBinding().setLifecycleOwner(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        lenovoViewModel = null;
    }
}
