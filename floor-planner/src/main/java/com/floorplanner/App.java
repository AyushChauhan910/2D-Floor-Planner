package com.floorplanner;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.io.*;

public class App extends JFrame {
    private static final long serialVersionUID = 1L;
    private CanvasPanel canvasPanel;

    public App() {
        setTitle("Floor Planner");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);
        setLocationRelativeTo(null);

        // Create main content panel with border layout
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(new Color(245, 245, 247));
        setContentPane(contentPanel);

        // Create and configure canvas panel
        canvasPanel = new CanvasPanel();
        canvasPanel.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        contentPanel.add(new JScrollPane(canvasPanel), BorderLayout.CENTER);

        // Create control panel
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));
        controlPanel.setBackground(new Color(250, 250, 252));
        controlPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Room Controls Section
        JPanel roomControls = createRoomControls();
        controlPanel.add(roomControls);
        controlPanel.add(Box.createVerticalStrut(20));

        // Furniture Controls Section
        JPanel furnitureControls = createFurnitureControls();
        controlPanel.add(furnitureControls);
        controlPanel.add(Box.createVerticalStrut(20));

        // Relative Position Controls Section
        JPanel relativePositionControls = createRelativePositionControls();
        controlPanel.add(relativePositionControls);
        controlPanel.add(Box.createVerticalStrut(20));

        // Save/Load Controls Section
        JPanel saveLoadControls = createSaveLoadControls();
        controlPanel.add(saveLoadControls);
        controlPanel.add(Box.createVerticalStrut(20));

        // Instructions Section
        JPanel instructionsPanel = createInstructionsPanel();
        controlPanel.add(instructionsPanel);

        // Add control panel to the frame
        contentPanel.add(controlPanel, BorderLayout.EAST);
    }

    private JPanel createRoomControls() {
        JPanel roomControls = new JPanel();
        roomControls.setLayout(new BoxLayout(roomControls, BoxLayout.Y_AXIS));
        roomControls.setBackground(new Color(250, 250, 252));

        // Title for Room Controls
        JLabel roomTitle = new JLabel("Add Room");
        roomTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));
        roomTitle.setForeground(new Color(51, 51, 51));
        roomTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        roomControls.add(roomTitle);
        roomControls.add(Box.createVerticalStrut(10));

        // Room Type Selection
        JComboBox<RoomType> roomTypeCombo = new JComboBox<>(RoomType.values());
        roomTypeCombo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        roomTypeCombo.setMaximumSize(new Dimension(300, 30));
        roomTypeCombo.setAlignmentX(Component.LEFT_ALIGNMENT);
        roomControls.add(roomTypeCombo);
        roomControls.add(Box.createVerticalStrut(10));

        // Room Dimensions Panel
        JPanel dimensionsPanel = new JPanel();
        dimensionsPanel.setLayout(new BoxLayout(dimensionsPanel, BoxLayout.Y_AXIS));
        dimensionsPanel.setBackground(new Color(250, 250, 252));
        dimensionsPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Width Input
        JPanel widthPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        widthPanel.setBackground(new Color(250, 250, 252));
        JLabel widthLabel = new JLabel("Width (pixels): ");
        widthLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        JTextField widthField = new JTextField(10);
        widthField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        widthPanel.add(widthLabel);
        widthPanel.add(widthField);

        // Height Input
        JPanel heightPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        heightPanel.setBackground(new Color(250, 250, 252));
        JLabel heightLabel = new JLabel("Height (pixels): ");
        heightLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        JTextField heightField = new JTextField(10);
        heightField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        heightPanel.add(heightLabel);
        heightPanel.add(heightField);

        dimensionsPanel.add(widthPanel);
        dimensionsPanel.add(Box.createVerticalStrut(5));
        dimensionsPanel.add(heightPanel);
        roomControls.add(dimensionsPanel);
        roomControls.add(Box.createVerticalStrut(10));

        // Add Room Button
        JButton addRoomButton = new JButton("Add Room");
        addRoomButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        addRoomButton.setBackground(new Color(0, 122, 255));
        addRoomButton.setForeground(Color.WHITE);
        addRoomButton.setFocusPainted(false);
        addRoomButton.setBorder(new EmptyBorder(8, 16, 8, 16));
        addRoomButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        addRoomButton.addActionListener(e -> {
            try {
                int width = Integer.parseInt(widthField.getText());
                int height = Integer.parseInt(heightField.getText());
                RoomType selectedType = (RoomType) roomTypeCombo.getSelectedItem();
                canvasPanel.addRoom(selectedType, width, height);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this,
                    "Please enter valid numbers for width and height",
                    "Input Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        });
        roomControls.add(addRoomButton);

        return roomControls;
    }

    private JPanel createFurnitureControls() {
        JPanel furnitureControls = new JPanel();
        furnitureControls.setLayout(new BoxLayout(furnitureControls, BoxLayout.Y_AXIS));
        furnitureControls.setBackground(new Color(250, 250, 252));

        // Title for Furniture Controls
        JLabel furnitureTitle = new JLabel("Add Furniture");
        furnitureTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));
        furnitureTitle.setForeground(new Color(51, 51, 51));
        furnitureTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        furnitureControls.add(furnitureTitle);
        furnitureControls.add(Box.createVerticalStrut(10));

        // Furniture Type Selection
        JComboBox<FurnitureType> furnitureTypeCombo = new JComboBox<>(FurnitureType.values());
        furnitureTypeCombo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        furnitureTypeCombo.setMaximumSize(new Dimension(300, 30));
        furnitureTypeCombo.setAlignmentX(Component.LEFT_ALIGNMENT);
        furnitureControls.add(furnitureTypeCombo);
        furnitureControls.add(Box.createVerticalStrut(10));

        // Add Furniture Button
        JButton addFurnitureButton = new JButton("Add Furniture");
        addFurnitureButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        addFurnitureButton.setBackground(new Color(88, 86, 214));
        addFurnitureButton.setForeground(Color.WHITE);
        addFurnitureButton.setFocusPainted(false);
        addFurnitureButton.setBorder(new EmptyBorder(8, 16, 8, 16));
        addFurnitureButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        addFurnitureButton.addActionListener(e -> {
            FurnitureType selectedType = (FurnitureType) furnitureTypeCombo.getSelectedItem();
            canvasPanel.addFurniture(selectedType);
        });
        furnitureControls.add(addFurnitureButton);

        return furnitureControls;
    }

    private JPanel createInstructionsPanel() {
        JPanel instructionsPanel = new JPanel();
        instructionsPanel.setLayout(new BoxLayout(instructionsPanel, BoxLayout.Y_AXIS));
        instructionsPanel.setBackground(new Color(250, 250, 252));

        JLabel instructionsTitle = new JLabel("Instructions");
        instructionsTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));
        instructionsTitle.setForeground(new Color(51, 51, 51));
        instructionsTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        instructionsPanel.add(instructionsTitle);
        instructionsPanel.add(Box.createVerticalStrut(10));

        JTextArea instructions = new JTextArea(
            "1. Select room type and enter dimensions\n" +
            "2. Click 'Add Room' to place a room\n" +
            "3. Drag rooms to reposition them\n" +
            "4. Select furniture type\n" +
            "5. Click 'Add Furniture' to place furniture\n" +
            "6. Drag furniture to reposition\n" +
            "7. Double-click furniture to rotate 90°\n" +
            "\nRooms will snap to grid and avoid overlapping.");
        instructions.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        instructions.setForeground(new Color(102, 102, 102));
        instructions.setBackground(new Color(250, 250, 252));
        instructions.setEditable(false);
        instructions.setWrapStyleWord(true);
        instructions.setLineWrap(true);
        instructions.setAlignmentX(Component.LEFT_ALIGNMENT);
        instructions.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        instructionsPanel.add(instructions);

        return instructionsPanel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            App app = new App();
            app.setVisible(true);
        });
    }

    private JPanel createRelativePositionControls() {
        JPanel panel = new JPanel(new GridLayout(1, 4));
        
        JButton northBtn = new JButton("North");
        JButton southBtn = new JButton("South");
        JButton eastBtn = new JButton("East");
        JButton westBtn = new JButton("West");
        
        northBtn.addActionListener(e -> {
            if (canvasPanel.getSelectedRoom() != null) {
                // Show room type selection dialog
                RoomType[] roomTypes = RoomType.values();
                RoomType selectedType = (RoomType)JOptionPane.showInputDialog(
                    this,
                    "Select room type:",
                    "Room Type",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    roomTypes,
                    RoomType.LIVING_ROOM);
                    
                if (selectedType != null) {
                    String widthStr = JOptionPane.showInputDialog(this, "Enter room width:", "200");
                    String heightStr = JOptionPane.showInputDialog(this, "Enter room height:", "200");
                    String offsetStr = JOptionPane.showInputDialog(this, "Enter offset from wall:", "0");
                    
                    try {
                        int width = Integer.parseInt(widthStr);
                        int height = Integer.parseInt(heightStr);
                        int offset = Integer.parseInt(offsetStr);
                        
                        canvasPanel.addRoomRelative(canvasPanel.getSelectedRoom(), 
                            selectedType, width, height, 
                            CanvasPanel.RelativePosition.NORTH, offset);
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(this, "Invalid input. Please enter numbers only.", 
                            "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
    
        southBtn.addActionListener(e -> {
            if (canvasPanel.getSelectedRoom() != null) {
                RoomType[] roomTypes = RoomType.values();
                RoomType selectedType = (RoomType)JOptionPane.showInputDialog(
                    this,
                    "Select room type:",
                    "Room Type",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    roomTypes,
                    RoomType.LIVING_ROOM);
                    
                if (selectedType != null) {
                    String widthStr = JOptionPane.showInputDialog(this, "Enter room width:", "200");
                    String heightStr = JOptionPane.showInputDialog(this, "Enter room height:", "200");
                    String offsetStr = JOptionPane.showInputDialog(this, "Enter offset from wall:", "0");
                    
                    try {
                        int width = Integer.parseInt(widthStr);
                        int height = Integer.parseInt(heightStr);
                        int offset = Integer.parseInt(offsetStr);
                        
                        canvasPanel.addRoomRelative(canvasPanel.getSelectedRoom(), 
                            selectedType, width, height, 
                            CanvasPanel.RelativePosition.SOUTH, offset);
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(this, "Invalid input. Please enter numbers only.", 
                            "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
    
        eastBtn.addActionListener(e -> {
            if (canvasPanel.getSelectedRoom() != null) {
                RoomType[] roomTypes = RoomType.values();
                RoomType selectedType = (RoomType)JOptionPane.showInputDialog(
                    this,
                    "Select room type:",
                    "Room Type",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    roomTypes,
                    RoomType.LIVING_ROOM);
                    
                if (selectedType != null) {
                    String widthStr = JOptionPane.showInputDialog(this, "Enter room width:", "200");
                    String heightStr = JOptionPane.showInputDialog(this, "Enter room height:", "200");
                    String offsetStr = JOptionPane.showInputDialog(this, "Enter offset from wall:", "0");
                    
                    try {
                        int width = Integer.parseInt(widthStr);
                        int height = Integer.parseInt(heightStr);
                        int offset = Integer.parseInt(offsetStr);
                        
                        canvasPanel.addRoomRelative(canvasPanel.getSelectedRoom(), 
                            selectedType, width, height, 
                            CanvasPanel.RelativePosition.EAST, offset);
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(this, "Invalid input. Please enter numbers only.", 
                            "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
    
        westBtn.addActionListener(e -> {
            if (canvasPanel.getSelectedRoom() != null) {
                RoomType[] roomTypes = RoomType.values();
                RoomType selectedType = (RoomType)JOptionPane.showInputDialog(
                    this,
                    "Select room type:",
                    "Room Type",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    roomTypes,
                    RoomType.LIVING_ROOM);
                    
                if (selectedType != null) {
                    String widthStr = JOptionPane.showInputDialog(this, "Enter room width:", "200");
                    String heightStr = JOptionPane.showInputDialog(this, "Enter room height:", "200");
                    String offsetStr = JOptionPane.showInputDialog(this, "Enter offset from wall:", "0");
                    
                    try {
                        int width = Integer.parseInt(widthStr);
                        int height = Integer.parseInt(heightStr);
                        int offset = Integer.parseInt(offsetStr);
                        
                        canvasPanel.addRoomRelative(canvasPanel.getSelectedRoom(), 
                            selectedType, width, height, 
                            CanvasPanel.RelativePosition.WEST, offset);
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(this, "Invalid input. Please enter numbers only.", 
                            "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
    
        panel.add(northBtn);
        panel.add(southBtn);
        panel.add(eastBtn);
        panel.add(westBtn);
        
        return panel;
    }

    private JPanel createSaveLoadControls() {
        JPanel panel = new JPanel(new GridLayout(1, 2));
        
        JButton saveBtn = new JButton("Save Plan");
        JButton loadBtn = new JButton("Load Plan");
        
        saveBtn.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Save Floor Plan");
            
            if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                try (PrintWriter writer = new PrintWriter(file)) {
                    writer.write(canvasPanel.savePlan());
                    JOptionPane.showMessageDialog(this, "Plan saved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this, "Error saving file: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    
        loadBtn.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Load Floor Plan");
            
            if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                    StringBuilder content = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        content.append(line).append("\n");
                    }
                    canvasPanel.loadPlan(content.toString());
                    JOptionPane.showMessageDialog(this, "Plan loaded successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this, "Error loading file: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        panel.add(saveBtn);
        panel.add(loadBtn);
        
        return panel;
    }
}