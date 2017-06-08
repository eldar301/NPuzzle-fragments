package com.goloviznin.eldar.npuzzle.model.game;

import com.goloviznin.eldar.npuzzle.model.astar.State;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Random;

public class Field extends State implements Serializable {

    private int[] field;
    private int size;
    private int zeroIndex;

    public Field(int size, int[] field) throws Exception {
        if (field == null) {
            throw new Exception("[field] could not be null");
        }
        if (size * size != field.length) {
            throw new Exception(String.format("Wrong given [size = %1$d] with [field.length = %2$d]", size, field.length));
        }

        int zerosCount = 0;
        for (int idx = 0; idx < field.length; ++idx) {
            if (field[idx] == 0) {
                zeroIndex = idx;
                ++zerosCount;
            }
        }

        if (zerosCount != 1) {
            throw new Exception(String.format("There are should be only one zero instance, but found [%1$d]", zerosCount));
        }

        this.size = size;
        this.field = field;
    }

    public Field(Field copyFrom) {
        this.field = Arrays.copyOf(copyFrom.field, copyFrom.field.length);
        this.size = copyFrom.size;
        this.zeroIndex = copyFrom.zeroIndex;
    }

    public int[] getField() {
        return Arrays.copyOf(field, field.length);
    }

    public int getAt(int index) {
        return field[index];
    }

    public int getSize() {
        return size;
    }

    public int indexOf(int value) {
        for (int idx = 0; idx < field.length; ++idx) {
            if (field[idx] == value) return idx;
        }
        return -1;
    }

    public int indexOfX(int value) {
        return indexOf(value) % size;
    }

    public int indexOfY(int value) {
        return indexOf(value) / size;
    }

    private void swap(int leftIndex, int rightIndex) {
        int temp = field[leftIndex];
        field[leftIndex] = field[rightIndex];
        field[rightIndex] = temp;
    }

    boolean turnTo(int num) {
        int numX = indexOfX(num);
        int numY = indexOfY(num);
        int zeroX = zeroIndex % size;
        int zeroY = zeroIndex / size;
        return makeTurn(numX - zeroX, numY - zeroY);
    }

    boolean turnRight() {
        return makeTurn(1, 0);
    }

    boolean turnUp() {
        return makeTurn(0, -1);
    }

    boolean turnLeft() {
        return makeTurn(-1, 0);
    }

    boolean turnDown() {
        return makeTurn(0, 1);
    }

    private boolean makeTurn(int xOffset, int yOffset) {
        if (Math.abs(xOffset) + Math.abs(yOffset) > 1) {
            return false;
        }
        if (xOffset == 0 && yOffset == 0) {
            return false;
        }
        int estimatedZeroX = zeroIndex % size + xOffset;
        int estimatedZeroY = zeroIndex / size + yOffset;
        if (estimatedZeroX >= 0 && estimatedZeroX < size && estimatedZeroY >= 0 && estimatedZeroY < size) {
            int newZeroIndex = estimatedZeroX + estimatedZeroY * size;
            swap(zeroIndex, newZeroIndex);
            zeroIndex = newZeroIndex;
            return true;
        } else {
            return false;
        }
    }

    void shuffle(int times) {
        if (times <= 0) {
            return;
        }
        int count = 0;
        Random generator = new Random(System.currentTimeMillis());
        while (count != times) {
            boolean isTurnSuccessful = false;
            switch (generator.nextInt() % 4) {
                case 0: isTurnSuccessful = turnDown();
                        break;
                case 1: isTurnSuccessful = turnLeft();
                        break;
                case 2: isTurnSuccessful = turnRight();
                        break;
                case 3: isTurnSuccessful = turnUp();
                        break;
            }
            if (isTurnSuccessful) {
                ++count;
            }
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Field)) {
            return false;
        }

        Field another = (Field)obj;

        return (this.size == another.size) && Arrays.equals(this.field, another.field);
    }

    @Override
    public int hashCode() {
        return size + Arrays.hashCode(field);
    }

    @Override
    public String toString() {
        StringBuilder out = new StringBuilder();
        for (int idx = 0; idx < size * size; ++idx) {
            if (idx % size == 0) {
                out.append("\n");
            }
            out.append(field[idx]).append(" ");
        }
        return out.toString();
    }
}