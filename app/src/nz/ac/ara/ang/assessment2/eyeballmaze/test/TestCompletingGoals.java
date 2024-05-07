package nz.ac.ara.ang.assessment2.eyeballmaze.test;

import nz.ac.ara.ang.assessment2.eyeballmaze.*;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class TestCompletingGoals {
	Game game;
	LevelDataHandler levelDataHandler;
	
	record SquareData(Color color, Shape shape, Position position) {};
	
	SquareData[] levelOneInitData = {
		new SquareData(Color.BLANK, Shape.BLANK, new Position(0, 0)),
		new SquareData(Color.BLANK, Shape.BLANK, new Position(0, 1)),
		new SquareData(Color.RED, Shape.FLOWER, new Position(0, 2)),
		new SquareData(Color.BLANK, Shape.BLANK, new Position(0, 3)),
		new SquareData(Color.BLUE, Shape.CROSS, new Position(1, 0)),
		new SquareData(Color.YELLOW, Shape.FLOWER, new Position(1, 1)),
		new SquareData(Color.YELLOW, Shape.DIAMOND, new Position(1, 2)),
		new SquareData(Color.GREEN, Shape.CROSS, new Position(1, 3)),
		new SquareData(Color.GREEN, Shape.FLOWER, new Position(2, 0)),
		new SquareData(Color.RED, Shape.STAR, new Position(2, 1)),
		new SquareData(Color.GREEN, Shape.STAR, new Position(2, 2)),
		new SquareData(Color.YELLOW, Shape.DIAMOND, new Position(2, 3)),
		new SquareData(Color.RED, Shape.FLOWER, new Position(3, 0)),
		new SquareData(Color.BLUE, Shape.FLOWER, new Position(3, 1)),
		new SquareData(Color.RED, Shape.STAR, new Position(3, 2)),
		new SquareData(Color.GREEN, Shape.FLOWER, new Position(3, 3)),
		new SquareData(Color.BLUE, Shape.STAR, new Position(4, 0)),
		new SquareData(Color.RED, Shape.DIAMOND, new Position(4, 1)),
		new SquareData(Color.BLUE, Shape.FLOWER, new Position(4, 2)),
		new SquareData(Color.BLUE, Shape.DIAMOND, new Position(4, 3)),
		new SquareData(Color.BLANK, Shape.BLANK, new Position(5, 0)),
		new SquareData(Color.BLUE, Shape.DIAMOND, new Position(5, 1)),
		new SquareData(Color.BLANK, Shape.BLANK, new Position(5, 2)),
		new SquareData(Color.BLANK, Shape.BLANK, new Position(5, 3)) };
	
	Position[] levelOneSolution = {
		new Position(3, 1), new Position(3, 3), new Position(1, 3), new Position(1, 0),
		new Position(4, 0), new Position(4, 2), new Position(0, 2) };
	
	private class LevelDataHandler {
		Game game;
		public LevelDataHandler(Game game) {
			this.game = game;
		}
		public void createLevel(int height, int width) {
			this.game.addLevel(height, width);
		}
		public void setUpLevel(SquareData[] levelInitData) {
			for (SquareData s : levelInitData) {
				Square square;
				if ((s.color == Color.BLANK) && (s.shape == Shape.BLANK)) {
					square = new BlankSquare();
				} else {
					square = new PlayableSquare(s.color, s.shape);
				}
				this.game.addSquare(square, s.position.getRow(), s.position.getColumn());
			}
		}
	}
	private void setUpLevelOne() {
		game = new Game();
		levelDataHandler = new LevelDataHandler(game);
		levelDataHandler.createLevel(6, 4);
		levelDataHandler.setUpLevel(levelOneInitData);
		game.addGoal(0, 2);
		game.addEyeball(5, 1, Direction.UP);
		}
	
		@Test
		void testCompletingLevelOne() {
			setUpLevelOne();
			for (Position p : levelOneSolution) {
			game.moveTo(p.getRow(), p.getColumn());
			}
			int expectedCompletedGoalCount = 1;
			int actualGoalCount = game.getCompletedGoalCount();
			assertEquals(expectedCompletedGoalCount, actualGoalCount);
		}
		
		private void add10High1WideLevel() {
			game = new Game();
			game.addLevel(10, 1);
			game.addSquare(new PlayableSquare(Color.YELLOW, Shape.STAR), 4, 0);
			game.addSquare(new PlayableSquare(Color.RED, Shape.CROSS), 5, 0);
			game.addSquare(new PlayableSquare(Color.GREEN, Shape.STAR), 6, 0);
			game.addSquare(new PlayableSquare(Color.BLUE, Shape.DIAMOND), 7, 0);
			game.addSquare(new PlayableSquare(Color.GREEN, Shape.FLOWER), 8, 0);
			game.addEyeball(4, 0, Direction.LEFT);
			game.addGoal(6, 0);
		}
		
		@Test
		void testOkToMoveToAGoalWithSameColorOrShape() {
			add10High1WideLevel();
			assertTrue(game.canMoveTo(6, 0));
		}
		
		@Test
		void testNoErrorMessageWhenMovingToAGoalWithSameColorOrShape() {
			add10High1WideLevel();
			Message expected = Message.OK;
			Message actual = game.MessageIfMovingTo(6, 0);
			assertEquals(expected, actual);
		}
		
		@Test
		void testNotOkToMoveToAGoalWithDifferentColorOrShape() {
			add10High1WideLevel();
			game.addGoal(8, 0);
			assertFalse(game.canMoveTo(8, 0));
		}
		
		@Test
		void testGetsErrorMessageWhenMovingToAGoalWithDifferentColorOrShape() {
			add10High1WideLevel();
			Message expected = Message.DIFFERENT_SHAPE_OR_COLOR;
			game.addGoal(8, 0);
			Message actual = game.MessageIfMovingTo(8, 0);
		}
		
		@Test
		void testNotOkToMoveToAGoalAtADiagonalSquare() {
			setUpLevelOne();
			game.addGoal(2, 3);
			assertFalse(game.canMoveTo(2, 3));
		}
		
		@Test
		void testNotOkWhenMovingBackwardsToAGoalWithSameColorOrShape() {
			setUpLevelOne();
			game.addGoal(3, 0);
			game.moveTo(3, 1);
			game.moveTo(3, 3);
			assertFalse(game.canMoveTo(3, 0));
		}
		
		@Test
		void testCompleting1GoalIncreasesCompletedGoalCountBy1() {
			add10High1WideLevel();
			int expectedCompletedGoalCount = game.getCompletedGoalCount() + 1;
			game.moveTo(6, 0);
			int actualCompletedGoalCount = game.getCompletedGoalCount();
			assertEquals(expectedCompletedGoalCount, actualCompletedGoalCount);
		}
		
		@Test
		void testCompleting1GoalDecreaseGoalCountBy1() {
			add10High1WideLevel();
			int expectedGoalCount = game.getGoalCount() - 1;
			game.moveTo(6, 0);
			int actualGoalCount = game.getGoalCount();
			assertEquals(expectedGoalCount, actualGoalCount);
		}
		
		@Test
			void testGoalWillBeRemovedFromSquareAfterCompletingTheGoal() {
			add10High1WideLevel();
			game.moveTo(6, 0);
			assertFalse(game.hasGoalAt(6, 0));
		}
		
		@Test
		void testGoalSquareWillNotBeChangedWhenEyeballMovesOntoIt() {
			add10High1WideLevel();
			String[] expected = { "GREEN", "STAR" };
			game.moveTo(6, 0);
			String[] actual = { game.getColorAt(6, 0).name(), game.getShapeAt(6, 0).name() };
			System.out.println(actual);
			assertArrayEquals(expected, actual);
		}
		
		@Test
		void testGoalSquareBecomesBlankSquareAfterEyeballMovesToAnotherSquare() {
			add10High1WideLevel();
			game.moveTo(6, 0);
			game.moveTo(8, 0);
			String[] expected = { "BLANK", "BLANK" };
			String[] actual = { game.getColorAt(6, 0).name(), game.getShapeAt(6, 0).name() };
			assertArrayEquals(expected, actual);
	}
}