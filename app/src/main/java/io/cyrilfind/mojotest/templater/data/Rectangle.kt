package io.cyrilfind.mojotest.templater.data

data class Rectangle(
    val left: Float,
    val top: Float,
    val right: Float,
    val bottom: Float,
    val color: String = "#FFFFFF",
    val media: Media? = null
) {
    val width: Float get() = right - left
    val height: Float get() = bottom - top
}

