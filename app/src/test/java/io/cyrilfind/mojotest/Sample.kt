package io.cyrilfind.mojotest

object Sample {
    object Overlay {
        val nested = Overlay(
            widthRatio = 1.0f,
            heightRatio = 1.0f,
            xRatio = 0.0f,
            yRatio = 0.0f,
            anchorX = HorizontalAnchor.LEFT,
            anchorY = VerticalAnchor.BOTTOM,
            backgroundColor = "#6BA2F7",
            padding = 0.1f,
            children = listOf(
                Overlay(
                    widthRatio = 1.0f,
                    heightRatio = 1.0f,
                    xRatio = 0.0f,
                    yRatio = 0.0f,
                    anchorX = HorizontalAnchor.LEFT,
                    anchorY = VerticalAnchor.BOTTOM,
                    backgroundColor = "#73D3A2"
                ),
                Overlay(
                    widthRatio = 0.8f,
                    heightRatio = 0.2f,
                    xRatio = 0.5f,
                    yRatio = 0.5f,
                    anchorX = HorizontalAnchor.CENTER,
                    anchorY = VerticalAnchor.CENTER,
                    backgroundColor = "#6BA2F7",
                    padding = 0.1f,
                    children = listOf(
                        Overlay(
                            widthRatio = 0.4375f,
                            heightRatio = 1.0f,
                            backgroundColor = "#73D3A2",
                        ),
                        Overlay(
                            widthRatio = 0.4375f,
                            heightRatio = 1.0f,
                            xRatio = 1.0f,
                            anchorX = HorizontalAnchor.RIGHT,
                            backgroundColor = "#73D3A2",
                        )
                    )
                )
            )
        )
        
        val withPaddings = Overlay(
            widthRatio = 1.0f,
            heightRatio = 1.0f,
            xRatio = 0.0f,
            yRatio = 0.0f,
            anchorX = HorizontalAnchor.LEFT,
            anchorY = VerticalAnchor.BOTTOM,
            backgroundColor = "#6BA2F7",
            padding = 0.1f,
            paddingRight = 0.02f,
            paddingTop = 0.03f,
            paddingBottom = 0.05f,
            children = listOf(
                Overlay(
                    widthRatio = 1.0f,
                    heightRatio = 1.0f,
                    xRatio = 0.0f,
                    yRatio = 0.0f,
                    paddingLeft = 0.1f,
                    anchorX = HorizontalAnchor.LEFT,
                    anchorY = VerticalAnchor.BOTTOM,
                    backgroundColor = "#73D3A2"
                )
            )
        )
        
        val withMedia = Overlay(
            widthRatio = 1.0f,
            heightRatio = 1.0f,
            backgroundColor = "#6BA2F7",
            mediaUrl = "https://picsum.photos/200/300",
            mediaContentMode = MediaContentMode.FILL,
            children = listOf(
                Overlay(
                    widthRatio = 0.9f,
                    heightRatio = 0.9f,
                    backgroundColor = "#73D3A2",
                    mediaUrl = "https://picsum.photos/400/300",
                    mediaContentMode = MediaContentMode.FIT,
                ),
                Overlay(
                    widthRatio = 1.0f,
                    heightRatio = 1.0f,
                    backgroundColor = "#73D3A2",
                    mediaUrl = "https://picsum.photos/300/400",
                    mediaContentMode = MediaContentMode.FILL,
                )
            )
        )

        val withAnchors = Overlay(
            widthRatio = 1.0f,
            heightRatio = 1.0f,
            xRatio = 0.0f,
            yRatio = 0.0f,
            anchorX = HorizontalAnchor.LEFT,
            anchorY = VerticalAnchor.BOTTOM,
            backgroundColor = "#6BA2F7",
            padding = 0.1f,
            children = listOf(
                Overlay(
                    widthRatio = 1.0f,
                    heightRatio = 1.0f,
                    xRatio = 0.0f,
                    yRatio = 0.0f,
                    anchorX = HorizontalAnchor.LEFT,
                    anchorY = VerticalAnchor.BOTTOM,
                    backgroundColor = "#73D3A2",
                    children = listOf(
                        Overlay(
                            widthRatio = 0.2f,
                            heightRatio = 0.2f,
                            xRatio = 0.1f,
                            yRatio = 0.9f,
                            anchorX = HorizontalAnchor.LEFT,
                            anchorY = VerticalAnchor.TOP,
                            backgroundColor = "#FF0000"
                        ),
                        Overlay(
                            widthRatio = 0.2f,
                            heightRatio = 0.2f,
                            xRatio = 0.9f,
                            yRatio = 0.9f,
                            anchorX = HorizontalAnchor.RIGHT,
                            anchorY = VerticalAnchor.TOP,
                            backgroundColor = "#FF0000"
                        ),
                        Overlay(
                            widthRatio = 0.2f,
                            heightRatio = 0.2f,
                            xRatio = 0.9f,
                            yRatio = 0.1f,
                            anchorX = HorizontalAnchor.RIGHT,
                            anchorY = VerticalAnchor.BOTTOM,
                            backgroundColor = "#FF0000"
                        ),
                        Overlay(
                            widthRatio = 0.2f,
                            heightRatio = 0.2f,
                            xRatio = 0.1f,
                            yRatio = 0.1f,
                            anchorX = HorizontalAnchor.LEFT,
                            anchorY = VerticalAnchor.BOTTOM,
                            backgroundColor = "#FF0000"
                        ),
                        Overlay(
                            widthRatio = 0.2f,
                            heightRatio = 0.2f,
                            xRatio = 0.5f,
                            yRatio = 0.5f,
                            anchorX = HorizontalAnchor.CENTER,
                            anchorY = VerticalAnchor.CENTER,
                            backgroundColor = "#FF0000"
                        ),
                        Overlay(
                            widthRatio = 0.2f,
                            heightRatio = 0.2f,
                            xRatio = 0.1f,
                            yRatio = 0.5f,
                            anchorX = HorizontalAnchor.LEFT,
                            anchorY = VerticalAnchor.CENTER,
                            backgroundColor = "#0000FF"
                        ),
                        Overlay(
                            widthRatio = 0.2f,
                            heightRatio = 0.2f,
                            xRatio = 0.9f,
                            yRatio = 0.5f,
                            anchorX = HorizontalAnchor.RIGHT,
                            anchorY = VerticalAnchor.CENTER,
                            backgroundColor = "#0000FF"
                        ),
                        Overlay(
                            widthRatio = 0.2f,
                            heightRatio = 0.2f,
                            xRatio = 0.5f,
                            yRatio = 0.9f,
                            anchorX = HorizontalAnchor.CENTER,
                            anchorY = VerticalAnchor.TOP,
                            backgroundColor = "#0000FF"
                        ),
                        Overlay(
                            widthRatio = 0.2f,
                            heightRatio = 0.2f,
                            xRatio = 0.5f,
                            yRatio = 0.1f,
                            anchorX = HorizontalAnchor.CENTER,
                            anchorY = VerticalAnchor.BOTTOM,
                            backgroundColor = "#0000FF"
                        )
                    )
                )
            )
        )
    }
    
    object Json {
        val nested = loadJson("overlay_nested.json")
        val withPaddings = loadJson("overlay_with_paddings.json")
        val withAnchors = loadJson("overlay_with_anchors.json")
        val withMedia = loadJson("overlay_with_media.json")
    }


    private fun loadJson(path: String): String {
        return javaClass.classLoader!!.getResourceAsStream(path).bufferedReader().use { it.readText() }
    }
}