package com.github.Higman;

import com.github.Higman.hanoi.Hanoi;
import com.github.Higman.hanoi.HanoiAlgorithmComp;
import com.github.Higman.hanoi.HanoiTowerAlgorithm;
import com.github.Higman.hanoi.HanoiWalshAlgorithm;

import java.util.Arrays;
import java.util.function.Supplier;

public class ConsoleEntryPoint {
    public static final int HANOI_SIZE = 3;  // ハノイの塔の大きさ

    public static void main(String[] args) {
        final int hanoiSize = HANOI_SIZE;
        Hanoi hanoi = new Hanoi(hanoiSize);
        Supplier<HanoiAlgorithmComp> algorithm = () -> new HanoiWalshAlgorithm(hanoi);
        if ( Arrays.stream(args).anyMatch(str -> str.equals("-t"))) { algorithm = () -> new HanoiTowerAlgorithm(hanoi); }
        HanoiAlgorithmComp hw = algorithm.get();
        hw.execHanoi();
    }
}
