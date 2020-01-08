package com.oranle.es.module.ui.senior.fragment

import com.oranle.es.R
import com.oranle.es.data.repository.DBRepository
import com.oranle.es.data.sp.SpUtil
import com.oranle.es.databinding.FragmentPwdBinding
import com.oranle.es.module.base.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.withContext

class ModifyPwdFragment : BaseFragment<FragmentPwdBinding>() {

    override val layoutId: Int
        get() = R.layout.fragment_pwd

    override fun initView() {
        val user = SpUtil.instance.getCurrentUser()

        if (user == null) {
            toast("获取信息出错")
            return
        }

        dataBinding?.apply {
            userName.setText(user.userName)
            psw.setText(user.psw)
            pswConfirm.setText(user.psw)

            confirmChange.setOnClickListener {

                val userNameStr = userName.text.toString()
                val pswStr = psw.text.toString()
                val pswConfirmStr = pswConfirm.text.toString()

                if (userNameStr.isEmpty()) {
                    toast("用户名不能为空")
                    return@setOnClickListener
                }

                if (pswStr.isEmpty()) {
                    toast("密码不能为空")
                    return@setOnClickListener
                }

                if (pswConfirmStr.isEmpty()) {
                    toast("请确认密码")
                    return@setOnClickListener
                }

                if (pswConfirmStr != pswStr) {
                    toast("密码不一致")
                    return@setOnClickListener
                }

                GlobalScope.launchWithLifecycle(this@ModifyPwdFragment.viewLifecycleOwner, UI) {
                    val size = withContext(IO) {
                        val copy = user.copy(
                            userName = userNameStr,
                            psw = pswStr
                        )
                        DBRepository.getDB().getUserDao().updateUser(copy)
                    }

                    if (size > 0) {
                        toast("修改成功")
                    } else
                        toast("修改失败")
                }

            }
        }
    }
}
