package io.cyrilfind.mojotest

import android.graphics.Bitmap
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.RectF

data class Drawing(
    val rect: RectF,
    val paint: Paint,
    val bitmap: Bitmap?,
    val bitmapRect: RectF,
    val clippingRect: Rect?
)
