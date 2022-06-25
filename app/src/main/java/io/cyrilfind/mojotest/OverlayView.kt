package io.cyrilfind.mojotest

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.util.Log
import android.view.View

/**
 * TODO: document your custom view class.
 */
class OverlayView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var rects: List<Pair<RectF, Paint>> = emptyList()

    private fun getContentWidth(): Float = (width - paddingLeft - paddingRight).toFloat()
    private fun getContentHeight(): Float = (height - paddingTop - paddingBottom).toFloat()

    fun setOverlay(overlay: Overlay) = post {
        val canvasRect = Rectangle(0f, 0f, getContentWidth(), getContentHeight())
        Log.d("OverlayView", "computing rects in $canvasRect")
        rects = overlay.computeRectangles(canvasRect).map {
            val rectF = RectF(it.left, it.top, it.right, it.bottom)
            val paint = Paint().apply {
                color = Color.parseColor(it.color)
                style = Paint.Style.FILL
            }
            rectF to paint
        }
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        Log.d("OverlayView", "drawing ${rects.size} rects: ${rects.map { it.first }} ")
        rects.forEach { (rect, paint) -> canvas.drawRect(rect, paint) }
    }
}
