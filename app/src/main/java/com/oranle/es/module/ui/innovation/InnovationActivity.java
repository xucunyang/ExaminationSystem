package com.oranle.es.module.ui.innovation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.oranle.es.R;
import com.oranle.es.databinding.ActivityInnovationBinding;
import com.oranle.es.module.base.BaseActivity;

public class InnovationActivity extends BaseActivity<ActivityInnovationBinding> {

    @Override
    public int getLayoutId() {
        return R.layout.activity_innovation;
    }

    public void onWarm(View view) {
        startActivity(new Intent(this, WarmActivity.class));
    }

    public void onCombinedTrain(View view) {
        startActivity(new Intent(this, CombinedTrainActivity.class));
    }

}
