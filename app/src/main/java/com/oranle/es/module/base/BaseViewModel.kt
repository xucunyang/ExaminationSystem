package com.oranle.es.module.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.oranle.es.data.repository.DBRepository
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import timber.log.Timber

abstract class BaseViewModel : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    internal val showDialog = MutableLiveData(false)
    val content = MutableLiveData<String>("请稍后...")

    override fun onCleared() {
        super.onCleared()
        Timber.v("onCleared")
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
        }
    }

    fun addToCompositeDisposable(disposable: Disposable) = compositeDisposable.add(disposable)

    fun showDialog(msg: String? = null) {
        msg?.let {
            content.value = msg
        }
        showDialog.value = true
    }

    fun hideDialog() {
        showDialog.value = false
    }

    fun getDB() = DBRepository.getDB()

}