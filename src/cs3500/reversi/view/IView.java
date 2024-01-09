package cs3500.reversi.view;

import java.awt.geom.Point2D;

/**
 * Represents a graphical view of Reversi.
 */
public interface IView {
  void addFeatures(ViewFeatures features);

  /**
   * Tell the user that they played an invalid move.
   */
  void showMessage(String s);

  /**
   * Selects the tile at logicalP. Deselects any selection if logicalP not on board.
   *
   * @param logicalP the logical coordinates of tile to select
   */
  void selectTile(Point2D logicalP);

  /**
   * Turns the view on or off.
   *
   * @param b true to turn on, false to turn off
   */
  void display(boolean b);

  /**
   * Checks for any updates to the view and draws them.
   */
  void update();
}
