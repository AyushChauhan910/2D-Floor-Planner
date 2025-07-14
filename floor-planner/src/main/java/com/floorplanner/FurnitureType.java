package com.floorplanner;

import java.awt.Color;

public enum FurnitureType {
    BED(new Color(255, 182, 193), "Bed", 100, 200),
    SOFA(new Color(176, 196, 222), "Sofa", 160, 80),
    TABLE(new Color(210, 180, 140), "Table", 120, 80),
    CHAIR(new Color(188, 143, 143), "Chair", 40, 40),
    DESK(new Color(222, 184, 135), "Desk", 120, 60),
    WARDROBE(new Color(205, 133, 63), "Wardrobe", 100, 60),
    BOOKSHELF(new Color(139, 69, 19), "Bookshelf", 100, 30),
    TV_STAND(new Color(160, 82, 45), "TV Stand", 120, 40),
    DINING_TABLE(new Color(210, 105, 30), "Dining Table", 160, 100),
    COFFEE_TABLE(new Color(244, 164, 96), "Coffee Table", 80, 60);
    
    private final Color color;
    private final String displayName;
    private final int defaultWidth;
    private final int defaultHeight;
    
    FurnitureType(Color color, String displayName, int defaultWidth, int defaultHeight) {
        this.color = color;
        this.displayName = displayName;
        this.defaultWidth = defaultWidth;
        this.defaultHeight = defaultHeight;
    }
    
    public Color getColor() {
        return color;
    }
    
    public String getDisplayName() {
        return displayName;
    }
    
    public int getDefaultWidth() {
        return defaultWidth;
    }
    
    public int getDefaultHeight() {
        return defaultHeight;
    }
    
    @Override
    public String toString() {
        return displayName;
    }
    
    public static FurnitureType fromString(String name) {
        for (FurnitureType type : values()) {
            if (type.displayName.equalsIgnoreCase(name)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown furniture type: " + name);
    }
}