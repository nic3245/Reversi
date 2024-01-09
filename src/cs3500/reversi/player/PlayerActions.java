package cs3500.reversi.player;

import java.util.Optional;

import cs3500.reversi.model.CustomPoint2D;

/**
 * Interface that represents an object that wants to know when a player tries to play.
 */
public interface PlayerActions {

  /**
   * Passes the player's turn.
   */
  void pass();

  /**
   * Places a tile at the given location.
   * @param move the location to place the tile at
   */
  void placeTile(Optional<CustomPoint2D> move);
}
