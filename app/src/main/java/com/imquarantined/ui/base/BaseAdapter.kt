package com.imquarantined.ui.base

import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.widget.RecyclerView

/* Created by ashiq.buet16 **/

abstract class BaseAdapter<Item>: RecyclerView.Adapter<BaseViewHolder<*, *>>(), Filterable {

    private val mItems: ArrayList<Item?> = ArrayList()
    private val mFilteredItems: ArrayList<Item?> = ArrayList()

    private lateinit var mSelectionTracker: SelectionTracker<Item?>

    private var mCallback: Any? = null

    object ViewType {
        const val LOADER = 1
        const val ITEM = 2
    }

    fun setItems(items: ArrayList<Item?>) {
        mItems.clear()
        mFilteredItems.clear()

        mItems.addAll(items)
        mFilteredItems.addAll(items)

        notifyDataSetChanged()
    }

    fun addItems(items: ArrayList<Item?>, shouldUpdateUi: Boolean = true) {
        if(items.isEmpty()) return

        val oldPosition = mFilteredItems.size

        mItems.addAll(items)
        mFilteredItems.addAll(items)

        if(shouldUpdateUi) notifyItemRangeInserted(oldPosition, mFilteredItems.size)
    }

    fun addItems(vararg items: Item?, shouldUpdateUi: Boolean = true) {
        if(items.isNullOrEmpty()) return

        val oldPosition = mFilteredItems.size

        mItems.addAll(items)
        mFilteredItems.addAll(items)

        if(shouldUpdateUi) notifyItemRangeInserted(oldPosition, mFilteredItems.size)
    }

    fun getItems(): ArrayList<Item?> {
        return mFilteredItems
    }

    fun getItemAt(position: Int): Item? {
        if(position < 0 || mFilteredItems.size < position) return null

        return mFilteredItems[position]
    }

    fun addItem(item: Item?) {
        if(mItems.indexOf(item) == -1) {
            mItems.add(item)
            mFilteredItems.add(item)

            notifyItemInserted(itemCount)
            return
        }

        updateItem(item)
    }

    fun updateItem(item: Item?) {
        var index = mItems.indexOf(item)
        if(index == -1) return

        mItems[index] = item

        index = mFilteredItems.indexOf(item)
        if(index == -1) return

        mFilteredItems[index] = item
        notifyItemChanged(index)
    }

    abstract fun updateItems(items: ArrayList<Item?>)

    fun removeItem(item: Item?) {
        if(!mItems.remove(item)) return

        val index = mFilteredItems.indexOf(item)
        if(mFilteredItems.remove(item)) notifyItemRemoved(index)
    }

    fun removeItems(vararg items: Item?) {
        if(items.isNullOrEmpty()) return

        if(!mItems.removeAll(items)) return

        items.forEach { item ->
            val index = mFilteredItems.indexOf(item)
            if(mFilteredItems.remove(item)) notifyItemRemoved(index)
        }
    }

    fun removeAll() {
        mItems.clear()
        mFilteredItems.clear()

        notifyDataSetChanged()
    }

    fun setSelectionTracker(tracker: SelectionTracker<Item?>) {
        mSelectionTracker = tracker
    }

    fun getSelectionTracker(): SelectionTracker<Item?> {
        return mSelectionTracker
    }

    fun setCallback(callback: Any) {
        mCallback = callback
    }

    override fun getItemCount(): Int {
        return mFilteredItems.size
    }

    @Suppress("UNCHECKED_CAST")
    override fun onBindViewHolder(holder: BaseViewHolder<*, *>, position: Int) {

        val item = getItemAt(position)
        if(!::mSelectionTracker.isInitialized) {
            (holder as BaseViewHolder<Item, Any>).bind(item, callback = mCallback)
            return
        }

        mSelectionTracker.let {
            (holder as BaseViewHolder<Item, Any>).bind(
                mFilteredItems[position], it.isSelected(item), mCallback
            )
        }
    }

    override fun getFilter(): Filter? {
        return null
    }
}
