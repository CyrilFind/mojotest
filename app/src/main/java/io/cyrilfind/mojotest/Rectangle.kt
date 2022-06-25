package io.cyrilfind.mojotest

data class Rectangle(
    var left: Float,
    var top: Float,
    var right: Float,
    var bottom: Float,
    var color: String
) {
    val width: Float get() = right - left
    val height: Float get() = bottom - top
    val centerX: Float get() = (left + right) / 2
    val centerY: Float get() = (top + bottom) / 2
}
