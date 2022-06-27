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

    @Test
    fun `given correct nested json input when serializing then output is correct`() {
        val actual = Json.decodeFromString<Overlay>(Sample.Json.nested)
        val expected = Sample.Overlay.nested
        Assert.assertEquals(expected, actual)
    }
    
    @Test
    fun `given correct json with anchors input when serializing then output is correct`() {
        val actual = Json.decodeFromString<Overlay>(Sample.Json.withAnchors)
        val expected = Sample.Overlay.withAnchors
        Assert.assertEquals(expected, actual)
    }   
    
    @Test
    fun `given correct json with paddings input when serializing then output is correct`() {
        val actual = Json.decodeFromString<Overlay>(Sample.Json.withPaddings)
        val expected = Sample.Overlay.withPaddings
        Assert.assertEquals(expected, actual)
    }
    
    @Test
    fun `given correct json with media input when serializing then output is correct`() {
        val actual = Json.decodeFromString<Overlay>(Sample.Json.withMedia)
        val expected = Sample.Overlay.withMedia
        Assert.assertEquals(expected, actual)
    }

    @Test
    fun `given nested overlay when computing rects then output is correct`() {

        // GIVEN
        val canvasRect = Rectangle(0f, 0f, 1000f, 1000f, "#FFFFFF")

        // WHEN
        val actual = Sample.Overlay.nested.computeRectangles(canvasRect)

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

    @Test
    fun `given overlay with all different anchors when computing rects then output is correct`() {

        // GIVEN
        val canvasRect = Rectangle(0f, 0f, 1000f, 1000f, "#FFFFFF")

        // WHEN
        val actual = Sample.Overlay.withAnchors.computeRectangles(canvasRect)

        // THEN
        val expected = listOf(
            Rectangle(0.0f, 0.0f, 1000.0f, 1000.0f, "#6BA2F7"),
            Rectangle(100.0f, 100.0f, 900.0f, 900.0f, "#73D3A2"),
            Rectangle(180.0f, 180.0f, 340.0f, 340.0f, "#FF0000"),
            Rectangle(660.0f, 180.0f, 820.0f, 340.0f, "#FF0000"),
            Rectangle(660.0f, 660.0f, 820.0f, 820.0f, "#FF0000"),
            Rectangle(180.0f, 660.0f, 340.0f, 820.0f, "#FF0000"),
            Rectangle(420.0f, 420.0f, 580.0f, 580.0f, "#FF0000"),
            Rectangle(180.0f, 420.0f, 340.0f, 580.0f, "#0000FF"),
            Rectangle(660.0f, 420.0f, 820.0f, 580.0f, "#0000FF"),
            Rectangle(420.0f, 180.0f, 580.0f, 340.0f, "#0000FF"),
            Rectangle(420.0f, 660.0f, 580.0f, 820.0f, "#0000FF")
        )

        Assert.assertEquals(expected, actual)
    }
}