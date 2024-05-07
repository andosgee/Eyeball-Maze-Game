package nz.ac.ara.ang.assessment2.eyeballmaze.test;

import nz.ac.ara.ang.assessment2.eyeballmaze.*;

import java.util.Random;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestDiagonalMoves {
	Game game;
	Random rand;
	
	@BeforeEach
	void setUpVerticalLevel() throws Exception {
		game = new Game();
		game.addLevel(10, 10);
		// each test needs to add the eyeball!!
		rand = new Random();
	}
	
	@Test
	void testNotOkWhenMovingToUpLeft() {
		boolean expected = false;
		game.addEyeball(9, 9, Direction.UP);
		boolean actual = game.isDirectionOK(rand.nextInt(9), rand.nextInt(9));
		assertEquals(expected, actual);
	}
	
	@Test
	void testGetsErrorMessageWhenMovingToUpLeft() {
		Message expected = Message.MOVING_DIAGONALLY;
		game.addEyeball(9, 9, Direction.UP);
		Message actual = game.checkDirectionMessage(rand.nextInt(9), rand.nextInt(9));
		assertEquals(expected, actual);
	}
	
	@Test
	void testNotOkWhenMovingToUpRight() {
		boolean expected = false;
		game.addEyeball(9, 0, Direction.RIGHT);
		boolean actual = game.isDirectionOK(rand.nextInt(9), rand.nextInt(9) + 1);
		assertEquals(expected, actual);
	}
	
	@Test
	void testGetsErrorMessageWhenMovingToUpRight() {
		Message expected = Message.MOVING_DIAGONALLY;
		game.addEyeball(9, 0, Direction.RIGHT);
		Message actual = game.checkDirectionMessage(rand.nextInt(9), rand.nextInt(9) + 1);
		assertEquals(expected, actual);
	}
	
	@Test
	void testNotOkWhenMovingToDownRight() {
		boolean expected = false;
		game.addEyeball(0, 0, Direction.DOWN);
		boolean actual = game.isDirectionOK(rand.nextInt(9) + 1, rand.nextInt(9) + 1);
		assertEquals(expected, actual);
	}
	
	@Test
	void testGetsErrorMessageWhenMovingToDownRight() {
		Message expected = Message.MOVING_DIAGONALLY;
		game.addEyeball(0, 0, Direction.DOWN);
		Message actual = game.checkDirectionMessage(rand.nextInt(9) + 1, rand.nextInt(9) + 1);
		assertEquals(expected, actual);
	}
	
	@Test
	void testNotOkWhenMovingToDownLeft() {
		boolean expected = false;
		game.addEyeball(0, 9, Direction.LEFT);
		boolean actual = game.isDirectionOK(rand.nextInt(9) + 1, rand.nextInt(9));
		assertEquals(expected, actual);
	}
	
	@Test
	void testGetsErrorMessageWhenMovingToDownLeft() {
		Message expected = Message.MOVING_DIAGONALLY;
		game.addEyeball(0, 9, Direction.LEFT);
		Message actual = game.checkDirectionMessage(rand.nextInt(9) + 1, rand.nextInt(9));
		assertEquals(expected, actual);
	}
	
	@Test
	void testNotOkWhenMovingBackwards() {
		boolean expected = false;
		game.addEyeball(0, 9, Direction.UP);
		boolean actual = game.isDirectionOK(rand.nextInt(9) + 1, rand.nextInt(9));
		assertEquals(expected, actual);
	}
	
	@Test
	void testGetsErrorMessageWhenMovingBackwards() {
		Message expected = Message.MOVING_DIAGONALLY;
		game.addEyeball(0, 9, Direction.UP);
		Message actual = game.checkDirectionMessage(rand.nextInt(9) + 1, rand.nextInt(9));
		assertEquals(expected, actual);
	}
}
