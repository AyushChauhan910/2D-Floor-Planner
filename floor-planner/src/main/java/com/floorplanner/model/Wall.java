package com.floorplanner.model;

import com.floorplanner.Room;

/**
 * Represents a wall between two rooms in the floor plan.
 * A wall can be either vertical or horizontal and has a specific position and length.
 */
public class Wall {
    private Room room1, room2;
    private WallType type;
    private int position; // x for vertical, y for horizontal
    private int start, end;
    
    /**
     * Creates a new wall between two rooms.
     *
     * @param room1 The first room adjacent to the wall
     * @param room2 The second room adjacent to the wall
     * @param type The orientation of the wall (VERTICAL or HORIZONTAL)
     * @param start The starting coordinate of the wall
     * @param end The ending coordinate of the wall
     */
    public Wall(Room room1, Room room2, WallType type, int start, int end) {
        this.room1 = room1;
        this.room2 = room2;
        this.type = type;
        this.start = start;
        this.end = end;
        
        // Calculate position based on wall type
        if (type == WallType.VERTICAL) {
            this.position = room1.getX() + (room1.getX() < room2.getX() ? room1.getWidth() : -1);
        } else {
            this.position = room1.getY() + (room1.getY() < room2.getY() ? room1.getHeight() : -1);
        }
    }
    
    /**
     * Gets the type of the wall (vertical or horizontal).
     * @return The wall type
     */
    public WallType getType() { return type; }

    /**
     * Gets the position of the wall.
     * For vertical walls, this is the x-coordinate.
     * For horizontal walls, this is the y-coordinate.
     * @return The wall position
     */
    public int getPosition() { return position; }

    /**
     * Gets the starting coordinate of the wall.
     * @return The start coordinate
     */
    public int getStart() { return start; }

    /**
     * Gets the ending coordinate of the wall.
     * @return The end coordinate
     */
    public int getEnd() { return end; }

    /**
     * Gets the first room adjacent to the wall.
     * @return The first room
     */
    public Room getRoom1() { return room1; }

    /**
     * Gets the second room adjacent to the wall.
     * @return The second room
     */
    public Room getRoom2() { return room2; }
}