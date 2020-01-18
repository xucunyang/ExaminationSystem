package com.oranle.es.module.ui.examinee

import android.os.Bundle
import android.os.CountDownTimer
import android.view.Menu
import androidx.lifecycle.Observer
import com.oranle.es.R
import com.oranle.es.data.entity.Assessment
import com.oranle.es.data.sp.SpUtil
import com.oranle.es.databinding.ActivityExamBinding
import com.oranle.es.module.base.BaseActivity
import com.oranle.es.module.base.toast
import com.oranle.es.module.examination.viewmodel.ExamDetailViewModel
import com.oranle.es.module.examination.viewmodel.ExamShowMode
import com.oranle.es.util.DateUtil
import timber.log.Timber

class ExamActivity : BaseActivity<ActivityExamBinding>() {

    lateinit var viewModel: ExamDetailViewModel

    var countDown: CountDownTimer? = null

    override val layoutId: Int
        get() = R.layout.activity_exam

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var assessment: Assessment? = null

        intent.extras?.apply {
            assessment = this["assessment"] as? Assessment
        }

        if (assessment == null) {
            toast("请选择量表")
            return
        }

        viewModel = getViewModel()
        val examAdapter = ExamAdapter(viewModel)

        dataBinding.apply {
            examRecyclerView.setAdapter(examAdapter)
        }

        countDown = object : CountDownTimer(1000 * 1 * 60 * 60, 1000) {
            override fun onFinish() {
                toast("时间到")
            }

            override fun onTick(millisUntilFinished: Long) {
                dataBinding.count.text = DateUtil.getTimeFromMillisecond(millisUntilFinished)
            }
        }
        (countDown as CountDownTimer).start()

        viewModel.load(assessment!!, ExamShowMode.Test, SpUtil.instance.getCurrentUser())

        viewModel.items.observe(this, Observer {
            Timber.d("observe xx $assessment")
            examAdapter.setData(it)
        })

    }

    override fun onStop() {
        super.onStop()

        if (countDown != null) {
            countDown!!.cancel()
            countDown = null
        }
    }

}