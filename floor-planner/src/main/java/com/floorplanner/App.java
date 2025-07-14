package com.floorplanner;

import javax.swing.*;
import java.awt.*;

public class App extends JFrame {
    private static final long serialVersionUID = 1L;
    private static final int SCREEN_WIDTH = 1920;
    private static final int SCREEN_HEIGHT = 1080;
    private static final float CANVAS_RATIO = 0.75f;
    
    private CanvasPanel canvasPanel;
    private JPanel controlPanel;
    private JTextField widthField;
    private JTextField heightField;
    private JComboBox<String> roomTypeCombo;
    
    public App() {
        setTitle("2D Floor Planner");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        
        // Initialize main panels
        initializePanels();
        
        // Add panels to frame
        add(canvasPanel, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.EAST);
    }
    
    private void initializePanels() {
        // Canvas Panel (Drawing Area)
        canvasPanel = new CanvasPanel();
        
        // Control Panel
        controlPanel = new JPanel();
        controlPanel.setPreferredSize(new Dimension((int)(SCREEN_WIDTH * (1 - CANVAS_RATIO)), SCREEN_HEIGHT));
        controlPanel.setBackground(new Color(245, 245, 247));
        controlPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 1, 0, 0, new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(20, 25, 20, 25)
        ));
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));
        
        // Add room controls
        addRoomControls();
    }
    
    private void addRoomControls() {
        // Title
        JLabel titleLabel = new JLabel("Room Controls");
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(new Color(51, 51, 51));
        controlPanel.add(titleLabel);
        controlPanel.add(Box.createVerticalStrut(20));
        
        // Room Type Selection
        String[] roomTypes = {"Bedroom", "Bathroom", "Kitchen", "Living Room", "Dining Room"};
        roomTypeCombo = new JComboBox<>(roomTypes);
        roomTypeCombo.setMaximumSize(new Dimension(300, 35));
        roomTypeCombo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        ((JLabel)roomTypeCombo.getRenderer()).setPreferredSize(new Dimension(270, 25));
        roomTypeCombo.setAlignmentX(Component.CENTER_ALIGNMENT);
        controlPanel.add(roomTypeCombo);
        controlPanel.add(Box.createVerticalStrut(10));
        
        // Room Dimensions
        JPanel dimensionsPanel = new JPanel();
        dimensionsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0));
        dimensionsPanel.setMaximumSize(new Dimension(300, 40));
        dimensionsPanel.setBackground(controlPanel.getBackground());
        
        widthField = new JTextField(5);
        heightField = new JTextField(5);
        
        // Style text fields
        Font fieldFont = new Font("Segoe UI", Font.PLAIN, 14);
        Dimension fieldSize = new Dimension(60, 30);
        
        widthField.setFont(fieldFont);
        widthField.setPreferredSize(fieldSize);
        heightField.setFont(fieldFont);
        heightField.setPreferredSize(fieldSize);
        
        dimensionsPanel.add(new JLabel("Width (px):"));
        dimensionsPanel.add(widthField);
        dimensionsPanel.add(new JLabel("Height (px):"));
        dimensionsPanel.add(heightField);
        
        controlPanel.add(dimensionsPanel);
        controlPanel.add(Box.createVerticalStrut(10));
        
        // Add Room Button
        JButton addRoomButton = new JButton("Add Room");
        addRoomButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        addRoomButton.setMaximumSize(new Dimension(300, 40));
        addRoomButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        addRoomButton.setBackground(new Color(70, 130, 180));
        addRoomButton.setForeground(Color.WHITE);
        addRoomButton.setFocusPainted(false);
        addRoomButton.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        addRoomButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        addRoomButton.addActionListener(e -> addRoom());
        controlPanel.add(addRoomButton);
        
        // Add some spacing
        controlPanel.add(Box.createVerticalStrut(20));
        
        // Add instructions
        JTextArea instructions = new JTextArea(
            "Instructions:\n\n" +
            "1. Select room type\n" +
            "2. Enter dimensions (in pixels)\n" +
            "3. Click 'Add Room' to place\n" +
            "4. Drag rooms to reposition\n" +
            "5. Rooms snap to grid\n" +
            "6. Overlapping is prevented"
        );
        instructions.setEditable(false);
        instructions.setBackground(controlPanel.getBackground());
        instructions.setWrapStyleWord(true);
        instructions.setLineWrap(true);
        instructions.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        instructions.setForeground(new Color(100, 100, 100));
        instructions.setMaximumSize(new Dimension(280, 200));
        instructions.setAlignmentX(Component.CENTER_ALIGNMENT);
        instructions.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        controlPanel.add(instructions);
    }
    
    private void addRoom() {
        try {
            int width = Integer.parseInt(widthField.getText());
            int height = Integer.parseInt(heightField.getText());
            
            if (width <= 0 || height <= 0) {
                throw new NumberFormatException();
            }
            
            String selectedType = (String) roomTypeCombo.getSelectedItem();
            RoomType roomType = RoomType.fromString(selectedType);
            
            canvasPanel.addRoom(roomType, width, height);
            
            // Clear input fields
            widthField.setText("");
            heightField.setText("");
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                "Please enter valid dimensions (positive numbers only)",
                "Invalid Input",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        SwingUtilities.invokeLater(() -> {
            App app = new App();
            app.setVisible(true);
        });
    }
}