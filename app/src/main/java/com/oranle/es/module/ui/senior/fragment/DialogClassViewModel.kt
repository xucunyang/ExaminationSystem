package com.oranle.es.module.ui.senior.fragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.oranle.es.data.entity.ClassEntity
import com.oranle.es.data.entity.Role
import com.oranle.es.data.entity.User
import com.oranle.es.module.base.BaseRecycleViewModel
import com.oranle.es.module.base.IO
import com.oranle.es.module.base.UI
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

class DialogClassViewModel : BaseRecycleViewModel<AddManagerDialog.ClassSelect>() {

    val userName = MutableLiveData<String>()

    val psw = MutableLiveData<String>()

    val pswConfirm = MutableLiveData<String>()

    val name = MutableLiveData<String>()

    fun load() {
        viewModelScope.launch(UI) {
            val list = withContext(IO) {
                getDB().getClassDao().getAllClass()
            }
            Timber.d("ExamSheetOperateViewModel load $list")

            val mutableListOf = mutableListOf<AddManagerDialog.ClassSelect>()

            list.forEachIndexed { index, entity ->
                mutableListOf.add(AddManagerDialog.ClassSelect(entity.className))
            }


            Timber.d("ExamSheetOperateViewModel wrap load $mutableListOf")

            notifyItem(mutableListOf)
        }
    }

    fun selectAll() {
        items.value?.forEach {
            it.isSelect = true
        }

        items.value?.forEach {
            Timber.d("select all sheet ${it}")
        }

        notifyItem(items.value)
    }
//
//    fun unselectAllSheet(entity: ClassEntity) {
//        items.value?.forEach {
//            entity.sheetList.remove(it.id.toString())
//        }
//
//        items.value?.forEach {
//            Timber.d("select all sheet ${it.id}  ${it.isSelect}")
//        }
//
//        notifyItem(items.value)
//    }
//
//    fun selectAllShowReportSheet() {
//        items.value?.forEach {
//            it.showReportSheet.value = true
//        }
//        notifyItem(items.value)
//    }
//
//    fun unselectAllShowReportSheet() {
//        items.value?.forEach {
//            it.showReportSheet.value = false
//        }
//
//        notifyItem(items.value)
//    }
//
//    private fun updateEntity(entity: ClassEntity) {
//        items.value?.forEach {
//            Timber.d("on click ${it.id}  + ${it.isSelect} + ${it.showReportSheet}")
//            if (it.isSelect.value == true) {
//                entity.sheetList.add(it.id.toString())
//            } else {
//                entity.sheetList.remove(it.id.toString())
//            }
//            if (it.showReportSheet.value == true) {
//                entity.showSheetReportList.add(it.id.toString())
//            } else {
//                entity.showSheetReportList.remove(it.id.toString())
//            }
//        }
//    }

    fun saveToDB(classEntity: ClassEntity?, schoolName: String?): Boolean {

        Timber.d("saveToDB $classEntity, ${items.value}")

        if (userName.value.isNullOrBlank()) {
            toast("请填写用户名")
            return false
        }

        if (psw.value.isNullOrBlank()) {
            toast("请填写密码")
            return false
        }

        if (pswConfirm.value.isNullOrBlank()) {
            toast("请确认密码")
            return false
        }

        if (pswConfirm.value != psw.value) {
            toast("密码不一致")
            return false
        }

        if (name.value.isNullOrBlank()) {
            toast("请填写姓名")
            return false
        }
        viewModelScope.launch(UI) {
            withContext(IO) {

                val classIncharge = mutableListOf<String>()
                items.value?.forEach {
                    if (it.isSelect) {
                        classIncharge.add(it.className)
                    }
                }

                getDB().getUserDao().addUser(
                    User(
                        userName = userName.value!!,
                        alias = name.value!!,
                        role = Role.Manager.value,
                        psw = psw.value!!,
                        classId = classEntity!!.id,
                        classIncharge = classIncharge.joinToString(",")
                    )
                )
            }

            toast("已录入")
        }
        return true
    }

}