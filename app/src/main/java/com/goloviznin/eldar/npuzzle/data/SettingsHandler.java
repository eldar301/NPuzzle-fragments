package com.goloviznin.eldar.npuzzle.data;

import com.goloviznin.eldar.npuzzle.model.game.FieldType;

public interface SettingsHandler {
    void load();
    int getFieldSize();
    FieldType getFieldType();
    int getCellBackColor();
}
