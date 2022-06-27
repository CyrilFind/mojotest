package io.cyrilfind.mojotest.templater.view

import android.graphics.Bitmap
import android.graphics.Rect
import android.graphics.RectF

data class BitmapWrapper(
    val bitmap: Bitmap,
    val src: Rect?,
    val dest: RectF
) {
    val width: Int get() = bitmap.width
    val height: Int get() = bitmap.height
}