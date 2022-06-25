package io.cyrilfind.mojotest

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

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
)
