package nz.ac.ara.ang.assessment2.eyeballmaze.test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import nz.ac.ara.ang.assessment2.eyeballmaze.*;

class TestGameHoldsLevels {
Game game = new Game();
void addTestLevel1() {
game.addLevel(1, 5);
}
void addTestLevel2() {
game.addLevel(7, 3);
}
@Test
void testGetLevelWidth() {
this.addTestLevel1();
int expectedWidth = 5;
int actualWidth = game.getLevelWidth();
assertEquals(expectedWidth, actualWidth);
}
@Test
void testGetLevelHeight() {
this.addTestLevel1();
int expectedHeight = 1;
int actualHeight = game.getLevelHeight();
assertEquals(expectedHeight, actualHeight);
}
@Test
void testGetLevelCountWithOneLevel() {
this.addTestLevel1();
int expectedLevelCount = 1;
int actuallevelCount = game.getLevelCount();
assertEquals(expectedLevelCount, actuallevelCount);
}
@Test
void testGetLevelCountWithTwoLevels() {
this.addTestLevel1();
this.addTestLevel2();
int expectedLevelCount = 2;
int actuallevelCount = game.getLevelCount();
assertEquals(expectedLevelCount, actuallevelCount);
}
@Test
void testMostRecentlyAddedLevelIsCurrentLevelbyCheckingSize() {
this.addTestLevel1();
this.addTestLevel2();
int[] expectedLevelSize = { 7, 3 };
int[] actualLevelSize = { game.getLevelHeight(), game.getLevelWidth() };
assertArrayEquals(expectedLevelSize, actualLevelSize);
}
@Test
void testSettingLevelChangesCurrentLevelbyCheckingSize() {
this.addTestLevel1();
this.addTestLevel2();
int[] expectedLevelSize = { 1, 5 };
game.setLevel(0);
int[] actualLevelSize = { game.getLevelHeight(), game.getLevelWidth() };
assertArrayEquals(expectedLevelSize, actualLevelSize);
}
@Test
void testSettingLeveltoToolargeNumberThrowsException() {
this.addTestLevel1();
this.addTestLevel2();
assertThrows(IllegalArgumentException.class, () -> {
game.setLevel(42);
});
}
}