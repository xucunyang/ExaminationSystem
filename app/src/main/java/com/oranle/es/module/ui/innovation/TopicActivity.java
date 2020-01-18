package com.oranle.es.module.ui.innovation;

import android.os.Bundle;

import com.oranle.es.R;
import com.oranle.es.databinding.ActivityTopicBinding;
import com.oranle.es.module.base.BaseActivity;

import org.jetbrains.annotations.Nullable;

public class TopicActivity extends BaseActivity<ActivityTopicBinding> {

    private TopicViewModel topicViewModel;

    @Override
    public int getLayoutId() {
        return R.layout.activity_topic;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        topicViewModel = new TopicViewModel(this);
        getDataBinding().setViewModel(topicViewModel);
        getDataBinding().setLifecycleOwner(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        topicViewModel = null;
    }
}
