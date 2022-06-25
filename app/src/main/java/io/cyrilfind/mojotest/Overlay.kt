package io.cyrilfind.mojotest

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Overlay(
    @SerialName("width")
    val widthRatio: Float,
    @SerialName("height")
    val heightRatio: Float,
    @SerialName("x")
    val xRatio: Float = 0f,
    @SerialName("y")
    val yRatio: Float = 0f,
    @SerialName("anchor_x")
    val anchorX: String = "left",
    @SerialName("anchor_y")
    val anchorY: String = "bottom",
    @SerialName("background_color")
    val backgroundColor: String,
    @SerialName("padding")
    val padding: Float = 0f,
    @SerialName("children")
    val children: List<Overlay> = emptyList()
) {

    fun computeRects(parentRect: Rectangle): List<Rectangle> {
        val rect = computeRect(parentRect)
        val list = mutableListOf(rect)

        val paddedRect = rect.copy()

        paddedRect.apply {
            left += padding * rect.width
            top += padding * rect.height
            right -= padding * rect.width
            bottom -= padding * rect.height
        }

        children.forEach { child ->
            list += child.computeRects(paddedRect)
        }

        return list
    }

    private fun computeRect(parentRect: Rectangle): Rectangle {
        val parentWidth = parentRect.width
        val parentHeight = parentRect.height
        val width = widthRatio * parentWidth
        val height = heightRatio * parentHeight
        val x = xRatio * parentWidth
        val y = yRatio * parentHeight

        val left = when (anchorX) {
            "left" -> parentRect.left + x
            "right" -> parentRect.left + x - width
            "center" -> parentRect.left + x - (width / 2)
            else -> 0f
        }

        val top = when (anchorY) {
            "bottom" -> parentRect.top + y
            "top" -> parentRect.top + y - height
            "center" -> parentRect.top + y - (height / 2)
            else -> 0f
        }

        val right = when (anchorX) {
            "left" -> parentRect.left + x + width
            "right" -> parentRect.left + x
            "center" -> parentRect.left + x + (width / 2)
            else -> 0f
        }

        val bottom = when (anchorY) {
            "bottom" -> parentRect.top + y + height
            "top" -> parentRect.top + y
            "center" -> parentRect.top + y + (height / 2)
            else -> 0f
        }

        val rect = Rectangle(left, top, right, bottom, backgroundColor)

        return rect
    }
}


