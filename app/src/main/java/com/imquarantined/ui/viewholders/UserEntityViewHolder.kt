package com.imquarantined.ui.viewholders

import android.view.View
import com.imquarantined.data.UserEntity
import com.imquarantined.databinding.RowUserBinding
import com.imquarantined.ui.base.BaseViewHolder
import com.imquarantined.ui.callbacks.OnClickUserCallback
import com.imquarantined.util.helper.load

/* Created by ashiq.buet16 **/

class UserEntityViewHolder(
    private val binding: RowUserBinding
) : BaseViewHolder<UserEntity, OnClickUserCallback>(binding.root) {

    override fun onBind() {

        val pos = "${mItem?.position}."
        binding.tvPosition.text = pos
        binding.tvUserName.text = mItem?.userName
        binding.tvScore.text = mItem?.points.toString()
        binding.tvUserName.text = mItem?.userName
        binding.imgUser.load(mItem?.imageUrl)

        setClickListener(binding.container)


    }

    override fun onClick(v: View?) {
        super.onClick(v)

        if (v == binding.container) {
            mCallback?.onClickUser(mItem!!)
        }
    }
}