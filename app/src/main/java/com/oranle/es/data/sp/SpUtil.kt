package com.oranle.es.data.sp

import android.content.Context
import android.content.SharedPreferences
import com.oranle.es.app.SessionApp
import com.oranle.es.data.entity.User
import com.oranle.es.util.GsonUtil
import timber.log.Timber
import java.lang.Exception

class SpUtil private constructor() {

    companion object {
        val instance: SpUtil by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            SpUtil()
        }
    }

    private val FIRST_OPEN = "first_open"

    private val ORGANIZATION_NAME = "organization_name"

    private val EXAM_SHEET_INDEX = "exam_sheet_index"

    private val USER_BEAN = "user_bean"

    private val editor: SharedPreferences.Editor
    private val sp = SessionApp.instance!!.getSharedPreferences("sp", Context.MODE_PRIVATE)

    init {
        editor = sp.edit()
    }

    private inline fun wrap(block: () -> Unit) {
        block()
        editor.commit()
    }

    fun isFirstOpen(): Boolean {
        val isFirst = sp.getBoolean(FIRST_OPEN, true)
        if (isFirst) {
            wrap {
                editor.putBoolean(FIRST_OPEN, false)
            }
        }
        return isFirst
    }

    fun setOrganizationName(name: String) = wrap { editor.putString(ORGANIZATION_NAME, name) }

    fun getOrganizationName() = sp.getString(ORGANIZATION_NAME, "")

    fun getExamSheetIndex(): Int {

        val index = sp.getInt(EXAM_SHEET_INDEX, 0)
        setExamSheetIndex(index + 1)
        return index
    }

    fun setExamSheetIndex(index: Int) = wrap { editor.putInt(EXAM_SHEET_INDEX, index) }

    fun setCurrentUser(user: User) = wrap {
        editor.putString(USER_BEAN,  GsonUtil.GsonString(user))
    }

    fun getCurrentUser(): User? {
        val string = sp.getString(USER_BEAN, "")
        var bean: User? = null
        if (string != null && string.isNotBlank()) {
            try {
                bean = GsonUtil.GsonToBean(string, User::class.java)
            } catch (e: Exception) {
                e.printStackTrace()
                Timber.e(e)
            }
        }
        return bean
    }

}