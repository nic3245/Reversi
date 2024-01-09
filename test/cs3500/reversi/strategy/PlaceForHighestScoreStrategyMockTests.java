package cs3500.reversi.strategy;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import cs3500.reversi.model.MockModelStrategy1Log;
import cs3500.reversi.model.PlayerTile;
import cs3500.reversi.model.ReversiModel;

/**
 * Tests the first strategy by mocking the model and checking the output.
 */
public class PlaceForHighestScoreStrategyMockTests {
  ReversiModel model;
  ReversiStrategy strategy1;
  StringBuilder log;

  @Before
  public void setUp() {
    this.log = new StringBuilder();
    this.model = new MockModelStrategy1Log(log);
    this.strategy1 = new PlaceForHighestScoreStrategy();
  }

  @Test
  public void testChooseMoveTie() {
    this.strategy1.chooseMove(this.model, PlayerTile.FIRST);
    //Have to do assertTrue on contains b/c set iteration is used in the strats, meaning
    //there is no guarantee of order.
    String s = this.log.toString();
    Assert.assertTrue(s.contains("Requested possible player moves for player: X\n" +
            "Moves given to model: \n" + "Move 1: 2,3\n" +
            "Move 2: 3,3\n" +
            "Move 3: 4,3\n"));
    Assert.assertTrue(s.contains("Requested score for move: 2,3\n" +
                    "Score returned for move: 2"));
    Assert.assertTrue(s.contains("Requested score for move: 4,3\n" +
            "Score returned for move: 4"));
    Assert.assertTrue(s.contains("Requested score for move: 3,3\n" +
            "Score returned for move: 3"));
  }
}