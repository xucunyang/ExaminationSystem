package com.oranle.es.module.ui.administrator.fragment

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.oranle.es.R
import com.oranle.es.data.entity.User
import com.oranle.es.data.repository.DBRepository
import com.oranle.es.databinding.FragmentLoginManagerBinding
import com.oranle.es.databinding.ItemLoginManagerBinding
import com.oranle.es.module.base.*
import com.oranle.es.module.ui.administrator.AddPersonalActivity
import com.oranle.es.module.ui.administrator.fragment.model.LoginManagerViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.withContext
import timber.log.Timber

class LoginManagerFragment : BaseFragment<FragmentLoginManagerBinding>() {

    override val layoutId: Int
        get() = R.layout.fragment_login_manager

    lateinit var viewModel: LoginManagerViewModel
    lateinit var loginManagerAdapter: LoginManagerAdapter
    val userList = mutableListOf<User>()
    val screenUserList = mutableListOf<User>()
    override fun initView() {

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = getViewModel()
        loginManagerAdapter = LoginManagerAdapter(viewModel)

        dataBinding?.apply {
            vm = viewModel
            recyclerView.adapter = loginManagerAdapter
            recyclerView.layoutManager = LinearLayoutManager(activity)

            checkPerson.setOnClickListener { onCheckAll() }
            tvDelete.setOnClickListener { delete() }
        }

        viewModel.items.observe(viewLifecycleOwner, Observer {
            Timber.d("observe ${it.size} -- $it")
            loginManagerAdapter = LoginManagerAdapter(viewModel!!)
            loginManagerAdapter!!.submitList(userList)
        })

        viewModel.load()
    }


    override fun onResume() {
        super.onResume()
        viewModel.load()
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        Timber.d("onHiddenChanged $hidden")
        if (!hidden) {
            viewModel.load()
        }
    }

    fun onCheckAll() {
        if (dataBinding!!.checkPerson.isChecked) {
            screenUserList.addAll(userList)
            for (i in screenUserList.indices) {
                screenUserList[i].selected = true
            }
            loginManagerAdapter.submitList(screenUserList)
            loginManagerAdapter.notifyDataSetChanged()
        } else {
            for (i in screenUserList.indices) {
                screenUserList[i].selected = false
            }
            loginManagerAdapter.submitList(screenUserList)
            loginManagerAdapter.notifyDataSetChanged()
            screenUserList.clear()
        }
    }

    fun delete() {
        GlobalScope.launchWithLifecycle(this@LoginManagerFragment, UI) {
            for (i in screenUserList.indices) {
                val size = withContext(IO) {
                    DBRepository.getDB().getUserDao().deleteUser(screenUserList[i])
                }
            }

            viewModel.load()
        }
    }


    inner class LoginManagerAdapter(viewModel: LoginManagerViewModel) :
        BaseAdapter<User, ItemLoginManagerBinding, LoginManagerViewModel>(viewModel) {
        override fun doBindViewHolder(
            binding: ItemLoginManagerBinding,
            item: User,
            viewModel: LoginManagerViewModel
        ) {
            binding.user = item
            binding.viewModel = viewModel
            binding.lifecycleOwner = this@LoginManagerFragment
            binding.checkbox.isChecked = item.selected
            binding.checkbox.setOnClickListener {
                if (binding.checkbox.isChecked) {
                    screenUserList.add(item)
                } else {
                    screenUserList.remove(item)
                }
            }
            binding.tvDelete.setOnClickListener {
                GlobalScope.launchWithLifecycle(this@LoginManagerFragment, UI) {
                    val size = withContext(IO) {
                        DBRepository.getDB().getUserDao().deleteUser(item)
                    }

                    if (size > 0) {
                        this@LoginManagerFragment.viewModel!!.load()
                        toast("删除成功")
                    } else
                        toast("删除失败")
                }
            }

            binding.tvEdit.setOnClickListener {
                var intent = Intent(activity, AddPersonalActivity::class.java)
                intent.putExtra("user", item)
                startActivity(intent)
            }
        }

        override val layoutRes = R.layout.item_login_manager

    }
}
