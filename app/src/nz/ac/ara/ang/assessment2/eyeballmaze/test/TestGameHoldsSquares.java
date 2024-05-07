package nz.ac.ara.ang.assessment2.eyeballmaze.test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import nz.ac.ara.ang.assessment2.eyeballmaze.*;

class TestGameHoldsSquares {
Game game = new Game();
private void setup() {
game = new Game();
game.addLevel(9, 1);
game.addSquare(new BlankSquare(), 0, 0);
game.addSquare(new BlankSquare(), 1, 0);
game.addSquare(new PlayableSquare(Color.BLUE, Shape.DIAMOND), 2, 0);
game.addSquare(new PlayableSquare(Color.RED, Shape.CROSS), 3, 0);
game.addSquare(new PlayableSquare(Color.YELLOW, Shape.STAR), 4, 0);
game.addSquare(new PlayableSquare(Color.GREEN, Shape.FLOWER), 5, 0);
game.addSquare(new PlayableSquare(Color.PURPLE, Shape.LIGHTNING), 6, 0);
game.addSquare(new BlankSquare(), 7, 0);
game.addSquare(new BlankSquare(), 8, 0);
}
@Test
void testAddingSquareOutsideLevelWidthThrowsException() {
game = new Game();
game.addLevel(9, 1);
assertThrows(IllegalArgumentException.class, () -> {
game.addSquare(new BlankSquare(), 0, 4);
});
}
@Test
void testAddingSquareOutsideLevelHeightThrowsException() {
game = new Game();
game.addLevel(9, 1);
assertThrows(IllegalArgumentException.class, () -> {
game.addSquare(new BlankSquare(), 22, 0);
});
}
@Test
void testColoursAreAsAdded() {
setup();
Color[] expectedColors = { Color.BLUE, Color.RED, Color.YELLOW, Color.GREEN, Color.BLANK,
Color.PURPLE };
Color[] actualColors = { game.getColorAt(2, 0), game.getColorAt(3, 0), game.getColorAt(4, 0),
game.getColorAt(5, 0), game.getColorAt(7, 0), game.getColorAt(6, 0) };
assertArrayEquals(expectedColors, actualColors);
}
@Test
void testShapesAreAsAdded() {
setup();
Shape[] expectedShapes = { Shape.DIAMOND, Shape.CROSS, Shape.STAR, Shape.FLOWER,
Shape.BLANK, Shape.LIGHTNING };
Shape[] actualShapes = { game.getShapeAt(2, 0), game.getShapeAt(3, 0), game.getShapeAt(4, 0),
game.getShapeAt(5, 0), game.getShapeAt(7, 0), game.getShapeAt(6, 0) };
assertArrayEquals(expectedShapes, actualShapes);
}
}
