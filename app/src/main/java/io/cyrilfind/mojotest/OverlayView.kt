package io.cyrilfind.mojotest

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View

/**
 * TODO: document your custom view class.
 */
class OverlayView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    var overlay: Overlay = Overlay(0f, 0f, backgroundColor =  "#FF00FF")

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // TODO: consider storing these as member variables to reduce
        // allocations per draw cycle.
        val paddingLeft = paddingLeft
        val paddingTop = paddingTop
        val paddingRight = paddingRight
        val paddingBottom = paddingBottom

        val contentWidth = width - paddingLeft - paddingRight
        val contentHeight = height - paddingTop - paddingBottom

        with(overlay) {
            canvas.drawRect(
                Rect(
                    paddingLeft,
                    paddingTop,
                    paddingLeft + contentWidth,
                    paddingTop + contentHeight
                ),
                Paint().apply {
                    color = Color.parseColor(backgroundColor);
                    style = Paint.Style.FILL
                }

            )
        }
    
    }
}