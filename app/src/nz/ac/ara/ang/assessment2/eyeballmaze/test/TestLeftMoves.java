package nz.ac.ara.ang.assessment2.eyeballmaze.test;

import nz.ac.ara.ang.assessment2.eyeballmaze.*;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestLeftMoves {
	Game game;
	@BeforeEach
	void setUpVerticalLevel() throws Exception {
		game = new Game();
		game.addLevel(1, 9);
		game.addSquare(new PlayableSquare(Color.GREEN, Shape.STAR), 0, 0);
		game.addSquare(new BlankSquare(), 0, 1);
		game.addSquare(new PlayableSquare(Color.BLUE, Shape.DIAMOND), 0, 2);
		game.addSquare(new PlayableSquare(Color.RED, Shape.CROSS), 0, 3);
		game.addSquare(new PlayableSquare(Color.YELLOW, Shape.STAR), 0, 4);
		game.addSquare(new PlayableSquare(Color.GREEN, Shape.FLOWER), 0, 5);
		game.addSquare(new PlayableSquare(Color.GREEN, Shape.STAR), 0, 6);
		// each test needs to add the eyeball!!
	}
	
	@Test
	void testOkToMoveToSameColorOrShape() {
		boolean[] expected = { true, true };
		game.addEyeball(0, 6, Direction.LEFT);
		boolean[] actual = { game.canMoveTo(0, 5), game.canMoveTo(0, 4) };
		assertArrayEquals(expected, actual);
	}
	
	@Test
	void testNotOkToMovetoDifferentColorAndShape() {
		boolean[] expected = { false, false };
		game.addEyeball(0, 6, Direction.LEFT);
		boolean[] actual = { game.canMoveTo(0, 2), game.canMoveTo(0, 3) };
		assertArrayEquals(expected, actual);
	}
	
	@Test
	void testNoErrorMessageWhenMovingToSameColorOrShape() {
		Message[] expected = { Message.OK, Message.OK };
		game.addEyeball(0, 6, Direction.LEFT);
		Message[] actual = { game.MessageIfMovingTo(0, 5), game.MessageIfMovingTo(0, 4) };
		assertArrayEquals(expected, actual);
	}
	
	@Test
	void testGetsErrorMessageWhenMovingToDifferentColorAndShape() {
		Message[] expected = { Message.DIFFERENT_SHAPE_OR_COLOR,
		Message.DIFFERENT_SHAPE_OR_COLOR };
		game.addEyeball(0, 6, Direction.LEFT);
		Message[] actual = { game.MessageIfMovingTo(0, 2), game.MessageIfMovingTo(0, 3) };
		assertArrayEquals(expected, actual);
	}
	
	@Test
	void testOkWhenEyeballFacesDownOnMovingLeft() {
		boolean expected = true;
		game.addEyeball(0, 6, Direction.DOWN);
		boolean actual = game.isDirectionOK(0, 5);
		assertEquals(expected, actual);
	}
	
	@Test
	void testOkWhenEyeballFacesUpOnMovingLeft() {
		boolean expected = true;
		game.addEyeball(0, 6, Direction.UP);
		boolean actual = game.isDirectionOK(0, 5);
		assertEquals(expected, actual);
	}
	
	@Test
	void testNotOKWhenMovingBackwards() {
		boolean expected = false;
		game.addEyeball(0, 6, Direction.RIGHT);
		boolean actual = game.isDirectionOK(0, 5);
		assertEquals(expected, actual);
	}
	
	@Test
	void testGetsErrorMessageWhenMovingBackwards() {
		Message expected = Message.BACKWARDS_MOVE;
		game.addEyeball(0, 6, Direction.RIGHT);
		Message actual = game.checkDirectionMessage(0, 5);
		assertEquals(expected, actual);
	}
	
	@Test
	void testNotOKWhenPathToDestinationCrossesBlank() {
		boolean expected = false;
		game.addEyeball(0, 6, Direction.LEFT);
		boolean actual = game.hasBlankFreePathTo(0, 0);
		assertEquals(expected, actual);
	}
	
	@Test
	void testErrorMessageWhenPathToDestinationCrossesBlank() {
		Message expected = Message.MOVING_OVER_BLANK;
		game.addEyeball(0, 6, Direction.LEFT);
		Message actual = game.checkMessageForBlankOnPathTo(0, 0);
		assertEquals(expected, actual);
	}
	
	@Test
	void testEyeballMovesToDestinationRowAndColumn() {
		int expectedRow = 0;
		int expectedColumn = 4;
		int[] expected = { expectedRow, expectedColumn };
		game.addEyeball(0, 6, Direction.LEFT);
		game.moveTo(0, 4);
		int[] actual = { game.getEyeballRow(), game.getEyeballColumn() };
		assertArrayEquals(expected, actual);
	}
	
	@Test
	void testEyeballFacesLeftOnMovingLeft() {
		Direction expectedDirection = Direction.LEFT;
		game.addEyeball(0, 6, Direction.UP);
		game.moveTo(0, 4);
		Direction actualDirection = game.getEyeballDirection();
		assertEquals(expectedDirection, actualDirection);
	}
}
