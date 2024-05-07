package nz.ac.ara.ang.assessment2.eyeballmaze;

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
	        if (width <= getLevelWidth() && height <= getLevelHeight()) {
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
	public void addSquare(Square  square, int width, int height) {
		if (currentLevelIndex == -1 || currentLevelIndex >= levels.size()) {
            throw new IllegalStateException("No level selected or invalid level index");
        } else {
        	 if (width <= getLevelWidth() && height <= getLevelHeight()) {
        		 levels.get(currentLevelIndex).setSquare(square, width, height);
        	 }else {
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
		int eyeballY = getEyeballRow();
		int eyeballX = getEyeballColumn();
		Direction facing;
		boolean validMove;
		if (eyeballX > y && eyeballY == x) {
			facing = Direction.LEFT;
			validMove = true;
		}else if(eyeballX < y && eyeballY == x) {
			facing = Direction.RIGHT;
			validMove = true;
		}else if(eyeballX == y && eyeballY > x) {
			validMove = true;
			facing = Direction.UP;
		}else if(eyeballX == y && eyeballY < x) {
			validMove = true;
			facing = Direction.DOWN;
		}else {
			validMove = false;
			facing = null;
		}
		if (validMove == true) {
			eyeball.moveTo(x,y, facing);
			// Check for a goal
			if (hasGoalAt(x,y) == true) {
				completeGoal(x, y);
			}
		}
	}
	
	
	public boolean isDirectionOK(int x, int y) {
		int eyeballY = getEyeballRow();
		int eyeballX = getEyeballColumn();
		boolean directionApproval;
		boolean diagOrNorm = checkMoveDiagNorm( x, y);
		Direction currentDirection = getEyeballDirection();
		if (diagOrNorm == true) {
			directionApproval = false;
		}else if (eyeballY > x && currentDirection == Direction.DOWN ) {
			directionApproval = false;
		}else if(eyeballY < x && currentDirection == Direction.UP){
			directionApproval = false;
		}else if(eyeballX < y && currentDirection == Direction.LEFT){
			directionApproval = false;
		}else if(eyeballX > y && currentDirection == Direction.RIGHT) {
			directionApproval = false;
		}else {
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
		boolean checkDirection= isDirectionOK(x,y);
		Message returnMessage;
		if (checkDirection == false) {
			if (y < getEyeballRow() && x < getEyeballColumn()) {
				returnMessage = Message.MOVING_DIAGONALLY;
			}else if(y > getEyeballRow() && x < getEyeballColumn()) {
				returnMessage = Message.MOVING_DIAGONALLY;
			}else if(y < getEyeballRow() && x > getEyeballColumn()) {
				returnMessage = Message.MOVING_DIAGONALLY;
			}else if(y > getEyeballRow() && x > getEyeballColumn()) {
				returnMessage = Message.MOVING_DIAGONALLY;
			}else {
			returnMessage = Message.BACKWARDS_MOVE;
			}
		}else {
				returnMessage = Message.OK;
		}
		return returnMessage;
	}
	
	
	public Message MessageIfMovingTo(int x, int y) {
		Color currentColor = getColorAt(getEyeballRow(),getEyeballColumn());
		Color newColor = getColorAt(x,y);
		Shape currentShape = getShapeAt(getEyeballRow(),getEyeballColumn());
		Shape newShape = getShapeAt(x,y);
		Message returnMessage;
		if (currentColor == newColor || currentShape == newShape) {
			returnMessage = Message.OK;
		}else {
			returnMessage = Message.DIFFERENT_SHAPE_OR_COLOR;
		}
		return returnMessage;
	}
	
	public boolean canMoveTo(int x, int y) {
	    boolean moveApproval;
	    // Check if the move is within the level boundaries
	    if (x >= 0 && x < getLevelHeight() && y >= 0 && y < getLevelWidth()) {
	        // Check if the move is not to a diagonal square
	        if (x == getEyeballRow() || y == getEyeballColumn()) {
	            Message canMove = MessageIfMovingTo(x, y);
	            // Allow the move only if the message is OK
	            moveApproval = canMove == Message.OK;
	        } else {
	            moveApproval = false; // Disallow diagonal moves
	        }
	    } else {
	        moveApproval = false; // Disallow moves outside the level boundaries
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
