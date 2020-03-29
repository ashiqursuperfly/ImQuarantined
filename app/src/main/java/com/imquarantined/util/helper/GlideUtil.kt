package com.imquarantined.util.helper

import android.annotation.TargetApi
import android.content.Context
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.os.StatFs
import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.annotation.Nullable
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory
import com.bumptech.glide.module.AppGlideModule
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.imquarantined.R
import timber.log.Timber


/* Created by ashiq.buet16 **/

@GlideModule
class LimitCacheSizeGlideModule : AppGlideModule() {

    override fun applyOptions(context: Context, builder: GlideBuilder) {
        super.applyOptions(context, builder)

        Timber.i("GLIDE: Limiting image cache size to " + DISK_CACHE_SIZE_FOR_SMALL_INTERNAL_STORAGE_MIB + "MiB")
        builder.setDiskCache(
            InternalCacheDiskCacheFactory(
                context,
                DISK_CACHE_SIZE_FOR_SMALL_INTERNAL_STORAGE_MIB.toLong()
            )
        )

    }

    private val totalBytesOfInternalStorage: Long
        get() {
            val stat = StatFs(Environment.getDataDirectory().path)
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                getTotalBytesOfInternalStorageWithStatFs(stat)
            } else {
                getTotalBytesOfInternalStorageWithStatFsPreJBMR2(stat)
            }
        }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    private fun getTotalBytesOfInternalStorageWithStatFs(stat: StatFs): Long {
        return stat.totalBytes
    }

    private fun getTotalBytesOfInternalStorageWithStatFsPreJBMR2(stat: StatFs): Long {
        return stat.blockSizeLong * stat.blockCountLong
    }

    companion object {
        private const val DISK_CACHE_SIZE_FOR_SMALL_INTERNAL_STORAGE_MIB = 50 * 1024 * 1024
    }
}


object GlideUtil {

    fun load(location: Int, imageView: ImageView) {
        val requestOptions = RequestOptions()
            //.timeout(20000)
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            .placeholder(R.drawable.ic_user)
            .error(R.drawable.ic_user)

        GlideApp.with(imageView.context)
            .load(if (location > 0) location else R.drawable.ic_user)
            .apply(requestOptions)
            .into(imageView)
    }

    fun load(location: String?, imageView: ImageView) {
        val requestOptions = RequestOptions()
            //.timeout(20000)
            .diskCacheStrategy(DiskCacheStrategy.ALL).centerInside().fitCenter()
            .placeholder(R.drawable.ic_user)
            .error(R.drawable.ic_user)

        GlideApp.with(imageView.context)
            .load(if (TextUtils.isEmpty(location)) R.drawable.ic_user else location)
            .apply(requestOptions)
            .into(imageView)
    }

    fun load(location: Uri?, imageView: ImageView) {
        val requestOptions = RequestOptions()
            //.timeout(5000)
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            .placeholder(R.drawable.ic_user)
            .error(R.drawable.ic_user)

        GlideApp.with(imageView.context)
            .load(location ?: R.drawable.ic_user)
            .apply(requestOptions)
            .into(imageView)
    }

    fun load(location: String, imageView: ImageView, placeholder: Int) {
        val requestOptions = RequestOptions()
            //.timeout(20000)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .placeholder(placeholder)
            .error(placeholder)

        GlideApp.with(imageView.context)
            .load(if (TextUtils.isEmpty(location)) placeholder else location)
            .apply(requestOptions)
            .into(imageView)
    }

    fun load(location: String?, imageView: ImageView, progressBar: ProgressBar) {
        progressBar.visibility = View.VISIBLE

        val options = RequestOptions()
            //.timeout(20000)
            .dontTransform()
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            .placeholder(R.drawable.ic_user)
            .error(R.drawable.ic_user)

        GlideApp.with(imageView.context)
            .load(if (TextUtils.isEmpty(location)) R.drawable.ic_user else location)
            .apply(options)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    @Nullable e: GlideException?, model: Any, target: Target<Drawable>,
                    isFirstResource: Boolean
                ): Boolean {
                    progressBar.visibility = View.GONE
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable, model: Any, target: Target<Drawable>,
                    dataSource: DataSource, isFirstResource: Boolean
                ): Boolean {
                    progressBar.visibility = View.GONE
                    return false
                }
            })
            .apply(options)
            .into(imageView)
    }
}
