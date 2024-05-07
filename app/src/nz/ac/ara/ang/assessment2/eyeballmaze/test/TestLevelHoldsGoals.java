package nz.ac.ara.ang.assessment2.eyeballmaze.test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import nz.ac.ara.ang.assessment2.eyeballmaze.*;


class TestLevelHoldsGoals {
Game game;
@BeforeEach
void add7High3WideLevel() throws Exception {
game = new Game();
game.addLevel(7, 3);
}
@Test
void testAddingOneGoalIncreasesGoalCountTo1() {
// this.add7High3WideLevel();
game.addGoal(4, 2);
int expectedGoalCount = 1;
int actualGoalCount = game.getGoalCount();
assertEquals(expectedGoalCount, actualGoalCount);
}
@Test
void testAddingOneGoalputsGoalAtExpectedPosition() {
// this.add7High3WideLevel();
game.addGoal(4, 2);
boolean hasGoal = game.hasGoalAt(4, 2);
assertTrue(hasGoal);
}
@Test
void testAddingTwoGoalIncreasesGoalCountto2() {
// this.add7High3WideLevel();
game.addGoal(4, 2);
game.addGoal(6, 1);
int expectedGoalCount = 2;
int actualGoalCount = game.getGoalCount();
assertEquals(expectedGoalCount, actualGoalCount);
}
@Test
void testAddingTwoGoalsPutsGoalsAtExpectedPositions() {
// this.add7High3WideLevel();
game.addGoal(4, 2);
game.addGoal(6, 1);
boolean[] expectedHasGoals = { true, true };
boolean[] actualHasGoals = { game.hasGoalAt(4, 2), game.hasGoalAt(6, 1) };
assertArrayEquals(expectedHasGoals, actualHasGoals);
}
@Test
void testNewGamehas0CompletedGoals() {
// this.add7High3WideLevel();
game.addGoal(4, 2);
game.addGoal(6, 1);
int expectedGoalCount = 0;
int actualGoalCount = game.getCompletedGoalCount();
assertEquals(expectedGoalCount, actualGoalCount);
}
@Test
void testAddingGoalsOutsideLevelHeightThrowsRangeException() {
// this.add7High3WideLevel();
assertThrows(IllegalArgumentException.class, () -> {
game.addGoal(8, 2);
});
}
@Test
void testAddingGoalsOutsideLevelWidthThrowsRangeException() {
// this.add7High3WideLevel();
assertThrows(IllegalArgumentException.class, () -> {
game.addGoal(2, 9);
});
}
}

