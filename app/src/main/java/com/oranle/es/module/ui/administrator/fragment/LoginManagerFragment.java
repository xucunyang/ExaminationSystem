package com.oranle.es.module.ui.administrator.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.oranle.es.R;
import com.oranle.es.databinding.FragmentLoginManagerBinding;
import com.oranle.es.module.base.BaseFragment;
import com.oranle.es.module.ui.administrator.fragment.model.LoginManagerViewModel;

public class LoginManagerFragment extends BaseFragment<FragmentLoginManagerBinding> {
    @Override
    public int getLayoutId() {
        return R.layout.fragment_login_manager;
    }

    @Override
    public void initView() {

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LoginManagerViewModel loginManagerViewModel = new LoginManagerViewModel(getActivity());
        getDataBinding().setVm(loginManagerViewModel);
        getDataBinding().setLifecycleOwner(getActivity());
    }
}
