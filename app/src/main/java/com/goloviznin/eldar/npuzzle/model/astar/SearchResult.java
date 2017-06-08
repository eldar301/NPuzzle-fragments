package com.goloviznin.eldar.npuzzle.model.astar;

import java.util.List;

public class SearchResult<S extends State> {

    private int openedListSize;
    private int closedListSize;
    private List<S> stack;

    void setOpenedListSize(int openedListSize) {
        this.openedListSize = openedListSize;
    }

    void setClosedListSize(int closedListSize) {
        this.closedListSize = closedListSize;
    }

    public List<S> getStack() {
        return stack;
    }

    void setStack(List<S> stack) {
        this.stack = stack;
    }

    @Override
    public String toString() {
        return String.format("Resources: [opened list size] = %1$d; [closed list size] = %2$d; [stack size] = %3$d", openedListSize, closedListSize, stack.size());
    }
}
