package com.imquarantined.util.helper

import android.net.Uri
import android.view.View
import android.widget.ImageView

fun ImageView.load(res: Int) {
    GlideUtil.load(res, this)
}

fun ImageView.load(url: String?) {
    GlideUtil.load(url, this)
}

fun ImageView.loadProfile(url: String?) {
    GlideUtil.load(url, this)
}

fun ImageView.load(url: Uri?) {
    GlideUtil.load(url, this)
}

fun ImageView.loadWithPlatter(url: String?, view: View?) {
    //ImageLoader.loadWithPlatter(url, this, view)
}