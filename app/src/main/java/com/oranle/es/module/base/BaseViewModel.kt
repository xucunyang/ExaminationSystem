package com.oranle.es.module.base

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import timber.log.Timber

abstract class BaseViewModel : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        Timber.v("onCleared")
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
        }
    }

    fun addToCompositeDisposable(disposable: Disposable) = compositeDisposable.add(disposable)

}