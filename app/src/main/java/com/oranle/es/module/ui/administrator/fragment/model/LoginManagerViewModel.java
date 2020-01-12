package com.oranle.es.module.ui.administrator.fragment.model;

import android.app.Activity;
import android.content.Intent;

import com.oranle.es.module.base.BaseRecycleViewModel;
import com.oranle.es.module.ui.administrator.AddPersonalActivity;

public class LoginManagerViewModel extends BaseRecycleViewModel {
    private Activity activity;

    public LoginManagerViewModel(Activity activity) {
        this.activity = activity;
    }

    public void onAddPersonal(){
        activity.startActivity(new Intent(activity, AddPersonalActivity.class));
    }
}
