package cs3500.reversi.view;

import org.junit.Assert;
import org.junit.Test;


import cs3500.reversi.model.AxialCustomPoint;
import cs3500.reversi.model.BasicReversiModel;
import cs3500.reversi.model.PlayerTile;
import cs3500.reversi.model.ReversiModel;

import javax.swing.SwingUtilities;

/**
 * Code to run to get the screenshots required in the assignment.
 */
public class ScreenShotCode {
  ReversiModel model = new BasicReversiModel(11);

  @Test
  public void testInitialGame() {
    SwingUtilities.invokeLater(() -> new ReversiGraphicsView(this.model));
    try {
      Thread.sleep(10000);
    } catch (InterruptedException ex) {
      ex.printStackTrace();
    }
    Assert.assertTrue(true);
  }

  @Test
  public void testMiddleGame() {
    this.model.placeTile(new AxialCustomPoint(2, -1), PlayerTile.FIRST);
    this.model.placeTile(new AxialCustomPoint(3, -2), PlayerTile.SECOND);
    this.model.placeTile(new AxialCustomPoint(1, 1), PlayerTile.FIRST);
    this.model.placeTile(new AxialCustomPoint(-1, 2), PlayerTile.SECOND);
    this.model.placeTile(new AxialCustomPoint(-2, 1), PlayerTile.FIRST);
    this.model.placeTile(new AxialCustomPoint(-1, -1), PlayerTile.SECOND);
    this.model.placeTile(new AxialCustomPoint(1, -2), PlayerTile.FIRST);
    SwingUtilities.invokeLater(() -> new ReversiGraphicsView(this.model));

    try {
      Thread.sleep(10000);
    } catch (InterruptedException ex) {
      ex.printStackTrace();
    }

    Assert.assertTrue(true);
  }


}
