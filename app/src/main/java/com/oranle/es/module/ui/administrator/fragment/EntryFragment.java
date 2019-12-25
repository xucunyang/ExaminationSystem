package com.oranle.es.module.ui.administrator.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.oranle.es.R;
import com.oranle.es.databinding.FragmentEntryBinding;
import com.oranle.es.module.base.BaseFragment;

public class EntryFragment extends BaseFragment<FragmentEntryBinding> {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_entry;
    }

    @Override
    public void initView() {

    }
}
