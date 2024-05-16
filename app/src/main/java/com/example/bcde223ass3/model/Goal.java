package com.example.bcde223ass3.model;

import java.util.ArrayList;
//import java.util.Arrays;
import java.util.List;

/**
 * This is the model for the Goals. All features for the Goals will 
 * be implemented here.
 *  
 *  @author Andrew Grant ang0565@ara.ac.nz
 *  @version 0.1
 *  @since 19-3-2024
 */

public class Goal {
	private List<Coordinate> goals;
	private List<Coordinate> completedGoals;
	
	//Constructor
	public Goal(){
		goals = new ArrayList<>();
		completedGoals = new ArrayList<>();
	}
	
	public void addGoal(int width, int height) {
		goals.add(new Coordinate(width, height));
	}
	
	public boolean hasGoalAt(int width, int height) {
		return goals.contains(new Coordinate(width, height));
	}
	
	public void completeGoal(int width, int height) {
        Coordinate goal = new Coordinate(width, height);
        if (goals.remove(goal)) {
            completedGoals.add(goal);
        }
    }
		
	// Get the goal lists sizes
	public int getGoalCount() {
		return goals.size();
	}
		
	public int getCompletedGoalCount() {
		return completedGoals.size();
		}
}
