package com.oranle.es.module.base.examsheetdialog

import androidx.lifecycle.viewModelScope
import com.oranle.es.data.entity.Assessment
import com.oranle.es.module.base.BaseRecycleViewModel
import com.oranle.es.module.base.IO
import com.oranle.es.module.base.UI
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

class ExamSheetViewModel : BaseRecycleViewModel<Assessment>() {

        fun load() {
            viewModelScope.launch(UI) {
                val list = withContext(IO) {
                    getDB().getAssessmentDao().getAllAssessments()
                }
                Timber.d("ExamSheetViewModel load $list")
                notifyItem(list)
            }
        }

        var selectedSheets = mutableListOf<Assessment>()
        var selectedShowReportSheet = mutableListOf<Assessment>()

        fun onSheetSelect(assessment: Assessment) {
            selectedSheets.add(assessment)
        }


        fun onShowReportSelect(assessment: Assessment) {
            selectedShowReportSheet.add(assessment)
        }

    }