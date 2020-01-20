package com.oranle.es.module.ui.examinee

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.CountDownTimer
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

    var isInForeGround = false

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
            title.text = assessment?.title
            examRecyclerView.setAdapter(examAdapter)
            examAdapter.setRecyclerView(examRecyclerView.recyclerView)

            closeBtn.setOnClickListener { v ->
                viewModel.checkIfNeedTip({
                    if (it)
                        finish()
                }, v)
            }
        }

        var countTime = 0
        countDown = object : CountDownTimer(Long.MAX_VALUE, 1000) {
            override fun onFinish() {
            }

            @SuppressLint("SetTextI18n")
            override fun onTick(millisUntilFinished: Long) {
                if (isInForeGround)
                    dataBinding.count.text = "测试所用时间：${DateUtil.change(countTime++)}"
            }
        }
        countDown?.start()

        viewModel.load(assessment!!, ExamShowMode.Test, SpUtil.instance.getCurrentUser())

        viewModel.items.observe(this, Observer {
            examAdapter.setData(it)
        })

        viewModel.dismissFlag.observe(this, Observer {
            if (it) {
                toast("回答完毕")
                finish()
            }
        })
    }

    override fun onResume() {
        super.onResume()
        isInForeGround = true
    }

    override fun onStop() {
        super.onStop()
        isInForeGround = false
    }

    override fun onDestroy() {
        super.onDestroy()
        if (countDown != null) {
            countDown!!.cancel()
            countDown = null
        }

        viewModel.items.value?.forEach {
            it.selectOption = null
        }
    }

}