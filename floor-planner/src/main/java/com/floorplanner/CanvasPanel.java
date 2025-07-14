package com.floorplanner;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class CanvasPanel extends JPanel {
    private static final long serialVersionUID = 1L;
    private List<Room> rooms;
    private Room selectedRoom;
    private Point dragStart;
    private Point originalRoomPos;
    private static final int GRID_SIZE = 20;
    
    public CanvasPanel() {
        rooms = new ArrayList<>();
        setBackground(Color.WHITE);
        setupMouseListeners();
    }
    
    private void setupMouseListeners() {
        MouseAdapter mouseAdapter = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                selectedRoom = findRoomAt(e.getPoint());
                if (selectedRoom != null) {
                    dragStart = e.getPoint();
                    originalRoomPos = new Point(selectedRoom.getX(), selectedRoom.getY());
                }
            }
            
            @Override
            public void mouseDragged(MouseEvent e) {
                if (selectedRoom != null && dragStart != null) {
                    int dx = e.getX() - dragStart.x;
                    int dy = e.getY() - dragStart.y;
                    
                    // Snap to grid
                    int newX = snapToGrid(originalRoomPos.x + dx);
                    int newY = snapToGrid(originalRoomPos.y + dy);
                    
                    // Store current position
                    int oldX = selectedRoom.getX();
                    int oldY = selectedRoom.getY();
                    
                    // Try new position
                    selectedRoom.setPosition(newX, newY);
                    
                    // Check for collisions
                    if (checkCollisions(selectedRoom)) {
                        // Restore old position if collision detected
                        selectedRoom.setPosition(oldX, oldY);
                    }
                    
                    repaint();
                }
            }
            
            @Override
            public void mouseReleased(MouseEvent e) {
                selectedRoom = null;
                dragStart = null;
                originalRoomPos = null;
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
    
    private boolean checkCollisions(Room room) {
        for (Room other : rooms) {
            if (other != room && room.intersects(other)) {
                return true;
            }
        }
        return false;
    }
    
    public void addRoom(RoomType type, int width, int height) {
        // Find position for new room using row-major order
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
        
        // Draw grid
        drawGrid(g2d);
        
        // Draw rooms
        for (Room room : rooms) {
            room.draw(g2d);
        }
    }
    
    private void drawGrid(Graphics2D g2d) {
        g2d.setColor(new Color(230, 230, 230));
        
        for (int x = 0; x < getWidth(); x += GRID_SIZE) {
            g2d.drawLine(x, 0, x, getHeight());
        }
        
        for (int y = 0; y < getHeight(); y += GRID_SIZE) {
            g2d.drawLine(0, y, getWidth(), y);
        }
    }
}