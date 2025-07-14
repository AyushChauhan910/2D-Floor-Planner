# 2D Floor Planner

A Java Swing-based application for creating and managing 2D floor plans with an intuitive graphical interface.

## Features

- Create and manage different types of rooms (Bedroom, Bathroom, Kitchen, Living Room, Dining Room)
- Color-coded room visualization
- Drag-and-drop room placement
- Grid-based alignment
- Automatic collision detection
- Interactive room positioning

## Technical Details

### Prerequisites

- Java 11 or higher
- Maven 3.6 or higher

### Building the Project

```bash
mvn clean package
```

### Running the Application

```bash
java -jar target/floor-planner-1.0-SNAPSHOT.jar
```

### Running Tests

```bash
mvn test
```

## Project Structure

- `src/main/java/com/floorplanner/`
  - `App.java` - Main application window and UI setup
  - `CanvasPanel.java` - Drawing panel for rooms and interaction handling
  - `Room.java` - Room entity with rendering logic
  - `RoomType.java` - Enum defining room types and colors

## Usage Guide

1. Launch the application
2. Select a room type from the dropdown menu
3. Enter room dimensions in pixels
4. Click "Add Room" to place the room
5. Drag rooms to reposition them
6. Rooms will automatically snap to grid
7. Overlapping rooms are prevented

## Design Decisions

- Grid-based system for precise placement
- Color coding for easy room type identification
- Drag-and-drop interface for intuitive interaction
- Automatic collision detection for valid placement
- Row-major room placement for initial positioning

## Testing

The project includes comprehensive unit tests for:
- Room creation and manipulation
- Room type management
- Collision detection
- Canvas panel functionality

## Future Enhancements

- Door and window placement
- Furniture and fixtures
- Save/load floor plans
- Room rotation
- Custom room colors
- Measurement units (meters, feet)