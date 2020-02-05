package com.oranle.es.module.ui.administrator.fragment

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.lifecycle.MutableLiveData
import com.oranle.es.R
import com.oranle.es.data.entity.Assessment
import com.oranle.es.data.entity.User
import com.oranle.es.databinding.FragmentPersonSelectBinding
import com.oranle.es.module.base.BaseFragment
import com.oranle.es.module.base.BaseViewModel
import com.oranle.es.module.base.toast
import com.oranle.es.module.ui.administrator.AdministratorActivity

class PersonalSelectFragment : BaseFragment<FragmentPersonSelectBinding>() {

    companion object {
        val KEY_CLASS_ID = "class_id"
        val KEY_ASSESSMENT = "key_assessment"

        fun newInstance(classId: Int, assessment: Assessment? = null): PersonalSelectFragment {
            val data = Bundle()
            data.putSerializable(KEY_ASSESSMENT, assessment)
            data.putInt(KEY_CLASS_ID, classId)

            val fragment = PersonalSelectFragment()
            fragment.arguments = data
            return fragment
        }
    }

    lateinit var viewModel: PersonSelectViewModel

    var school: String? = null

    override val layoutId: Int
        get() = R.layout.fragment_person_select

    override fun initView() {
        viewModel = getViewModel()

        val classId = arguments?.getInt(KEY_CLASS_ID, -1)
        viewModel.assessment = arguments?.getSerializable(KEY_ASSESSMENT) as Assessment

        if (classId == null || classId == -1) {
            toast("参数错误")
            return
        }

        dataBinding?.apply {
            vm = viewModel

            studentSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {
                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    viewModel.allStudent.value?.apply {
                        viewModel.currentStudent = get(position)
                    }
                }
            }

            nextStep.setOnClickListener {
                val administratorActivity = activity as AdministratorActivity
                administratorActivity.onReport(it)
            }
        }

        viewModel.loadStudent(classId)
    }

}

class PersonSelectViewModel : BaseViewModel() {

    val studentsName = MutableLiveData<List<String>>()
    val allStudent = MutableLiveData<List<User>>()

    var currentStudent: User? = null
    var assessment: Assessment? = null

    fun loadStudent(classId: Int) {
        asyncCall(
            {
                getDB().getUserDao().getUserByClassId(classId)
            }, { list ->
                allStudent.value = list

                val names = mutableListOf<String>()
                list.forEach { user ->
                    names.add(user.userName)
                }
                studentsName.value = names
            }
        )
    }

    fun onNextStep(v: View) {
        val activity = v.context as AdministratorActivity
        activity.showPersonStatistic(currentStudent!!.id, assessment)
    }
}