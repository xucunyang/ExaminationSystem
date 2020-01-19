package com.oranle.es.module.ui.innovation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.oranle.es.R;
import com.oranle.es.module.base.BaseActivity;

import org.jetbrains.annotations.Nullable;

public class WarmActivity extends BaseActivity {
    @Override
    public int getLayoutId() {
        return R.layout.activity_warm;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void onDivergent(View view) {
        startActivity(new Intent(this, DivergentActivity.class));
    }

    public void onLogic(View view) {
        startActivity(new Intent(this, LogicActivity.class));
    }

    public void onVisual(View view) {

    }

    public void onMathematical(View view) {
        startActivity(new Intent(this, MathematicalActivity.class));
    }
}
