package com.imquarantined.ui.base

import androidx.recyclerview.selection.ItemDetailsLookup

/* Created by ashiq.buet16 **/

class BaseItemDetails<Item>(
    private val adapterPosition: Int,
    private val selectionKey: Item?
) : ItemDetailsLookup.ItemDetails<Item>() {

    override fun getPosition(): Int {
        return adapterPosition
    }

    override fun getSelectionKey(): Item? {
        return selectionKey
    }
}