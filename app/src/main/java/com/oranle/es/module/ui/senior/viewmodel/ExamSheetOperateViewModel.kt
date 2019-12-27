package com.oranle.es.module.ui.senior.viewmodel

import android.view.View
import androidx.lifecycle.viewModelScope
import com.oranle.es.data.entity.Assessment
import com.oranle.es.module.base.*
import com.oranle.es.module.base.examsheetdialog.AssessmentSheetDialog
import com.oranle.es.module.ui.senior.SeniorAdminActivity
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ExamSheetOperateViewModel : BaseRecycleViewModel<Assessment>() {

    fun start() {
        viewModelScope.launch(UI) {
            val allClass = withContext(IO) {
                getDB().getAssessmentDao().getAllAssessments()
            }
            notifyItem(allClass)
        }
    }


    fun onDelete(entity: Assessment) {
        viewModelScope.launch {
            val deleteSize = withContext(IO) {
                getDB().getAssessmentDao().deleteAssessment(entity)
            }
            if (deleteSize == 1) {
                toast("已删除")
            }
        }
    }

    fun onChange(v: View, entity: Assessment) {
        toast("onclick on change")
        val dialog = AssessmentSheetDialog(v.context)
        val activity = v.context as SeniorAdminActivity
        dialog.show(activity.supportFragmentManager, "")


//        val dialog = CommonDialog(activity, "")
//        dialog.create()
//        dialog.show()
    }

    fun onClearMember(entity: Assessment) {

    }

}