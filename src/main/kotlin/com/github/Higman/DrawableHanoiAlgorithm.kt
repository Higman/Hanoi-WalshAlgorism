package com.github.Higman

import com.github.Higman.hanoi.Hanoi
import com.github.Higman.hanoi.HanoiAlgorithmComp
import java.util.*

interface DrawableHanoiAlgorithm {
    val hanoi: Hanoi
    fun getSortOperations(): List<() -> Optional<HanoiAlgorithmComp.MoveInformation>>
    fun initialize()
}