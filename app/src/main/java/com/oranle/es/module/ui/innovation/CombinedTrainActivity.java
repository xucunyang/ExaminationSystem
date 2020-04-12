package com.oranle.es.module.ui.innovation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.oranle.es.R;
import com.oranle.es.databinding.ActivityCombinedTrainBinding;
import com.oranle.es.module.base.BaseActivity;

import org.jetbrains.annotations.Nullable;

public class CombinedTrainActivity extends BaseActivity<ActivityCombinedTrainBinding> {
    @Override
    public int getLayoutId() {
        return R.layout.activity_combined_train;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void onLanguage(View view) {
        startActivity(new Intent(this, TopicActivity.class));
    }

    public void onDigital(View view) {
        startActivity(new Intent(this, DigitalActivity.class));
    }

    public void onAnalogy(View view) {
        startActivity(new Intent(this, DigitalActivity.class));
    }

    public void onDialectical(View view) {
        startActivity(new Intent(this, DigitalActivity.class));
    }

    public void onStrain(View view) {
        startActivity(new Intent(this, StrainActivity.class));
    }

    public void onReverse(View view) {
        startActivity(new Intent(this, ReverseActivity.class));
    }

    public void onLogicThink(View view) {
        startActivity(new Intent(this, LogicThinkActivity.class));
    }

    public void onLenovo(View view) {
        startActivity(new Intent(this, LenovoActivity.class));
    }

    public void onDivergentThink(View view) {
        startActivity(new Intent(this, DivergentThinkActivity.class));
    }

    public void onImage(View view) {
        startActivity(new Intent(this, ImageActivity.class));
    }
}
