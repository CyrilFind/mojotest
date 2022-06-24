package io.cyrilfind.mojotest

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import kotlin.math.roundToInt

/**
 * TODO: document your custom view class.
 */
class OverlayView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    var overlay: Overlay = Overlay(0f, 0f, backgroundColor = "#FF00FF")

    private fun Canvas.draw(overlay: Overlay) {
        // TODO: consider storing these as member variables to reduce
        // allocations per draw cycle.
        val paddingLeft = paddingLeft
        val paddingTop = paddingTop
        val paddingRight = paddingRight
        val paddingBottom = paddingBottom

        val contentWidth = width - paddingLeft - paddingRight
        val contentHeight = height - paddingTop - paddingBottom

        drawRect(
            Rect(
                paddingLeft,
                paddingTop,
                paddingLeft + (contentWidth * overlay.width).roundToInt(),
                paddingTop + (contentHeight * overlay.height).roundToInt()
            ),
            Paint().apply {
                color = Color.parseColor(overlay.backgroundColor);
                style = Paint.Style.FILL
            }
        )
        overlay.children.forEach { child -> draw(child) }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.draw(overlay)
    }
}
