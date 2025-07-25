package com.floorplanner;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class Room {
    private int x;
    private int y;
    private int width;
    private int height;
    private RoomType type;
    private Rectangle2D.Double bounds;
    
    public Room(int x, int y, int width, int height, RoomType type) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.type = type;
        this.bounds = new Rectangle2D.Double(x, y, width, height);
    }
    

    
    public boolean intersects(Room other) {
        return bounds.intersects(other.bounds);
    }
    
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
        this.bounds.x = x;
        this.bounds.y = y;
    }
    
    // Getters and setters
    public int getX() { return x; }
    public int getY() { return y; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public RoomType getType() { return type; }
    public Rectangle2D.Double getBounds() { return bounds; }
}