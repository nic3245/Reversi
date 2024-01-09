package cs3500.reversi.strategy;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import cs3500.reversi.model.AxialCustomPoint;
import cs3500.reversi.model.BasicReversiModel;
import cs3500.reversi.model.PlayerTile;
import cs3500.reversi.model.ReversiModel;

/**
 * Tests the first strategy for correctness.
 */
public class PlaceForCornerOptimalThenHighestScoreStrategyTest {
  ReversiModel model;
  ReversiStrategy strategy23;

  @Before
  public void setUp() {
    this.model = new BasicReversiModel(7);
    this.strategy23 = new PlaceForCornerOptimalThenHighestScore();
  }

  @Test
  public void testChooseMoveTie() {
    //Top-most
    Assert.assertEquals(new AxialCustomPoint(1, -2),
            this.strategy23.chooseMove(this.model, PlayerTile.FIRST).get());
    this.model.placeTile(new AxialCustomPoint(1, -2), PlayerTile.FIRST);
    //Left-most when multiple on same row available
    //Also implicitly checks that place can be chosen when not player's turn
    Assert.assertEquals(new AxialCustomPoint(-1, -1),
            this.strategy23.chooseMove(this.model, PlayerTile.FIRST).get());
  }

  @Test
  public void testChoosesCornerWhenAvailable() {
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
    Assert.assertEquals(new AxialCustomPoint(-3, 0),
            this.strategy23.chooseMove(this.model, PlayerTile.SECOND).get());
  }

  @Test
  public void testChoosesNotNextToCorner() {
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
    Assert.assertNotEquals(new AxialCustomPoint(3, -1),
            this.strategy23.chooseMove(this.model, PlayerTile.FIRST).get());
  }

  @Test
  public void testChooseMoveReturnsEmptyWhenNothingLeft() {
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
    //Second has no more moves left
    Assert.assertEquals(Optional.empty(),
            this.strategy23.chooseMove(this.model, PlayerTile.SECOND));
    this.model.pass(PlayerTile.SECOND);
    this.model.placeTile(new AxialCustomPoint(1, -3), PlayerTile.FIRST);
    //First has no more moves left
    Assert.assertEquals(Optional.empty(),
            this.strategy23.chooseMove(this.model, PlayerTile.FIRST));
  }
}