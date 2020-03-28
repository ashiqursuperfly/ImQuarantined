package com.stylinecollection.util.helper

import android.content.Context
import android.util.DisplayMetrics


/*
*  ****************************************************************************
*  * Created by : arhan on 2019-05-26 at 12:15.
*  * Email : ashik.pstu.cse@gmail.com
*  *
*  * This class is for: 
*  * 1.
*  * 2.
*  * 3.
*  * 
*  * Last edited by : arhan on 2019-05-26.
*  *
*  * Last Reviewed by : <Reviewer Name> on <mm/dd/yy>
*  ****************************************************************************
*/

object PixelUtil {

    fun dpToPx(context: Context, dp: Int): Int {
        return Math.round(dp * getPixelScaleFactor(context))
    }

    fun pxToDp(context: Context, px: Int): Int {
        return Math.round(px / getPixelScaleFactor(context))
    }

    private fun getPixelScaleFactor(context: Context): Float {
        val displayMetrics = context.resources.displayMetrics

        return displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT
    }
}