package com.github.Higman;

import com.github.Higman.hanoi.Hanoi;
import com.github.Higman.hanoi.HanoiAlgorithmComp;
import com.github.Higman.hanoi.HanoiTowerAlgorithm;
import com.github.Higman.hanoi.HanoiWalshAlgorithm;

public class ConsoleEntryPoint {
    public static final int HANOI_SIZE = 3;  // ハノイの塔の大きさ

    public static void main(String[] args) {
        int hanoiSize = HANOI_SIZE;
        HanoiAlgorithmComp hw = new HanoiTowerAlgorithm(new Hanoi(hanoiSize));
        hw.execHanoi();
    }
}
