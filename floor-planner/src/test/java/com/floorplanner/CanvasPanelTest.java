package com.floorplanner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.awt.Point;
import java.lang.reflect.Field;
import java.util.List;

public class CanvasPanelTest {
    private CanvasPanel canvasPanel;
    
    @BeforeEach
    public void setUp() {
        canvasPanel = new CanvasPanel();
    }
    
    @Test
    public void testAddRoom() {
        // Add a room and verify it's added to the rooms list
        canvasPanel.addRoom(RoomType.BEDROOM, 100, 100);
        List<Room> rooms = getRooms();
        
        assertNotNull(rooms, "Rooms list should not be null");
        assertEquals(1, rooms.size(), "Should have one room");
        
        Room addedRoom = rooms.get(0);
        assertEquals(RoomType.BEDROOM, addedRoom.getType());
        assertEquals(100, addedRoom.getWidth());
        assertEquals(100, addedRoom.getHeight());
    }
    
    @Test
    public void testRoomCollisionPrevention() {
        // Add first room
        canvasPanel.addRoom(RoomType.BEDROOM, 100, 100);
        List<Room> rooms = getRooms();
        Room firstRoom = rooms.get(0);
        
        // Try to add a second room at the same position
        canvasPanel.addRoom(RoomType.BATHROOM, 100, 100);
        
        // The second room should be placed at a different position
        Room secondRoom = rooms.get(1);
        assertFalse(firstRoom.intersects(secondRoom), "Rooms should not overlap");
    }
    
    @Test
    public void testFindRoomAt() {
        // Add a room at a known position
        canvasPanel.addRoom(RoomType.BEDROOM, 100, 100);
        List<Room> rooms = getRooms();
        Room addedRoom = rooms.get(0);
        
        // Test point inside room
        Point insidePoint = new Point(addedRoom.getX() + 50, addedRoom.getY() + 50);
        Room foundRoom = findRoomAt(insidePoint);
        assertSame(addedRoom, foundRoom, "Should find the correct room");
        
        // Test point outside room
        Point outsidePoint = new Point(addedRoom.getX() + 200, addedRoom.getY() + 200);
        Room notFoundRoom = findRoomAt(outsidePoint);
        assertNull(notFoundRoom, "Should not find any room");
    }
    
    // Helper method to access private rooms field
    @SuppressWarnings("unchecked")
    private List<Room> getRooms() {
        try {
            Field roomsField = CanvasPanel.class.getDeclaredField("rooms");
            roomsField.setAccessible(true);
            return (List<Room>) roomsField.get(canvasPanel);
        } catch (Exception e) {
            fail("Failed to access rooms field: " + e.getMessage());
            return null;
        }
    }
    
    // Helper method to access private findRoomAt method
    private Room findRoomAt(Point p) {
        try {
            java.lang.reflect.Method method = CanvasPanel.class.getDeclaredMethod("findRoomAt", Point.class);
            method.setAccessible(true);
            return (Room) method.invoke(canvasPanel, p);
        } catch (Exception e) {
            fail("Failed to invoke findRoomAt method: " + e.getMessage());
            return null;
        }
    }
}