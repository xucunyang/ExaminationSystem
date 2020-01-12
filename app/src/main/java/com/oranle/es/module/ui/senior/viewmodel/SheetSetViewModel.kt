package com.oranle.es.module.ui.senior.viewmodel

import androidx.lifecycle.MutableLiveData
import com.oranle.es.data.entity.Assessment
import com.oranle.es.module.base.BaseViewModel
import com.oranle.es.module.base.toast
import timber.log.Timber

class SheetSetViewModel : BaseViewModel() {

    val sheetOriginName = MutableLiveData<String>()

    val sheetAlias = MutableLiveData<String>()

    val showDesc = MutableLiveData<Boolean>()

    val showTip = MutableLiveData<Boolean>()

    lateinit var originAssessment: Assessment

    fun load(assessment: Assessment) {
        originAssessment = assessment
        sheetOriginName.value = assessment.title
        sheetAlias.value = assessment.alias
        showDesc.value = assessment.showIntroduction
        showTip.value = assessment.showTip
    }

    fun doChange() {
        asyncCall(
            {
                val copy = originAssessment.copy(
                    alias = sheetAlias.value ?: originAssessment.alias,
                    showIntroduction = showDesc.value ?: originAssessment.showIntroduction,
                    showTip = showTip.value ?: originAssessment.showTip
                )
                getDB().getAssessmentDao().updateAssessment(copy)
            },
            {
                if (it > 0) {
                    toast("已修改")
                } else {
                    toast("出错")
                }
            }
        )
    }

}