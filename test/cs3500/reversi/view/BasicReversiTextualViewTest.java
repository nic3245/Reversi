package cs3500.reversi.view;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import cs3500.reversi.model.AxialCustomPoint;
import cs3500.reversi.model.BasicReversiModel;
import cs3500.reversi.model.PlayerTile;
import cs3500.reversi.model.ReversiModel;

/**
 * Represents a test suit for BasicReversiView.
 */
public class BasicReversiTextualViewTest {
  ReversiModel model;

  @Before
  public void setUp() {
    this.model = new BasicReversiModel(7);
  }

  @Test
  public void testEmptyBoard() {
    StringBuilder out = new StringBuilder();
    ReversiTextualView view = new ReversiTextualViewImplementation(model, out);
    try {
      view.render();
    } catch (IOException e) {
      e.printStackTrace();
    }
    Assert.assertEquals("   _ _ _ _\n" + "  _ _ _ _ _\n" + " _ _ X O _ _\n"
                    + "_ _ O _ X _ _\n" + " _ _ X O _ _\n" + "  _ _ _ _ _\n" + "   _ _ _ _",
            out.toString());
  }

  @Test
  public void testLargeBoard() {
    StringBuilder out = new StringBuilder();
    ReversiTextualView view = new ReversiTextualViewImplementation(new BasicReversiModel(15), out);
    try {
      view.render();
    } catch (IOException e) {
      e.printStackTrace();
    }
    Assert.assertEquals("       _ _ _ _ _ _ _ _\n" + "      _ _ _ _ _ _ _ _ _\n" +
            "     _ _ _ _ _ _ _ _ _ _\n" + "    _ _ _ _ _ _ _ _ _ _ _\n" +
            "   _ _ _ _ _ _ _ _ _ _ _ _\n" + "  _ _ _ _ _ _ _ _ _ _ _ _ _\n" +
            " _ _ _ _ _ _ X O _ _ _ _ _ _\n" + "_ _ _ _ _ _ O _ X _ _ _ _ _ _\n" +
            " _ _ _ _ _ _ X O _ _ _ _ _ _\n" + "  _ _ _ _ _ _ _ _ _ _ _ _ _\n" +
            "   _ _ _ _ _ _ _ _ _ _ _ _\n" + "    _ _ _ _ _ _ _ _ _ _ _\n" +
            "     _ _ _ _ _ _ _ _ _ _\n" + "      _ _ _ _ _ _ _ _ _\n" +
            "       _ _ _ _ _ _ _ _", out.toString());
  }

  @Test
  public void testFinishedGame() {
    StringBuilder out = new StringBuilder();
    ReversiTextualView view = new ReversiTextualViewImplementation(this.model, out);

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
    try {
      view.render();
    } catch (IOException e) {
      e.printStackTrace();
    }

    Assert.assertEquals("   _ X X X\n" + "  O _ X _ X\n" + " O O O X X X\n" +
            "O _ O _ X _ X\n" + " O O O X X X\n" + "  O _ O _ X\n" + "   O O O _", out.toString());
  }
}