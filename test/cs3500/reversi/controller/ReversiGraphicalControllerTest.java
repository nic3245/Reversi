package cs3500.reversi.controller;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.awt.Point;
import java.util.Optional;

import cs3500.reversi.model.AxialCustomPoint;
import cs3500.reversi.model.BasicReversiModel;
import cs3500.reversi.model.PlayerTile;
import cs3500.reversi.model.ReversiModel;
import cs3500.reversi.player.Player;
import cs3500.reversi.player.ReversiPlayer;
import cs3500.reversi.view.IView;
import cs3500.reversi.view.ReversiGraphicsViewMock;

/**
 * Represents a test class for the ReversiGraphicalController.
 */
public class ReversiGraphicalControllerTest {
  ReversiGraphicalController c1;
  ReversiGraphicalController c2;
  ReversiModel model;
  StringBuilder in;

  @Before
  public void init() {
    model = new BasicReversiModel(7);
    in = new StringBuilder();
    IView view = new ReversiGraphicsViewMock(in);
    Player p1 = new ReversiPlayer(PlayerTile.FIRST);
    Player p2 = new ReversiPlayer(PlayerTile.SECOND);
    c1 = new ReversiGraphicalController(model, p1, view);
    c2 = new ReversiGraphicalController(model, p2, view);
  }

  @Test
  public void testControllerPass() {
    PlayerTile p1 = model.getTurn();
    c1.pass();
    Assert.assertNotEquals(p1, model.getTurn());
    Assert.assertEquals(in.toString().trim(), "Features added to view\n" +
            "Displaying view\n" +
            "Features added to view\n" +
            "Displaying view\n" +
            "View updated\n" +
            "View updated");
  }

  @Test
  public void testControllerPlaceTile() {
    // Basic placement
    this.c1.placeTile(Optional.of(new AxialCustomPoint(2, -1)));
    Assert.assertEquals(PlayerTile.FIRST, this.model.getTileAt(new AxialCustomPoint(2, -1)));
    Assert.assertEquals(PlayerTile.FIRST, this.model.getTileAt(new AxialCustomPoint(1, -1)));
    Assert.assertEquals(PlayerTile.FIRST, this.model.getTileAt(new AxialCustomPoint(0, -1)));

    // Flips multiple tiles in a line, as well as placing on border
    this.c2.placeTile(Optional.of(new AxialCustomPoint(3, -2)));
    Assert.assertEquals(PlayerTile.SECOND, this.model.getTileAt(new AxialCustomPoint(0, 1)));
    Assert.assertEquals(PlayerTile.SECOND, this.model.getTileAt(new AxialCustomPoint(1, 0)));
    Assert.assertEquals(PlayerTile.SECOND, this.model.getTileAt(new AxialCustomPoint(2, -1)));
    Assert.assertEquals(PlayerTile.SECOND, this.model.getTileAt(new AxialCustomPoint(3, -2)));

    // Flips tiles even if multiple different directions
    this.c1.placeTile(Optional.of(new AxialCustomPoint(1, 1)));
    Assert.assertEquals(PlayerTile.FIRST, this.model.getTileAt(new AxialCustomPoint(1, 1)));
    Assert.assertEquals(PlayerTile.FIRST, this.model.getTileAt(new AxialCustomPoint(0, 1)));
    Assert.assertEquals(PlayerTile.FIRST, this.model.getTileAt(new AxialCustomPoint(-1, 1)));
    Assert.assertEquals(PlayerTile.FIRST, this.model.getTileAt(new AxialCustomPoint(1, 0)));
    Assert.assertEquals(PlayerTile.FIRST, this.model.getTileAt(new AxialCustomPoint(1, -1)));
  }

  @Test
  public void testControllerSelectedTile() {
    this.c1.selectTile(new Point(-100, 100));
    Assert.assertEquals(in.toString().trim(),
            "Features added to view\n" +
                    "Displaying view\n" +
                    "Features added to view\n" +
                    "Displaying view\n" +
                    "Tile selected at: -100.0,100.0");

    this.c2.selectTile(new Point(0, 0));
    Assert.assertEquals(in.toString().trim(), "Features added to view\n" +
            "Displaying view\n" +
            "Features added to view\n" +
            "Displaying view\n" +
            "Tile selected at: -100.0,100.0\n" +
            "Tile selected at: 0.0,0.0");
  }

  @Test
  public void testTurnChanged() {
    this.c1.turnChanged(PlayerTile.FIRST);
    Assert.assertEquals(in.toString().trim(), "Features added to view\n" +
            "Displaying view\n" +
            "Features added to view\n" +
            "Displaying view\n" +
            "View updated");
    this.c1.turnChanged(PlayerTile.FIRST);
    Assert.assertEquals(in.toString().trim(), "Features added to view\n" +
            "Displaying view\n" +
            "Features added to view\n" +
            "Displaying view\n" +
            "View updated\n" +
            "View updated");

  }
}
