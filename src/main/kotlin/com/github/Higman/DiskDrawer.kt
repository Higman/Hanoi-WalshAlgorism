package com.github.Higman

import javafx.geometry.Dimension2D
import javafx.scene.Group
import javafx.scene.control.Label
import javafx.scene.paint.Color
import javafx.scene.shape.Rectangle
import javafx.scene.text.Font
import kotlin.properties.Delegates

class DiskDrawer(x: Double, y: Double, size: Dimension2D, val diskNum: Int) : Group() {
    var sizeProperty: Dimension2D by Delegates.observable(size) { _, _, newVal -> resize(newVal) }
    private val rectangle: Rectangle = Rectangle(0.0, 0.0, size.width, size.height)
    private val label = Label(diskNum.toString()).apply {
        resize(sizeProperty)
        widthProperty().addListener {_, _, _ -> resize(sizeProperty)}
        heightProperty().addListener {_, _, _ -> resize(sizeProperty)}
    }

    init {
        layoutX = x; layoutY = y
        children.add(rectangle)
        children.add(label)
    }

    private fun resize(size: Dimension2D) {
        rectangle.width = size.width
        rectangle.height = size.height
        label.resize(size)
    }

    private fun Label.resize(size: Dimension2D) {
        val layoutX = (size.width - width) / 2.0
        val layoutY = (size.height - height) / 2.0
        relocate(layoutX, layoutY)
        setStyle(String.format("-fx-effect: dropshadow( gaussian , rgba(255,255,255,0.7) , 0,0,0,1.6 );"+
                "-fx-font-size: %dpx;", (sizeProperty.height).toInt())) // フォントサイズの調整
    }


    fun fill(color: Color) {
        rectangle.fill = color
    }
}