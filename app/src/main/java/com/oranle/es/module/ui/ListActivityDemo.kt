package com.oranle.es.module.ui

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.oranle.es.R
import com.oranle.es.databinding.ActivityListBinding
import com.oranle.es.databinding.ListItemDetailBinding
import com.oranle.es.module.DemoListViewModel
import com.oranle.es.module.base.BaseActivity
import com.oranle.es.module.base.BaseAdapter
import timber.log.Timber

class ListActivityDemo : BaseActivity<ActivityListBinding>() {

    override val layoutId = R.layout.activity_list

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val listViewModel = ViewModelProviders.of(this).get(DemoListViewModel::class.java)

        dataBinding.vm = listViewModel
        val myAdapter = MyAdapter(listViewModel)
        dataBinding.rv.adapter = myAdapter
        dataBinding.rv.itemAnimator = DefaultItemAnimator()
        dataBinding.rv.layoutManager = LinearLayoutManager(this)
        dataBinding.rv.addItemDecoration(
            DividerItemDecoration(
                this,
                DividerItemDecoration.VERTICAL
            )
        )

        Timber.d("start")

        listViewModel.start()

        listViewModel.items.observe(this, Observer {
            Timber.d("observer changed $it")

            myAdapter.submitList(it)
        })
    }

    class MyAdapter(
        viewModel: DemoListViewModel
    ) : BaseAdapter<List<String>, ListItemDetailBinding, DemoListViewModel>(
        viewModel, Diff()
    ) {
        override fun doBindViewHolder(
            binding: ListItemDetailBinding,
            item: List<String>,
            viewModel: DemoListViewModel
        ) {
            Timber.d("doBindViewHolder ${item[0]}")
            binding.item = item
        }

        override val layoutRes: Int = R.layout.list_item_detail

    }

    class Diff : DiffUtil.ItemCallback<List<String>>() {
        override fun areItemsTheSame(oldItem: List<String>, newItem: List<String>) =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: List<String>, newItem: List<String>) =
            oldItem == newItem
    }

}