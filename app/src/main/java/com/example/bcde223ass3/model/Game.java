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
	
	public void addLevel(int width, int height) {
		levels.add(new Level(width, height));
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
	public void addGoal(int width, int height) {
	    if (currentLevelIndex == -1 || currentLevelIndex >= levels.size()) {
	        throw new IllegalStateException("No level selected or invalid level index");
	    } else {
	        if (width-1 <= getLevelWidth() && height-1 <= getLevelHeight()) {
	        	goals.get(currentLevelIndex).addGoal(width, height);
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
	public boolean hasGoalAt(int width, int height) {
		return goals.get(currentLevelIndex).hasGoalAt(width, height);
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
	public void addSquare(Square square, int width, int height) {
		if (currentLevelIndex == -1 || currentLevelIndex >= levels.size()) {
			throw new IllegalStateException("No level selected or invalid level index");
		} else {
			int levelWidth = getLevelWidth();
			int levelHeight = getLevelHeight();
			if (width >= 0 && width-1 <= levelWidth && height >= 0 && height-1 <= levelHeight) {
				levels.get(currentLevelIndex).setSquare(square, width, height);
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
	public void addEyeball(int x, int y, Direction direction) {
		if (currentLevelIndex == -1 || currentLevelIndex >= levels.size()) {
            throw new IllegalStateException("No level selected or invalid level index");
        } else {
        	if (x <= getLevelWidth() && y <= getLevelHeight()) {
        		eyeball = new Eyeball(x, y,direction);
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
	public void moveTo(int x, int y) {
		int eyeballX = getEyeballRow();
		int eyeballY = getEyeballColumn();
		Direction facing;
		boolean validMove;
		if (eyeballX > y && eyeballY == x) {
			facing = Direction.LEFT;
			validMove = true;
		} else if (eyeballX < y && eyeballY == x) {
			facing = Direction.RIGHT;
			validMove = true;
		} else if (eyeballX == y && eyeballY > x) {
			validMove = true;
			facing = Direction.DOWN;
		} else if (eyeballX == y && eyeballY < x) {
			validMove = true;
			facing = Direction.UP;
		} else {
			validMove = false;
			facing = null;
		}
		if (validMove == true) {
			eyeball.moveTo(y, x, facing);
			System.out.println("Eyeball moved to: (" + x + ", " + y + ") facing " + facing);
			// Check for a goal
			if (hasGoalAt(x, y) == true) {
				completeGoal(x, y);
			}
		} else {
			System.out.println("Invalid move attempted to: (" + x + ", " + y + ")");
		}
	}

	public boolean isDirectionOK(int x, int y) {
		int eyeballX = getEyeballColumn(); // Corrected variable name
		int eyeballY = getEyeballRow(); // Corrected variable name
		boolean directionApproval;
		boolean diagOrNorm = checkMoveDiagNorm(x, y);
		Direction currentDirection = getEyeballDirection();
		if (diagOrNorm == true) {
			directionApproval = false;
		} else if (eyeballY > x && currentDirection == Direction.UP) { // Adjusted comparison
			directionApproval = false;
		} else if (eyeballY < x && currentDirection == Direction.DOWN) { // Adjusted comparison
			directionApproval = false;
		} else if (eyeballX < y && currentDirection == Direction.LEFT) {
			directionApproval = false;
		} else if (eyeballX > y && currentDirection == Direction.RIGHT) {
			directionApproval = false;
		} else {
			directionApproval = true;
		}
		return directionApproval;
	}
	
	public boolean checkMoveDiagNorm(int x, int y) {
	    boolean returnBoolDiag;	
		if (y < getEyeballRow() && x < getEyeballColumn()) {
			returnBoolDiag = true;
		}else if(y > getEyeballRow() && x < getEyeballColumn()) {
			returnBoolDiag = true;
		}else if(y < getEyeballRow() && x > getEyeballColumn()) {
			returnBoolDiag = true;
		}else if(y > getEyeballRow() && x > getEyeballColumn()) {
			returnBoolDiag = true;
	    }else {
			returnBoolDiag = false;
		}
		return returnBoolDiag;
	}

	public Message checkDirectionMessage(int x, int y) {
		Direction currentDirection = getEyeballDirection();
		Direction intendedDirection;

		// Determine the intended direction based on the movement
		if (x < getEyeballRow()) {
			intendedDirection = Direction.DOWN;
		} else if (x > getEyeballRow()) {
			intendedDirection = Direction.UP;
		} else if (y < getEyeballColumn()) {
			intendedDirection = Direction.RIGHT;
		} else {
			intendedDirection = Direction.LEFT;
		}

		// Check if the intended direction is opposite to the current direction
		if ((currentDirection == Direction.UP && intendedDirection == Direction.DOWN) ||
				(currentDirection == Direction.DOWN && intendedDirection == Direction.UP) ||
				(currentDirection == Direction.LEFT && intendedDirection == Direction.RIGHT) ||
				(currentDirection == Direction.RIGHT && intendedDirection == Direction.LEFT)) {
			return Message.BACKWARDS_MOVE;
		} else {
			return Message.OK;
		}
	}
	
	public Message MessageIfMovingTo(int x, int y) {
		Color currentColor = getColorAt(getEyeballColumn(),getEyeballRow());
		Color newColor = getColorAt(x,y);
		Shape currentShape = getShapeAt(getEyeballColumn(),getEyeballRow());
		Shape newShape = getShapeAt(x,y);
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

	public Message canMoveTo(int x, int y) {
		System.out.println("Attempting to move to: (" + x + ", " + y + ")");
		System.out.println("Current Eyeball position: (" + eyeball.getEyeballRow() + ", " + eyeball.getEyeballColumn() + ")");
		System.out.println("Eyeball facing: " + eyeball.getDirection());

		Message moveApproval;
		Message moveMessage = MessageIfMovingTo(x, y);
		System.out.println("Message from MessageIfMovingTo: " + moveMessage);

		if (moveMessage == Message.OK) {
			Message directionMessage = checkDirectionMessage(x, y);
			System.out.println("Message from checkDirectionMessage: " + directionMessage);

			if (directionMessage == Message.OK) {
				moveApproval = Message.OK;
			} else {
				System.out.println("Move not allowed due to direction issue: " + directionMessage);
				moveApproval = directionMessage;
			}
		} else {
			System.out.println("Move not allowed due to shape/color issue: " + moveMessage);
			moveApproval = moveMessage;
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
