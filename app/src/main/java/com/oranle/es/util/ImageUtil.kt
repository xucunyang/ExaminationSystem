package com.oranle.es.util

import android.content.res.AssetManager
import android.graphics.BitmapFactory
import android.widget.ImageView
import timber.log.Timber
import java.io.IOException

object ImageUtil {

    @JvmStatic
    fun loadAssetsImage(imageView: ImageView, fileName: String?) {

        if (fileName == null || fileName.isBlank()) {
            Timber.w("load assets image is blank $imageView")
            return
        }

        val assets: AssetManager = imageView.context.assets

        try {
            val input = assets.open("single_choice_imgs/$fileName")

            val stream = BitmapFactory.decodeStream(input)

            imageView.setImageBitmap(stream)

        } catch (e: IOException) {
            e.printStackTrace()
            Timber.w("$e")
        }
    }

}