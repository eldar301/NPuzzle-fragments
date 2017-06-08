package com.goloviznin.eldar.npuzzle.model.game;

import com.goloviznin.eldar.npuzzle.model.astar.StateHandler;
import com.goloviznin.eldar.npuzzle.model.game.calculator.ExtendedHeuristicCalculator;
import com.goloviznin.eldar.npuzzle.model.game.calculator.HeuristicCalculator;

import java.util.LinkedList;
import java.util.List;

public class FieldHandler implements StateHandler<Field> {

    private HeuristicCalculator calculator;

    public FieldHandler() {
        calculator = new ExtendedHeuristicCalculator();
    }

    @Override
    public List<Field> getNeighbours(Field parent) {
        List<Field> neighbours = new LinkedList<>();

        Field neighbour;

        neighbour = new Field(parent);
        if (neighbour.turnUp()) {
            neighbours.add(neighbour);
            neighbour = new Field(parent);
        }
        if (neighbour.turnRight()) {
            neighbours.add(neighbour);
            neighbour = new Field(parent);
        }
        if (neighbour.turnDown()) {
            neighbours.add(neighbour);
            neighbour = new Field(parent);
        }
        if (neighbour.turnLeft()) {
            neighbours.add(neighbour);
        }

        return neighbours;
    }

    public void setHeuristicCalculator(HeuristicCalculator calculator) {
        this.calculator = calculator;
    }

    @Override
    public int getHeuristicWeight(Field from, Field to) {
        return calculator.getHeuristicWeight(from, to);
    }
}