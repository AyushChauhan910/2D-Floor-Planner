package com.floorplanner;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RoomTypeTest {
    
    @Test
    public void testRoomTypeColors() {
        assertNotNull(RoomType.BEDROOM.getColor(), "Bedroom color should not be null");
        assertNotNull(RoomType.BATHROOM.getColor(), "Bathroom color should not be null");
        assertNotNull(RoomType.KITCHEN.getColor(), "Kitchen color should not be null");
        assertNotNull(RoomType.LIVING_ROOM.getColor(), "Living room color should not be null");
        assertNotNull(RoomType.DINING_ROOM.getColor(), "Dining room color should not be null");
    }
    
    @Test
    public void testRoomTypeDisplayNames() {
        assertEquals("Bedroom", RoomType.BEDROOM.toString());
        assertEquals("Bathroom", RoomType.BATHROOM.toString());
        assertEquals("Kitchen", RoomType.KITCHEN.toString());
        assertEquals("Living Room", RoomType.LIVING_ROOM.toString());
        assertEquals("Dining Room", RoomType.DINING_ROOM.toString());
    }
    
    @Test
    public void testFromString() {
        assertEquals(RoomType.BEDROOM, RoomType.fromString("Bedroom"));
        assertEquals(RoomType.BATHROOM, RoomType.fromString("Bathroom"));
        assertEquals(RoomType.KITCHEN, RoomType.fromString("Kitchen"));
        assertEquals(RoomType.LIVING_ROOM, RoomType.fromString("Living Room"));
        assertEquals(RoomType.DINING_ROOM, RoomType.fromString("Dining Room"));
    }
    
    @Test
    public void testFromStringCaseInsensitive() {
        assertEquals(RoomType.BEDROOM, RoomType.fromString("bedroom"));
        assertEquals(RoomType.BATHROOM, RoomType.fromString("BATHROOM"));
    }
    
    @Test
    public void testFromStringInvalid() {
        assertThrows(IllegalArgumentException.class, () -> {
            RoomType.fromString("Invalid Room Type");
        });
    }
}