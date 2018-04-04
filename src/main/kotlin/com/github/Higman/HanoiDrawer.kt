package com.github.Higman

import com.github.Higman.hanoi.Hanoi
import com.github.Higman.hanoi.HanoiAlgorithmComp
import com.github.Higman.hanoi.HanoiWalshAlgorithm
import javafx.scene.layout.Pane
import javafx.scene.paint.Color
import javafx.scene.shape.Rectangle
import java.util.*

class HanoiDrawer(val algorithm: DrawableHanoiAlgorithm, val pane: Pane) {
    val processList = mutableListOf<() -> Optional<HanoiAlgorithmComp.MoveInformation>>()
    private val towerDrawers: Map<Hanoi.TowerID, TowerDrawer> by lazy { createMap() }

    init {
        processList.addAll(algorithm.getSortOperations())
        pane.children.addAll(towerDrawers.values)
        val rand = Random()

        pane.widthProperty().addListener {e -> resizeMap()}
        pane.heightProperty().addListener {e -> resizeMap()}

        (1..algorithm.hanoi.diskNum).forEach { n ->
            towerDrawers.get(Hanoi.TowerID.A)?.let {
                val sizeUnit = it.sizeProperty.value / (algorithm.hanoi.diskNum + 1)
                val width = it.sizeProperty.value - sizeUnit * n
                val height = it.sizeProperty.value / (algorithm.hanoi.diskNum + 1)
                val rect = Rectangle(0.0, 0.0, width, height)
                rect.fill = Color.rgb(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255))
                it.pushDisk(rect)
            }
        }
        algorithm.initialize()
    }

    fun next(): Boolean {
        val process = processList.removeAt(0)
        val mInfo = process()
        if (!mInfo.isPresent) throw IllegalProcessException()  // 異常動作例外
        val movingDisk = towerDrawers.get(mInfo.get().movingSource)?.popDisk()
        movingDisk?.let { towerDrawers.get(mInfo.get().destination)?.pushDisk(it) }
        processList.add(process)
        return algorithm. hanoi.isFinished
    }

    private fun createMap(): Map<Hanoi.TowerID, TowerDrawer> {
        val interval = pane.width / 50.0
        val size = pane.width / 3.0 - (interval + interval / 3)
        val baseY = (pane.height - size) / 2.0
        return mapOf(
                Hanoi.TowerID.A to TowerDrawer(interval, baseY, size, algorithm.hanoi.diskNum),
                Hanoi.TowerID.B to TowerDrawer(interval + (size + interval), baseY, size, algorithm.hanoi.diskNum),
                Hanoi.TowerID.C to TowerDrawer(interval + (size + interval) * 2, baseY, size, algorithm.hanoi.diskNum)
        )
    }

    private fun resizeMap() {
        towerDrawers.values.forEachIndexed { idx, tw ->
            val interval = pane.width / 50.0
            val size = pane.width / 3.0 - (interval + interval / 3)
            val baseY = (pane.height - size) / 2.0
            tw.layoutX = interval + (size + interval) * idx
            tw.layoutY = baseY
            tw.sizeProperty.value = size
        }
    }

    private class IllegalProcessException : RuntimeException()
}