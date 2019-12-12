package com.oranle.es.module.base

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.annotation.ColorInt
import androidx.lifecycle.LifecycleOwner
import com.oranle.es.app.SessionApp
import kotlinx.coroutines.*
import timber.log.Timber
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

val UI: CoroutineDispatcher = Dispatchers.Main
val IO: CoroutineDispatcher = Dispatchers.IO

fun runOnUI(block: suspend CoroutineScope.() -> Unit): Job = GlobalScope.launch(context = UI, block = block)

fun runInBackground(block: suspend CoroutineScope.() -> Unit): Job = GlobalScope.async(context = IO, block = block)

fun <T> asyncOnUI(block: suspend CoroutineScope.() -> T): Deferred<T> = GlobalScope.async(context = UI, block = block)

fun <T> asyncInBackground(block: suspend CoroutineScope.() -> T): Deferred<T> = GlobalScope.async(context = IO, block = block)

fun <T> GlobalScope.asyncWithLifecycle(lifecycleOwner: LifecycleOwner,
                                       context: CoroutineContext = EmptyCoroutineContext,
                                       start: CoroutineStart = CoroutineStart.DEFAULT,
                                       block: suspend CoroutineScope.() -> T): Deferred<T> {

    val deferred = async(context, start, block)

    lifecycleOwner.lifecycle.addObserver(CoroutinesLifecycleObserver(deferred))

    return deferred
}

fun GlobalScope.launchWithLifecycle(lifecycleOwner: LifecycleOwner?,
                                    context: CoroutineContext = EmptyCoroutineContext,
                                    start: CoroutineStart = CoroutineStart.DEFAULT,
                                    block: suspend CoroutineScope.() -> Unit) {

    val job = launch(context, start, block)

    lifecycleOwner?.lifecycle?.addObserver(CoroutinesLifecycleObserver(job))
}

fun GlobalScope.blockWithTryCatch(tryBlock: () -> Unit, catchBlock: () -> Unit, finalBlock: () -> Unit): Unit {
    try {
        tryBlock()
    } catch (e: Exception) {
        catchBlock()
    } finally {
        finalBlock()
    }
}

fun launchWrapped(lifecycleOwner: LifecycleOwner?,
                              context: CoroutineContext = EmptyCoroutineContext,
                              block: suspend CoroutineScope.() -> Unit,
                              exceptionHandler: (Exception) -> Unit,
                              finalBlock: () -> Unit,
                              start: CoroutineStart = CoroutineStart.DEFAULT) {
        val TAG = "test-extension"
        Timber.d("$TAG ${Thread.currentThread().name} start")
        val job: Job
        try {
            job = GlobalScope.launch(context, start, block)
            lifecycleOwner?.lifecycle?.addObserver(CoroutinesLifecycleObserver(job))
            Timber.d("$TAG ${Thread.currentThread().name} launch end")
        } catch (e: Exception) {
            Timber.d("$TAG  {Thread.currentThread().name} Exception $e")
            exceptionHandler(e)
        } finally {
            finalBlock()
        }
        Timber.d("$TAG  ${Thread.currentThread().name} execute end")
}

object MainHandler : Handler(Looper.getMainLooper())

fun runOnUIThread(uiWork: () -> Unit) {
    if (Looper.myLooper() == Looper.getMainLooper()) {
        uiWork()
    } else {
        MainHandler.post(uiWork)
    }
}

@SuppressLint("ShowToast")
fun toast(text: CharSequence?, @ColorInt messageColor: Int = Color.WHITE, vararg views: Pair<View, Int> = arrayOf()) =
    runOnUIThread {
        Toast.makeText(SessionApp.instance, text, Toast.LENGTH_SHORT)
            .also {
                if (views.isNotEmpty()) {
                    for (addView in views) {
                        (it.view as LinearLayout).addView(addView.first, addView.second)
                    }
                }
            }
            .show()
    }