package com.github.Higman;

import com.github.Higman.hanoi.Hanoi;
import com.github.Higman.hanoi.HanoiWalsh;

public class ConsoleEntryPoint {
    public static final int HANOI_SIZE = 3;  // ハノイの塔の大きさ

    public static void main(String[] args) {
        int hanoiSize = HANOI_SIZE;
        HanoiWalsh hw = new HanoiWalsh(new Hanoi(hanoiSize));
        hw.execHanoi();
    }
}
