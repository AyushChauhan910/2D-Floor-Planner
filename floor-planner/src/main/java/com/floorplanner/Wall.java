package com.floorplanner;

public class Wall {
    private Room room1;
    private Room room2;
    private WallType type;
    private int start;
    private int end;
    private int position;
    
    public Wall(Room room1, Room room2, WallType type, int start, int end) {
        this.room1 = room1;
        this.room2 = room2;
        this.type = type;
        this.start = start;
        this.end = end;
        calculatePosition();
    }
    
    private void calculatePosition() {
        if (type == WallType.VERTICAL) {
            if (room1.getX() + room1.getWidth() == room2.getX()) {
                position = room1.getX() + room1.getWidth();
            } else {
                position = room2.getX() + room2.getWidth();
            }
        } else {
            if (room1.getY() + room1.getHeight() == room2.getY()) {
                position = room1.getY() + room1.getHeight();
            } else {
                position = room2.getY() + room2.getHeight();
            }
        }
    }
    
    // Getters
    public Room getRoom1() { return room1; }
    public Room getRoom2() { return room2; }
    public WallType getType() { return type; }
    public int getStart() { return start; }
    public int getEnd() { return end; }
    public int getPosition() { return position; }
}