package com.imquarantined.ui.viewholders

import android.view.View
import androidx.core.content.ContextCompat
import com.imquarantined.R
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

        mItem?.let {

            val pos = "${it.position}."
            binding.tvPosition.text = pos
            binding.tvUserName.text = it.userName
            binding.tvScore.text = it.points.toString()
            binding.imgUser.load(it.imageUrl)
            val days = "Quarantined: ${it.daysQuarantined} days"
            binding.tvDaysQuarantined.text = days

            if(it.isUser){
                binding.container.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorGreen))
                binding.tvPosition.setTextColor(ContextCompat.getColor(mContext,R.color.colorWhite))
                binding.tvUserName.setTextColor(ContextCompat.getColor(mContext,R.color.colorWhite))
                binding.tvScore.setTextColor(ContextCompat.getColor(mContext,R.color.colorWhite))
                binding.tvDaysQuarantined.setTextColor(ContextCompat.getColor(mContext,R.color.colorWhite))
                binding.tvLabelPoints.setTextColor(ContextCompat.getColor(mContext,R.color.colorWhite))
            }

        }


        setClickListener(binding.container)


    }

    override fun onClick(v: View?) {
        super.onClick(v)

        if (v == binding.container) {
            mCallback?.onClickUser(mItem!!)
        }
    }
}