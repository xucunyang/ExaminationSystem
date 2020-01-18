package com.oranle.es.module.ui.administrator

import android.os.Bundle
import android.view.View
import com.oranle.es.R
import com.oranle.es.data.entity.Assessment
import com.oranle.es.data.entity.ClassEntity
import com.oranle.es.databinding.ActivityAdminBinding
import com.oranle.es.module.base.BaseActivity
import com.oranle.es.module.base.BaseFragment
import com.oranle.es.module.ui.administrator.fragment.*
import com.oranle.es.module.ui.administrator.fragment.ReportFragment
import timber.log.Timber

class AdministratorActivity : BaseActivity<ActivityAdminBinding>() {

    var fragList = mutableListOf<BaseFragment<*>>()

    override val layoutId: Int
        get() = R.layout.activity_admin

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.d("xxx %s", intent.extras!!["user"])
        initView()
    }

    private fun initView() {
        fragList.add(LoginManagerFragment())
        fragList.add(ReportFragment.newInstance(isShowAll = true))
        fragList.add(ManualInputFragment())
        fragList.add(GroupStatisticFragment())
        fragList.add(PersonalStatisticFragment())
        fragList.add(ExportFragment())
        fragList.add(AdminPwdFragment())
        supportFragmentManager.beginTransaction()
            .replace(R.id.frameLayout, fragList.get(0), "0").commit()
    }

    fun onLoginManager(view: View?) {
        initViewpager(0)
    }

    fun onReport(view: View?) {
        initViewpager(1)
    }

    fun onEntry(view: View?) {
        initViewpager(2)
    }

    fun onGroup(view: View?) {
        initViewpager(3)
    }

    fun onPersonStatistic(view: View?) {
        initViewpager(4)
    }

    fun onExport(view: View?) {
        initViewpager(5)
    }

    fun showGroupStatistic(assessment: Assessment?, clazz: ClassEntity?) {
        if (fragList.size > 7) {
            fragList.removeAt(7)
        }
        val fragment =
            ReportFragment.newInstance(
                assessment = assessment,
                clazz = clazz
            )
        fragList.add(7, fragment)
        initViewpager(7)
    }

    private fun initViewpager(tag: Int) {
        try {
            val ft =
                supportFragmentManager!!.beginTransaction()
            ft.replace(R.id.frameLayout, fragList!![tag], tag.toString() + "")
            ft.commit()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun onAdminPwd(view: View?) {
        initViewpager(6)
    }
}