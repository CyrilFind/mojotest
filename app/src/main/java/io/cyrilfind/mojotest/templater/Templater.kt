package io.cyrilfind.mojotest.templater

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.RectF
import androidx.annotation.RawRes
import androidx.core.graphics.toRect
import io.cyrilfind.mojotest.ImageFetcher
import io.cyrilfind.mojotest.ServiceLocator
import io.cyrilfind.mojotest.templater.data.MediaContentMode
import io.cyrilfind.mojotest.templater.data.Rectangle
import io.cyrilfind.mojotest.templater.data.Template
import io.cyrilfind.mojotest.templater.view.BitmapWrapper
import io.cyrilfind.mojotest.templater.view.Drawing
import io.cyrilfind.mojotest.templater.view.TemplateView
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine
import kotlin.math.min

class Templater(
    private val context: Context,
    private val imageFetcher: ImageFetcher,
    private val jsonDeserializer: Json,
    private val backgroundDispatcher: CoroutineDispatcher,
    private val ioDispatcher: CoroutineDispatcher,
) {
    private lateinit var template: Template

    suspend fun load(@RawRes resId: Int): Templater = withContext(ioDispatcher) {
        val raw = context.resources.openRawResource(resId).bufferedReader().use { it.readText() }
        return@withContext load(jsonDeserializer.decodeFromString<Template>(raw))
    }

    fun load(template: Template): Templater {
        this.template = template
        return this
    }

    suspend fun into(templateView: TemplateView) = withContext(backgroundDispatcher) {
        with(templateView) {
            val (contentWidth, contentHeight) = suspendCoroutine {
                post {
                    val width = width - paddingLeft - paddingRight
                    val height = height - paddingTop - paddingBottom
                    it.resume(width to height)
                }
            }
            val canvas = Rectangle(0f, 0f, contentWidth.toFloat(), contentHeight.toFloat())
            val rectangles = template.computeRectangles(canvas)
            templateView.drawings = computeDrawings(rectangles)
        }
    }

    private suspend fun computeDrawings(rectangles: List<Rectangle>): List<Drawing> {
        val drawings = mutableListOf<Drawing>()
        rectangles.forEach {
            val rect = it.toRectF()
            val paint = Paint().apply {
                color = Color.parseColor(it.color)
                style = Paint.Style.FILL
            }
            val bitmapWrapper: BitmapWrapper? = computeMedia(it)
            drawings += Drawing(rect, paint, bitmapWrapper)
        }
        return drawings
    }

    private suspend fun computeMedia(rectangle: Rectangle): BitmapWrapper? {
        val mediaUrl = rectangle.media?.url ?: return null
        val bitmap = imageFetcher.fetch(mediaUrl)
        var src: Rect? = null
        var dest: RectF = rectangle.toRectF()
        when (rectangle.media.contentMode) {
            MediaContentMode.FIT -> {
                val scale = min(
                    rectangle.width / bitmap.width,
                    rectangle.height / bitmap.height
                )
                dest = RectF(
                    rectangle.left,
                    rectangle.top,
                    rectangle.left + bitmap.width * scale,
                    rectangle.top + bitmap.height * scale
                )
            }
            MediaContentMode.FILL -> {
                src = RectF(0f, 0f, rectangle.width, rectangle.height).toRect()
            }
        }
        return BitmapWrapper(bitmap, src, dest)
    }

    private fun Rectangle.toRectF() = RectF(left, top, right, bottom)

    companion object {
        fun get() = ServiceLocator.getTemplater()
    }
}