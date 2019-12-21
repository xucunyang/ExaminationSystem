package com.oranle.es.module.examination.inportFile

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.oranle.es.R
import com.oranle.es.databinding.ActivityImportFileBinding
import com.oranle.es.module.base.BaseActivity
import com.oranle.es.module.examination.inportFile.Utils.getRealFilePath
import timber.log.Timber

class FileImportActivity : BaseActivity<ActivityImportFileBinding>() {

    private lateinit var viewModel: FileImportViewModel

    override val layoutId: Int
        get() = R.layout.activity_import_file

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(FileImportViewModel::class.java)

        dataBinding.vm = viewModel
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && requestCode == FILE_REQUEST_CODE) {
            Timber.d("timer: ${getRealFilePath(this, data?.data)}")

            viewModel.saveAssessment(data?.data)
        }
    }
}