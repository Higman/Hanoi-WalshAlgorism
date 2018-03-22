package com.github.Higman.hanoi;

import java.util.Objects;

public abstract class HanoiComp implements Cloneable {
    public final int label;
    public final int size;

    protected HanoiComp(int label, int size) { this.label = label;  this.size = size; }

    @Override
    public boolean equals(Object o) {
        if ( this == o ) { return true; }
        if ( o == null || getClass() != o.getClass() ) { return false; }
        HanoiComp hanoiComp = (HanoiComp) o;
        return label == hanoiComp.label &&
                size == hanoiComp.size;
    }

    @Override
    public int hashCode() {
        return Objects.hash(label, size);
    }

    @Override
    public String toString() {
        return "{" +
                 + label +
                ", " + size +
                '}';
    }

    @Override
    protected HanoiComp clone() throws CloneNotSupportedException {
        HanoiComp hanoiComp = null;
        try {
            hanoiComp = (HanoiComp) super.clone();
        } catch ( CloneNotSupportedException e ) {
            e.printStackTrace();
        }
        return hanoiComp;
    }
}
