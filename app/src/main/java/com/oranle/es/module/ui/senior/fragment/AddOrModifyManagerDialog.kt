package com.oranle.es.module.ui.senior.fragment

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.oranle.es.R
import com.oranle.es.data.entity.ClassEntity
import com.oranle.es.data.entity.User
import com.oranle.es.data.repository.DBRepository
import com.oranle.es.data.sp.SpUtil
import com.oranle.es.databinding.FragmentManagerAddBinding
import com.oranle.es.databinding.ItemClassSelectBinding
import com.oranle.es.module.base.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.withContext
import timber.log.Timber

/**
 *  添加或者修改管理员信息
 */
class AddOrModifyManagerDialog(val cxt: Context) : DialogFragment() {

    lateinit var dataBinding: FragmentManagerAddBinding

    lateinit var viewModel: DialogClassViewModel

    private lateinit var adapter: MultiSelectClassAdapter

    private var originUser: User? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_manager_add, container, false
        )
        dataBinding.setLifecycleOwner(viewLifecycleOwner)

        initView()

        return dataBinding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setStyle(STYLE_NO_FRAME, R.style.dialogStyle)

        viewModel = ViewModelProviders.of(this).get(DialogClassViewModel::class.java)

        originUser = arguments?.getSerializable("user") as? User

        Timber.d("oncreate originUser ${originUser == null}")
    }

    var mListener: DialogInterface.OnDismissListener? = null

    override fun onStart() {
        super.onStart()
//        initWindow()
        dialog.setCanceledOnTouchOutside(false)

        if (mListener != null)
            dialog.setOnDismissListener(mListener)
    }

    private fun initView() {

        dataBinding.apply {

            vm = viewModel

            adapter = MultiSelectClassAdapter(viewModel)
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(cxt)

            viewModel.items.observe(viewLifecycleOwner, Observer {
                adapter = MultiSelectClassAdapter(viewModel)
                adapter.submitList(it)
            })

            closeBtn.setOnClickListener {
                dismiss()
            }

            if (originUser != null) {
                title.text = "修改管理员信息"
                addManager.text = "修改"
            } else {
                title.text = "添加管理员"
                addManager.text = "添加"
            }
            initOriginAdminData(originUser)

            GlobalScope.launchWithLifecycle(viewLifecycleOwner, UI) {

                val classes = withContext(IO) {
                    DBRepository.getDB().getClassDao().getAllClass()
                }
                Timber.d("get from db getAllClass ${classes.size}")

                if (classes.isEmpty()) {
                    toast("请先创建班级！")
                    return@launchWithLifecycle
                }

                val array = arrayOfNulls<String>(classes.size)
                var currentSelect = 0
                classes.forEachIndexed { index, entity ->
                    array[index] = entity.className
                    if (originUser != null && originUser?.classId == entity.id) {
                        currentSelect = index
                    }
                }

                val classAdapter =
                    ArrayAdapter<String>(context!!, android.R.layout.simple_list_item_1, array)
                var selectClassEntity: ClassEntity? = classes[0]
                spinnerClass.adapter = classAdapter
                spinnerClass.setSelection(currentSelect)
                spinnerClass.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

                    override fun onNothingSelected(parent: AdapterView<*>?) {}

                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        selectClassEntity = classes[position]
                    }
                }

                val schoolName = SpUtil.instance.getOrganizationName()
                val schoolAdapter =
                    ArrayAdapter<String>(
                        context!!,
                        android.R.layout.simple_list_item_1,
                        arrayOf(schoolName)
                    )
                schoolSpinner.adapter = schoolAdapter

                addManager.setOnClickListener {
                    val result =
                        if (originUser == null) {
                            viewModel.saveToDB(selectClassEntity, schoolName)
                        } else {
                            viewModel.updateUser(originUser!!)
                        }

                    if (result) {
                        dismiss()
                    } else {
                        toast("出错")
                    }

                    Timber.d("vm data select ${viewModel.items.value}")
                }
            }
        }

        val selectClassList = originUser?.classIncharge?.split(",")
        viewModel.load(selectClassList ?: emptyList())
    }

    private fun initOriginAdminData(user: User?) {
        viewModel.initOriginData(user)
    }

//    private fun initWindow() {
//        val dialogWindow = dialog.window
//        dialogWindow!!.setGravity(Gravity.CENTER)
//        val lp = dialogWindow.attributes
//        lp.width = 1024
//        lp.height = 700
//        dialogWindow.attributes = lp
//    }

    fun setDismissListener(listener: DialogInterface.OnDismissListener?) {
        this.mListener = listener
    }

    inner class MultiSelectClassAdapter(vm: DialogClassViewModel) :
        BaseAdapter<ClassSelect, ItemClassSelectBinding, DialogClassViewModel>(vm) {
        override fun doBindViewHolder(
            binding: ItemClassSelectBinding,
            item: ClassSelect,
            viewModel: DialogClassViewModel
        ) {
            binding.item = item
            binding.checkClass.isChecked = item.isSelect
            binding.checkClass.setOnCheckedChangeListener { _, isChecked ->
                item.isSelect = isChecked
            }
        }

        override val layoutRes = R.layout.item_class_select

    }

}

data class ClassSelect(
    var classId: Int = -1,
    var className: String = "",
    var isSelect: Boolean = false
)