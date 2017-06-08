package com.goloviznin.eldar.npuzzle.model.game.calculator;

import com.goloviznin.eldar.npuzzle.model.game.Field;

public class ExtendedHeuristicCalculator implements HeuristicCalculator {

   /*
    * Возвращает сумму, в которой содержится общий суммарный путь,
    * который необходимо пройти каждой плитке от ее текущего до конечного положения.
    */
    @Override
    public int getHeuristicWeight(Field from, Field to) {
        if (from.getSize() == to.getSize()) {
            int size = from.getSize();
            int totalDistance = 0;
            for (int idx = 0; idx < size * size; ++idx) {
                int currentValue = from.getAt(idx);
                if (currentValue != to.getAt(idx)) {
                    totalDistance += getRoughDistance(from.indexOfX(currentValue), from.indexOfY(currentValue),
                                                        to.indexOfX(currentValue),   to.indexOfY(currentValue));
                }
            }
            return totalDistance;
        } else {
            return Integer.MIN_VALUE;
        }
    }

    private int getRoughDistance(int xFrom, int yFrom, int xTo, int yTo) {
        return Math.abs(xFrom - xTo) + Math.abs(yFrom - yTo);
    }
}
