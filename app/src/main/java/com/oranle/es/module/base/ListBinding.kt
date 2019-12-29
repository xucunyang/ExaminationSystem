package com.oranle.es.module.base

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import timber.log.Timber

@BindingAdapter("app:items")
fun <E> setItems(listView: RecyclerView, items: List<E>) {

    Timber.d("set items $items ${listView.adapter}")

    (listView.adapter as ListAdapter<E, *>).submitList(items)
}