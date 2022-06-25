package io.cyrilfind.mojotest

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class VerticalAnchor {
    @SerialName("top")
    TOP,
    @SerialName("center")
    CENTER,
    @SerialName("bottom")
    BOTTOM;
}

@Serializable
enum class HorizontalAnchor {
    @SerialName("left")
    LEFT,
    @SerialName("center")
    CENTER,
    @SerialName("right")
    RIGHT;
}


