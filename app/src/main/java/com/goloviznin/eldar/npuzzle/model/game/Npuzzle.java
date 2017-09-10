package com.goloviznin.eldar.npuzzle.model.game;

import com.goloviznin.eldar.npuzzle.model.astar.SearchAlgorithm;
import com.goloviznin.eldar.npuzzle.model.astar.SearchResult;

import java.io.Serializable;
import java.util.List;

public class NPuzzle implements Serializable {

    private Field currentField;
    private final Field finiteField;

    private NPuzzle(int fieldSize, int[] finiteArrangement) {
        finiteField = new Field(fieldSize, finiteArrangement);
    }

    public static NPuzzle newInstance(FieldType type, int size) {
        NPuzzle npuzzle;
        switch (type) {
            case CONVENTIONAL:
                npuzzle = createConventional(size);
                break;
            case SPIRAL:
                npuzzle = createSpiral(size);
                break;
            default:
                npuzzle = createConventional(size);
                break;
        }
        npuzzle.generateInitialField();
        return npuzzle;
    }

    private static NPuzzle createConventional(int size) {
        int[] finiteArray = new int[size * size];
        for (int idx = 0; idx < finiteArray.length; ++idx) {
            finiteArray[idx] = (idx + 1) % finiteArray.length;
        }
        return new NPuzzle(size, finiteArray);
    }

    private static NPuzzle createSpiral(int size) {
        int[] finiteArray = new int[size * size];
        final int RIGHT = 1;
        final int LEFT = -1;
        final int UP = -size;
        final int DOWN = size;
        int dir = RIGHT;
        int pos = 0;
        for (int idx = 0; idx < finiteArray.length; ++idx) {
            finiteArray[pos] = (idx + 1) % finiteArray.length;
            int nextPos = pos + dir;
            if (pos == size - 1 || pos == size * size - 1 || pos == size * (size - 1) || finiteArray[nextPos] != 0) {
                if (dir == RIGHT) {
                    dir = DOWN;
                } else if (dir == LEFT) {
                    dir = UP;
                } else if (dir == UP) {
                    dir = RIGHT;
                } else {
                    dir = LEFT;
                }
            }
            pos += dir;
        }
        return new NPuzzle(size, finiteArray);
    }

    private void generateInitialField() {
        currentField = new Field(finiteField);
        currentField.shuffle();
    }

    public Field getCurrentField() {
        return currentField;
    }

    public Field getFiniteField() {
        return finiteField;
    }

    public boolean turnRight() {
        return currentField.turnRight();
    }

    public boolean turnDown() {
        return currentField.turnDown();
    }

    public boolean turnLeft() {
        return currentField.turnLeft();
    }

    public boolean turnUp() {
        return currentField.turnUp();
    }

    public boolean turnTo(int num) {
        return currentField.turnTo(num);
    }

    public boolean isSolved() {
        return finiteField.equals(currentField);
    }

    public List<Field> solve() {
        SearchAlgorithm<Field, FieldHandler> solver = new SearchAlgorithm<>(new FieldHandler());
        SearchResult<Field> solution =  solver.doSearch(currentField, finiteField);
        currentField = new Field(finiteField);
        return solution.getStack();
    }
}