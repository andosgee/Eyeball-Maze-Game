package com.example.bcde223ass3.model;

import java.util.ArrayList;
import java.util.List;

/**
 * This is the model for the game. All features for the game will 
 * be implemented here.
 *  
 *  @author Andrew Grant ang0565@ara.ac.nz
 *  @version 0.1
 *  @since 19-3-2024
 */

public class Game {
	private List<Level> levels; // Store the levels
	private List<Goal> goals;
	private Eyeball eyeball;
	private int currentLevelIndex;

	// Constructor
	public Game() {
		levels = new ArrayList<>();
		goals = new ArrayList<>();
		currentLevelIndex = -1;
	}

	public void addLevel(int rows, int columns) {
		levels.add(new Level(rows, columns));
		goals.add(new Goal());
		if (currentLevelIndex == -1) {
			currentLevelIndex = 0; // Sets the Level if there are no levels set
		}
		currentLevelIndex = levels.size() - 1; // Defaults to latest Level
	}

	// Get level dimensions
	public int getLevelWidth() {
		return levels.get(currentLevelIndex).getWidth();
	}

	public int getLevelHeight() {
		return levels.get(currentLevelIndex).getHeight();
	}

	// Get the total number of levels
	public int getLevelCount() {
		return levels.size();
	}

	// Sets the current level
	public void setLevel(int index) {
		if (index < 0 || index >= levels.size()) {
			throw new IllegalArgumentException("Invalid Level Number");
		}
		currentLevelIndex = index;
	}

	// Goal Setups and Getters

	// Adds a Goal
	public void addGoal(int row, int column) {
		if (currentLevelIndex == -1 || currentLevelIndex >= levels.size()) {
			throw new IllegalStateException("No level selected or invalid level index");
		} else {
			if (column <= getLevelWidth() && row <= getLevelHeight()) {
				goals.get(currentLevelIndex).addGoal(row, column);
			}else {
				throw new IllegalArgumentException ("Height or Width is outside Bounds");
			}
		}
	}

	// Get goal count
	public int getGoalCount() {
		return goals.get(currentLevelIndex).getGoalCount();
	}

	// Returns if there is a Goal at Position
	public boolean hasGoalAt(int row, int column) {
		return goals.get(currentLevelIndex).hasGoalAt(row, column);
	}

	// Get the Completed goal count
	public int getCompletedGoalCount() {
		return goals.get(currentLevelIndex).getCompletedGoalCount();
	}

	// Complete a Goal
	public void completeGoal(int width, int height) {
		goals.get(currentLevelIndex).completeGoal(width, height);
		// Make blank
		Square square = levels.get(currentLevelIndex).getSquare(width, height);
		if (square instanceof PlayableSquare) {
			levels.get(currentLevelIndex).setSquare(new BlankSquare(), width, height);
		}
	}

	// Setup Squares
	public void addSquare(Square square, int row, int column) {
		if (currentLevelIndex == -1 || currentLevelIndex >= levels.size()) {
			throw new IllegalStateException("No level selected or invalid level index");
		} else {
			int levelWidth = getLevelWidth();
			int levelHeight = getLevelHeight();
			if (column >= 0 && column <= levelWidth && row>= 0 && row <= levelHeight) {
				levels.get(currentLevelIndex).setSquare(square, row, column);
			} else {
				throw new IllegalArgumentException("Square position is outside the level boundaries");
			}
		}
	}

	// Get the shape at position
	public Shape getShapeAt (int width, int height) {
		Square square = levels.get(currentLevelIndex).getSquare(width, height);
		if (square instanceof PlayableSquare) {
			return ((PlayableSquare) square).getShape();
		} else {
			return Shape.BLANK;
		}
	}

	// Get the color
	public Color getColorAt (int width, int height) {
		Square square = levels.get(currentLevelIndex).getSquare(width, height);
		if (square instanceof PlayableSquare) {
			return ((PlayableSquare) square).getColor();
		} else {
			return Color.BLANK;
		}
	}

	// Eyeball
	public void addEyeball(int row, int column, Direction direction) {
		if (currentLevelIndex == -1 || currentLevelIndex >= levels.size()) {
			throw new IllegalStateException("No level selected or invalid level index");
		} else {
			if (column <= getLevelWidth() && row <= getLevelHeight()) {
				eyeball = new Eyeball(row, column,direction);
			}else {
				throw new IllegalArgumentException("Square position is outside the level boundaries");
			}
		}
	}

	public Direction getEyeballDirection() {
		return eyeball.getDirection();
	}

	public int getEyeballRow() {
		return eyeball.getEyeballRow();
	}

	public int getEyeballColumn() {
		return eyeball.getEyeballColumn();
	}

	//Eyeball Moves
	public void moveTo(int row, int column) {
		System.out.println("Move: " + row + " " + column);
		int eyeballRow = getEyeballRow();
		System.out.println("Row: " + eyeballRow);
		int eyeballColumn = getEyeballColumn();
		System.out.println("Column: " + eyeballColumn);
		Direction facing;
		boolean validMove;

		if (eyeballRow < row && eyeballColumn == column) {
			facing = Direction.UP;
			validMove = true;
		} else if (eyeballRow > row && eyeballColumn == column) {
			facing = Direction.DOWN;
			validMove = true;
		} else if (eyeballRow == row && eyeballColumn > column) {
			validMove = true;
			facing = Direction.LEFT;
		} else if (eyeballRow == row && eyeballColumn < column) {
			validMove = true;
			facing = Direction.RIGHT;
		} else {
			validMove = false;
			facing = null;
		}
		if (validMove == true) {
			eyeball.moveTo(row, column, facing);
//			System.out.println("Eyeball moved to: (" + x + ", " + y + ") facing " + facing);
			// Check for a goal
			if (hasGoalAt(row, column) == true) {
				completeGoal(row, column);
			}
		} else {
//			System.out.println("Invalid move attempted to: (" + x + ", " + y + ")");
		}
	}

	public boolean isDirectionOK(int row, int column) {
		int eyeballColumn = getEyeballColumn();
		int eyeballRow = getEyeballRow();
		boolean directionApproval;
		boolean diagOrNorm = checkMoveDiagNorm(row, column);
		Direction currentDirection = getEyeballDirection();
		System.out.println(currentDirection);
		if (diagOrNorm == true) {
			directionApproval = false;
			System.out.println("Diag");
		} else if (eyeballRow > row && currentDirection == Direction.DOWN) {
			directionApproval = false;
		} else if (eyeballRow < row && currentDirection == Direction.UP) {
			directionApproval = false;
		} else if (eyeballColumn > column && currentDirection == Direction.RIGHT) {
			directionApproval = false;
		} else if (eyeballColumn < column && currentDirection == Direction.LEFT) {
			directionApproval = false;
		} else {
			directionApproval = true;
		}
		return directionApproval;
	}

	public boolean checkMoveDiagNorm(int row, int column) {
		boolean returnBoolDiag;
		if (row < getEyeballRow() && column < getEyeballColumn()) {
			returnBoolDiag = true;
		}else if(row > getEyeballRow() && column < getEyeballColumn()) {
			returnBoolDiag = true;
		}else if(row < getEyeballRow() && column > getEyeballColumn()) {
			returnBoolDiag = true;
		}else if(row > getEyeballRow() && column > getEyeballColumn()) {
			returnBoolDiag = true;
		}else {
			returnBoolDiag = false;
		}
		return returnBoolDiag;
	}

	public Message checkDirectionMessage(int row, int column) {
		Direction currentDirection = getEyeballDirection();
		System.out.println("CD: " + currentDirection);
		Direction intendedDirection;
		// Determine the intended direction based on the movement
		if(checkMoveDiagNorm(row, column) == true) {
			return Message.MOVING_DIAGONALLY;
		}else {
			if (row <  getEyeballRow()) {
				intendedDirection = Direction.DOWN;
				System.out.println("Down");
			} else if (row > getEyeballRow()) {
				intendedDirection = Direction.UP;
				System.out.println("Up");
			} else if (column < getEyeballColumn()) {
				intendedDirection = Direction.RIGHT;
				System.out.println("Right");
			} else {
				intendedDirection = Direction.LEFT;
				System.out.println("Left");
			}
			// Check if the intended direction is opposite to the current direction
			if ((currentDirection == Direction.UP && intendedDirection == Direction.DOWN) ||
					(currentDirection == Direction.DOWN && intendedDirection == Direction.UP) ||
					(currentDirection == Direction.LEFT && intendedDirection == Direction.RIGHT) ||
					(currentDirection == Direction.RIGHT && intendedDirection == Direction.LEFT)) {
				System.out.println("here");
				return Message.BACKWARDS_MOVE;
			} else {
				return Message.OK;
			}
		}
	}

	public Message MessageIfMovingTo(int row, int column) {
		Color currentColor = getColorAt(getEyeballRow(),getEyeballColumn());
		Color newColor = getColorAt(row,column);
		Shape currentShape = getShapeAt(getEyeballRow(),getEyeballColumn());
		Shape newShape = getShapeAt(row,column);
		System.out.println("New Shape and Color:" + newColor + " " + newShape);
		System.out.println("Current Shape and Color:" + currentColor + " " + currentShape);
		Message returnMessage;
		if (currentColor == newColor || currentShape == newShape) {
			returnMessage = Message.OK;
		}else {
			returnMessage = Message.DIFFERENT_SHAPE_OR_COLOR;
		}
		return returnMessage;
	}

	public boolean canMoveTo(int x, int y) {
		System.out.println("Attempting to move to: (" + x + ", " + y + ")");
		System.out.println("Current Eyeball position: (" + eyeball.getEyeballRow() + ", " + eyeball.getEyeballColumn() + ")");
		System.out.println("Eyeball facing: " + eyeball.getDirection());

		boolean moveApproval;
		Message moveMessage = MessageIfMovingTo(x, y);
		System.out.println("Message from MessageIfMovingTo: " + moveMessage);

		if (moveMessage == Message.OK) {
			Message directionMessage = checkDirectionMessage(x, y);
			System.out.println("Message from checkDirectionMessage: " + directionMessage);

			if (directionMessage == Message.OK) {
				moveApproval = true;
			} else {
				System.out.println("Move not allowed due to direction issue: " + directionMessage);
				moveApproval = false;
			}
		} else {
			System.out.println("Move not allowed due to shape/color issue: " + moveMessage);
			moveApproval = false;
		}

		return moveApproval;
	}

	public boolean hasBlankFreePathTo(int x, int y) {
		int eyeballY = getEyeballRow();
		int eyeballX = getEyeballColumn();
		boolean returnState = true;
		if (x < eyeballY) {
			for (int range = x;  range < eyeballY; range++ ) {
				if (getShapeAt(range, y) == Shape.BLANK) {
					returnState = false;
				}
			}
		}else if (y < eyeballX) {
			for (int range = y;  range < eyeballX; range++ ) {
				if (getShapeAt(x, range) == Shape.BLANK) {
					returnState = false;
				}
			}
		}else if (x > eyeballY) {
			for (int range = eyeballY;  range < x; range++ ) {
				if (getShapeAt(range, y) == Shape.BLANK) {
					returnState = false;
				}
			}
		}else if (y > eyeballX) {
			for (int range = eyeballX;  range < y; range++ ) {
				if (getShapeAt(x, range) == Shape.BLANK) {
					returnState = false;
				}
			}
		}
		return returnState;
	}

	public Message checkMessageForBlankOnPathTo(int x, int y) {
		boolean checkPath= hasBlankFreePathTo(x,y);
		Message returnMessage;
		if (checkPath == false) {
			returnMessage = Message.MOVING_OVER_BLANK;
		}else {
			returnMessage = Message.OK;
		}
		return returnMessage;
	}
}
