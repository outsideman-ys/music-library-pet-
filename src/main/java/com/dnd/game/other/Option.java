package com.dnd.game.other;

public class Option {
    private String value;
    private String label;
    private boolean selected;

    public Option(String value, String label, boolean selected) {
        this.value = value;
        this.label = label;
        this.selected = selected;
    }

    public String getValue() { return value; }
    public String getLabel() { return label; }
    public boolean isSelected() { return selected; }
}
