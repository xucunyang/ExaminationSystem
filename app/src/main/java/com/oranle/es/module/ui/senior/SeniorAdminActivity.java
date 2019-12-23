package com.oranle.es.module.ui.senior;

import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.oranle.es.R;
import com.oranle.es.databinding.ActivitySeniorAdminBinding;
import com.oranle.es.module.base.BaseActivity;
import com.oranle.es.module.ui.senior.fragment.AdministratorFragment;
import com.oranle.es.module.ui.senior.fragment.ModifyPwdFragment;
import com.oranle.es.module.ui.senior.fragment.TableFragment;
import com.oranle.es.module.ui.senior.fragment.UnitNameFragment;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class SeniorAdminActivity extends BaseActivity<ActivitySeniorAdminBinding> {

    private FragmentManager supportFragmentManager;
    private List<Fragment> fragList;

    @Override
    public int getLayoutId() {
        return R.layout.activity_senior_admin;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        fragList = new ArrayList<>();
        fragList.add(new UnitNameFragment());
        fragList.add(new AdministratorFragment());
        fragList.add(new TableFragment());
        fragList.add(new ModifyPwdFragment());

        supportFragmentManager = getSupportFragmentManager();
        supportFragmentManager.beginTransaction().replace(R.id.frameLayout, fragList.get(0), "0").commit();
    }

    public void onUnit(View view) {
        initViewpager(0);
    }

    public void onAdmin(View view) {
        initViewpager(1);
    }

    public void onTable(View view) {
        initViewpager(2);
    }

    public void onPwd(View view) {
        initViewpager(3);
    }

    private void initViewpager(int tag) {
        try {
            Fragment frag = supportFragmentManager.findFragmentByTag("" + tag);
            if (frag == null) {
                supportFragmentManager.beginTransaction().add(R.id.frameLayout, fragList.get(tag), tag + "").commit();
            }
            FragmentTransaction ft = supportFragmentManager.beginTransaction();
            for (int i = 0; i < fragList.size(); i++) {
                if (i == tag) {
                    ft.show(fragList.get(i));

                } else {
                    ft.hide(fragList.get(i));
                }
            }
            ft.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
