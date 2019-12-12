package com.oranle.es.module

import org.junit.Test

import org.junit.Assert.*

class DemoListViewModelTest {

    @Test
    fun start() {
//        var data: MutableList<List<String>> = ArrayList()
        val data = mutableListOf<List<String>>()
        for(index in 1..20) {
            val col = mutableListOf<String>()
            for(colIndex in 0..4) {
                col.add("${index}-$colIndex")
            }
            data.add(col)
        }

        println("$data")
    }
}