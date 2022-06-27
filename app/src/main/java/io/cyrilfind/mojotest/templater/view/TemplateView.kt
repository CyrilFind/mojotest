package io.cyrilfind.mojotest.templater.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import kotlin.math.abs
import kotlin.math.roundToInt

class TemplateView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    internal var drawings = listOf<Drawing>()
        set(value) {
            field = value
            postInvalidate()
        }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawings.forEach { drawing ->
            with(drawing) {
                canvas.drawRect(rect, paint)
                bitmapWrapper?.let { canvas.drawBitmap(it.bitmap, it.src, it.dest, null) }
            }
        }
    }

    // Handle dragging Canvas elements

    private var draggingIndex = -1
    private var draggingStartX = 0f
    private var draggingStartY = 0f

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                draggingIndex = drawings.indexOfLast {
                    it.rect.contains(event.x, event.y) && it.bitmapWrapper?.src != null
                }
                if (draggingIndex == -1) return true
                val previousOffsetX = drawings[draggingIndex].bitmapWrapper!!.src!!.left
                val previousOffsetY = drawings[draggingIndex].bitmapWrapper!!.src!!.top

                draggingStartX = event.x + previousOffsetX
                draggingStartY = event.y + previousOffsetY
                return true
            }
            MotionEvent.ACTION_MOVE -> {
                if (draggingIndex == -1) return true
                val drawing = drawings[draggingIndex]
                val rectW = drawing.rect.width()
                val rectH = drawing.rect.height()
                val bitmapW = drawing.bitmapWrapper!!.width
                val bitmapH = drawing.bitmapWrapper.height
                val offsetX = -(event.x - draggingStartX).coerceIn(-abs(rectW - bitmapW), 0f)
                val offsetY = -(event.y - draggingStartY).coerceIn(-abs(rectH - bitmapH), 0f)


                drawings[draggingIndex].bitmapWrapper!!.src!!.set(
                    offsetX.roundToInt(),
                    offsetY.roundToInt(),
                    (offsetX + rectW).roundToInt(),
                    (offsetY + rectH).roundToInt()
                )

                postInvalidate()
                return true
            }
        }
        return super.onTouchEvent(event)
    }
}
