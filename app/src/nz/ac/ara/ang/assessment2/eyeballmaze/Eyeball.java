package nz.ac.ara.ang.assessment2.eyeballmaze;

/**
 * This is a class for the eyeball.
 *  
 *  @author Andrew Grant ang0565@ara.ac.nz
 *  @version 0.1
 *  @since 19-3-2024
 */

public class Eyeball {
	public int x;
	public int y;
	public Direction direction;
	
	// Constructor
	public Eyeball(int x, int y, Direction direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
    }
	
	public Direction getDirection(){
		return direction;
	}
	
	public int getEyeballRow() {
		return x;
	}
	
	public int getEyeballColumn() {
		return y;
	}
	
	public void moveTo(int x, int y, Direction facing) {
		this.x = x;
		this.y = y;
		this.direction = facing;
	}
}
