package com.goloviznin.eldar.npuzzle.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;

import com.goloviznin.eldar.npuzzle.R;
import com.goloviznin.eldar.npuzzle.model.game.FieldType;

public class SettingsHandlerDefault implements SettingsHandler {

    private final SharedPreferences preferences;
    private final Context context;

    private int fieldSize;
    private FieldType fieldType;
    private int cellBackColor;

    public SettingsHandlerDefault(Context context, SharedPreferences preferences) {
        this.preferences = preferences;
        this.context = context;
    }

    private final static String CONVENTIONAL = "conventional";
    private final static String SPIRAL = "spiral";

    @Override
    public void load() {
        fieldSize = Integer.parseInt(preferences.getString(context.getString(R.string.preferences_field_size),
                context.getString(R.string.default_preferences_field_size)));
        String type = preferences.getString(context.getString(R.string.preferences_field_type),
                context.getString(R.string.default_preferences_field_type));
        switch (type) {
            case CONVENTIONAL:
                fieldType = FieldType.CONVENTIONAL;
                break;
            case SPIRAL:
                fieldType = FieldType.SPIRAL;
                break;
        }
        cellBackColor = Color.parseColor(preferences.getString(context.getString(R.string.preferences_cell_back),
                context.getString(R.string.default_preferences_cell_back)));
    }

    @Override
    public int getFieldSize() {
        return fieldSize;
    }

    @Override
    public FieldType getFieldType() {
        return fieldType;
    }

    @Override
    public int getCellBackColor() {
        return cellBackColor;
    }

}