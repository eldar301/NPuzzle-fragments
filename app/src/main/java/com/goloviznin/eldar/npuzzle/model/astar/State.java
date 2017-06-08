package com.goloviznin.eldar.npuzzle.model.astar;

public class State {

    private int g;
    private int h;

    private State parent;

    int getG() {
        return g;
    }

    void setG(int g) {
        this.g = g;
    }

    void setH(int h) {
        this.h = h;
    }

    int getF() {
        return g + h;
    }

    State getParent() {
        return parent;
    }

    void setParent(State parent) {
        this.parent = parent;
    }

    void clear() {
        this.g = 0;
        this.h = 0;
        this.parent = null;
    }
}