package nz.ac.ara.ang.assessment2.eyeballmaze;

/**
 * This is the model for the Levels. All features for the Levels will 
 * be implemented here.
 *  
 *  @author Andrew Grant ang0565@ara.ac.nz
 *  @version 0.1
 *  @since 19-3-2024
 */

public class Level {
	private int width;
	private int height;
	private Square[][] grid;
	
	// Constructor
	public Level(int width, int height) {
		this.width = width;
		this.height = height;
		grid = new Square[height][width];
	}
	
	//Get level dimensions
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public void setSquare(Square square, int x, int y) {
		grid[y][x] = square;
    }
	
	public Square getSquare(int x, int y) {
        return grid[y][x];
    }
	
}
