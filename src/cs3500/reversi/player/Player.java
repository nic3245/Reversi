package cs3500.reversi.player;

import cs3500.reversi.model.PlayerTile;

/**
 * Represents a player in a game of Reversi.
 */
public interface Player {

  /**
   * Subscribes the given listener to this player's turn notifications.
   * @param listener the listener to subscribe
   */
  void subscribeForPlayerWantsToTakeTurn(PlayerActions listener);

  /**
   * Returns the tile representing the player, for turn purposes.
   * @return the tile representing this player
   */
  PlayerTile getPlayerRepresentation();

  /**
   * Has the player taken their turn.
   */
  void takeTurn();
}
