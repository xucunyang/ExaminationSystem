package com.oranle.es.module.ui.administrator.fragment.model

import android.view.View
import androidx.lifecycle.viewModelScope
import com.oranle.es.data.entity.ClassEntity
import com.oranle.es.data.entity.Role
import com.oranle.es.data.entity.User
import com.oranle.es.data.sp.SpUtil
import com.oranle.es.module.base.BaseRecycleViewModel
import com.oranle.es.module.base.start
import com.oranle.es.module.ui.administrator.AddPersonalActivity
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

class LoginManagerViewModel : BaseRecycleViewModel<User>() {

    fun onAddPersonal(v: View) {
        val context = v.context
        context.start<AddPersonalActivity>()
    }

    fun load() {
        viewModelScope.launch(UI) {
            val adminList = withContext(IO) {
                getAllStudentByManager()
            }
            notifyItem(adminList)
        }
    }

    suspend fun getAllStudentByManager(): List<User> {

        val currentUser = SpUtil.instance.getCurrentUser()

        if (currentUser == null) {
            com.oranle.es.module.base.toast("当前登录用户为空，请检查")
            return emptyList()
        }

        val classesInCharge = getAllClassesInCharge(currentUser)
        val classesIdInCharge = mutableListOf<Int>()
        classesInCharge.forEach {
            classesIdInCharge.add(it.id)
        }

        val allStudents = getAllStudentByClassIds(classesIdInCharge)

        val stuIds = mutableListOf<Int>()
        allStudents.forEach {
            stuIds.add(it.id)
        }

        return allStudents
    }
    
    /**
     * 根据班级id查出所有的学生
     */
    suspend fun getAllStudentByClassIds(classIds: List<Int>): List<User> =
        getDB().getUserDao().getUserByClassIdsAndRole(classIds, Role.Examinee.value)


    suspend fun getAllClassesInCharge(manager: User): List<ClassEntity> {
        val classList = mutableListOf<ClassEntity>()
        manager.classInChargeList.forEach {
            if (it.isNotBlank()) {
                val classEntity = getDB().getClassDao().getClassById(it.toInt())
                classList.add(classEntity)
            } else {
                Timber.w("query class by id $it")
            }
        }
        return classList
    }

}
