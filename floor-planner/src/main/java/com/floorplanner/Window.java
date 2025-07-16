package com.floorplanner;

import java.awt.*;
import java.awt.geom.*;

public class Window {
    private Room room;
    private int position; // Position along wall (0-100%)
    private static final int WIDTH = 30;
    private static final int HEIGHT = 10;
    
    public Window(Room room, int position) {
        this.room = room;
        this.position = position;
    }
    
    public void draw(Graphics2D g2d) {
        Point windowPos = calculatePosition();
        
        // Save original stroke
        Stroke originalStroke = g2d.getStroke();
        
        // Set dashed stroke for window
        float[] dashPattern = {5, 5};
        g2d.setStroke(new BasicStroke(1.5f, BasicStroke.CAP_BUTT, 
                                    BasicStroke.JOIN_MITER, 10, dashPattern, 0));
        
        // Draw window frame
        g2d.setColor(Color.LIGHT_GRAY);
        g2d.drawRect(windowPos.x, windowPos.y, WIDTH, HEIGHT);
        
        // Draw window divider
        g2d.drawLine(windowPos.x + WIDTH/2, windowPos.y, 
                    windowPos.x + WIDTH/2, windowPos.y + HEIGHT);
        
        // Restore original stroke
        g2d.setStroke(originalStroke);
    }
    
    private Point calculatePosition() {
        // Calculate window position based on room walls
        // Implementation depends on your room/wall representation
        return new Point(0, 0); // Placeholder
    }
    
    public boolean intersects(Window other) {
        // Check if this window overlaps with another window
        return false; // Placeholder
    }

    // Add getters
    public Room getRoom() { return room; }
    public int getPosition() { return position; }
}