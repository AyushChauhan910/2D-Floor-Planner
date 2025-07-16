package com.floorplanner;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

public class CanvasPanel extends JPanel {
    private static final long serialVersionUID = 1L;
    private List<Room> rooms;
    private List<Furniture> furniture;
    private Room selectedRoom;
    private Furniture selectedFurniture;
    private Point dragStart;
    private Point originalRoomPos;
    private Point originalFurniturePos;
    private static final int GRID_SIZE = 20;
    
    // Add fields for door and window management
    private List<Door> doors = new ArrayList<>();
    private List<Window> windows = new ArrayList<>();
    private List<Wall> walls = new ArrayList<>();
    
    public CanvasPanel() {
        rooms = new ArrayList<>();
        furniture = new ArrayList<>();
        setBackground(Color.WHITE);
        setupMouseListeners();
    }
    
    private void setupMouseListeners() {
        MouseAdapter mouseAdapter = new MouseAdapter() {
            private boolean isResizing = false;
            private int resizeHandle = -1; // 0: NW, 1: NE, 2: SE, 3: SW
            private Point resizeStart;
            private int originalWidth;
            private int originalHeight;

            @Override
            public void mousePressed(MouseEvent e) {
                if (e.isControlDown()) {
                    // Delete functionality
                    selectedFurniture = findFurnitureAt(e.getPoint());
                    if (selectedFurniture != null) {
                        furniture.remove(selectedFurniture);
                        selectedFurniture = null;
                        repaint();
                        return;
                    }
                    
                    selectedRoom = findRoomAt(e.getPoint());
                    if (selectedRoom != null) {
                        rooms.remove(selectedRoom);
                        selectedRoom = null;
                        detectWalls();
                        repaint();
                        return;
                    }
                    return;
                }

                selectedFurniture = findFurnitureAt(e.getPoint());
                if (selectedFurniture != null) {
                    // Check if clicking on resize handles
                    resizeHandle = getResizeHandle(e.getPoint(), selectedFurniture);
                    if (resizeHandle != -1) {
                        isResizing = true;
                        resizeStart = e.getPoint();
                        originalWidth = selectedFurniture.getWidth();
                        originalHeight = selectedFurniture.getHeight();
                    } else {
                        dragStart = e.getPoint();
                        originalFurniturePos = new Point(selectedFurniture.getX(), selectedFurniture.getY());
                    }
                } else {
                    selectedRoom = findRoomAt(e.getPoint());
                    if (selectedRoom != null) {
                        dragStart = e.getPoint();
                        originalRoomPos = new Point(selectedRoom.getX(), selectedRoom.getY());
                    }
                }
            }
            
            @Override
            public void mouseDragged(MouseEvent e) {
                if (selectedFurniture != null && isResizing) {
                    int dx = e.getX() - resizeStart.x;
                    int dy = e.getY() - resizeStart.y;
                    
                    // Snap to grid
                    dx = snapToGrid(dx);
                    dy = snapToGrid(dy);
                    
                    int newWidth = originalWidth;
                    int newHeight = originalHeight;
                    
                    switch (resizeHandle) {
                        case 0: // NW
                            newWidth = originalWidth - dx;
                            newHeight = originalHeight - dy;
                            if (newWidth >= GRID_SIZE && newHeight >= GRID_SIZE) {
                                selectedFurniture.setSize(newWidth, newHeight);
                                selectedFurniture.setPosition(
                                    selectedFurniture.getX() + dx,
                                    selectedFurniture.getY() + dy
                                );
                            }
                            break;
                        case 1: // NE
                            newWidth = originalWidth + dx;
                            newHeight = originalHeight - dy;
                            if (newWidth >= GRID_SIZE && newHeight >= GRID_SIZE) {
                                selectedFurniture.setSize(newWidth, newHeight);
                                selectedFurniture.setPosition(
                                    selectedFurniture.getX(),
                                    selectedFurniture.getY() + dy
                                );
                            }
                            break;
                        case 2: // SE
                            newWidth = originalWidth + dx;
                            newHeight = originalHeight + dy;
                            if (newWidth >= GRID_SIZE && newHeight >= GRID_SIZE) {
                                selectedFurniture.setSize(newWidth, newHeight);
                            }
                            break;
                        case 3: // SW
                            newWidth = originalWidth - dx;
                            newHeight = originalHeight + dy;
                            if (newWidth >= GRID_SIZE && newHeight >= GRID_SIZE) {
                                selectedFurniture.setSize(newWidth, newHeight);
                                selectedFurniture.setPosition(
                                    selectedFurniture.getX() + dx,
                                    selectedFurniture.getY()
                                );
                            }
                            break;
                    }
                    repaint();
                } else if (selectedRoom != null && dragStart != null) {
                    int dx = e.getX() - dragStart.x;
                    int dy = e.getY() - dragStart.y;
                    
                    int newX = snapToGrid(originalRoomPos.x + dx);
                    int newY = snapToGrid(originalRoomPos.y + dy);
                    
                    int oldX = selectedRoom.getX();
                    int oldY = selectedRoom.getY();
                    
                    selectedRoom.setPosition(newX, newY);
                    
                    if (checkCollisions(selectedRoom)) {
                        selectedRoom.setPosition(oldX, oldY);
                    }
                    
                    repaint();
                } else if (selectedFurniture != null && dragStart != null) {
                    int dx = e.getX() - dragStart.x;
                    int dy = e.getY() - dragStart.y;
                    
                    int newX = snapToGrid(originalFurniturePos.x + dx);
                    int newY = snapToGrid(originalFurniturePos.y + dy);
                    
                    selectedFurniture.setPosition(newX, newY);
                    repaint();
                }
            }
            
            @Override
            public void mouseReleased(MouseEvent e) {
                selectedRoom = null;
                selectedFurniture = null;
                dragStart = null;
                originalRoomPos = null;
                originalFurniturePos = null;
                isResizing = false;
                resizeHandle = -1;
                resizeStart = null;
            }
            
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && selectedFurniture != null) {
                    selectedFurniture.rotate(90);
                    repaint();
                }
            }
        };
        
        addMouseListener(mouseAdapter);
        addMouseMotionListener(mouseAdapter);
    }

    private int getResizeHandle(Point p, Furniture furniture) {
        int handleSize = 10;
        Rectangle2D.Double bounds = furniture.getBounds();
        
        // Check NW handle
        if (new Rectangle2D.Double(bounds.x - handleSize/2, bounds.y - handleSize/2, handleSize, handleSize).contains(p)) {
            return 0;
        }
        // Check NE handle
        if (new Rectangle2D.Double(bounds.x + bounds.width - handleSize/2, bounds.y - handleSize/2, handleSize, handleSize).contains(p)) {
            return 1;
        }
        // Check SE handle
        if (new Rectangle2D.Double(bounds.x + bounds.width - handleSize/2, bounds.y + bounds.height - handleSize/2, handleSize, handleSize).contains(p)) {
            return 2;
        }
        // Check SW handle
        if (new Rectangle2D.Double(bounds.x - handleSize/2, bounds.y + bounds.height - handleSize/2, handleSize, handleSize).contains(p)) {
            return 3;
        }
        return -1;
    }

    private Room findRoomAt(Point p) {
        for (Room room : rooms) {
            if (room.getBounds().contains(p)) {
                return room;
            }
        }
        return null;
    }
    
    private Furniture findFurnitureAt(Point p) {
        for (Furniture item : furniture) {
            if (item.getBounds().contains(p)) {
                return item;
            }
        }
        return null;
    }
    
    private boolean checkCollisions(Room room) {
        for (Room other : rooms) {
            if (other != room && room.intersects(other)) {
                return true;
            }
        }
        return false;
    }
    
    public void addRoom(RoomType type, int width, int height) {
        Point position = findNextAvailablePosition(width, height);
        Room newRoom = new Room(position.x, position.y, width, height, type);
        
        if (!checkCollisions(newRoom)) {
            rooms.add(newRoom);
            repaint();
        } else {
            JOptionPane.showMessageDialog(this,
                "Cannot place room: Overlapping with existing room",
                "Placement Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void addFurniture(FurnitureType type) {
        Point position = findNextAvailablePosition(type.getDefaultWidth(), type.getDefaultHeight());
        Furniture newFurniture = new Furniture(position.x, position.y, type);
        furniture.add(newFurniture);
        repaint();
    }
    
    private Point findNextAvailablePosition(int width, int height) {
        int x = GRID_SIZE;
        int y = GRID_SIZE;
        boolean positionFound = false;
        
        while (!positionFound) {
            Room testRoom = new Room(x, y, width, height, null);
            if (!checkCollisions(testRoom)) {
                positionFound = true;
            } else {
                x += GRID_SIZE;
                if (x + width > getWidth()) {
                    x = GRID_SIZE;
                    y += GRID_SIZE;
                }
            }
        }
        
        return new Point(x, y);
    }
    
    private int snapToGrid(int value) {
        return Math.round(value / (float) GRID_SIZE) * GRID_SIZE;
    }
    
    public enum RelativePosition {
        NORTH, SOUTH, EAST, WEST
    }
    
    public void addRoomRelative(Room baseRoom, RoomType type, int width, int height, 
                              RelativePosition position, int offset) {
        int x = baseRoom.getX();
        int y = baseRoom.getY();
        
        switch (position) {
            case NORTH:
                x += offset;
                y -= height;
                break;
            case SOUTH:
                x += offset;
                y += baseRoom.getHeight();
                break;
            case EAST:
                x += baseRoom.getWidth();
                y += offset;
                break;
            case WEST:
                x -= width;
                y += offset;
                break;
        }
        
        Room newRoom = new Room(x, y, width, height, type);
        
        if (!checkCollisions(newRoom)) {
            rooms.add(newRoom);
            detectWalls(); // Update walls when adding rooms
            repaint();
        } else {
            JOptionPane.showMessageDialog(this,
                "Cannot place room: Overlapping with existing room",
                "Placement Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public Room getSelectedRoom() {
        return selectedRoom;
    }
    
    public String savePlan() {
        StringBuilder sb = new StringBuilder();
        
        // Save rooms
        sb.append("ROOMS:\n");
        for (Room room : rooms) {
            sb.append(String.format("%s,%d,%d,%d,%d\n", 
                room.getType(), room.getX(), room.getY(), 
                room.getWidth(), room.getHeight()));
        }
        
        // Save furniture
        sb.append("FURNITURE:\n");
        for (Furniture item : furniture) {
            sb.append(String.format("%s,%d,%d,%d\n", 
                item.getType(), item.getX(), item.getY(), 
                item.getRotation()));
        }
        
        // Save doors
        sb.append("DOORS:\n");
        for (Door door : doors) {
            sb.append(String.format("%d,%d,%d\n", 
                rooms.indexOf(door.getRoom1()), 
                door.getRoom2() != null ? rooms.indexOf(door.getRoom2()) : -1,
                door.getPosition()));
        }
        
        // Save windows
        sb.append("WINDOWS:\n");
        for (Window window : windows) {
            sb.append(String.format("%d,%d\n", 
                rooms.indexOf(window.getRoom()), 
                window.getPosition()));
        }
        
        return sb.toString();
    }
    
    public void loadPlan(String planData) {
        // Clear existing data
        rooms.clear();
        furniture.clear();
        doors.clear();
        windows.clear();
        
        String[] lines = planData.split("\\n");
        String currentSection = "";
        
        for (String line : lines) {
            if (line.startsWith("ROOMS:") || line.startsWith("FURNITURE:") || 
                line.startsWith("DOORS:") || line.startsWith("WINDOWS:")) {
                currentSection = line.replace(":", "");
                continue;
            }
            
            if (line.trim().isEmpty()) continue;
            
            try {
                switch (currentSection) {
                    case "ROOMS":
                        String[] roomParts = line.split(",");
                        RoomType type = RoomType.valueOf(roomParts[0]);
                        int x = Integer.parseInt(roomParts[1]);
                        int y = Integer.parseInt(roomParts[2]);
                        int width = Integer.parseInt(roomParts[3]);
                        int height = Integer.parseInt(roomParts[4]);
                        rooms.add(new Room(x, y, width, height, type));
                        break;
                        
                    case "FURNITURE":
                        String[] furnitureParts = line.split(",");
                        FurnitureType furnitureType = FurnitureType.valueOf(furnitureParts[0]);
                        int fx = Integer.parseInt(furnitureParts[1]);
                        int fy = Integer.parseInt(furnitureParts[2]);
                        int rotation = Integer.parseInt(furnitureParts[3]);
                        Furniture f = new Furniture(fx, fy, furnitureType);
                        f.setRotation(rotation);
                        furniture.add(f);
                        break;
                        
                    case "DOORS":
                        String[] doorParts = line.split(",");
                        int room1Index = Integer.parseInt(doorParts[0]);
                        int room2Index = Integer.parseInt(doorParts[1]);
                        int doorPos = Integer.parseInt(doorParts[2]);
                        
                        Room room1 = rooms.get(room1Index);
                        Room room2 = room2Index >= 0 ? rooms.get(room2Index) : null;
                        doors.add(new Door(room1, room2, doorPos));
                        break;
                        
                    case "WINDOWS":
                        String[] windowParts = line.split(",");
                        int windowRoomIndex = Integer.parseInt(windowParts[0]);
                        int windowPos = Integer.parseInt(windowParts[1]);
                        windows.add(new Window(rooms.get(windowRoomIndex), windowPos));
                        break;
                }
            } catch (Exception e) {
                System.err.println("Error parsing line: " + line);
            }
        }
        
        detectWalls();
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        
        // Draw walls between rooms
        g2d.setColor(new Color(100, 100, 100));
        g2d.setStroke(new BasicStroke(2));
        
        for (Wall wall : walls) {
            if (wall.getType() == WallType.VERTICAL) {
                g2d.drawLine(wall.getPosition(), wall.getStart(), wall.getPosition(), wall.getEnd());
            } else {
                g2d.drawLine(wall.getStart(), wall.getPosition(), wall.getEnd(), wall.getPosition());
            }
        }
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        
        drawGrid(g2d);
        
        // Draw rooms
        for (Room room : rooms) {
            Color roomColor = room.getType().getColor();
            g2d.setColor(new Color(roomColor.getRed(), roomColor.getGreen(), roomColor.getBlue(), 230));
            g2d.fillRoundRect(room.getX(), room.getY(), room.getWidth(), room.getHeight(), 10, 10);

            g2d.setColor(new Color(100, 100, 100, 150));
            g2d.setStroke(new BasicStroke(1.5f));
            g2d.drawRoundRect(room.getX(), room.getY(), room.getWidth(), room.getHeight(), 10, 10);
            
            // Draw doors and windows for rooms
            drawRoomFeatures(g2d, room);
            
            String roomType = room.getType().getDisplayName();
            g2d.setFont(new Font("Segoe UI", Font.BOLD, 14));
            FontMetrics fm = g2d.getFontMetrics();
            int textX = room.getX() + (room.getWidth() - fm.stringWidth(roomType)) / 2;
            int textY = room.getY() + (room.getHeight() + fm.getAscent()) / 2;
            
            g2d.setColor(new Color(0, 0, 0, 50));
            g2d.drawString(roomType, textX + 1, textY + 1);
            
            g2d.setColor(new Color(51, 51, 51));
            g2d.drawString(roomType, textX, textY);
        }
        
        // Draw furniture
        for (Furniture item : furniture) {
            AffineTransform oldTransform = g2d.getTransform();
            
            g2d.translate(item.getX() + item.getWidth()/2, item.getY() + item.getHeight()/2);
            g2d.rotate(Math.toRadians(item.getRotation()));
            g2d.translate(-item.getWidth()/2, -item.getHeight()/2);
            
            // Draw furniture icon based on type
            drawFurnitureIcon(g2d, item);
            
            g2d.setTransform(oldTransform);
            
            // Draw resize handles if selected
            if (item == selectedFurniture) {
                g2d.setColor(Color.BLACK);
                int handleSize = 10;
                // NW handle
                g2d.fillRect(item.getX() - handleSize/2, item.getY() - handleSize/2, handleSize, handleSize);
                // NE handle
                g2d.fillRect(item.getX() + item.getWidth() - handleSize/2, item.getY() - handleSize/2, handleSize, handleSize);
                // SE handle
                g2d.fillRect(item.getX() + item.getWidth() - handleSize/2, item.getY() + item.getHeight() - handleSize/2, handleSize, handleSize);
                // SW handle
                g2d.fillRect(item.getX() - handleSize/2, item.getY() + item.getHeight() - handleSize/2, handleSize, handleSize);
            }
        }
        
        // Draw doors
        for (Door door : doors) {
            door.draw(g2d);
        }
        
        // Draw windows
        for (Window window : windows) {
            window.draw(g2d);
        }
    }

    private void detectWalls() {
        walls.clear();
        // Check for adjacent rooms and create walls
        for (int i = 0; i < rooms.size(); i++) {
            Room room1 = rooms.get(i);
            // Check room boundaries for external walls
            // North wall
            walls.add(new Wall(room1, null, WallType.HORIZONTAL, room1.getY(), room1.getX()));
            // South wall
            walls.add(new Wall(room1, null, WallType.HORIZONTAL, room1.getY() + room1.getHeight(), room1.getX()));
            // West wall
            walls.add(new Wall(room1, null, WallType.VERTICAL, room1.getX(), room1.getY()));
            // East wall
            walls.add(new Wall(room1, null, WallType.VERTICAL, room1.getX() + room1.getWidth(), room1.getY()));
            // Check for shared walls with other rooms
            for (int j = i + 1; j < rooms.size(); j++) {
                Room room2 = rooms.get(j);
                // Check if rooms share a wall
                // Horizontal walls (North/South)
                if (room1.getY() == room2.getY() + room2.getHeight() || 
                    room2.getY() == room1.getY() + room1.getHeight()) {
                    // Find overlapping x-range
                    int startX = Math.max(room1.getX(), room2.getX());
                    int endX = Math.min(room1.getX() + room1.getWidth(), room2.getX() + room2.getWidth());
                    if (endX > startX) {
                        // There is an overlap, create a shared wall
                        int wallY = (room1.getY() == room2.getY() + room2.getHeight()) ? 
                                    room1.getY() : room1.getY() + room1.getHeight();
                        walls.add(new Wall(room1, room2, WallType.HORIZONTAL, wallY, startX));
                    }
                }
                // Vertical walls (East/West)
                if (room1.getX() == room2.getX() + room2.getWidth() || 
                    room2.getX() == room1.getX() + room1.getWidth()) {
                    // Find overlapping y-range
                    int startY = Math.max(room1.getY(), room2.getY());
                    int endY = Math.min(room1.getY() + room1.getHeight(), room2.getY() + room2.getHeight());
                    if (endY > startY) {
                        // There is an overlap, create a shared wall
                        int wallX = (room1.getX() == room2.getX() + room2.getWidth()) ? 
                                    room1.getX() : room1.getX() + room1.getWidth();
                        walls.add(new Wall(room1, room2, WallType.VERTICAL, wallX, startY));
                    }
                }
            }
        }
    }

    private void drawGrid(Graphics2D g2d) {
        int width = getWidth();
        int height = getHeight();
        g2d.setColor(new Color(230, 230, 230));
        g2d.setStroke(new BasicStroke(0.5f));
        // Draw vertical grid lines
        for (int x = 0; x < width; x += GRID_SIZE) {
            g2d.drawLine(x, 0, x, height);
        }
        // Draw horizontal grid lines
        for (int y = 0; y < height; y += GRID_SIZE) {
            g2d.drawLine(0, y, width, y);
        }
    }

    private void drawRoomFeatures(Graphics2D g2d, Room room) {
        // Draw doors for this room
        for (Door door : doors) {
            if (door.getRoom1() == room || door.getRoom2() == room) {
                // Door is connected to this room
                door.draw(g2d);
            }
        }
        // Draw windows for this room
        for (Window window : windows) {
            if (window.getRoom() == room) {
                // Window is in this room
                window.draw(g2d);
            }
        }
    }

    private void drawFurnitureIcon(Graphics2D g2d, Furniture item) {
        FurnitureType type = item.getType();
        int width = item.getWidth();
        int height = item.getHeight();
        // Draw furniture background
        g2d.setColor(new Color(200, 200, 200, 200));
        g2d.fillRect(0, 0, width, height);
        // Draw furniture border
        g2d.setColor(new Color(100, 100, 100));
        g2d.setStroke(new BasicStroke(1.5f));
        g2d.drawRect(0, 0, width, height);
        // Draw furniture icon based on type
        g2d.setColor(new Color(80, 80, 80));
        switch (type) {
            case BED:
                // Draw a bed icon
                g2d.fillRect(width/10, height/4, width*8/10, height*6/10);
                g2d.setColor(new Color(150, 150, 150));
                g2d.fillRect(width/10, height/10, width*8/10, height/6);
                break;
            case SOFA:
                // Draw a sofa icon
                g2d.fillRect(width/10, height/4, width*8/10, height*6/10);
                g2d.setColor(new Color(150, 150, 150));
                g2d.fillRect(width/10, height/10, width*8/10, height/6);
                g2d.fillRect(width/10, height*3/4, width*8/10, height/6);
                break;
            case TABLE:
                // Draw a table icon
                g2d.fillRect(width/6, height/6, width*2/3, height*2/3);
                break;
            case CHAIR:
                // Draw a chair icon
                g2d.fillRect(width/4, height/4, width/2, height/2);
                g2d.drawLine(width/4, height*3/4, width/4, height*7/8);
                g2d.drawLine(width*3/4, height*3/4, width*3/4, height*7/8);
                g2d.drawLine(width/4, height/4, width/4, height/8);
                g2d.drawLine(width*3/4, height/4, width*3/4, height/8);
                break;
           
            default:
                // Generic furniture icon
                g2d.drawLine(0, 0, width, height);
                g2d.drawLine(0, height, width, 0);
        }
        // Draw furniture label
        String label = type.toString();
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Arial", Font.PLAIN, 10));
        FontMetrics fm = g2d.getFontMetrics();
        int textWidth = fm.stringWidth(label);
        int textX = (width - textWidth) / 2;
        int textY = height - 5;
        g2d.drawString(label, textX, textY);
    }
} // End of CanvasPanel class
// Remove any content after this line