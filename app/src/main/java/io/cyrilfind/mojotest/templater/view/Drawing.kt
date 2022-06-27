package io.cyrilfind.mojotest.templater.view

import android.graphics.Paint
import android.graphics.RectF
import io.cyrilfind.mojotest.templater.view.BitmapWrapper

data class Drawing(
    val rect: RectF,
    val paint: Paint,
    val bitmapWrapper: BitmapWrapper?
)