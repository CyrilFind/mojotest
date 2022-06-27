package io.cyrilfind.mojotest.templater.data

data class Media(
    val url: String,
    val contentMode: MediaContentMode = MediaContentMode.FILL,
)