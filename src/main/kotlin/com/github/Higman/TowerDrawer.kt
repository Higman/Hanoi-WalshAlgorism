package com.github.Higman

import javafx.beans.property.DoubleProperty
import javafx.beans.property.SimpleDoubleProperty
import javafx.scene.Group
import javafx.scene.shape.Rectangle
import java.util.*

class TowerDrawer(x: Double, y: Double, size: Double, val maxDiskNum: Int) : Group() {
    val sizeProperty: DoubleProperty = SimpleDoubleProperty(size)
    private val disks: Deque<Rectangle> = ArrayDeque()
    val rectangles: Array<Rectangle> = arrayOf(
            Rectangle(size / 2.0 - size / ((maxDiskNum + 1) * 2.0), 0.0, size / (maxDiskNum + 1), size),
            Rectangle(0.0, size - size / (maxDiskNum + 1), size, size / (maxDiskNum + 1))
    )

    init {
        layoutX = x
        layoutY = y
        children.addAll(rectangles)
        sizeProperty.addListener { e -> resize() }
    }

    fun pushDisk(rect: Rectangle) {
        disks.push(rect); relocateDisks()
        children.add(rect)
    }

    fun popDisk(): Rectangle? {
        val disk = disks.pop(); relocateDisks()
        children.remove(disk)
        return disk
    }

    private fun changeSizeOfTowerRectangles(size: Double) {
        val division = maxDiskNum + 1
        rectangles[0].x = size / 2.0 - size / (division*2)
        rectangles[0].width = size / division
        rectangles[0].height = size

        rectangles[1].y = size - size / division
        rectangles[1].width = size
        rectangles[1].height = size / division
    }

    private fun relocateDisks() {
        disks.reversed().forEachIndexed { idx, d ->
            println(idx)
            val diskUnitHeight = sizeProperty.value / (maxDiskNum + 1)
            val baseY = sizeProperty.value - 2 * diskUnitHeight
            d.x = (sizeProperty.value - d.width) / 2.0
            d.y = baseY - idx * diskUnitHeight
        }
    }

    private fun resize() {
        changeSizeOfTowerRectangles(sizeProperty.value)
        resizeDisk()
        relocateDisks()
    }

    private fun resizeDisk() {
        val division = maxDiskNum + 1

        val sizeUnit = sizeProperty.value / (maxDiskNum + 1)
        disks.forEachIndexed { idx, dsk ->
            dsk.height = sizeProperty.value / division
            dsk.width = sizeUnit * (idx+1)
        }
    }
}