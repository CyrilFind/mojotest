package io.cyrilfind.mojotest.templater.data

import io.cyrilfind.mojotest.HorizontalAnchor
import io.cyrilfind.mojotest.VerticalAnchor
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Template(
    @SerialName("width")
    val widthRatio: Float,
    @SerialName("height")
    val heightRatio: Float,
    @SerialName("background_color")
    val backgroundColor: String,
    @SerialName("x")
    val xRatio: Float = 0f,
    @SerialName("y")
    val yRatio: Float = 0f,
    @SerialName("anchor_x")
    val anchorX: HorizontalAnchor = HorizontalAnchor.LEFT,
    @SerialName("anchor_y")
    val anchorY: VerticalAnchor = VerticalAnchor.BOTTOM,
    @SerialName("padding")
    val padding: Float = 0f,
    @SerialName("padding_left")
    val paddingLeft: Float = padding,
    @SerialName("padding_top")
    val paddingTop: Float = padding,
    @SerialName("padding_right")
    val paddingRight: Float = padding,
    @SerialName("padding_bottom")
    val paddingBottom: Float = padding,
    @SerialName("children")
    val children: List<Template> = emptyList(),
    @SerialName("media")
    val mediaUrl: String? = null,
    @SerialName("media_content_mode")
    val mediaContentMode: MediaContentMode? = null,
) {
    fun computeRectangles(parent: Rectangle): List<Rectangle> {
        val rectangle = computeRectangle(parent)
        val result = mutableListOf(rectangle)

        val paddedRect = rectangle.copy(
            left = rectangle.left + (paddingLeft * rectangle.width),
            top = rectangle.top + (paddingTop * rectangle.height),
            right = rectangle.right - (paddingRight * rectangle.width),
            bottom = rectangle.bottom - (paddingBottom * rectangle.height)
        )

        children.forEach { result += it.computeRectangles(paddedRect) }

        return result
    }

    private fun computeRectangle(parentRect: Rectangle): Rectangle {
        val width = widthRatio * parentRect.width
        val height = heightRatio * parentRect.height

        val relativeX = xRatio * parentRect.width
        val x = parentRect.left + relativeX
        val (left, right) = when (anchorX) {
            HorizontalAnchor.LEFT -> x to x + width
            HorizontalAnchor.RIGHT -> x - width to x
            HorizontalAnchor.CENTER -> x - (width / 2) to x + (width / 2)
        }

        val relativeY = yRatio * parentRect.height
        val y = parentRect.bottom - relativeY // Y is defined from bottom
        val (top, bottom) = when (anchorY) {
            VerticalAnchor.TOP -> y to y + height
            VerticalAnchor.BOTTOM -> y - height to y
            VerticalAnchor.CENTER -> y - (height / 2) to y + (height / 2)
        }

        val media =
            if (mediaUrl != null && mediaContentMode != null)
                Media(mediaUrl, mediaContentMode)
            else null
        return Rectangle(left, top, right, bottom, backgroundColor, media)
    }
}

