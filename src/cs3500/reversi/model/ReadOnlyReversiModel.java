package cs3500.reversi.model;

import java.util.Map;
import java.util.Set;

/**
 * Interface representing observations on a Reversi model.
 */
public interface ReadOnlyReversiModel {
  /**
   * Returns the player whose turn it is.
   * @return the PlayerTile the model considers as the current turn
   */
  PlayerTile getTurn();

  /**
   * Returns whose tile is at the given location.
   *
   * @param point the point of the tile
   * @return the PlayerTile at the given location
   * @throws IllegalArgumentException if location is not on board
   * @throws IllegalStateException    if location does not have a tile
   */
  PlayerTile getTileAt(CustomPoint2D point);

  /**
   * Returns whether the game is over.
   *
   * @return true iff neither player can move
   */
  boolean isGameOver();

  /**
   * Returns who is winning.
   * A player is winning when they have more tiles than the other player.
   * Ties go to the first player.
   *
   * @return PlayerTile of the type with more on the board
   */
  PlayerTile whoIsWinning();

  /**
   * Returns whether the given PlayerTile has any possible valid moves.
   *
   * @param t the player to check
   * @return true if player can move, false if not
   */
  boolean playerCanMove(PlayerTile t);

  /**
   * Returns all possible valid moves for the given player.
   *
   * @param t the player to check
   * @return the set of possible moves, as Point2Ds representing q and r
   */
  Set<CustomPoint2D> playerMoves(PlayerTile t);

  /**
   * Returns the score of the given player.
   *
   * @param t the player to get the score of
   * @return the score of the player
   */

  int playerScore(PlayerTile t);


  /**
   * Returns whether the spot is empty.
   *
   * @param point the point to check if the spot is empty
   * @return true if empty, false if not
   * @throws IllegalArgumentException if location is not on board
   */
  boolean isSpotEmpty(CustomPoint2D point);

  /**
   * Gets the size of the board, as defined by the implementation.
   *
   * @return the size of the board
   */
  int getBoardSize();

  /**
   * Gets the number of spots in this row.
   *
   * @param row the row to select, with middle row as zero
   * @return the width of the row
   */
  int getRowWidth(int row);

  /**
   * Returns a copy of the board.
   */
  Map<CustomPoint2D, PlayerTile> getBoard();

  /**
   * Returns the score of the move if it were to be played.
   * Can be used regardless of whose turn it actually is.
   * @param moveToPlay move to test
   * @param turn turn to play the move for
   * @return the score of the player after the move is played
   * @throws IllegalArgumentException if coordinates are not on board
   * @throws IllegalStateException    if move is an invalid move
   */
  int getScoreIfMovePlayed(CustomPoint2D moveToPlay, PlayerTile turn);
}
