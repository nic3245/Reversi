package cs3500.reversi.view;

import java.awt.geom.Point2D;

import cs3500.reversi.player.PlayerActions;

/**
 * Represents the features that a view should implement.
 * TODO: when adding controller, impl. this interface, also add selecting to it.
 */
public interface ViewFeatures extends PlayerActions {
  void selectTile(Point2D logicalP);
}
