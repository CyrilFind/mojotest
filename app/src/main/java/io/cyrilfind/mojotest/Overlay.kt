package io.cyrilfind.mojotest

import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
data class Overlay(
    @SerialName("width")
    val width: Float,
    @SerialName("height")
    val height: Float,
    @SerialName("x")
    val x: Float = 0f,
    @SerialName("y")
    val y: Float = 0f,
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
