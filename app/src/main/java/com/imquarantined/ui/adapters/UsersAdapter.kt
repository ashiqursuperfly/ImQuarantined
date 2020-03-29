package com.imquarantined.ui.adapters


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.imquarantined.R
import com.imquarantined.data.UserEntity
import com.imquarantined.databinding.RowUserBinding
import com.imquarantined.ui.base.BaseAdapter
import com.imquarantined.ui.base.BaseViewHolder
import com.imquarantined.ui.viewholders.UserEntityViewHolder

/* Created by ashiq.buet16 **/

class UsersAdapter : BaseAdapter<UserEntity>() {

    override fun updateItems(items: ArrayList<UserEntity?>) {
        removeAll()
        addItems(items, false)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*, *> {
        val inflater = LayoutInflater.from(parent.context)

        val binding = DataBindingUtil.inflate<RowUserBinding>(
            inflater, R.layout.row_user
            , parent, false
        )
        return UserEntityViewHolder(binding)
    }

}
