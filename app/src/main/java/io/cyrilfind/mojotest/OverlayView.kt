package io.cyrilfind.mojotest

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.RectF
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.core.graphics.toRect
import com.bumptech.glide.Glide
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Float.min

class OverlayView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val job = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + job)

    private val ioDispatcher = Dispatchers.IO
    private val defaultDispatcher = Dispatchers.Default

    private var drawings = mutableListOf<Drawing>()

    private val glide = Glide.with(context)
    var overlay: Overlay? = null

    private fun computeOverlay() {
        val overlay = overlay ?: return
        uiScope.launch(defaultDispatcher) {
            val contentWidth = (measuredWidth - paddingLeft - paddingRight).toFloat()
            val contentHeight = (measuredHeight - paddingTop - paddingBottom).toFloat()

            val canvasRect = Rectangle(0f, 0f, contentWidth, contentHeight)
            Log.d("OverlayView", "computing rects in $canvasRect")

            overlay.computeRectangles(canvasRect).forEach {
                val rect = RectF(it.left, it.top, it.right, it.bottom)
                val paint = Paint().apply {
                    color = Color.parseColor(it.color)
                    style = Paint.Style.FILL
                }
                withContext(ioDispatcher) {
                    val bitmap = it.mediaUrl?.let { url ->
                        glide.asBitmap().load(url).submit().get()
                    }
                    var clippingRect: Rect? = null
                    var bitmapRect: RectF = rect
                    when {
                        bitmap == null -> null
                        it.mediaContentMode == MediaContentMode.FILL -> {
                            clippingRect = RectF(0f, 0f, rect.width(), rect.height()).toRect()
                        }
                        it.mediaContentMode == MediaContentMode.FIT -> {
                            val scale = min(
                                rect.width() / bitmap.width,
                                rect.height() / bitmap.height
                            )
                            bitmapRect = RectF(
                                rect.left,
                                rect.top,
                                rect.left + (scale * bitmap.width),
                                rect.top + (scale * bitmap.height)
                            )
                        }
                    }
                    drawings += Drawing(rect, paint, bitmap, bitmapRect, clippingRect)
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
        Log.d("OverlayView", "drawing ${drawings.size} rects: ${drawings.map { it.rect }} ")
        drawings.forEach {
            with(it) {
                canvas.drawRect(rect, paint)
                if (bitmap != null)
                    canvas.drawBitmap(bitmap, clippingRect, bitmapRect, null)
            }
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        job.cancel()
    }
}
