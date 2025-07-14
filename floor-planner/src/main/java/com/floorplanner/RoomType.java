package com.floorplanner;

import java.awt.Color;

public enum RoomType {
    BEDROOM(new Color(144, 238, 144), "Bedroom"),      // Light green
    BATHROOM(new Color(135, 206, 235), "Bathroom"),    // Sky blue
    KITCHEN(new Color(255, 182, 193), "Kitchen"),      // Light red/pink
    LIVING_ROOM(new Color(255, 218, 185), "Living Room"), // Peach
    DINING_ROOM(new Color(255, 222, 173), "Dining Room"); // Light orange
    
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