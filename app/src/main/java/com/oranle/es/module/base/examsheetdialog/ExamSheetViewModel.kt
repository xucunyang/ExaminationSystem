package com.oranle.es.module.base.examsheetdialog

import androidx.lifecycle.viewModelScope
import com.oranle.es.data.entity.Assessment
import com.oranle.es.data.entity.ClassEntity
import com.oranle.es.module.base.BaseRecycleViewModel
import com.oranle.es.module.base.IO
import com.oranle.es.module.base.UI
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

class ExamSheetViewModel : BaseRecycleViewModel<Assessment>() {

    var selectedSheets = mutableListOf<Assessment>()

    var selectedShowReportSheet = mutableListOf<Assessment>()

    fun load() {
        viewModelScope.launch(UI) {
            val list = withContext(IO) {
                getDB().getAssessmentDao().getAllAssessments()
            }
            Timber.d("ExamSheetOperateViewModel load $list")
            notifyItem(list)
        }
    }

    fun onSheetSelect(assessment: Assessment) {
        selectedSheets.add(assessment)
    }

    fun onShowReportSelect(assessment: Assessment) {
        selectedShowReportSheet.add(assessment)
    }

    fun saveToDB(entity: ClassEntity) {
        viewModelScope.launch(UI) {
            withContext(IO) {
                val sheetStr = entity.sheetList.joinToString(",")?.replace(" ", "")
                val reportStr = entity.showSheetReportList.joinToString(",").replace(" ", "")
                Timber.d("saveToDB $entity ,   xxxxx $sheetStr")
                val copy = entity.copy(sheet = sheetStr ?: "", showSheetReport = reportStr)
                Timber.d("copy changed $copy")
                getDB().getClassDao().updateClass(copy)
            }

            toast("已修改")
        }
    }

}