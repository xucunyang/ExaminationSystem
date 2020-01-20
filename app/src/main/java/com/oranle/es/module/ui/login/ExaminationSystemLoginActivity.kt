package com.oranle.es.module.ui.login

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import com.oranle.es.BuildConfig
import com.oranle.es.R
import com.oranle.es.data.entity.Role
import com.oranle.es.data.repository.DBRepository
import com.oranle.es.data.sp.SpUtil
import com.oranle.es.databinding.ActivityExamLoginBinding
import com.oranle.es.module.base.*
import com.oranle.es.module.ui.administrator.AdministratorActivity
import com.oranle.es.module.ui.examinee.ExamineeActivity
import com.oranle.es.module.ui.senior.SeniorAdminActivity
import kotlinx.android.synthetic.main.activity_exam_login.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.withContext
import timber.log.Timber

class ExaminationSystemLoginActivity : BaseActivity<ActivityExamLoginBinding>() {

    var currentRole = Role.Examinee.value

    override val layoutId: Int
        get() = R.layout.activity_exam_login

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        if (BuildConfig.DEBUG) {
//            user_name_ed.setText("XCY")
//            psw_et.setText("1")
//            spinner.setSelection(0)
//        }

        dataBinding.spinner.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val role = resources.getStringArray(R.array.role_type)[position]
                Timber.d("xxxx $position, $parent, $id $role")

                currentRole = position
            }

        }
    }

    fun onLoginClick(view: View) {

        val userName = user_name_ed.editableText.toString()
        val psw = psw_et.editableText.toString()

        if (userName.isEmpty()) {
            toast("用户名为空")
            return
        }

        if (psw.isEmpty()) {
            toast("密码为空")
            return
        }

        GlobalScope.launchWithLifecycle(this, UI) {
            val user = withContext(IO) {
                DBRepository.getDB().getUserDao().getUserByAuth(userName, psw, currentRole)
            }

            Timber.d("user $user")
            if (user != null) {
                toast("登录成功")
                val bundle = Bundle()
                bundle.putSerializable("user", user)

                SpUtil.instance.setCurrentUser(user)

                when (currentRole) {
                    Role.Examinee.value -> start<ExamineeActivity>(bundle)
                    Role.Manager.value -> start<AdministratorActivity>(bundle)
                    Role.Root.value -> start<SeniorAdminActivity>(bundle)
                }

                this@ExaminationSystemLoginActivity.finish()

            } else {
                toast("用户名或密码错误")
            }
        }

    }

}