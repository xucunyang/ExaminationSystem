package com.oranle.es.module.ui.innovation;

import android.os.Bundle;

import com.oranle.es.R;
import com.oranle.es.databinding.ActivityStrainBinding;
import com.oranle.es.module.base.BaseActivity;

import org.jetbrains.annotations.Nullable;

public class StrainActivity extends BaseActivity<ActivityStrainBinding> {

    private StrainViewModel strainViewModel;

    @Override
    public int getLayoutId() {
        return R.layout.activity_strain;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        strainViewModel = new StrainViewModel(this);
        getDataBinding().setViewModel(strainViewModel);
        getDataBinding().setLifecycleOwner(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        strainViewModel = null;
    }
}
