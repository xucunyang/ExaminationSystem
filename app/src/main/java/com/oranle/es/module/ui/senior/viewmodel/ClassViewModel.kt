package com.oranle.es.module.ui.senior.viewmodel

import android.view.View
import androidx.lifecycle.viewModelScope
import com.oranle.es.data.entity.ClassEntity
import com.oranle.es.module.base.*
import com.oranle.es.module.base.examsheetdialog.AssessmentSheetDialog
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
        val dialog = AssessmentSheetDialog(v.context, entity)
        val activity = v.context as SeniorAdminActivity
        dialog.show(activity.supportFragmentManager, "")
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