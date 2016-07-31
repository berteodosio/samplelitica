package com.berteodosio.samplelitica.toolkit.extension

import android.widget.ImageView
import com.bumptech.glide.Glide

// this is awesome
fun ImageView.loadImage(url: String) {
    Glide.with(context).load(url).centerCrop().into(this)
}