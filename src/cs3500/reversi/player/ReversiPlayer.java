package cs3500.reversi.player;

import cs3500.reversi.model.PlayerTile;

/**
 * A class representing a human player for a game of reversi.
 */
public class ReversiPlayer implements Player {
  private final PlayerTile player;

  /**
   * Constructor that makes a default, human player of the given PlayerTile.
   *
   * @param player which player it should be (black/white, first/second, etc.)
   */
  public ReversiPlayer(PlayerTile player) {
    this.player = player;
  }

  @Override
  public void subscribeForPlayerWantsToTakeTurn(PlayerActions listener) {
    // Does nothing b/c handled through view
  }

  @Override
  public PlayerTile getPlayerRepresentation() {
    return this.player;
  }

  @Override
  public void takeTurn() {
    // Does nothing - a human player will take indefinite time, and make their move through GUI.
  }
}
