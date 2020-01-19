package com.oranle.es.module.ui.senior.viewmodel

import android.view.View
import com.oranle.es.data.entity.ClassEntity
import com.oranle.es.module.base.BaseRecycleViewModel
import com.oranle.es.module.ui.senior.SeniorAdminActivity

class ClassViewModel : BaseRecycleViewModel<ClassEntity>() {

    fun start() {
        asyncCall(
            {
                val allClass = getDB().getClassDao().getAllClass()
                allClass.forEach {
                    it.size = getDB().getUserDao().getUserSizeByClassId(it.id).size
                }

                allClass
            }, {
                notifyItem(it)
            }
        )
    }

    fun onDelete(entity: ClassEntity) {
        asyncCall(
            {
                getDB().getUserDao().clearExamineeByClassId(entity.id)
                getDB().getClassDao().deleteClass(entity)
            }, {
                if (it == 1) {
                    toast("已删除")
                }
                val list = (items.value)?.toMutableList()
                list?.remove(entity)

                notifyItem(list)
            }
        )
    }

    fun onChange(v: View, entity: ClassEntity) {
        val activity = v.context as SeniorAdminActivity
        activity.showChangeClass(entity)
    }

    fun onClearMember(entity: ClassEntity) {
        asyncCall(
            {
                getDB().getUserDao().clearExamineeByClassId(entity.id)
            }, {
                toast("已清空")
            }
        )
    }

}