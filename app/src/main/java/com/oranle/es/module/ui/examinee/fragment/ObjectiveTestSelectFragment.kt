package com.oranle.es.module.ui.examinee.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import com.oranle.es.R
import com.oranle.es.databinding.FragmentSubjectiveSelectBinding
import com.oranle.es.module.base.BaseFragment
import com.oranle.es.module.ui.examinee.viewmodel.AssessmentSelectViewModel
import com.oranle.es.module.ui.examinee.viewmodel.AssessmentType
import timber.log.Timber

class ObjectiveTestSelectFragment : BaseFragment<FragmentSubjectiveSelectBinding>() {

    lateinit var viewModel : AssessmentSelectViewModel

    override val layoutId: Int
        get() = R.layout.fragment_subjective_select

    override fun initView() {

        viewModel = getViewModel()

        dataBinding?.apply {
            vm = viewModel

            examSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {
                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    val assessment = viewModel.assessments.value?.get(position)
                    viewModel.setCurrentAssessment(assessment)
                }
            }
        }

        viewModel.loadAssessments(AssessmentType.OBJECTIVE)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.d("onCreate")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Timber.d("onCreateView")
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Timber.d("onActivityCreated")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.d("onViewCreated")
    }

    override fun onStart() {
        super.onStart()
        Timber.d("onStart")
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        Timber.d("onAttach")
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        Timber.d("onCreate")
    }

    override fun onPause() {
        super.onPause()
        Timber.d("onPause")
    }

    override fun onAttachFragment(childFragment: Fragment?) {
        super.onAttachFragment(childFragment)
        Timber.d("onAttachFragment")
    }

    override fun onResume() {
        super.onResume()
        Timber.d("onResume")
    }

    override fun onDetach() {
        super.onDetach()
        Timber.d("onDetach")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Timber.d("onDestroyView")
        viewModel.setCurrentAssessment(null)
    }


    override fun onDestroy() {
        super.onDestroy()
        Timber.d("onDestroy")
    }

}