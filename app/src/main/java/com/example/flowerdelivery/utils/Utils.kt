package com.example.flowerdelivery.utils

import android.os.SystemClock
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.example.flowerdelivery.R
import java.text.DecimalFormat

const val BLOCK_IN_MILLIS = 500
const val FADE_ANIMATION_DURATION = 500

fun ImageView.loadImageFromUrl(url: String?) {
    val options = RequestOptions()
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .error(R.drawable.no_image)


    Glide.with(context)
        .setDefaultRequestOptions(options)
        .load(url)
        .transition(DrawableTransitionOptions.withCrossFade(FADE_ANIMATION_DURATION))
        .into(this)
}

@BindingAdapter("android:imageUrl")
fun loadImage(view: ImageView, url: String?) {
    view.loadImageFromUrl(url)
}

@BindingAdapter("android:onSingleClick")
fun View.safeClick(listener: View.OnClickListener) {
    var lastClickTime: Long = 0
    this.setOnClickListener {
        if (SystemClock.elapsedRealtime() - lastClickTime < BLOCK_IN_MILLIS) return@setOnClickListener
        lastClickTime = SystemClock.elapsedRealtime()
        listener.onClick(this)
    }
}

@BindingAdapter("android:distanceInKm")
fun formatToKm(view: TextView, distanceInMeters: Int) {
    view.text = (distanceInMeters / 1000.0).toString().plus(" km")
}

@BindingAdapter("android:isRefreshing")
fun isRefreshing(view: SwipeRefreshLayout, isRefreshing: Boolean) {
    view.isRefreshing = !isRefreshing
}

@BindingAdapter("android:isEnabled")
fun isEnabled(view: SwipeRefreshLayout, isEnabled: Boolean) {
    view.isEnabled = !isEnabled
}