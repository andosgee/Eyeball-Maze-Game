package nz.ac.ara.ang.assessment2.eyeballmaze.test;

import nz.ac.ara.ang.assessment2.eyeballmaze.*;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TestGameHoldsEyeball {
	Game game;
	@BeforeEach
	void add7High3WideLevel() throws Exception {
	game = new Game();
	game.addLevel(7, 3);
	}
	
	@Test
	void testAddingEyeballOutsideHeightThrowsException() {
	// this.add7High3WideLevel();
	assertThrows(IllegalArgumentException.class, () -> {
		game.addEyeball(9, 2, Direction.UP);
		});
	}
	
	@Test
	void testAddingEyeballOutsideWidthThrowsException() {
	// this.add7High3WideLevel();
	assertThrows(IllegalArgumentException.class, () -> {
		game.addEyeball(6, 5, Direction.UP);
		});
	}
	
	@Test
	void testAddingEyeballPutsItWhereExpected() {
	// this.add7High3WideLevel();
	game.addEyeball(4, 2, Direction.UP);
	int[] expectedRowColumn = { 4, 2 };
	int[] actualRowColumn = { game.getEyeballRow(), game.getEyeballColumn() };
	assertArrayEquals(expectedRowColumn, actualRowColumn);
	}
	
	@Test
	void testAddingEyeballFacingUpFacesUP() {
	// this.add7High3WideLevel();
	game.addEyeball(4, 2, Direction.UP);
	Direction expectedDirection = Direction.UP;
	Direction actualDirection = game.getEyeballDirection();
	assertEquals(expectedDirection, actualDirection);
	}
	
	@Test
	void testAddingEyeballFacingDOWNFacesDOWN() {
	// this.add7High3WideLevel();
	game.addEyeball(4, 2, Direction.DOWN);
	Direction expectedDirection = Direction.DOWN;
	Direction actualDirection = game.getEyeballDirection();
	assertEquals(expectedDirection, actualDirection);
	}
	
	@Test
	void testAddingEyeballFacingLEFTFacesLEFT() {
	// this.add7High3WideLevel();
	game.addEyeball(4, 2, Direction.LEFT);
	Direction expectedDirection = Direction.LEFT;
	Direction actualDirection = game.getEyeballDirection();
	assertEquals(expectedDirection, actualDirection);
	}
	
	@Test
	void testAddingEyeballFacingRIGHTFacesRIGHT() {
	// this.add7High3WideLevel();
	game.addEyeball(4, 2, Direction.RIGHT);
	Direction expectedDirection = Direction.RIGHT;
	Direction actualDirection = game.getEyeballDirection();
	assertEquals(expectedDirection, actualDirection);
	}
}
