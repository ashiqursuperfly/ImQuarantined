package com.imquarantined.ui.base

import android.content.Context
import android.view.View
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.widget.RecyclerView

/* Created by ashiq.buet16 **/

abstract class BaseViewHolder<Item, Callback> internal constructor(private val root: View)
    : RecyclerView.ViewHolder(root), View.OnClickListener {

    var mContext: Context = root.context

    var mItem: Item? = null
    var mSelected: Boolean = false
    var mCallback: Callback? = null

    abstract fun onBind()

    open fun onBind(isSelected: Boolean) {}

    fun bind(item: Item?, isSelected: Boolean = false, callback: Callback?) {
        mItem = item
        mSelected = isSelected
        mCallback = callback

        onBind()
    }

    override fun onClick(v: View?) { }

    fun setClickListener(vararg views: View) {
        views.forEach { view -> view.setOnClickListener(this) }
    }

    //responsible for the item details of recycler-view-selection
    open fun getItemDetails(): ItemDetailsLookup.ItemDetails<Item> {
        return BaseItemDetails(adapterPosition, mItem)
    }
}