package com.oranle.es.module.ui.senior.viewmodel

import android.view.View
import androidx.lifecycle.viewModelScope
import com.oranle.es.data.entity.ClassEntity
import com.oranle.es.data.repository.DBRepository
import com.oranle.es.module.base.BaseRecycleViewModel
import com.oranle.es.module.base.BaseViewModel
import com.oranle.es.module.base.IO
import com.oranle.es.module.base.UI
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ClassViewModel : BaseRecycleViewModel<ClassEntity>() {

    fun start() {
        viewModelScope.launch(UI) {
            val allClass = withContext(IO) {
                DBRepository.getDB().getClassDao().getAllClass()
            }
            notifyItem(allClass)
        }
    }


    fun onDelete(entity: ClassEntity) {
        viewModelScope.launch {
            val deleteSize = withContext(IO) {
                getDB().getClassDao().deletelass(entity)
            }
            if (deleteSize == 1) {
                toast("已删除")
            }
        }
    }

    fun onChange(entity: ClassEntity) {

    }

    fun onClearMember(entity: ClassEntity) {

    }

}