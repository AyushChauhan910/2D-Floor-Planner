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
        double oldRotation = this.rotation;
        this.rotation = (this.rotation + degrees) % 360;
        if (this.rotation < 0) {
            this.rotation += 360;
        }
        int oldNum = (int) (oldRotation / 90) % 2;
        int newNum = (int) (this.rotation / 90) % 2;
        if (oldNum != newNum) {
            double oldCenterX = x + width / 2.0;
            double oldCenterY = y + height / 2.0;
            int temp = width;
            width = height;
            height = temp;
            bounds.width = width;
            bounds.height = height;
            x = (int) Math.round(oldCenterX - width / 2.0);
            y = (int) Math.round(oldCenterY - height / 2.0);
            bounds.x = x;
            bounds.y = y;
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
    
    public void setRotation(int degrees) {
        double oldRotation = this.rotation;
        this.rotation = degrees % 360;
        if (this.rotation < 0) {
            this.rotation += 360;
        }
        int oldNum = (int) (oldRotation / 90) % 2;
        int newNum = (int) (this.rotation / 90) % 2;
        if (oldNum != newNum) {
            double oldCenterX = x + width / 2.0;
            double oldCenterY = y + height / 2.0;
            int temp = width;
            width = height;
            height = temp;
            bounds.width = width;
            bounds.height = height;
            x = (int) Math.round(oldCenterX - width / 2.0);
            y = (int) Math.round(oldCenterY - height / 2.0);
            bounds.x = x;
            bounds.y = y;
        }
    }
    
    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;
        this.bounds.width = width;
        this.bounds.height = height;
    }
}