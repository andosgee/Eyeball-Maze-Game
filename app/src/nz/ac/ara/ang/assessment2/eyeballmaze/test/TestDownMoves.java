package nz.ac.ara.ang.assessment2.eyeballmaze.test;

import nz.ac.ara.ang.assessment2.eyeballmaze.*;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestDownMoves {
	Game game;
	
	@BeforeEach
	void setUpVerticalLevel() throws Exception {
		game = new Game();
		game.addLevel(9, 1);
		game.addSquare(new PlayableSquare(Color.GREEN, Shape.STAR), 0, 0);
		game.addSquare(new PlayableSquare(Color.GREEN, Shape.FLOWER), 1, 0);
		game.addSquare(new PlayableSquare(Color.YELLOW, Shape.STAR), 2, 0);
		game.addSquare(new PlayableSquare(Color.RED, Shape.CROSS), 3, 0);
		game.addSquare(new PlayableSquare(Color.BLUE, Shape.DIAMOND), 4, 0);
		game.addSquare(new PlayableSquare(Color.YELLOW, Shape.FLOWER), 5, 0);
		game.addSquare(new BlankSquare(), 6, 0);
		game.addSquare(new PlayableSquare(Color.GREEN, Shape.STAR), 7, 0);
		game.addSquare(new PlayableSquare(Color.RED, Shape.FLOWER), 8, 0);
		// each test needs to add the eyeball!!
	}
	
	@Test
	void testOkToMoveToSameColorOrShape() {
		boolean[] expected = { true, true };
		game.addEyeball(0, 0, Direction.DOWN);
		boolean[] actual = { game.canMoveTo(1, 0), game.canMoveTo(2, 0) };
		assertArrayEquals(expected, actual);
	}
	
	@Test
	void testNotOkToMoveToDifferentColorAndShape() {
		boolean[] expected = { false, false };
		game.addEyeball(0, 0, Direction.DOWN);
		boolean[] actual = { game.canMoveTo(3, 0), game.canMoveTo(5, 0) };
		assertArrayEquals(expected, actual);
	}
	
	@Test
	void testNoErrorMessageWhenMovingToSameColorOrShape() {
		Message[] expected = { Message.OK, Message.OK };
		game.addEyeball(0, 0, Direction.DOWN);
		Message[] actual = { game.MessageIfMovingTo(1, 0), game.MessageIfMovingTo(2, 0) };
		assertArrayEquals(expected, actual);
	}
	
	@Test
	void testGetsErrorMessageWhenMovingToDifferentColorAndShape() {
		Message[] expected = { Message.DIFFERENT_SHAPE_OR_COLOR,
		Message.DIFFERENT_SHAPE_OR_COLOR };
		game.addEyeball(0, 0, Direction.DOWN);
		Message[] actual = { game.MessageIfMovingTo(3, 0), game.MessageIfMovingTo(5, 0) };
		assertArrayEquals(expected, actual);
	}
	
	@Test
	void testOkWhenEyeballFacesRightOnMovingDown() {
		boolean expected = true;
		game.addEyeball(0, 0, Direction.RIGHT);
		boolean actual = game.isDirectionOK(2, 0);
		assertEquals(expected, actual);
	}
	
	@Test
	void testOkWhenEyeballFacesLeftOnMovingDown() {
		boolean expected = true;
		game.addEyeball(0, 0, Direction.LEFT);
		boolean actual = game.isDirectionOK(2, 0);
		assertEquals(expected, actual);
	}
	
	@Test
	void testNotOkWhenMovingBackwards() {
		boolean expected = false;
		game.addEyeball(0, 0, Direction.UP);
		boolean actual = game.isDirectionOK(2, 0);
		assertEquals(expected, actual);
	}
	
	@Test
	void testGetsErrorMessageWhenMovingBackwards() {
		Message expected = Message.BACKWARDS_MOVE;
		game.addEyeball(0, 0, Direction.UP);
		Message actual = game.checkDirectionMessage(2, 0);
		assertEquals(expected, actual);
	}
	
	@Test
	void testNotOkWhenPathToDestinationCrossesBlank() {
		boolean expected = false;
		game.addEyeball(1, 0, Direction.DOWN);
		boolean actual = game.hasBlankFreePathTo(7, 0);
		assertEquals(expected, actual);
	}
	
	@Test
	void testErrorMessageWhenPathToDestinationCrossesBlank() {
		Message expected = Message.MOVING_OVER_BLANK;
		game.addEyeball(1, 0, Direction.DOWN);
		Message actual = game.checkMessageForBlankOnPathTo(7, 0);
		assertEquals(expected, actual);
	}
	
	@Test
	void testEyeballMovesToDestinationRowAndColumn() {
		int expectedRow = 5;
		int expectedColumn = 0;
		int[] expected = { expectedRow, expectedColumn };
		game.addEyeball(1, 0, Direction.DOWN);
		game.moveTo(5, 0);
		int[] actual = { game.getEyeballRow(), game.getEyeballColumn() };
		assertArrayEquals(expected, actual);
	}
	@Test
	void testEyeballFacesDownOnMovingDown() {
		Direction expectedDirection = Direction.DOWN;
		game.addEyeball(1, 0, Direction.RIGHT);
		game.moveTo(5, 0);
		Direction actualDirection = game.getEyeballDirection();
		assertEquals(expectedDirection, actualDirection);
	}
}
