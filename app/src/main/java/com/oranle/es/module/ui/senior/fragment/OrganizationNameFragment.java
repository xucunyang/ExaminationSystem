package com.oranle.es.module.ui.senior.fragment;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.oranle.es.R;
import com.oranle.es.data.sp.SpUtil;
import com.oranle.es.databinding.FragmentUnitNameBinding;
import com.oranle.es.module.base.BaseFragment;
import com.oranle.es.module.ui.senior.SeniorAdminActivity;

public class OrganizationNameFragment extends BaseFragment<FragmentUnitNameBinding> {

    @Override
    public int getLayoutId() {
        return R.layout.fragment_unit_name;
    }


    @Override
    public void initView() {
        final SpUtil spUtil = SpUtil.Companion.getInstance();
        String organizationName = spUtil.getOrganizationName();

        EditText nameEt = getDataBinding().organizationNameEt;
        Button confirm = getDataBinding().confirm;
        if (!TextUtils.isEmpty(organizationName)) {
            nameEt.setText(organizationName);
            nameEt.setEnabled(false);
            confirm.setVisibility(View.GONE);
        } else {
            nameEt.setHint("请设置单位名称，设置后不可更改");
            nameEt.clearFocus();
            confirm.setVisibility(View.VISIBLE);
        }
        confirm.setOnClickListener(v -> {
            String name = nameEt.getEditableText().toString();
            String tip;
            if (!TextUtils.isEmpty(name)) {
                spUtil.setOrganizationName(name);
                nameEt.setEnabled(false);
                confirm.setVisibility(View.GONE);
                tip = "设置成功";
            } else {
                tip = "单位名称为空";
            }
            Toast.makeText(getActivity(), tip, Toast.LENGTH_SHORT).show();
        });


        getDataBinding().addClass.setOnClickListener(
                (View view) -> {
                    ((SeniorAdminActivity)getActivity()).showAddClassFrag();
                }

        );
    }

}
