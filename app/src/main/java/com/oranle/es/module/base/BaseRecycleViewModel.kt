package com.oranle.es.module.base

import androidx.lifecycle.MutableLiveData
import timber.log.Timber

open class BaseRecycleViewModel<E>: BaseViewModel() {

    val isEmpty = MutableLiveData<Boolean>(true)

    protected val _items = MutableLiveData<List<E>>().apply { value = ArrayList()}

    val items: MutableLiveData<List<E>> = _items

    fun toast(msg: String) {
        com.oranle.es.module.base.toast(msg)
    }

    protected fun notifyItem(rows: List<E>?) {
        if (rows != null && rows.isNotEmpty()) {
            Timber.d("notifyItem")
            _items.value = rows
        } else {
            _items.value = emptyList()
        }
    }

}