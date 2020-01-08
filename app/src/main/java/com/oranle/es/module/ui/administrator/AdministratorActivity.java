package com.oranle.es.module.ui.administrator;

import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.oranle.es.R;
import com.oranle.es.databinding.ActivityAdminBinding;
import com.oranle.es.module.base.BaseActivity;
import com.oranle.es.module.ui.administrator.fragment.AdminPwdFragment;
import com.oranle.es.module.ui.administrator.fragment.ManualInputFragment;
import com.oranle.es.module.ui.administrator.fragment.ExportFragment;
import com.oranle.es.module.ui.administrator.fragment.GroupFragment;
import com.oranle.es.module.ui.administrator.fragment.LoginManagerFragment;
import com.oranle.es.module.ui.administrator.fragment.PersonalFragment;
import com.oranle.es.module.ui.administrator.fragment.ReportFragment;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

public class AdministratorActivity extends BaseActivity<ActivityAdminBinding> {
    private FragmentManager supportFragmentManager;
    private List<Fragment> fragList;

    @Override
    public int getLayoutId() {
        return R.layout.activity_admin;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Timber.d("xxx %s", getIntent().getExtras().get("user"));

        initView();
    }

    private void initView() {
        fragList = new ArrayList<>();
        fragList.add(new LoginManagerFragment());
        fragList.add(new ReportFragment());
        fragList.add(new ManualInputFragment());
        fragList.add(new GroupFragment());
        fragList.add(new PersonalFragment());
        fragList.add(new ExportFragment());
        fragList.add(new AdminPwdFragment());

        supportFragmentManager = getSupportFragmentManager();
        supportFragmentManager.beginTransaction().replace(R.id.frameLayout, fragList.get(0), "0").commit();
    }

    public void onLoginManager(View view) {
        initViewpager(0);
    }

    public void onReport(View view) {
        initViewpager(1);
    }

    public void onEntry(View view) {
        initViewpager(2);
    }

    public void onGroup(View view) {
        initViewpager(3);
    }

    public void onPerson(View view) {
        initViewpager(4);
    }

    public void onExport(View view) {
        initViewpager(5);
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

    public void onAdminPwd(View view) {
        initViewpager(6);
    }
}
