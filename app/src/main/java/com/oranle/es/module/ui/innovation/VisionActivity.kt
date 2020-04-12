package com.oranle.es.module.ui.innovation

import android.os.Bundle
import androidx.lifecycle.Observer
import com.oranle.es.R
import com.oranle.es.databinding.ActivityTestVisualBinding
import com.oranle.es.module.base.BaseActivity

class VisionActivity :
    BaseActivity<ActivityTestVisualBinding>() {

    lateinit var mViewModel: VisualViewModel

    override val layoutId: Int
        get() = R.layout.activity_test_visual

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
    }

    private fun initView() {
        mViewModel = getViewModel()

        dataBinding.viewModel = mViewModel
        dataBinding.lifecycleOwner = this

        mViewModel.activityFinish.observe(this, Observer {
            if (it) {
                finish()
            }
        })
    }

}