//package com.stylinecollection.opflex.ui.adapters
//
//import android.view.LayoutInflater
//import android.view.ViewGroup
//import androidx.databinding.DataBindingUtil
//import com.imquarantined.R
//import com.stylinecollection.opflex.R
//import com.stylinecollection.opflex.data.ProductEntity
//import com.stylinecollection.opflex.databinding.RowProductBinding
//import com.imquarantined.ui.base.BaseAdapter
//import com.imquarantined.ui.base.BaseViewHolder
//import com.stylinecollection.opflex.ui.viewholders.ProductEntityViewHolder
//
///* Created by ashiq.buet16 **/
//
//class ProductsAdapter : BaseAdapter<ProductEntity>() {
//
//    override fun updateItems(items: ArrayList<ProductEntity?>) {
//        removeAll()
//        addItems(items, false)
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*, *> {
//        val inflater = LayoutInflater.from(parent.context)
//
//        val binding = DataBindingUtil.inflate<RowProductBinding>(
//            inflater, R.layout.row_product
//            , parent, false
//        )
//        return ProductEntityViewHolder(binding)
//    }
//
//}
