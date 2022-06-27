package io.cyrilfind.mojotest

data class Rectangle(
    val left: Float,
    val top: Float,
    val right: Float,
    val bottom: Float,
    val color: String = "#FFFFFF",
    val mediaUrl: String? = null,
    val mediaContentMode: MediaContentMode = MediaContentMode.FILL, 
) {
    val width: Float get() = right - left
    val height: Float get() = bottom - top
}
