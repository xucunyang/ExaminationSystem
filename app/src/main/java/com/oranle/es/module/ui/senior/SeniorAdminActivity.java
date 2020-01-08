package com.oranle.es.module.ui.senior;

import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.oranle.es.R;
import com.oranle.es.data.entity.Assessment;
import com.oranle.es.data.entity.ClassEntity;
import com.oranle.es.databinding.ActivitySeniorAdminBinding;
import com.oranle.es.module.base.BaseActivity;
import com.oranle.es.module.ui.senior.fragment.AddClassFragment;
import com.oranle.es.module.ui.senior.fragment.AddClassFragmentKt;
import com.oranle.es.module.ui.senior.fragment.AdministratorFragment;
import com.oranle.es.module.ui.senior.fragment.ModifyPwdFragment;
import com.oranle.es.module.ui.senior.fragment.OrganizationNameFragment;
import com.oranle.es.module.ui.senior.fragment.SheetSetFragment;
import com.oranle.es.module.ui.senior.fragment.TableFragment;

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
        fragList.add(new OrganizationNameFragment());
        fragList.add(new AdministratorFragment());
        fragList.add(new TableFragment());
        fragList.add(new ModifyPwdFragment());
        fragList.add(new AddClassFragment());
        fragList.add(new SheetSetFragment());

        supportFragmentManager = getSupportFragmentManager();
        supportFragmentManager.beginTransaction().replace(R.id.frameLayout, fragList.get(0), "0").commit();
    }

    public void onUnit(View view) {
        initViewpager(0);
    }

    public void onAdmin(View view) {
        fragList.remove(1);
        AdministratorFragment fragment = new AdministratorFragment();
        fragList.add(1, fragment);

        initViewpager(1);
    }

    public void onTable(View view) {
        initViewpager(2);
    }

    public void onPwd(View view) {
        initViewpager(3);
    }

    public void showAddClassFrag() {
        showChangeClass(null);
    }

    public void showChangeClass(ClassEntity entity) {

        AddClassFragment frag = (AddClassFragment) fragList.get(4);
        fragList.remove(frag);

        AddClassFragment newFrag = new AddClassFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(AddClassFragmentKt.CLASS_ENTITY, entity);
        newFrag.setArguments(bundle);

        fragList.add(4, newFrag);

        initViewpager(4);
    }

    public void onExit(View view) {
        finish();
    }

    public void showChangeSheet(Assessment assessment) {
        fragList.remove(5);
        SheetSetFragment fragment = new SheetSetFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("assessment", assessment);
        fragment.setArguments(bundle);
        fragList.add(5, fragment);
        initViewpager(5);
    }

    public Fragment getFragment(int index) {
        return fragList.get(index);
    }

    private void initViewpager(int tag) {
        try {
            FragmentTransaction ft = supportFragmentManager.beginTransaction();

            ft.replace(R.id.frameLayout, fragList.get(tag), tag + "");
            ft.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
