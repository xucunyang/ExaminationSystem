package com.oranle.es.module.ui.senior.viewmodel

import android.view.View
import androidx.lifecycle.viewModelScope
import com.oranle.es.data.entity.Assessment
import com.oranle.es.module.base.BaseRecycleViewModel
import com.oranle.es.module.examination.ExamDetailDialog
import com.oranle.es.module.examination.viewmodel.ExamShowMode
import com.oranle.es.module.ui.senior.SeniorAdminActivity
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ExamSheetOperateViewModel : BaseRecycleViewModel<Assessment>() {

    fun start() {
        load()
    }

    fun load() {
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
                getDB().getRuleDao().deleteReportRuleBySheetId(entity.id)
                getDB().getSingleChoiceDao().deleteSingleChoicesBySheetId(entity.id)
                getDB().getAssessmentDao().deleteAssessment(entity)
            }
            if (deleteSize == 1) {
                toast("已删除")
                load()
            } else {
                toast("删除失败")
            }
        }
    }

    fun onViewDetail(v: View, entity: Assessment) {
        toast("onclick on change")
        val activity = v.context as SeniorAdminActivity
        val dialog = ExamDetailDialog(activity, entity, ExamShowMode.AdminView)
        dialog.show(activity.supportFragmentManager, "")
    }

    fun onChangeSet(v: View, entity: Assessment) {
        val activity = v.context as SeniorAdminActivity
        activity.showChangeSheet(entity)
    }

}