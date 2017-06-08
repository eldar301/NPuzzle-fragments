package com.goloviznin.eldar.npuzzle.model.game.calculator;

import com.goloviznin.eldar.npuzzle.model.game.Field;

public interface HeuristicCalculator {
    int getHeuristicWeight(Field from, Field to);
}
