package com.floorplanner;

import java.awt.geom.Rectangle2D;

public class Furniture {
    private int x;
    private int y;
    private int width;
    private int height;
    private FurnitureType type;
    private Rectangle2D.Double bounds;
    private double rotation; // in degrees
    
    public Furniture(int x, int y, FurnitureType type) {
        this.x = x;
        this.y = y;
        this.type = type;
        this.width = type.getDefaultWidth();
        this.height = type.getDefaultHeight();
        this.bounds = new Rectangle2D.Double(x, y, width, height);
        this.rotation = 0;
    }
    
    public boolean intersects(Rectangle2D.Double other) {
        return bounds.intersects(other);
    }
    
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
        this.bounds.x = x;
        this.bounds.y = y;
    }
    
    public void rotate(double degrees) {
        this.rotation = (this.rotation + degrees) % 360;
        if (this.rotation < 0) {
            this.rotation += 360;
        }
        
        // Swap width and height if rotation is 90 or 270 degrees
        if ((int)rotation % 180 == 90) {
            int temp = width;
            width = height;
            height = temp;
            bounds.width = width;
            bounds.height = height;
        }
    }
    
    // Getters
    public int getX() { return x; }
    public int getY() { return y; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public FurnitureType getType() { return type; }
    public Rectangle2D.Double getBounds() { return bounds; }
    public double getRotation() { return rotation; }
}