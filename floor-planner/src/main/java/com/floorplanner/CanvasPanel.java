package com.floorplanner;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
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
    
    public CanvasPanel() {
        rooms = new ArrayList<>();
        furniture = new ArrayList<>();
        setBackground(Color.WHITE);
        setupMouseListeners();
    }
    
    private void setupMouseListeners() {
        MouseAdapter mouseAdapter = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                selectedFurniture = findFurnitureAt(e.getPoint());
                if (selectedFurniture != null) {
                    dragStart = e.getPoint();
                    originalFurniturePos = new Point(selectedFurniture.getX(), selectedFurniture.getY());
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
                if (selectedRoom != null && dragStart != null) {
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
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
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
        }
    }
    
    private void drawRoomFeatures(Graphics2D g2d, Room room) {
        // Add a door to each room
        int doorWidth = 40;
        int doorHeight = 10;
        IconManager.drawDoor(g2d, room.getX() + room.getWidth()/2 - doorWidth/2,
                               room.getY() + room.getHeight() - doorHeight,
                               doorWidth, doorHeight);
        
        // Add windows based on room size
        int windowWidth = 30;
        int windowHeight = 10;
        int spacing = room.getWidth() / 3;
        
        // Draw windows on the top wall
        for (int x = room.getX() + spacing; x < room.getX() + room.getWidth() - windowWidth; x += spacing) {
            IconManager.drawWindow(g2d, x, room.getY(), windowWidth, windowHeight);
        }
    }
    
    private void drawFurnitureIcon(Graphics2D g2d, Furniture item) {
        switch (item.getType()) {
            case BED:
                IconManager.drawBed(g2d, 0, 0, item.getWidth(), item.getHeight(), item.getType().getColor());
                break;
            case SOFA:
                IconManager.drawSofa(g2d, 0, 0, item.getWidth(), item.getHeight(), item.getType().getColor());
                break;
            case TABLE:
                IconManager.drawTable(g2d, 0, 0, item.getWidth(), item.getHeight(), item.getType().getColor());
                break;
            case CHAIR:
                IconManager.drawChair(g2d, 0, 0, item.getWidth(), item.getHeight(), item.getType().getColor());
                break;
            case WARDROBE:
                IconManager.drawWardrobe(g2d, 0, 0, item.getWidth(), item.getHeight(), item.getType().getColor());
                break;
            case BOOKSHELF:
                IconManager.drawBookshelf(g2d, 0, 0, item.getWidth(), item.getHeight(), item.getType().getColor());
                break;
            default:
                // Fallback to basic rectangle for unknown types
                g2d.setColor(item.getType().getColor());
                g2d.fillRoundRect(0, 0, item.getWidth(), item.getHeight(), 5, 5);
                break;
        }
    }
    
    private void drawGrid(Graphics2D g2d) {
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(new Color(230, 230, 230));
        g2d.setStroke(new BasicStroke(1.0f));

        for (int x = 0; x <= getWidth(); x += GRID_SIZE) {
            g2d.drawLine(x, 0, x, getHeight());
        }

        for (int y = 0; y <= getHeight(); y += GRID_SIZE) {
            g2d.drawLine(0, y, getWidth(), y);
        }
    }
}