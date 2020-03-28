package com.imquarantined.util.helper

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView


class GridItemDecoration(
    private val spacePx: Int,
    private val includeEdge: Boolean = true
): RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {

        if(parent.layoutManager !is GridLayoutManager) return

        val layoutManager = parent.layoutManager as GridLayoutManager
        val spanCount = layoutManager.spanCount
        val orientation = layoutManager.orientation

        val position = parent.getChildAdapterPosition(view) // item position
        val column = position % spanCount // item column

        if(orientation == GridLayoutManager.HORIZONTAL) {
            if (includeEdge) {
                outRect.top = spacePx - column * spacePx / spanCount // spacePx - column * ((1f / spanCount) * spacePx)
                outRect.bottom = (column + 1) * spacePx / spanCount // (column + 1) * ((1f / spanCount) * spacePx)

                if (position < spanCount) { // left edge
                    outRect.left = spacePx
                }
                outRect.right = spacePx // item right
            } else {
                outRect.top = column * spacePx / spanCount // column * ((1f / spanCount) * spacePx)
                outRect.bottom = spacePx - (column + 1) * spacePx / spanCount // spacePx - (column + 1) * ((1f / spanCount) * spacePx)
                if (position >= spanCount) {
                    outRect.left = spacePx // item top
                }
            }

        } else {
            if (includeEdge) {
                outRect.left = spacePx - column * spacePx / spanCount // spacePx - column * ((1f / spanCount) * spacePx)
                outRect.right = (column + 1) * spacePx / spanCount // (column + 1) * ((1f / spanCount) * spacePx)

                if (position < spanCount) { // top edge
                    outRect.top = spacePx
                }
                outRect.bottom = spacePx // item bottom
            } else {
                outRect.left = column * spacePx / spanCount // column * ((1f / spanCount) * spacePx)
                outRect.right = spacePx - (column + 1) * spacePx / spanCount // spacePx - (column + 1) * ((1f / spanCount) * spacePx)
                if (position >= spanCount) {
                    outRect.top = spacePx // item top
                }
            }
        }

    }


}