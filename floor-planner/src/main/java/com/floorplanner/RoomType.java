package com.floorplanner;

import java.awt.Color;

public enum RoomType {
    BEDROOM(new Color(142, 190, 238), "Bedroom"),       // Soft blue
    BATHROOM(new Color(255, 183, 197), "Bathroom"),     // Soft pink
    KITCHEN(new Color(152, 223, 175), "Kitchen"),       // Mint green
    LIVING_ROOM(new Color(255, 206, 168), "Living Room"), // Warm peach
    DINING_ROOM(new Color(216, 181, 232), "Dining Room"); // Soft purple
    
    private final Color color;
    private final String displayName;
    
    RoomType(Color color, String displayName) {
        this.color = color;
        this.displayName = displayName;
    }
    
    public Color getColor() {
        return color;
    }
    
    @Override
    public String toString() {
        return displayName;
    }
    
    public static RoomType fromString(String text) {
        for (RoomType type : RoomType.values()) {
            if (type.displayName.equalsIgnoreCase(text)) {
                return type;
            }
        }
        throw new IllegalArgumentException("No room type with display name: " + text);
    }
}