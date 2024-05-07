package nz.ac.ara.ang.assessment2.eyeballmaze.test;

import nz.ac.ara.ang.assessment2.eyeballmaze.*;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TestUpMoves {
	Game game;
	@BeforeEach
	void setUpVerticalLevel() throws Exception {
		game = new Game();
		game.addLevel(9, 1);
		game.addSquare(new PlayableSquare(Color.GREEN, Shape.STAR), 0, 0);
		game.addSquare(new BlankSquare(), 1, 0);
		game.addSquare(new PlayableSquare(Color.BLUE, Shape.DIAMOND), 2, 0);
		game.addSquare(new PlayableSquare(Color.RED, Shape.CROSS), 3, 0);
		game.addSquare(new PlayableSquare(Color.YELLOW, Shape.STAR), 4, 0);
		game.addSquare(new PlayableSquare(Color.GREEN, Shape.FLOWER), 5, 0);
		game.addSquare(new PlayableSquare(Color.GREEN, Shape.STAR), 6, 0);
		// each test needs to add the eyeball!!
	}
	
	@Test
	void testOkToMoveToSameColorOrShape() {
		boolean[] expected = { true, true };
		game.addEyeball(6, 0, Direction.UP);
		boolean[] actual = { game.canMoveTo(5, 0), game.canMoveTo(4, 0) };
		assertArrayEquals(expected, actual);
	}
	
	@Test
	void testNotOkToMovetoDifferentColorAndShape() {
		boolean[] expected = { false, false };
		game.addEyeball(6, 0, Direction.UP);
		boolean[] actual = { game.canMoveTo(2, 0), game.canMoveTo(3, 0) };
		assertArrayEquals(expected, actual);
	}
	
	@Test
	void testNoErrorMessageWhenMovingToSameColorOrShape() {
		Message[] expected = { Message.OK, Message.OK };
		game.addEyeball(6, 0, Direction.UP);
		Message[] actual = { game.MessageIfMovingTo(5, 0), game.MessageIfMovingTo(4, 0) };
		assertArrayEquals(expected, actual);
	}
	
	@Test
	void testGetsErrorMessageWhenMovingToDifferentColorAndShape() {
		Message[] expected = { Message.DIFFERENT_SHAPE_OR_COLOR,
		Message.DIFFERENT_SHAPE_OR_COLOR };
		game.addEyeball(6, 0, Direction.UP);
		Message[] actual = { game.MessageIfMovingTo(2, 0), game.MessageIfMovingTo(3, 0) };
		assertArrayEquals(expected, actual);
	}
	
	@Test
	void testOkWhenEyeballFacesRightOnMovingUp() {
		boolean expected = true;
		game.addEyeball(6, 0, Direction.RIGHT);
		boolean actual = game.isDirectionOK(5, 0);
		assertEquals(expected, actual);
	}
	
	@Test
	void testOkWhenEyeballFacesLeftOnMovingUp() {
		boolean expected = true;
		game.addEyeball(6, 0, Direction.LEFT);
		boolean actual = game.isDirectionOK(5, 0);
		assertEquals(expected, actual);
	}
	
	@Test
	void testNotOkWhenMovingBackwards() {
		boolean expected = false;
		game.addEyeball(6, 0, Direction.DOWN);
		boolean actual = game.isDirectionOK(5, 0);
		assertEquals(expected, actual);
	}
	
	@Test
	void testGetsErrorMessageWhenMovingBackwards() {
		Message expected = Message.BACKWARDS_MOVE;
		game.addEyeball(6, 0, Direction.DOWN);
		Message actual = game.checkDirectionMessage(5, 0);
		assertEquals(expected, actual);
	}
	
	@Test
	void testNotOkWhenPathToDestinationCrossesBlank() {
		boolean expected = false;
		game.addEyeball(6, 0, Direction.UP);
		boolean actual = game.hasBlankFreePathTo(0, 0);
		assertEquals(expected, actual);
	}
	
	@Test
	void testErrorMessageWhenPathToDestinationCrossesBlank() {
		Message expected = Message.MOVING_OVER_BLANK;
		game.addEyeball(6, 0, Direction.UP);
		Message actual = game.checkMessageForBlankOnPathTo(0, 0);
		assertEquals(expected, actual);
	}
	
	@Test
	void testEyeballMovesToDestinationRowAndColumn() {
		int expectedRow = 4;
		int expectedColumn = 0;
		int[] expected = { expectedRow, expectedColumn };
		game.addEyeball(6, 0, Direction.UP);
		game.moveTo(4, 0);
		int[] actual = { game.getEyeballRow(), game.getEyeballColumn() };
		assertArrayEquals(expected, actual);
	}
	
	@Test
	void testEyeballFacesUPOnMovingUp() {
		Direction expectedDirection = Direction.UP;
		game.addEyeball(6, 0, Direction.RIGHT);
		game.moveTo(4, 0);
		Direction actualDirection = game.getEyeballDirection();
		assertEquals(expectedDirection, actualDirection);
	}
}