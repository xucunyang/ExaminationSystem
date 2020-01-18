package com.oranle.es.module.ui.innovation;

import android.os.Bundle;

import com.oranle.es.R;
import com.oranle.es.databinding.ActivityAnalogyBinding;
import com.oranle.es.module.base.BaseActivity;

import org.jetbrains.annotations.Nullable;

public class AnalogyActivity extends BaseActivity<ActivityAnalogyBinding> {

    private AnalogyViewModel analogyViewModel;

    @Override
    public int getLayoutId() {
        return R.layout.activity_analogy;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        analogyViewModel = new AnalogyViewModel(this);
        getDataBinding().setViewModel(analogyViewModel);
        getDataBinding().setLifecycleOwner(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        analogyViewModel = null;
    }
}
