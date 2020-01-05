package com.oranle.es.module.ui

import androidx.lifecycle.viewModelScope
import com.oranle.es.module.base.BaseRecycleViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

class DemoListViewModel : BaseRecycleViewModel<List<String>>() {

    fun start() {
        viewModelScope.launch(UI){
            Timber.d("start in ${Thread.currentThread().name}")

            Timber.d("start")
            val d = withContext(IO) {
                val data = mutableListOf<List<String>>()
                for(index in 1..20) {
                    val col = mutableListOf<String>()
                    for(colIndex in 0..4) {
                        col.add("$index-$colIndex")
                    }
                    data.add(col)
                }
                data
            }

            notifyItem(d)
            Timber.d("end")
        }



    }

}