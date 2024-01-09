package cs3500.reversi.model;

/**
 * Interface representing a model for the game of reversi.
 */
public interface ReversiModel extends ReadOnlyReversiModel {

  /**
   * Starts the game of the model.
   */
  void startGame();

  /**
   * Places a tile at the given location, for the player's turn it is.
   *
   * @param point the point to place the tile at
   * @param t the tile to place
   * @throws IllegalArgumentException if coordinates are not on board
   * @throws IllegalStateException    if move is an invalid move
   * @throws IllegalStateException    if tile given does not match the current turn
   */
  void placeTile(CustomPoint2D point, PlayerTile t);

  /**
   * Passes a player's turn.
   *
   * @throws IllegalStateException if tile given does not match the current turn
   */
  void pass(PlayerTile t);

  /**
   * After calling, the given t will have t.turnChanged() called whenever the turn changes.
   * @param listener a listener (usually controller)
   */
  void subscribeForTurnNotifs(ModelFeatures listener);
}
