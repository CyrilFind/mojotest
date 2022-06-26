package io.cyrilfind.mojotest

import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
enum class MediaContentMode {
    @SerialName("fit")
    FIT, 
    @SerialName("fill")
    FILL
}
