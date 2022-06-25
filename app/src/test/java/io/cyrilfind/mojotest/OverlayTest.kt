package io.cyrilfind.mojotest

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.junit.Assert
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class OverlayTest {
    val sampleJson = """
            {
              "width": 1,
              "height": 1,
              "x": 0,
              "y": 0,
              "anchor_x": "left",
              "anchor_y": "bottom",
              "background_color": "#6BA2F7",
              "padding": 0.1,
              "children": [
                {
                  "width": 1,
                  "height": 1,
                  "x": 0,
                  "y": 0,
                  "anchor_x": "left",
                  "anchor_y": "bottom",
                  "background_color": "#73D3A2"
                },
                {
                  "width": 0.8,
                  "height": 0.2,
                  "x": 0.5,
                  "y": 0.5,
                  "anchor_x": "center",
                  "anchor_y": "center",
                  "background_color": "#6BA2F7",
                  "padding": 0.1,
                  "children": [
                    {
                      "width": 0.4375,
                      "height": 1,
                      "background_color": "#73D3A2"
                    },
                    {
                      "x": 1,
                      "width": 0.4375,
                      "height": 1,
                      "anchor_x": "right",
                      "background_color": "#73D3A2"
                    }
                  ]
                }
              ]
            }
        """.trimIndent()

    val sampleOverlay = Overlay(
        widthRatio = 1.0f,
        heightRatio = 1.0f,
        xRatio = 0.0f,
        yRatio = 0.0f,
        anchorX = "left",
        anchorY = "bottom",
        backgroundColor = "#6BA2F7",
        padding = 0.1f,
        children = listOf(
            Overlay(
                widthRatio = 1.0f,
                heightRatio = 1.0f,
                xRatio = 0.0f,
                yRatio = 0.0f,
                anchorX = "left",
                anchorY = "bottom",
                backgroundColor = "#73D3A2"
            ),
            Overlay(
                widthRatio = 0.8f,
                heightRatio = 0.2f,
                xRatio = 0.5f,
                yRatio = 0.5f,
                anchorX = "center",
                anchorY = "center",
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
                        anchorX = "right",
                        backgroundColor = "#73D3A2",
                    )
                )
            )
        )
    )

    @Test
    fun `given correct json input when serializing then output is correct`() {
        val actual = Json.decodeFromString<Overlay>(sampleJson)
        val expected = sampleOverlay
        Assert.assertEquals(expected, actual)
    }

    @Test
    fun `given sample overlay when computing rects then output is correct`() {

        // GIVEN
        val canvasRect = Rectangle(0f, 0f, 1000f, 1000f, "#FFFFFF")

        // WHEN
        val actual = sampleOverlay.computeRects(canvasRect)

        // THEN
        val expected = listOf(
            Rectangle(0.0f, 0.0f, 1000.0f, 1000.0f, "#6BA2F7"),
            Rectangle(100.0f, 100.0f, 900.0f, 900.0f, "#73D3A2"),
            Rectangle(180.0f, 420.0f, 820.0f, 580.0f, "#6BA2F7"),
            Rectangle(244.0f, 436.0f, 468.0f, 564.0f, "#73D3A2"),
            Rectangle(532.0f, 436.0f, 756.0f, 564.0f, "#73D3A2")
        )

        Assert.assertEquals(expected, actual)
    }
}