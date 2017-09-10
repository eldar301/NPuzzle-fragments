package com.goloviznin.eldar.npuzzle.model.astar;

import java.util.List;

public class SearchResult<S extends State> {
    private List<S> stack;

    public List<S> getStack() {
        return stack;
    }

    void setStack(List<S> stack) {
        this.stack = stack;
    }
}
