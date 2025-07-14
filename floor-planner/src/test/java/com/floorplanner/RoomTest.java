package com.floorplanner;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RoomTest {
    
    @Test
    public void testRoomCreation() {
        Room room = new Room(10, 20, 100, 150, RoomType.BEDROOM);
        assertEquals(10, room.getX());
        assertEquals(20, room.getY());
        assertEquals(100, room.getWidth());
        assertEquals(150, room.getHeight());
        assertEquals(RoomType.BEDROOM, room.getType());
    }
    
    @Test
    public void testRoomIntersection() {
        Room room1 = new Room(0, 0, 100, 100, RoomType.BEDROOM);
        Room room2 = new Room(50, 50, 100, 100, RoomType.BATHROOM);
        Room room3 = new Room(200, 200, 100, 100, RoomType.KITCHEN);
        
        assertTrue(room1.intersects(room2), "Overlapping rooms should intersect");
        assertFalse(room1.intersects(room3), "Non-overlapping rooms should not intersect");
    }
    
    @Test
    public void testRoomPositionUpdate() {
        Room room = new Room(0, 0, 100, 100, RoomType.BEDROOM);
        room.setPosition(50, 75);
        
        assertEquals(50, room.getX());
        assertEquals(75, room.getY());
        assertEquals(50, room.getBounds().getX());
        assertEquals(75, room.getBounds().getY());
    }
}