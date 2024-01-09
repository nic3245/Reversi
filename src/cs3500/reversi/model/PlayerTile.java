package cs3500.reversi.model;

import java.awt.Color;

/**
 * Enum representing a player's tile in a game of reversi.
 */
public enum PlayerTile {
  FIRST("X", Color.BLACK),
  SECOND("O", Color.WHITE);

  private final String displayString;
  private final Color color;

  PlayerTile(String displayString, Color color) {
    this.displayString = displayString;
    this.color = color;
  }

  /**
   * Returns the string associated with the enum value.
   *
   * @return X for first, O for second
   */
  public String toString() {
    return this.displayString;
  }

  /**
   * Returns whether this player's tile is opposite the given one.
   *
   * @param t tile to compare to
   * @return true iff, between given tile and this tile, one is first and one is second
   */
  public boolean isOpposite(PlayerTile t) {
    return (t == PlayerTile.FIRST && this == PlayerTile.SECOND)
            || (t == PlayerTile.SECOND && this == PlayerTile.FIRST);
  }

  /**
   * Returns the opposite of this tile.
   *
   * @return second iff first, first iff second
   */
  public PlayerTile getOpposite() {
    if (this == PlayerTile.FIRST) {
      return PlayerTile.SECOND;
    } else {
      return PlayerTile.FIRST;
    }
  }

  /**
   * Returns the color of the tile associated with the player.
   *
   * @return the color
   */
  public Color getColor() {
    return this.color;
  }
}
