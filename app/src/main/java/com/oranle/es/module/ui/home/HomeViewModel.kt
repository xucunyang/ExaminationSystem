package com.oranle.es.module.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.oranle.es.module.base.BaseViewModel

class HomeViewModel : BaseViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text
}