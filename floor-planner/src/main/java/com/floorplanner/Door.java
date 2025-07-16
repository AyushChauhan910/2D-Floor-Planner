package com.floorplanner;

import java.awt.*;
import java.awt.geom.*;

public class Door {
    private Room room1;
    private Room room2;
    private int position; // Position along wall (0-100%)
    private static final int WIDTH = 40;
    private static final int HEIGHT = 10;
    
    public Door(Room room1, Room room2, int position) {
        this.room1 = room1;
        this.room2 = room2;
        this.position = position;
    }
    
    public void draw(Graphics2D g2d) {
        // Determine which wall the door is on
        Point doorPos = calculatePosition();
        
        // Draw door as a gap in the wall
        g2d.setColor(new Color(139, 69, 19)); // Brown door color
        g2d.fillRect(doorPos.x, doorPos.y, WIDTH, HEIGHT);
        
        // Draw door handle
        g2d.setColor(Color.DARK_GRAY);
        g2d.fillOval(doorPos.x + WIDTH - 10, doorPos.y + HEIGHT/2, 5, 5);
    }
    
    private Point calculatePosition() {
        // Calculate door position based on room walls
        // Implementation depends on your room/wall representation
        return new Point(0, 0); // Placeholder
    }
    
    public boolean intersects(Door other) {
        // Check if this door overlaps with another door
        return false; // Placeholder
    }

    // Add getters
    public Room getRoom1() { return room1; }
    public Room getRoom2() { return room2; }
    public int getPosition() { return position; }
}