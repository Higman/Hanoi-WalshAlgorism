package com.github.Higman.hanoi;

import javax.annotation.Nonnull;
import java.util.ArrayDeque;
import java.util.Deque;

public class Tower extends HanoiComp implements Cloneable {
    private Deque<Disk> towerStack = new ArrayDeque<>();

    protected Tower(int label) {
        super(label, Integer.MAX_VALUE);
    }

    public void addToTop(HanoiComp newDisk) {
        if ( getTopHanoiComp().size <= newDisk.size || !(newDisk instanceof Disk) ) { throw new IllegalDiskMove(); }
        towerStack.push((Disk) newDisk);
    }

    public int getDiskNum() {
        return towerStack.size();
    }

    @Nonnull
    public HanoiComp removeFromTop() {
        if ( towerStack.size() < 1 ) { return this; }
        return towerStack.pop();
    }

    @Nonnull
    public HanoiComp getTopHanoiComp() {
        if ( towerStack.size() < 1 ) { return this; }
        return towerStack.getFirst();
    }

    public class IllegalDiskMove extends RuntimeException {}

    public Disk[] getDisks() {
        Tower cp = this.clone();
        return (Disk[]) cp.towerStack.toArray();
    }

    @Override
    public String toString() {
        String ret = "Tower"+super.toString()+"{";

        for ( HanoiComp hc : towerStack ) {
            ret += hc + ", ";
        }
        ret += "}";

        return ret;
    }

    @Override
    protected Tower clone() {
        Tower tw = null;

        try {
            tw = (Tower) super.clone();
            for (Disk comp : towerStack) {
                tw.towerStack.addLast(comp);
            }
        } catch ( CloneNotSupportedException e ) {
            e.printStackTrace();
        }
        return tw;
    }
}
