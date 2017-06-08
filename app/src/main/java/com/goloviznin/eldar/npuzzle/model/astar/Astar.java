package com.goloviznin.eldar.npuzzle.model.astar;

import java.util.LinkedList;
import java.util.List;

public class Astar<S extends State, SH extends StateHandler<S>> {

    private SH stateHandler;

    public Astar(SH stateHandler) {
        this.stateHandler = stateHandler;
    }

    public SearchResult<S> doSearch(S from, S to) {
        List<S> openedList = new LinkedList<>();
        List<S> closedList = new LinkedList<>();
        from.clear();
        to.clear();
        openedList.add(from);
        from.setG(0);
        from.setH(stateHandler.getHeuristicWeight(from, to));
        while (!openedList.isEmpty()) {
            S current = openedList.get(0);
            int minF = current.getF();
            for (S state : openedList) {
                if (state.getF() < minF) {
                    minF = state.getF();
                    current = state;
                }
            }
            if (current.equals(to)) {
                return buildPath(current, openedList.size(), closedList.size());
            }
            openedList.remove(current);
            closedList.add(current);

            List<S> neighbours = stateHandler.getNeighbours(current);
            for (S neighbour : neighbours) {
                if (closedList.contains(neighbour)) {
                    continue;
                }
                int estimatedG = current.getG() + 1;
                if (neighbour.getParent() != null) {
                    if (neighbour.getG() > estimatedG) {
                        neighbour.setParent(current);
                        neighbour.setG(estimatedG);
                        neighbour.setH(stateHandler.getHeuristicWeight(neighbour, to));
                    }
                } else {
                    neighbour.setParent(current);
                    neighbour.setG(estimatedG);
                    neighbour.setH(stateHandler.getHeuristicWeight(neighbour, to));
                    openedList.add(neighbour);
                }
            }
        }
        return null;
    }

    /*
    * В данной реализации алгоритма в метод setParent() экземпляра класса S всегда передается экземпляр класса S
    */
    @SuppressWarnings("unchecked")
    private SearchResult<S> buildPath(S solution, int openedListSize, int closedListSize) {
        SearchResult<S> result = new SearchResult<>();
        LinkedList<S> stateList = new LinkedList<>();
        State stacked = solution;
        while (stacked != null) {
            stateList.addFirst((S)stacked);
            stacked = stacked.getParent();
        }
        result.setOpenedListSize(openedListSize);
        result.setClosedListSize(closedListSize);
        result.setStack(stateList);
        return result;
    }

}
