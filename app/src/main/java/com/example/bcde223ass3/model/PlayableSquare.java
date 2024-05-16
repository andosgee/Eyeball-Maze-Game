package com.example.bcde223ass3.model;

/**
 * This is an extension of the class squares. This is for playable
 * squares.
 *  
 *  @author Andrew Grant ang0565@ara.ac.nz
 *  @version 0.1
 *  @since 19-3-2024
 */

public class PlayableSquare extends Square {
    private Color color;
    private Shape shape;

    public PlayableSquare(Color color, Shape shape) {
    	super(0,0);
        this.color = color;
        this.shape = shape;
    }

    public Color getColor() {
        return color;
    }

    public Shape getShape() {
        return shape;
    }
}