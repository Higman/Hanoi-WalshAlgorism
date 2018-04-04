package com.github.Higman.hanoi;

import java.util.Arrays;
import java.util.Iterator;

public abstract class HanoiAlgorithmComp {
    protected Hanoi hanoi;

    public HanoiAlgorithmComp(Hanoi hanoi) {
        this.hanoi = hanoi;
    }

    public abstract void execHanoi();

    protected Hanoi.TowerID getTowerIDHavingSmallestDisk() {
        Hanoi.TowerID[] searchIDs = Hanoi.TowerID.values();
        return getTowerIDHavingSmallestDiskComp(searchIDs);
    }

    protected Hanoi.TowerID getTowerIDOfOtherTower(Hanoi.TowerID... excludeTowerIDs) {
        return Arrays.stream(Hanoi.TowerID.values()).filter(id ->
                Arrays.stream(excludeTowerIDs).allMatch(excludeID -> id != excludeID)
        ).findFirst().orElseGet(null);
    }

    protected Hanoi.TowerID getTowerIDHavingSmallestDisk(Hanoi.TowerID excludeTowerID) {
        Hanoi.TowerID[] searchIDs = Arrays.stream(Hanoi.TowerID.values()).filter(id -> id != excludeTowerID).toArray(Hanoi.TowerID[]::new);
        return getTowerIDHavingSmallestDiskComp(searchIDs);
    }

    protected Hanoi.TowerID getTowerIDHavingSmallestDiskComp(Hanoi.TowerID[] searchIDs) {
        Hanoi.TowerInformation towerInfoHavingSmallestDisk = hanoi.getInfo(searchIDs[0]);
        Iterator<Hanoi.TowerID> towerIt = Arrays.stream(searchIDs).iterator();
        towerIt.next();
        while ( towerIt.hasNext() ) {
            Hanoi.TowerInformation tmpInfo = hanoi.getInfo(towerIt.next());
            if ( tmpInfo.topDisk.size < towerInfoHavingSmallestDisk.topDisk.size ) {
                towerInfoHavingSmallestDisk = tmpInfo;
            }
        }
        return towerInfoHavingSmallestDisk.towerID;
    }

    public class MoveInformation {
        public Hanoi.TowerID movingSource;
        public Hanoi.TowerID destination;

        public MoveInformation(Hanoi.TowerID movingSource, Hanoi.TowerID destination) {
            this.movingSource = movingSource;
            this.destination = destination;
        }
    }
}
