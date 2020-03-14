package com.oranle.es.module.base

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<E, VDB : ViewDataBinding, VM : BaseRecycleViewModel<E>>(
    private val viewModel: VM,
    diffCallback: DiffUtil.ItemCallback<E> = DefaultDiff<E>()
) : ListAdapter<E, BaseViewHolder<VDB>>(diffCallback) {

    init {
        this.submitList(viewModel.items.value)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<VDB> {
        return BaseViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                layoutRes,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: BaseViewHolder<VDB>, position: Int) {
        currPosition = position
        val binding = holder.binding
        doBindViewHolder(binding, getItem(position), viewModel)
        binding.executePendingBindings()
    }

    private var currPosition: Int = 0
    fun getPosition() = currPosition + 1

    abstract fun doBindViewHolder(binding: VDB, item: E, viewModel: VM)

    abstract val layoutRes: Int
}


class BaseViewHolder<out Binding : ViewDataBinding>(
    val binding: Binding
) : RecyclerView.ViewHolder(binding.root)

class DefaultDiff<E> : DiffUtil.ItemCallback<E>() {
    override fun areItemsTheSame(oldItem: E, newItem: E) = oldItem == newItem

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: E, newItem: E) = (oldItem == newItem)
}

