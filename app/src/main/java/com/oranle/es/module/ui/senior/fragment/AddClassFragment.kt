package com.oranle.es.module.ui.senior.fragment

import android.view.View
import com.oranle.es.R
import com.oranle.es.data.entity.Assessment
import com.oranle.es.data.entity.ClassEntity
import com.oranle.es.data.repository.DBRepository
import com.oranle.es.databinding.FragmentClassAddBinding
import com.oranle.es.module.base.*
import com.oranle.es.module.base.examsheetdialog.AssessmentSheetDialog
import com.oranle.es.module.ui.senior.SeniorAdminActivity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.lang.StringBuilder

const val CLASS_ENTITY = "class_entity"

class AddClassFragment : BaseFragment<FragmentClassAddBinding>() {

    private val sheetSelectSet = mutableSetOf<Assessment>()

    private val showSheetReportSet = mutableSetOf<Assessment>()

    override val layoutId: Int
        get() = R.layout.fragment_class_add

    override fun initView() {

        var originClassEntity = arguments?.getSerializable(CLASS_ENTITY) as? ClassEntity

        Timber.d("originClassEntity $originClassEntity")

        val isAdd = (originClassEntity == null)

        dataBinding?.apply {

            GlobalScope.launchWithLifecycle(lifecycleOwner = viewLifecycleOwner, context = UI) {
                if (!isAdd) {
                    originClassEntity = withContext(IO) {
                        DBRepository.getDB().getClassDao().getClassById(originClassEntity!!.id)
                    }
                    Timber.d("updated            $originClassEntity")
                }

                if (isAdd) {
                    title.text = "添加班级信息"
                } else {
                    title.text = "修改班级信息"
                    classNameEt.setText(originClassEntity!!.className)

                    val sSheetSet = originClassEntity!!.sheet.split(",").toSet()
                    sheetSelectSet.addAll(getAssessment(sSheetSet))
                    val sReportSet = originClassEntity!!.showSheetReport.split(",").toSet()
                    showSheetReportSet.addAll(getAssessment(sReportSet))

                    organizeSelectedText(sheetSelectSet, showSheetReportSet)
                }

                radioGroup.check(
                    if (isAdd)
                        R.id.not_permit
                    else {
                        if (originClassEntity!!.isRegister)
                            R.id.permit
                        else
                            R.id.not_permit
                    }
                )

                selectSheetLayout.visibility =
                    if (originClassEntity != null && originClassEntity!!.isRegister) View.VISIBLE else View.GONE

                var isPermitRegister: Boolean = true
                radioGroup.setOnCheckedChangeListener { checkBox, i ->
                    isPermitRegister = (i == R.id.permit)
                    selectSheetLayout.visibility =
                        if (isPermitRegister) View.VISIBLE else View.GONE
                }

                select.setOnClickListener {
                    activity?.apply {
                        val dialog =
                            AssessmentSheetDialog(this, sheetSelectSet, showSheetReportSet)
                        dialog.show(this.supportFragmentManager, "")
                        dialog.setCallBack { sheet, report ->

                            Timber.d(" on select $sheet $report")
                            sheetSelectSet.addAll(sheet)
                            showSheetReportSet.addAll(report)

                            organizeSelectedText(sheet, report)
                        }
                    }
                }

                addClass.text = if (isAdd) "添加" else "修改"

                addClass.setOnClickListener { v ->
                    val className = classNameEt.editableText.toString()
                    if (className.isEmpty()) {
                        toast("请输入班级名称")
                        return@setOnClickListener
                    }

                    GlobalScope.launchWithLifecycle(
                        lifecycleOwner = viewLifecycleOwner,
                        context = UI
                    ) {
                        withContext(IO) {
                            val classDao = DBRepository.getDB().getClassDao()
                            if (isAdd) {
                                val isEmpty = classDao.getClassByName(className).isEmpty()
                                if (isEmpty) {
                                    classDao.addClass(
                                        ClassEntity(
                                            className = className,
                                            isRegister = isPermitRegister,
                                            sheet = if (isPermitRegister) getSheetId(sheetSelectSet) else "",
                                            showSheetReport = if (isPermitRegister) getSheetId(
                                                showSheetReportSet
                                            ) else ""
                                        )
                                    )
                                    toast("班级已录入")
                                } else {
                                    toast("班级名称已存在")
                                }
                            } else {
                                val copy = originClassEntity!!.copy(
                                    className = className,
                                    isRegister = isPermitRegister,
                                    sheet = if (isPermitRegister) getSheetId(sheetSelectSet) else "",
                                    showSheetReport = if (isPermitRegister) getSheetId(
                                        showSheetReportSet
                                    ) else ""
                                )
                                classDao.updateClass(copy)
                                toast("班级已修改")
                            }

                        }

                    }

                }

                back.setOnClickListener {
                    val seniorAdminActivity = activity as SeniorAdminActivity
                    seniorAdminActivity.onUnit(it)
                }
            }

        }
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        Timber.d("onHiddenChanged $hidden")
        if (!hidden) {
            initView()
        } else {

        }
    }

    private suspend fun getAssessment(ids: Set<String>) = withContext(IO) {
        val assessments = DBRepository.getDB().getAssessmentDao().getAllAssessments()
        val finalAssessments = mutableSetOf<Assessment>()
        ids.forEach { id ->
            assessments.forEach { a ->
                if (a.id.toString() == id) finalAssessments.add(a)
            }
        }
        finalAssessments
    }

    private fun getSheetId(sheets: Set<Assessment>): String {
        val set = mutableSetOf<Int>()
        sheets.forEach {
            set.add(it.id)
        }
        return set.joinToString(",")
    }

    private fun FragmentClassAddBinding.organizeSelectedText(
        sheet: Set<Assessment>,
        report: Set<Assessment>
    ) {
        if (sheet.isNotEmpty() || report.isNotEmpty()) {
            val sb = StringBuilder()
            sb.append("---所选测试量表---")
            sb.append(System.lineSeparator())
            sb.append(System.lineSeparator())
            sheet.forEach {
                sb.append(it.title)
                sb.append(System.lineSeparator())
            }
            sb.append(System.lineSeparator())
            sb.append("---被测人可直接看到报告的量表---")
            sb.append(System.lineSeparator())
            sb.append(System.lineSeparator())
            report.forEach {
                sb.append(it.title)
                sb.append(System.lineSeparator())
            }
            selectText.text = sb.toString()
        }
    }

}