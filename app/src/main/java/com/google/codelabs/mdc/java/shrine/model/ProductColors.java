package com.google.codelabs.mdc.java.shrine.model;

public class ProductColors {
    private int color;

    private boolean isSelected = false;

    public ProductColors(int color, boolean isSelected) {
        this.color = color;
        this.isSelected = isSelected;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
