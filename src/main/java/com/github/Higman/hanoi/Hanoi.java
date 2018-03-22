package com.github.Higman.hanoi;

import java.util.Arrays;
import java.util.Optional;

public class Hanoi implements Cloneable {
    private Tower[] towers = new Tower[3];
    private int diskNum;

    public Hanoi(int n) {
        diskNum = n;
        initHanoi(n);
    }

    public void move(TowerID begin, TowerID end) {
        HanoiComp movingTarget = towers[begin.id].removeFromTop();
        try {
            towers[end.id].addToTop(movingTarget);
        } catch ( Tower.IllegalDiskMove e ) {
            System.out.println("不適切な移動です。[ Tower "+begin+" =/=> Tower " + end + " ]" );
            towers[begin.id].addToTop(movingTarget);
        }
    }

    public boolean isFinished() {
        return Arrays.stream(towers).anyMatch(tower -> tower.getDiskNum() == diskNum);
    }

    public TowerInformation getInfo(TowerID towerID) {
        Tower tower = towers[towerID.id];
        return  new TowerInformation(towerID, tower.label, tower.getDiskNum(), tower.getTopHanoiComp());
    }

    public Tower[] getTowers() {
        return Arrays.copyOf(towers, towers.length);
    }

    private void initHanoi(int n) {
        towers[TowerID.A.id] = new Tower(n + 1);
        towers[TowerID.B.id] = new Tower(n + 3);
        towers[TowerID.C.id] = new Tower(n + 2);

        for ( int i = n; i > 0; i-- ) {
            towers[TowerID.A.id].addToTop(new Disk(i, i));
        }
    }

    public enum TowerID {
        A(0), B(1), C(2);

        public final int id;

        TowerID(int id) {
            this.id = id;
        }
    }

    public class TowerInformation {
        public TowerID towerID;
        public int towerLabel;
        public int diskNum;
        public HanoiComp topDisk;

        public TowerInformation(TowerID towerID, int towerLabel, int diskNum, HanoiComp topDiskLabel) {
            this.towerID = towerID;
            this.towerLabel = towerLabel;
            this.diskNum = diskNum;
            this.topDisk = topDiskLabel;
        }

        @Override
        public String toString() {
            return "TowerInformation{" +
                    "towerLabel=" + towerLabel +
                    ", diskNum=" + diskNum +
                    ", topDisk=" + topDisk +
                    '}';
        }
    }

    @Override
    public String toString() {
        String ret = "Hanoi\n";
        for ( Tower tower : towers ) {
            ret += "  " + tower + "\n";
        }

        return ret;
    }

    @Override
    protected Hanoi clone() throws CloneNotSupportedException {
        Hanoi h = null;

        try {
            h = (Hanoi) super.clone();
            h.towers = new Tower[3];
            for ( int i = 0; i < towers.length; i++ ) {
                h.towers[i] = towers[i].clone();
            }
        } catch ( CloneNotSupportedException e ) {
            e.printStackTrace();
        }
        return h;
    }
}
