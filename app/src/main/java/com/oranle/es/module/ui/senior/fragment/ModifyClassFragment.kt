package com.oranle.es.module.ui.senior.fragment

import com.oranle.es.R
import com.oranle.es.data.entity.ClassEntity
import com.oranle.es.data.repository.DBRepository
import com.oranle.es.databinding.FragmentClassAddBinding
import com.oranle.es.module.base.BaseFragment
import com.oranle.es.module.base.UI
import com.oranle.es.module.base.launchWithLifecycle
import com.oranle.es.module.base.toast
import com.oranle.es.module.ui.senior.SeniorAdminActivity
import kotlinx.coroutines.GlobalScope
import timber.log.Timber

class ModifyClassFragment : BaseFragment<FragmentClassAddBinding>() {

    override val layoutId: Int
        get() = R.layout.fragment_class_add

    override fun initView() {

        val get = arguments?.getSerializable(CLASS_ENTITY)
        val isClassEntity = get is ClassEntity

        if (!isClassEntity) {
            toast("班级信息错误")
            return
        }

        dataBinding?.apply {

            var isPermitRegister = true
            radioGroup.setOnCheckedChangeListener { _, i -> isPermitRegister = (i == 0) }

            addClass.text = "修改"

            addClass.setOnClickListener { v ->
                val className = classNameEt.editableText.toString()
                if (className.isEmpty()) {
                    toast("请输入班级名称")
                    return@setOnClickListener
                }

                GlobalScope.launchWithLifecycle(lifecycleOwner = viewLifecycleOwner, context = UI) {
                    val classDao = DBRepository.getDB().getClassDao()
                    val isEmpty = classDao.getClassByName(className).isEmpty()
                    if (isEmpty) {
                        classDao.addClass(
                            ClassEntity(
                                className = className,
                                isRegister = isPermitRegister
                            )
                        )
                        toast("班级已录入")
                    } else {
                        toast("班级名称已存在")
                    }
                }

            }

            back.setOnClickListener {
                (activity as SeniorAdminActivity).onUnit(it)
            }

        }
    }

    override fun onResume() {
        super.onResume()

        Timber.d("onResume")
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)

        Timber.d("onHiddenChanged $hidden")

        if (!hidden) {
            initView()
        }
    }

}
