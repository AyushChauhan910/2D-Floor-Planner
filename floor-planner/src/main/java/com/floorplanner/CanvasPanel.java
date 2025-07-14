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
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        
        // Draw grid
        drawGrid(g2d);
        
        // Draw rooms with enhanced appearance
        for (Room room : rooms) {
            // Draw room background with transparency
            Color roomColor = room.getType().getColor();
            g2d.setColor(new Color(roomColor.getRed(), roomColor.getGreen(), roomColor.getBlue(), 230));
            g2d.fillRoundRect(room.getX(), room.getY(), room.getWidth(), room.getHeight(), 10, 10);

            // Draw room border
            g2d.setColor(new Color(100, 100, 100, 150));
            g2d.setStroke(new BasicStroke(1.5f));
            g2d.drawRoundRect(room.getX(), room.getY(), room.getWidth(), room.getHeight(), 10, 10);
            
            // Draw room label
            String roomType = room.getType().toString();
            g2d.setFont(new Font("Segoe UI", Font.BOLD, 14));
            FontMetrics fm = g2d.getFontMetrics();
            int textX = room.getX() + (room.getWidth() - fm.stringWidth(roomType)) / 2;
            int textY = room.getY() + (room.getHeight() + fm.getAscent()) / 2;
            
            // Add text shadow
            g2d.setColor(new Color(0, 0, 0, 50));
            g2d.drawString(roomType, textX + 1, textY + 1);
            
            // Draw main text
            g2d.setColor(new Color(51, 51, 51));
            g2d.drawString(roomType, textX, textY);
        }
    }
    
    private void drawGrid(Graphics2D g2d) {
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw subtle grid
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