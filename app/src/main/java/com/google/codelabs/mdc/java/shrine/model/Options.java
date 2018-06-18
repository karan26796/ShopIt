package com.google.codelabs.mdc.java.shrine.model;

public class Options {

    int drawableIcon;
    String title;

    public Options(int drawableIcon, String title) {
        this.drawableIcon = drawableIcon;
        this.title = title;
    }

    public Options() {

    }

    public int getDrawableIcon() {
        return drawableIcon;
    }

    public void setDrawableIcon(int drawableIcon) {
        this.drawableIcon = drawableIcon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
