package cs3500.reversi.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Set;


import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

/**
 * Represents a test-suit for BasicReversiModel.
 */
public class BasicReversiModelTest {
  ReversiModel model;

  @Before
  public void setUp() {
    this.model = new BasicReversiModel(7);
  }

  @Test
  public void testBoardThrowsExceptionWithBadSize() {
    Assert.assertThrows("Index cannot be below 0", IllegalArgumentException.class, () ->
            new BasicReversiModel(-2));
    Assert.assertThrows("Too small index", IllegalArgumentException.class, () ->
            new BasicReversiModel(3));
    Assert.assertThrows("Even index", IllegalArgumentException.class, () ->
            new BasicReversiModel(8));
  }

  @Test
  public void testPlaceTileThrowsProperExceptions() {
    // Throws for location not even on board
    Assert.assertThrows(IllegalArgumentException.class, () ->
            this.model.placeTile(new AxialCustomPoint(-4, 0), PlayerTile.FIRST));
    Assert.assertThrows(IllegalArgumentException.class, () ->
            this.model.placeTile(new AxialCustomPoint(0, -4), PlayerTile.FIRST));
    Assert.assertThrows(IllegalArgumentException.class, () ->
            this.model.placeTile(new AxialCustomPoint(4, 0), PlayerTile.FIRST));
    Assert.assertThrows(IllegalArgumentException.class, () ->
            this.model.placeTile(new AxialCustomPoint(0, 4), PlayerTile.FIRST));

    // Throws for location already has a tile
    Assert.assertThrows(IllegalStateException.class, () ->
            this.model.placeTile(new AxialCustomPoint(-1, 0), PlayerTile.FIRST));

    // Throws for logically invalid move
    Assert.assertThrows(IllegalStateException.class, () -> // Next to your own
            this.model.placeTile(new AxialCustomPoint(0, -2), PlayerTile.FIRST));
    Assert.assertThrows(IllegalStateException.class, () -> // Next to nothing
            this.model.placeTile(new AxialCustomPoint(0, -3), PlayerTile.FIRST));
    Assert.assertThrows(IllegalStateException.class, () -> // Next opposite, but empty tile in run
            this.model.placeTile(new AxialCustomPoint(0, 2), PlayerTile.FIRST));

    // Throws for wrong turn
    Assert.assertThrows(IllegalStateException.class, () ->
            this.model.placeTile(new AxialCustomPoint(-1, -1), PlayerTile.SECOND));
    this.model.pass(PlayerTile.FIRST);
    Assert.assertThrows(IllegalStateException.class, () ->
            this.model.placeTile(new AxialCustomPoint(2, -1), PlayerTile.FIRST));
  }

  @Test
  public void testPlaceTileWorks() {
    // Basic placement
    this.model.placeTile(new AxialCustomPoint(2, -1), PlayerTile.FIRST);
    Assert.assertEquals(PlayerTile.FIRST, this.model.getTileAt(new AxialCustomPoint(2, -1)));
    Assert.assertEquals(PlayerTile.FIRST, this.model.getTileAt(new AxialCustomPoint(1, -1)));
    Assert.assertEquals(PlayerTile.FIRST, this.model.getTileAt(new AxialCustomPoint(0, -1)));
    // Flips multiple tiles in a line, as well as placing on border
    this.model.placeTile(new AxialCustomPoint(3, -2), PlayerTile.SECOND);
    Assert.assertEquals(PlayerTile.SECOND, this.model.getTileAt(new AxialCustomPoint(0, 1)));
    Assert.assertEquals(PlayerTile.SECOND, this.model.getTileAt(new AxialCustomPoint(1, 0)));
    Assert.assertEquals(PlayerTile.SECOND, this.model.getTileAt(new AxialCustomPoint(2, -1)));
    Assert.assertEquals(PlayerTile.SECOND, this.model.getTileAt(new AxialCustomPoint(3, -2)));
    // Flips tiles even if multiple different directions
    this.model.placeTile(new AxialCustomPoint(1, 1), PlayerTile.FIRST);
    Assert.assertEquals(PlayerTile.FIRST, this.model.getTileAt(new AxialCustomPoint(1, 1)));
    Assert.assertEquals(PlayerTile.FIRST, this.model.getTileAt(new AxialCustomPoint(0, 1)));
    Assert.assertEquals(PlayerTile.FIRST, this.model.getTileAt(new AxialCustomPoint(-1, 1)));
    Assert.assertEquals(PlayerTile.FIRST, this.model.getTileAt(new AxialCustomPoint(1, 0)));
    Assert.assertEquals(PlayerTile.FIRST, this.model.getTileAt(new AxialCustomPoint(1, -1)));
  }

  @Test
  public void testPassWorks() {
    Assert.assertThrows(IllegalStateException.class, () -> // wrong turn
            this.model.pass(PlayerTile.SECOND));
    this.model.pass(PlayerTile.FIRST);
    Assert.assertThrows(IllegalStateException.class, () -> // wrong turn
            this.model.pass(PlayerTile.FIRST));
    // After passing, now second player can play a valid move
    this.model.placeTile(new AxialCustomPoint(-1, -1), PlayerTile.SECOND);
    Assert.assertTrue(true);
  }

  @Test
  public void testGetTileAtThrowsProperExceptions() {
    // Throws for location not even on board
    Assert.assertThrows(IllegalArgumentException.class, () ->
            this.model.getTileAt(new AxialCustomPoint(-4, 0)));
    Assert.assertThrows(IllegalArgumentException.class, () ->
            this.model.getTileAt(new AxialCustomPoint(0, -4)));
    Assert.assertThrows(IllegalArgumentException.class, () ->
            this.model.getTileAt(new AxialCustomPoint(4, 0)));
    Assert.assertThrows(IllegalArgumentException.class, () ->
            this.model.getTileAt(new AxialCustomPoint(0, 4)));

    // Throws for location does not have a tile
    Assert.assertThrows(IllegalStateException.class, () ->
            this.model.getTileAt(new AxialCustomPoint(0, 0)));
  }

  @Test
  public void testGetTileAtBasicFunctionality() {
    // Relies upon board being initialized correctly, so implicitly tests that
    Assert.assertEquals(PlayerTile.FIRST, this.model.getTileAt(new AxialCustomPoint(0, -1)));
    Assert.assertEquals(PlayerTile.FIRST, this.model.getTileAt(new AxialCustomPoint(-1, 1)));
    Assert.assertEquals(PlayerTile.FIRST, this.model.getTileAt(new AxialCustomPoint(1, 0)));
    Assert.assertEquals(PlayerTile.SECOND, this.model.getTileAt(new AxialCustomPoint(0, 1)));
    Assert.assertEquals(PlayerTile.SECOND, this.model.getTileAt(new AxialCustomPoint(1, -1)));
    Assert.assertEquals(PlayerTile.SECOND, this.model.getTileAt(new AxialCustomPoint(-1, 0)));
  }

  @Test
  public void testGameOverAndPlayerCanMove() {
    this.model.placeTile(new AxialCustomPoint(2, -1), PlayerTile.FIRST);
    assertTrue(this.model.playerCanMove(PlayerTile.FIRST));
    assertTrue(this.model.playerCanMove(PlayerTile.SECOND));
    assertFalse(this.model.isGameOver());
    this.model.placeTile(new AxialCustomPoint(3, -2), PlayerTile.SECOND);
    assertTrue(this.model.playerCanMove(PlayerTile.FIRST));
    assertTrue(this.model.playerCanMove(PlayerTile.SECOND));
    assertFalse(this.model.isGameOver());
    this.model.placeTile(new AxialCustomPoint(1, 1), PlayerTile.FIRST);
    assertTrue(this.model.playerCanMove(PlayerTile.FIRST));
    assertTrue(this.model.playerCanMove(PlayerTile.SECOND));
    assertFalse(this.model.isGameOver());
    this.model.placeTile(new AxialCustomPoint(-1, 2), PlayerTile.SECOND);
    assertTrue(this.model.playerCanMove(PlayerTile.FIRST));
    assertTrue(this.model.playerCanMove(PlayerTile.SECOND));
    assertFalse(this.model.isGameOver());
    this.model.placeTile(new AxialCustomPoint(-2, 1), PlayerTile.FIRST);
    assertTrue(this.model.playerCanMove(PlayerTile.FIRST));
    assertTrue(this.model.playerCanMove(PlayerTile.SECOND));
    assertFalse(this.model.isGameOver());
    this.model.placeTile(new AxialCustomPoint(-1, -1), PlayerTile.SECOND);
    assertTrue(this.model.playerCanMove(PlayerTile.FIRST));
    assertTrue(this.model.playerCanMove(PlayerTile.SECOND));
    assertFalse(this.model.isGameOver());
    this.model.placeTile(new AxialCustomPoint(1, -2), PlayerTile.FIRST);
    assertTrue(this.model.playerCanMove(PlayerTile.FIRST));
    assertTrue(this.model.playerCanMove(PlayerTile.SECOND));
    assertFalse(this.model.isGameOver());
    this.model.placeTile(new AxialCustomPoint(2, 1), PlayerTile.SECOND);
    assertTrue(this.model.playerCanMove(PlayerTile.FIRST));
    assertTrue(this.model.playerCanMove(PlayerTile.SECOND));
    assertFalse(this.model.isGameOver());
    this.model.placeTile(new AxialCustomPoint(1, 2), PlayerTile.FIRST);
    assertTrue(this.model.playerCanMove(PlayerTile.FIRST));
    assertTrue(this.model.playerCanMove(PlayerTile.SECOND));
    assertFalse(this.model.isGameOver());
    this.model.placeTile(new AxialCustomPoint(-3, 1), PlayerTile.SECOND);
    assertTrue(this.model.playerCanMove(PlayerTile.FIRST));
    assertTrue(this.model.playerCanMove(PlayerTile.SECOND));
    assertFalse(this.model.isGameOver());
    this.model.placeTile(new AxialCustomPoint(-2, -1), PlayerTile.FIRST);
    assertTrue(this.model.playerCanMove(PlayerTile.FIRST));
    assertTrue(this.model.playerCanMove(PlayerTile.SECOND));
    assertFalse(this.model.isGameOver());
    this.model.placeTile(new AxialCustomPoint(-1, -2), PlayerTile.SECOND);
    assertTrue(this.model.playerCanMove(PlayerTile.FIRST));
    assertTrue(this.model.playerCanMove(PlayerTile.SECOND));
    assertFalse(this.model.isGameOver());
    this.model.placeTile(new AxialCustomPoint(3, -1), PlayerTile.FIRST);
    assertTrue(this.model.playerCanMove(PlayerTile.FIRST));
    assertTrue(this.model.playerCanMove(PlayerTile.SECOND));
    assertFalse(this.model.isGameOver());
    this.model.placeTile(new AxialCustomPoint(-3, 0), PlayerTile.SECOND);
    assertTrue(this.model.playerCanMove(PlayerTile.FIRST));
    assertTrue(this.model.playerCanMove(PlayerTile.SECOND));
    assertFalse(this.model.isGameOver());
    this.model.placeTile(new AxialCustomPoint(-3, 2), PlayerTile.FIRST);
    assertTrue(this.model.playerCanMove(PlayerTile.FIRST));
    assertTrue(this.model.playerCanMove(PlayerTile.SECOND));
    assertFalse(this.model.isGameOver());
    this.model.placeTile(new AxialCustomPoint(-3, 3), PlayerTile.SECOND);
    assertTrue(this.model.playerCanMove(PlayerTile.FIRST));
    assertTrue(this.model.playerCanMove(PlayerTile.SECOND));
    assertFalse(this.model.isGameOver());
    this.model.placeTile(new AxialCustomPoint(-2, 3), PlayerTile.FIRST);
    assertTrue(this.model.playerCanMove(PlayerTile.FIRST));
    assertTrue(this.model.playerCanMove(PlayerTile.SECOND));
    assertFalse(this.model.isGameOver());
    this.model.placeTile(new AxialCustomPoint(-1, 3), PlayerTile.SECOND);
    assertTrue(this.model.playerCanMove(PlayerTile.FIRST));
    assertTrue(this.model.playerCanMove(PlayerTile.SECOND));
    assertFalse(this.model.isGameOver());
    this.model.placeTile(new AxialCustomPoint(3, 0), PlayerTile.FIRST);
    assertTrue(this.model.playerCanMove(PlayerTile.FIRST));
    assertTrue(this.model.playerCanMove(PlayerTile.SECOND));
    assertFalse(this.model.isGameOver());
    this.model.placeTile(new AxialCustomPoint(2, -3), PlayerTile.SECOND);
    assertTrue(this.model.playerCanMove(PlayerTile.FIRST));
    assertFalse(this.model.playerCanMove(PlayerTile.SECOND));
    assertFalse(this.model.isGameOver());
    this.model.placeTile(new AxialCustomPoint(3, -3), PlayerTile.FIRST);
    assertTrue(this.model.playerCanMove(PlayerTile.FIRST));
    assertFalse(this.model.playerCanMove(PlayerTile.SECOND));
    assertFalse(this.model.isGameOver());
    this.model.pass(PlayerTile.SECOND);
    assertFalse(this.model.isGameOver());
    this.model.placeTile(new AxialCustomPoint(1, -3), PlayerTile.FIRST);
    assertFalse(this.model.playerCanMove(PlayerTile.FIRST));
    assertFalse(this.model.playerCanMove(PlayerTile.SECOND));
    assertTrue(this.model.isGameOver());
  }

  @Test
  public void testPlayerScoreWhoIsWinning() {
    this.model.placeTile(new AxialCustomPoint(2, -1), PlayerTile.FIRST);
    Assert.assertEquals(5, this.model.playerScore(PlayerTile.FIRST));
    Assert.assertEquals(2, this.model.playerScore(PlayerTile.SECOND));
    Assert.assertEquals(PlayerTile.FIRST, this.model.whoIsWinning());
    this.model.placeTile(new AxialCustomPoint(3, -2), PlayerTile.SECOND);
    Assert.assertEquals(3, this.model.playerScore(PlayerTile.FIRST));
    Assert.assertEquals(5, this.model.playerScore(PlayerTile.SECOND));
    Assert.assertEquals(PlayerTile.SECOND, this.model.whoIsWinning());
    this.model.placeTile(new AxialCustomPoint(1, 1), PlayerTile.FIRST);
    Assert.assertEquals(6, this.model.playerScore(PlayerTile.FIRST));
    Assert.assertEquals(3, this.model.playerScore(PlayerTile.SECOND));
    Assert.assertEquals(PlayerTile.FIRST, this.model.whoIsWinning());
    this.model.placeTile(new AxialCustomPoint(-1, 2), PlayerTile.SECOND);
    Assert.assertEquals(3, this.model.playerScore(PlayerTile.FIRST));
    Assert.assertEquals(7, this.model.playerScore(PlayerTile.SECOND));
    Assert.assertEquals(PlayerTile.SECOND, this.model.whoIsWinning());
    this.model.placeTile(new AxialCustomPoint(-2, 1), PlayerTile.FIRST);
    Assert.assertEquals(7, this.model.playerScore(PlayerTile.FIRST));
    Assert.assertEquals(4, this.model.playerScore(PlayerTile.SECOND));
    Assert.assertEquals(PlayerTile.FIRST, this.model.whoIsWinning());
    this.model.placeTile(new AxialCustomPoint(-1, -1), PlayerTile.SECOND);
    Assert.assertEquals(3, this.model.playerScore(PlayerTile.FIRST));
    Assert.assertEquals(9, this.model.playerScore(PlayerTile.SECOND));
    Assert.assertEquals(PlayerTile.SECOND, this.model.whoIsWinning());
    this.model.placeTile(new AxialCustomPoint(1, -2), PlayerTile.FIRST);
    Assert.assertEquals(8, this.model.playerScore(PlayerTile.FIRST));
    Assert.assertEquals(5, this.model.playerScore(PlayerTile.SECOND));
    Assert.assertEquals(PlayerTile.FIRST, this.model.whoIsWinning());
    this.model.placeTile(new AxialCustomPoint(2, 1), PlayerTile.SECOND);
    Assert.assertEquals(6, this.model.playerScore(PlayerTile.FIRST));
    Assert.assertEquals(8, this.model.playerScore(PlayerTile.SECOND));
    Assert.assertEquals(PlayerTile.SECOND, this.model.whoIsWinning());
    this.model.placeTile(new AxialCustomPoint(1, 2), PlayerTile.FIRST);
    Assert.assertEquals(8, this.model.playerScore(PlayerTile.FIRST));
    Assert.assertEquals(7, this.model.playerScore(PlayerTile.SECOND));
    Assert.assertEquals(PlayerTile.FIRST, this.model.whoIsWinning());

  }

  @Test
  public void testIsSpotEmpty() {
    // Should throw when given coordinates that are not on the board
    Assert.assertThrows(IllegalArgumentException.class, () ->
            this.model.isSpotEmpty(new AxialCustomPoint(-4, 0)));
    Assert.assertThrows(IllegalArgumentException.class, () ->
            this.model.isSpotEmpty(new AxialCustomPoint(0, -4)));
    Assert.assertThrows(IllegalArgumentException.class, () ->
            this.model.isSpotEmpty(new AxialCustomPoint(4, 0)));
    Assert.assertThrows(IllegalArgumentException.class, () ->
            this.model.isSpotEmpty(new AxialCustomPoint(0, 4)));

    Assert.assertFalse(this.model.isSpotEmpty(new AxialCustomPoint(-1, 0)));
    Assert.assertTrue(this.model.isSpotEmpty(new AxialCustomPoint(0, 0)));
    Assert.assertTrue(this.model.isSpotEmpty(new AxialCustomPoint(0, -3)));

  }

  @Test
  public void testGetRowWidth() {
    Assert.assertEquals(4, this.model.getRowWidth(-3));
    Assert.assertEquals(5, this.model.getRowWidth(-2));
    Assert.assertEquals(6, this.model.getRowWidth(-1));
    Assert.assertEquals(7, this.model.getRowWidth(0));
    Assert.assertEquals(6, this.model.getRowWidth(1));
    Assert.assertEquals(5, this.model.getRowWidth(2));
    Assert.assertEquals(4, this.model.getRowWidth(3));
  }

  @Test
  public void testGameOnLargerBoard() {
    this.model = new BasicReversiModel(15);
    this.model.placeTile(new AxialCustomPoint(2, -1), PlayerTile.FIRST);
    this.model.placeTile(new AxialCustomPoint(3, -2), PlayerTile.SECOND);
    this.model.placeTile(new AxialCustomPoint(1, 1), PlayerTile.FIRST);
    this.model.placeTile(new AxialCustomPoint(-1, 2), PlayerTile.SECOND);
    this.model.placeTile(new AxialCustomPoint(-2, 1), PlayerTile.FIRST);
    this.model.placeTile(new AxialCustomPoint(-1, -1), PlayerTile.SECOND);
    this.model.placeTile(new AxialCustomPoint(1, -2), PlayerTile.FIRST);
    this.model.placeTile(new AxialCustomPoint(2, 1), PlayerTile.SECOND);
    this.model.placeTile(new AxialCustomPoint(1, 2), PlayerTile.FIRST);
    this.model.placeTile(new AxialCustomPoint(-3, 1), PlayerTile.SECOND);
    this.model.placeTile(new AxialCustomPoint(-2, -1), PlayerTile.FIRST);
    this.model.placeTile(new AxialCustomPoint(-1, -2), PlayerTile.SECOND);
    this.model.placeTile(new AxialCustomPoint(3, -1), PlayerTile.FIRST);
    this.model.placeTile(new AxialCustomPoint(-3, 0), PlayerTile.SECOND);
    this.model.placeTile(new AxialCustomPoint(-3, 2), PlayerTile.FIRST);
    this.model.placeTile(new AxialCustomPoint(-3, 3), PlayerTile.SECOND);
    this.model.placeTile(new AxialCustomPoint(-2, 3), PlayerTile.FIRST);
    this.model.placeTile(new AxialCustomPoint(-1, 3), PlayerTile.SECOND);
    this.model.placeTile(new AxialCustomPoint(3, 0), PlayerTile.FIRST);
    this.model.placeTile(new AxialCustomPoint(2, -3), PlayerTile.SECOND);
    this.model.placeTile(new AxialCustomPoint(3, -3), PlayerTile.FIRST);
    this.model.pass(PlayerTile.SECOND);
    this.model.placeTile(new AxialCustomPoint(1, -3), PlayerTile.FIRST);
    Assert.assertTrue(true);

  }

  @Test
  public void testPlayerMoves() {
    Assert.assertEquals(Set.of(new AxialCustomPoint(1, -2), new AxialCustomPoint(2, -1),
            new AxialCustomPoint(-2, 1), new AxialCustomPoint(-1, -1), new AxialCustomPoint(1, 1),
            new AxialCustomPoint(-1, 2)), this.model.playerMoves(PlayerTile.FIRST));
    this.model.placeTile(new AxialCustomPoint(2, -1), PlayerTile.FIRST);
    this.model.placeTile(new AxialCustomPoint(3, -2), PlayerTile.SECOND);
    this.model.placeTile(new AxialCustomPoint(1, 1), PlayerTile.FIRST);
    this.model.placeTile(new AxialCustomPoint(-1, 2), PlayerTile.SECOND);
    this.model.placeTile(new AxialCustomPoint(-2, 1), PlayerTile.FIRST);
    this.model.placeTile(new AxialCustomPoint(-1, -1), PlayerTile.SECOND);
    this.model.placeTile(new AxialCustomPoint(1, -2), PlayerTile.FIRST);
    this.model.placeTile(new AxialCustomPoint(2, 1), PlayerTile.SECOND);
    this.model.placeTile(new AxialCustomPoint(1, 2), PlayerTile.FIRST);
    this.model.placeTile(new AxialCustomPoint(-3, 1), PlayerTile.SECOND);
    this.model.placeTile(new AxialCustomPoint(-2, -1), PlayerTile.FIRST);
    this.model.placeTile(new AxialCustomPoint(-1, -2), PlayerTile.SECOND);
    this.model.placeTile(new AxialCustomPoint(3, -1), PlayerTile.FIRST);
    this.model.placeTile(new AxialCustomPoint(-3, 0), PlayerTile.SECOND);
    this.model.placeTile(new AxialCustomPoint(-3, 2), PlayerTile.FIRST);
    this.model.placeTile(new AxialCustomPoint(-3, 3), PlayerTile.SECOND);
    this.model.placeTile(new AxialCustomPoint(-2, 3), PlayerTile.FIRST);
    this.model.placeTile(new AxialCustomPoint(-1, 3), PlayerTile.SECOND);
    this.model.placeTile(new AxialCustomPoint(3, 0), PlayerTile.FIRST);
    this.model.placeTile(new AxialCustomPoint(2, -3), PlayerTile.SECOND);
    this.model.placeTile(new AxialCustomPoint(3, -3), PlayerTile.FIRST);
    this.model.pass(PlayerTile.SECOND);
    this.model.placeTile(new AxialCustomPoint(1, -3), PlayerTile.FIRST);
    Assert.assertEquals(Set.of(), this.model.playerMoves(PlayerTile.FIRST));
    Assert.assertEquals(Set.of(), this.model.playerMoves(PlayerTile.SECOND));

  }

}

















