package com.oranle.es.module.base

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment

inline fun <reified T> Context.start(extras: Bundle? = null, newTask: Boolean = false) {
    val intent = Intent(this, T::class.java).apply {

        extras?.let {
            this.putExtras(extras)
        }
    }.let {
        if (newTask) it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK and Intent.FLAG_ACTIVITY_NEW_TASK)
        else it
    }

    startActivity(intent)
}

fun <T : Any> androidx.fragment.app.FragmentActivity.argument(key: String) =
    lazy {
        intent?.extras?.get(key) as? T ?: error("Intent Argument $key is missing")
    }

fun <T : Any> Fragment.argument(key: String) =
    lazy {
        arguments?.get(key) as? T ?: error("Intent Argument $key is missing")
    }