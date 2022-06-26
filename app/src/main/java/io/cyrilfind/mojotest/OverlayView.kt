package io.cyrilfind.mojotest

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import com.bumptech.glide.Glide
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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
        set(value) {
            field = value
            computeOverlay()
        }

    private fun computeOverlay() {
        val overlay = overlay ?: return
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
                    val matrix = Matrix()
                    val bitmap =
                        it.mediaUrl?.let { url -> glide.asBitmap().load(url).submit().get() }
                    if (bitmap != null) {
                        val src = RectF(0f, 0f, bitmap.width.toFloat(), bitmap.height.toFloat())
                        val scaleToFit =
                            if (it.mediaContentMode == MediaContentMode.FIT) Matrix.ScaleToFit.CENTER
                            else Matrix.ScaleToFit.FILL
                        matrix.setRectToRect(src, rect, scaleToFit)
                    }
                    drawings += Drawing(rect, paint, bitmap, matrix)
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
                if (bitmap != null) {
                    canvas.drawBitmap(bitmap, matrix, null)
                }
            }
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        job.cancel()
    }
}
