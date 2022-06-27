package io.cyrilfind.mojotest.templater.data

import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
enum class MediaContentMode {
    @SerialName("fit")
    FIT, 
    @SerialName("fill")
    FILL
}
