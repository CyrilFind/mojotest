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

    private val contentWidth: Int
        get() = width - paddingLeft - paddingRight
    private val contentHeight: Int
        get() = height - paddingTop - paddingBottom

    fun setOverlay(overlay: Overlay) {
        rects =
            computeRects(overlay, RectF(0f, 0f, contentWidth.toFloat(), contentHeight.toFloat()))
        Log.d("OverlayView", "drawing ${rects.size} rects: ${rects.map { it.first }} ")
        invalidate()
    }

    private fun computeRects(
        overlay: Overlay,
        parentRect: RectF,
    ): List<Pair<RectF, Paint>> {
        val (rect, paint) = computeRect(overlay, parentRect)
        val list = mutableListOf(rect to paint)
        
        val paddedRect = RectF(rect)
        val width = rect.width()
        val height = rect.height()
       
        paddedRect.apply {
            left += overlay.padding * width
            top += overlay.padding * height
            right -= overlay.padding * width
            bottom -= overlay.padding * height
        }
        Log.d("OverlayView", "parent: $parentRect, padded: $paddedRect ")

        overlay.children.forEach { child ->
            list += computeRects(child, paddedRect)
        }

        return list
    }

    private fun computeRect(overlay: Overlay, parentRect: RectF): Pair<RectF, Paint> {
        val parentWidth = parentRect.width()
        val parentHeight = parentRect.height()
        val width = overlay.widthRatio * parentWidth
        val height = overlay.heightRatio * parentHeight
        val x = overlay.xRatio * parentWidth
        val y = overlay.yRatio * parentHeight

        val left = when (overlay.anchorX) {
            "left" -> parentRect.left + x
            "right" -> parentRect.left + x - width
            "center" -> parentRect.left + x - (width / 2)
            else -> 0f
        }

        val top = when (overlay.anchorY) {
            "bottom" -> parentRect.top + y
            "top" -> parentRect.top + y - height
            "center" -> parentRect.top + y - (height / 2)
            else -> 0f
        }

        val right = when (overlay.anchorX) {
            "left" -> parentRect.left + x + width
            "right" -> parentRect.left + x
            "center" -> parentRect.left + x + (width / 2)
            else -> 0f
        }
        
        val bottom = when (overlay.anchorY) {
            "bottom" -> parentRect.top + y + height
            "top" -> parentRect.top + y
            "center" -> parentRect.top + y + (height / 2)
            else -> 0f
        }

        val rect = RectF(left, top, right, bottom)

        val paint = Paint().apply {
            color = Color.parseColor(overlay.backgroundColor);
            style = Paint.Style.FILL
        }
        return rect to paint
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        rects.forEach { (rect, paint) -> canvas.drawRect(rect, paint) }
    }
}
