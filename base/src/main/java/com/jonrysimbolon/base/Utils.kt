package com.jonrysimbolon.base

import android.widget.ImageView
import androidx.annotation.DrawableRes
import coil.load
import coil.transform.RoundedCornersTransformation

fun setImageUrl(
    url: String,
    imageView: ImageView,
    crossFade: Boolean = true,
    @DrawableRes
    placeHolderImage: Int,
    @DrawableRes
    errorImage: Int,
    roundCorner: Float = 0f,
) {
    imageView.load(url) {
        crossfade(crossFade)
        placeholder(placeHolderImage)
        error(errorImage)
        transformations(
            RoundedCornersTransformation(
                roundCorner,
                roundCorner,
                roundCorner,
                roundCorner
            )
        )
    }
}