//package com.stylinecollection.opflex.ui.dialog
//
//import android.content.Context
//import android.text.TextUtils
//import android.view.LayoutInflater
//import androidx.appcompat.app.AlertDialog
//import androidx.databinding.DataBindingUtil
//import com.stylinecollection.opflex.R
//import com.stylinecollection.opflex.databinding.DialogUpdateQtyBinding
//import com.imquarantined.util.helper.DialogUtil
//import com.imquarantined.util.helper.Toaster
//
//object UpdateQtyDialog {
//
//    fun showChangeQuantityDialog(
//        context: Context,
//        callback: ChangeQuantityCallback
//    ) {
//
//        val binding = DataBindingUtil.inflate<DialogUpdateQtyBinding>(
//            LayoutInflater.from(context), R.layout.dialog_update_qty, null, false
//        )
//
//        val counterDialog = DialogUtil.createCustomDialog(context, binding.root)
//
//        binding.btnUpdateQty.setOnClickListener {
//            val qty = binding.etQty.text
//            if(TextUtils.isEmpty(qty) || qty.toString().toInt() < 1) {
//                Toaster.showToast("Enter Valid Quantity")
//            } else {
//                counterDialog.dismiss()
//                callback.onChange(qty.toString().trim().toInt())
//            }
//        }
//
//        counterDialog.show()
//    }
//
//    abstract class ChangeQuantityCallback {
//        abstract fun onChange(qty: Int)
//    }
//
//}