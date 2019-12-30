package com.oranle.es.module.ui.senior.viewmodel

import android.view.View
import androidx.lifecycle.viewModelScope
import com.oranle.es.data.entity.ClassEntity
import com.oranle.es.module.base.BaseRecycleViewModel
import com.oranle.es.module.ui.senior.SeniorAdminActivity
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ClassViewModel : BaseRecycleViewModel<ClassEntity>() {

    fun start() {
        viewModelScope.launch(UI) {
            val allClass = withContext(IO) {
                getDB().getClassDao().getAllClass()
            }
            notifyItem(allClass)
        }
    }

    fun updateClass(entity: ClassEntity) {
        viewModelScope.launch(UI) {
            val size = withContext(IO) {
                getDB().getClassDao().updateClass(entity)
            }
            if (size == 1) {
                toast("已修改")
            } else {
                toast("修改失败")
            }

        }
    }

    fun onDelete(entity: ClassEntity) {
        viewModelScope.launch {
            val deleteSize = withContext(IO) {
                getDB().getClassDao().deleteClass(entity)
            }
            if (deleteSize == 1) {
                toast("已删除")
            }

            val list = (items.value)?.toMutableList()
            list?.remove(entity)

            notifyItem(list)
        }
    }

    fun onChange(v: View, entity: ClassEntity) {
        toast("onclick on change")
        val activity = v.context as SeniorAdminActivity
//        val dialog = AssessmentSheetDialog(v.context, entity)
//        dialog.show(activity.supportFragmentManager, "")

        activity.showChangeClass(entity)

    }

    fun onClearMember(entity: ClassEntity) {
        viewModelScope.launch {
            withContext(IO) {
                getDB().getUserDao().clearExamineeByClassId(entity.id)
            }
            toast("已清空")
        }

    }

}