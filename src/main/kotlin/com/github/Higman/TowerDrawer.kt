package com.github.Higman

import javafx.beans.property.DoubleProperty
import javafx.beans.property.SimpleDoubleProperty
import javafx.geometry.Dimension2D
import javafx.scene.Group
import javafx.scene.control.Label
import javafx.scene.layout.Background
import javafx.scene.paint.Color
import javafx.scene.shape.Rectangle
import java.util.*
import kotlin.math.max

class TowerDrawer(x: Double, y: Double, size: Double, val maxDiskNum: Int) : Group() {
    val sizeProperty: DoubleProperty = SimpleDoubleProperty(size)
    private val disks: Deque<DiskDrawer> = ArrayDeque()
    val rectangles: Array<Rectangle> = arrayOf(
            Rectangle(size / 2.0 - size / ((maxDiskNum + 1) * 2.0), 0.0, size / (maxDiskNum + 1), size),
            Rectangle(0.0, size - size / (maxDiskNum + 1), size, size / (maxDiskNum + 1))
    )

    val label = Label("aa").apply {
        resize(size)
        textFill = Color.WHITE
        widthProperty().addListener { _, _, _ -> resize(sizeProperty.value) }
        heightProperty().addListener { _, _, _ -> resize(sizeProperty.value) }
    }


    init {
        layoutX = x; layoutY = y
        children.addAll(rectangles)
        children.add(label)
        sizeProperty.addListener { _ -> resize() }
    }

    fun pushDisk(rect: DiskDrawer) {
        disks.push(rect); relocateDisks()
        children.add(rect)
    }

    fun popDisk(): DiskDrawer? {
        val disk = disks.pop(); relocateDisks()
        children.remove(disk)
        return disk
    }

    private fun changeSizeOfTowerRectangles(size: Double) {
        val division = maxDiskNum + 1
        rectangles[0].x = size / 2.0 - size / (division * 2)
        rectangles[0].width = size / division
        rectangles[0].height = size

        rectangles[1].y = size - size / division
        rectangles[1].width = size
        rectangles[1].height = size / division
    }

    private fun relocateDisks() {
        disks.reversed().forEachIndexed { idx, d ->
            val diskUnitHeight = sizeProperty.value / (maxDiskNum + 1)
            val baseY = sizeProperty.value - 2 * diskUnitHeight
            d.layoutX = (sizeProperty.value - d.sizeProperty.width) / 2.0
            d.layoutY = baseY - idx * diskUnitHeight
        }
    }

    private fun resize() {
        changeSizeOfTowerRectangles(sizeProperty.value)
        resizeDisk()
        relocateDisks()
        label.resize(sizeProperty.value)
    }

    private fun resizeDisk() {
        val division = maxDiskNum + 1
        val sizeUnit = sizeProperty.value / (maxDiskNum + 1)
        disks.forEach { dsk ->
            dsk.sizeProperty = Dimension2D(sizeUnit * dsk.diskNum, sizeProperty.value / division)
        }
    }

    private fun Label.resize(size: Double) {
        val diskUnitHeight = size / (maxDiskNum + 1)
        val layoutX = (size - width) / 2.0
        val layoutY = size - diskUnitHeight
        relocate(layoutX, layoutY)
        setStyle(String.format("-fx-font-size: %dpx;", (diskUnitHeight/1.5).toInt())) // フォントサイズの調整
    }
}