package com.oranle.es.module.ui.innovation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.oranle.es.R;
import com.oranle.es.module.base.BaseActivity;

import org.jetbrains.annotations.Nullable;

public class InnovationActivity extends BaseActivity {
    @Override
    public int getLayoutId() {
        return R.layout.activity_innovation;
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
}
