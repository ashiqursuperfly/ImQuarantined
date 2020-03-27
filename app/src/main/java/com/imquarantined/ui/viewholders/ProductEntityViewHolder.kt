//package com.stylinecollection.opflex.ui.viewholders
//
//import android.view.View
//import androidx.core.content.ContextCompat
//import com.stylinecollection.opflex.R
//import com.imquarantined.data.Const
//import com.stylinecollection.opflex.data.ProductEntity
//import com.stylinecollection.opflex.databinding.RowProductBinding
//import com.imquarantined.ui.base.BaseViewHolder
//import com.imquarantined.ui.callbacks.OnClickProductCallback
//
///* Created by ashiq.buet16 **/
//
//class ProductEntityViewHolder(
//    private val binding: RowProductBinding
//) : BaseViewHolder<ProductEntity, OnClickProductCallback>(binding.root) {
//
//    override fun onBind() {
//
//        val productId = "#${mItem?.id}"
//        binding.tvProductId.text = productId
//        binding.tvStatus.text = mItem?.status
//        if (mItem?.status!!.startsWith(Const.ValidationStatus.OK)) {
//            binding.tvStatus.setTextColor(ContextCompat.getColor(mContext, R.color.colorGreen))
//        } else if (mItem?.status!!.startsWith(Const.ValidationStatus.EXTRA) || mItem?.status!!.startsWith(
//                Const.ValidationStatus.QTY_MISMATCH.split("###")[0]
//            )
//        ) {
//            binding.tvStatus.setTextColor(ContextCompat.getColor(mContext, R.color.colorRed))
//        }else if(mItem?.status!!.startsWith(Const.ValidationStatus.PENDING)){
//            binding.tvStatus.setTextColor(ContextCompat.getColor(mContext, R.color.color2F2F2F))
//        }
//
//        binding.tvQuantity.text = mItem?.quantity.toString()
//
//
//        setClickListener(binding.container)
//
//
//    }
//
//    override fun onClick(v: View?) {
//        super.onClick(v)
//
//        if (v == binding.container) {
//            mCallback?.onClickProduct(mItem!!)
//        }
//    }
//}
