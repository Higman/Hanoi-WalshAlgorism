package com.github.Higman.hanoi;

public class Disk extends HanoiComp implements Cloneable {
    protected Disk(int label, int size) {
        super(label, size);
    }

    @Override
    public String toString() {
        return "Disk" + super.toString();
    }

    @Override
    protected Disk clone() throws CloneNotSupportedException {
        return (Disk) super.clone();
    }
}
