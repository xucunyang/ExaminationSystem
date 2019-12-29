package com.oranle.es.module.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import timber.log.Timber

open class BaseRecycleViewModel<E> : BaseViewModel() {

    val isEmpty = MutableLiveData<Boolean>(true)

    private val _items = MutableLiveData<List<E>>().apply { value = emptyList() }

    val items: LiveData<List<E>> = _items

    fun toast(msg: String) {
        com.oranle.es.module.base.toast(msg)
    }

    fun notifyItem(rows: List<E>?) {
        if (rows != null && rows.isNotEmpty()) {
            Timber.d("notifyItem")
            _items.value = ArrayList(rows)
        } else {
            _items.value = emptyList()
        }
    }

}