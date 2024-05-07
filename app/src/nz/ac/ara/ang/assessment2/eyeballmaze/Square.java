package nz.ac.ara.ang.assessment2.eyeballmaze;

/**
 * This is the model for the squares. All features for the squares will 
 * be implemented here.
 *  
 *  @author Andrew Grant ang0565@ara.ac.nz
 *  @version 0.1
 *  @since 19-3-2024
 */

public abstract class Square {
    private int x;
    private int y;

    public Square(int x, int y) {
        this.x = x;
        this.y = y;
    }

    
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
