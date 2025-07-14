package com.floorplanner;

import java.awt.*;
import java.awt.geom.*;

public class IconManager {
    public static void drawBed(Graphics2D g2d, int x, int y, int width, int height, Color color) {
        // Base color with transparency
        g2d.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), 230));
        
        // Draw mattress
        g2d.fillRoundRect(x + width/10, y + height/6, width*8/10, height*4/6, 10, 10);
        
        // Draw headboard
        g2d.fillRoundRect(x, y, width, height/4, 5, 5);
        
        // Draw pillows
        g2d.setColor(Color.WHITE);
        g2d.fillRoundRect(x + width/8, y + height/4, width/4, height/6, 5, 5);
        g2d.fillRoundRect(x + width*5/8, y + height/4, width/4, height/6, 5, 5);
        
        // Draw borders
        g2d.setColor(new Color(100, 100, 100, 150));
        g2d.setStroke(new BasicStroke(1.5f));
        g2d.drawRoundRect(x, y, width, height/4, 5, 5);
        g2d.drawRoundRect(x + width/10, y + height/6, width*8/10, height*4/6, 10, 10);
    }
    
    public static void drawSofa(Graphics2D g2d, int x, int y, int width, int height, Color color) {
        // Base color with transparency
        g2d.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), 230));
        
        // Draw base
        g2d.fillRoundRect(x, y + height/3, width, height*2/3, 10, 10);
        
        // Draw backrest
        g2d.fillRoundRect(x, y, width, height/2, 10, 10);
        
        // Draw armrests
        g2d.fillRoundRect(x, y, width/6, height, 10, 10);
        g2d.fillRoundRect(x + width*5/6, y, width/6, height, 10, 10);
        
        // Draw cushions
        g2d.setColor(new Color(color.getRed() + 20, color.getGreen() + 20, color.getBlue() + 20, 230));
        g2d.fillRoundRect(x + width/5, y + height/2, width/3, height/3, 5, 5);
        g2d.fillRoundRect(x + width/2, y + height/2, width/3, height/3, 5, 5);
        
        // Draw borders
        g2d.setColor(new Color(100, 100, 100, 150));
        g2d.setStroke(new BasicStroke(1.5f));
        g2d.drawRoundRect(x, y, width, height, 10, 10);
    }
    
    public static void drawTable(Graphics2D g2d, int x, int y, int width, int height, Color color) {
        // Base color with transparency
        g2d.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), 230));
        
        // Draw tabletop
        g2d.fillRoundRect(x, y, width, height/4, 5, 5);
        
        // Draw legs
        int legWidth = width/8;
        g2d.fillRect(x + legWidth, y + height/4, legWidth, height*3/4);
        g2d.fillRect(x + width - legWidth*2, y + height/4, legWidth, height*3/4);
        
        // Draw borders
        g2d.setColor(new Color(100, 100, 100, 150));
        g2d.setStroke(new BasicStroke(1.5f));
        g2d.drawRoundRect(x, y, width, height/4, 5, 5);
        g2d.drawRect(x + legWidth, y + height/4, legWidth, height*3/4);
        g2d.drawRect(x + width - legWidth*2, y + height/4, legWidth, height*3/4);
    }
    
    public static void drawChair(Graphics2D g2d, int x, int y, int width, int height, Color color) {
        // Base color with transparency
        g2d.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), 230));
        
        // Draw seat
        g2d.fillRoundRect(x, y + height/2, width, height/3, 5, 5);
        
        // Draw backrest
        g2d.fillRoundRect(x + width/6, y, width*2/3, height/2, 5, 5);
        
        // Draw legs
        int legWidth = width/8;
        g2d.fillRect(x + legWidth, y + height*5/6, legWidth, height/6);
        g2d.fillRect(x + width - legWidth*2, y + height*5/6, legWidth, height/6);
        
        // Draw borders
        g2d.setColor(new Color(100, 100, 100, 150));
        g2d.setStroke(new BasicStroke(1.5f));
        g2d.drawRoundRect(x, y + height/2, width, height/3, 5, 5);
        g2d.drawRoundRect(x + width/6, y, width*2/3, height/2, 5, 5);
    }
    
    public static void drawWardrobe(Graphics2D g2d, int x, int y, int width, int height, Color color) {
        // Base color with transparency
        g2d.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), 230));
        
        // Draw main body
        g2d.fillRoundRect(x, y, width, height, 5, 5);
        
        // Draw doors
        g2d.setColor(new Color(color.getRed() + 20, color.getGreen() + 20, color.getBlue() + 20, 230));
        g2d.fillRoundRect(x + width/20, y + height/20, width*9/20, height*9/10, 3, 3);
        g2d.fillRoundRect(x + width/2, y + height/20, width*9/20, height*9/10, 3, 3);
        
        // Draw handles
        g2d.setColor(new Color(100, 100, 100));
        g2d.fillRoundRect(x + width*4/10, y + height/2, width/20, height/10, 2, 2);
        g2d.fillRoundRect(x + width*9/10, y + height/2, width/20, height/10, 2, 2);
        
        // Draw borders
        g2d.setColor(new Color(100, 100, 100, 150));
        g2d.setStroke(new BasicStroke(1.5f));
        g2d.drawRoundRect(x, y, width, height, 5, 5);
        g2d.drawLine(x + width/2, y + height/20, x + width/2, y + height*19/20);
    }
    
    public static void drawBookshelf(Graphics2D g2d, int x, int y, int width, int height, Color color) {
        // Base color with transparency
        g2d.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), 230));
        
        // Draw main frame
        g2d.fillRoundRect(x, y, width, height, 5, 5);
        
        // Draw shelves
        g2d.setColor(new Color(color.getRed() + 20, color.getGreen() + 20, color.getBlue() + 20, 230));
        for (int i = 1; i < 4; i++) {
            g2d.fillRect(x, y + height*i/4, width, height/20);
        }
        
        // Draw books (simplified)
        g2d.setColor(new Color(100, 100, 100, 150));
        for (int shelf = 0; shelf < 4; shelf++) {
            int shelfY = y + shelf*height/4;
            for (int book = 0; book < 5; book++) {
                g2d.fillRect(x + book*width/6 + width/12, shelfY + height/20,
                            width/12, height/4 - height/10);
            }
        }
        
        // Draw borders
        g2d.setColor(new Color(100, 100, 100, 150));
        g2d.setStroke(new BasicStroke(1.5f));
        g2d.drawRoundRect(x, y, width, height, 5, 5);
    }
    
    public static void drawDoor(Graphics2D g2d, int x, int y, int width, int height) {
        // Draw door frame
        g2d.setColor(new Color(139, 69, 19, 230)); // Brown color for door
        g2d.fillRect(x, y, width, height);
        
        // Draw door handle
        g2d.setColor(new Color(150, 150, 150));
        g2d.fillOval(x + width*3/4, y + height/3, height/3, height/3);
        
        // Draw border
        g2d.setColor(new Color(100, 100, 100, 150));
        g2d.setStroke(new BasicStroke(1.5f));
        g2d.drawRect(x, y, width, height);
    }
    
    public static void drawWindow(Graphics2D g2d, int x, int y, int width, int height) {
        // Draw window frame
        g2d.setColor(new Color(200, 200, 200, 230));
        g2d.fillRect(x, y, width, height);
        
        // Draw window panes
        g2d.setColor(new Color(220, 220, 255, 150));
        g2d.fillRect(x + 2, y + 2, width/2 - 2, height - 4);
        g2d.fillRect(x + width/2, y + 2, width/2 - 2, height - 4);
        
        // Draw frame lines
        g2d.setColor(new Color(100, 100, 100, 150));
        g2d.setStroke(new BasicStroke(1.5f));
        g2d.drawRect(x, y, width, height);
        g2d.drawLine(x + width/2, y, x + width/2, y + height);
    }
}