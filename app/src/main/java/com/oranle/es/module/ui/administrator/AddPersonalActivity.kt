package com.oranle.es.module.ui.administrator

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.lifecycle.ViewModelProviders
import com.oranle.es.R
import com.oranle.es.data.entity.User
import com.oranle.es.data.sp.SpUtil
import com.oranle.es.databinding.ActivityAddPersonalBinding
import com.oranle.es.module.base.BaseActivity
import com.oranle.es.module.base.IO
import com.oranle.es.module.base.UI
import com.oranle.es.module.base.launchWithLifecycle
import com.oranle.es.module.ui.administrator.fragment.model.AddPersonalViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.withContext

class AddPersonalActivity : BaseActivity<ActivityAddPersonalBinding>() {

    lateinit var classSelectData: Array<String?>
    val sexArray = mutableListOf<String>()
    private var originUser: User? = null
    lateinit var viewModel: AddPersonalViewModel
    override val layoutId: Int
        get() = R.layout.activity_add_personal

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
    }

    private fun initView() {
        originUser = intent.getSerializableExtra("user") as? User
        viewModel = ViewModelProviders.of(this).get(AddPersonalViewModel::class.java)
        dataBinding.viewModel = viewModel
        dataBinding.lifecycleOwner = this
        if (originUser != null) {
            viewModel.userLoginName.value = originUser!!.userName
            viewModel.name.value = originUser!!.alias
            viewModel.pwd.value = originUser!!.psw
            viewModel.comfirmPwd.value = originUser!!.psw
            viewModel.classId.value = originUser!!.classId
            viewModel.className.value = originUser!!.className
            viewModel.schoolName.value = originUser!!.schoolName
        }
        GlobalScope.launchWithLifecycle(this, UI) {
            val classes = withContext(IO) {
                viewModel.getAllStudentByManager()
            }
            viewModel.classes.value = classes
            classSelectData = arrayOfNulls(classes.size)

            val array = arrayOfNulls<String>(classes.size)
            var currentSelect = 0
            classes.forEachIndexed { index, entity ->
                array[index] = entity.className
                classSelectData[index] = entity.className
                if(originUser?.classId == entity.id) {
                    currentSelect = index
                }
            }

            val classAdapter =
                ArrayAdapter<String>(
                    this@AddPersonalActivity,
                    android.R.layout.simple_list_item_1,
                    array
                )
            dataBinding.spinnerClass.adapter = classAdapter
            dataBinding.spinnerClass.setSelection(currentSelect)
            dataBinding.spinnerClass.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {

                    override fun onNothingSelected(parent: AdapterView<*>?) {}

                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        viewModel.className.value = classes[position]!!.className
                        viewModel.classId.value = classes[position]!!.id
                    }
                }
        }


        val schoolName = SpUtil.instance.getOrganizationName()
        val schoolAdapter =
            ArrayAdapter<String>(
                this@AddPersonalActivity,
                android.R.layout.simple_list_item_1,
                arrayOf(schoolName)
            )
        dataBinding.schoolSpinner.adapter = schoolAdapter
        viewModel.schoolName.value = schoolName


        sexArray.add("男")
        sexArray.add("女")
        val sexAdapter =
            ArrayAdapter<String>(
                this@AddPersonalActivity,
                android.R.layout.simple_list_item_1,
                sexArray
            )
        dataBinding.spinnerSex.adapter = sexAdapter

        dataBinding.spinnerClass.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {

                override fun onNothingSelected(parent: AdapterView<*>?) {}

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    viewModel.sex.value = sexArray[position]
                }
            }
    }

    fun onCommit(view: View) {
        if (originUser == null) {
            viewModel.onAddPersonal()
        } else {
            viewModel.updateUser(originUser!!)
        }
    }
}
