package io.cyrilfind.mojotest

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.core.graphics.toRect
import com.bumptech.glide.Glide
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Float.min
import kotlin.math.abs
import kotlin.math.log
import kotlin.math.roundToInt

class OverlayView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private val job = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + job)
    private val ioDispatcher = Dispatchers.IO
    private val defaultDispatcher = Dispatchers.Default

    private var drawings = mutableListOf<Drawing>()

    private val glide = Glide.with(context)
    var overlay: Overlay = Overlay(1f, 1f, "#FFFFFF")
        set(value) {
            field = value
            computeOverlay()
        }

    private fun computeOverlay() {
        uiScope.launch(defaultDispatcher) {
            val contentWidth = (measuredWidth - paddingLeft - paddingRight).toFloat()
            val contentHeight = (measuredHeight - paddingTop - paddingBottom).toFloat()

            val canvasRect = Rectangle(0f, 0f, contentWidth, contentHeight)
            overlay.computeRectangles(canvasRect).forEach {
                val rect = RectF(it.left, it.top, it.right, it.bottom)
                val paint = Paint().apply {
                    color = Color.parseColor(it.color)
                    style = Paint.Style.FILL
                }

                withContext(ioDispatcher) {
                    val bitmap =
                        it.mediaUrl?.let { url -> glide.asBitmap().load(url).submit().get() }
                    var src: RectF? = null
                    var dest: RectF = rect
                    if (bitmap != null) {
                        when (it.mediaContentMode) {
                            MediaContentMode.FIT -> {
                                val scale = min(
                                    rect.width() / bitmap.width,
                                    rect.height() / bitmap.height
                                )
                                dest = RectF(
                                    rect.left,
                                    rect.top,
                                    rect.left + bitmap.width * scale,
                                    rect.top + bitmap.height * scale
                                )
                            }
                            else -> {
                                // fill by default
                                src = RectF(0f, 0f, rect.width(), rect.height())
                            }

                        }
                    }

                    drawings += Drawing(rect, paint, bitmap, src?.toRect(), dest)
                }
            }
            postInvalidate()
        }
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        computeOverlay()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawings.forEach { drawing ->
            with(drawing) {
                canvas.drawRect(rect, paint)
                if (bitmap != null) canvas.drawBitmap(bitmap, src, dest, null)
            }
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        job.cancel()
    }

    private var draggingIndex = -1
    private var draggingStartX = 0f
    private var draggingStartY = 0f

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                draggingIndex = drawings.indexOfLast {
                    it.rect.contains(event.x, event.y) && it.src != null && it.bitmap != null
                }
                if (draggingIndex == -1) return true
                val previousOffsetX = drawings[draggingIndex].src!!.left
                val previousOffsetY = drawings[draggingIndex].src!!.top
                
                draggingStartX = event.x + previousOffsetX
                draggingStartY = event.y + previousOffsetY
                return true
            }
            MotionEvent.ACTION_MOVE -> {
                if (draggingIndex == -1) return true
                val drawing = drawings[draggingIndex]
                val rectW = drawing.rect.width()
                val rectH = drawing.rect.height()
                val bitmapW = drawing.bitmap!!.width
                val bitmapH = drawing.bitmap.height
                val offsetX = -(event.x - draggingStartX).coerceIn(-abs(rectW - bitmapW), 0f)
                val offsetY = -(event.y - draggingStartY).coerceIn(-abs(rectH - bitmapH), 0f)
                
                
                drawings[draggingIndex].src!!.set(
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
