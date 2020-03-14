package com.oranle.es.module.ui.examinee

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import com.oranle.es.module.base.BaseAdapter
import com.oranle.es.module.base.BaseRecycleViewModel
import com.oranle.es.module.base.BaseViewHolder

abstract class RecyclerWithHeaderAdapter<E, VDB : ViewDataBinding, VM : BaseRecycleViewModel<E>>(
    vm: VM
) : BaseAdapter<E, VDB, VM>(vm) {

    private val headerViewList: ArrayList<VDB> = ArrayList<VDB>()
    private val footerViewList: ArrayList<VDB> = ArrayList<VDB>()

    private val TYPE_NORMAL = 1
    private val TYPE_HEADER = 2
    private val TYPE_FOOTER = 3

    override fun getItemViewType(position: Int) =
        when {
            position < headerViewList.size -> {
                TYPE_HEADER
            }
            position < (itemCount + headerViewList.size) -> {
                TYPE_NORMAL
            }
            else -> {
                TYPE_FOOTER
            }
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<VDB> {
        if (viewType == TYPE_NORMAL) {
            return super.onCreateViewHolder(parent, viewType)
        } else if (viewType == TYPE_HEADER) {
            return BaseViewHolder(headerViewList[0])
        } else {
            return BaseViewHolder(footerViewList[0])
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder<VDB>, position: Int) {
        val itemViewType = getItemViewType(position)
        if (itemViewType == TYPE_NORMAL) {
            super.onBindViewHolder(holder, position)
        }
    }

    fun addHeader(viewDataBinding: VDB) {
        headerViewList.add(viewDataBinding)
        notifyDataSetChanged()
    }

    fun addFooter(viewDataBinding: VDB) {
        footerViewList.add(viewDataBinding)
        notifyDataSetChanged()
    }

}