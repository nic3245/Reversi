package cs3500.reversi.strategy;

import java.util.Set;

import cs3500.reversi.model.CustomPoint2D;
import cs3500.reversi.model.PlayerTile;
import cs3500.reversi.model.ReadOnlyReversiModel;

/**
 * An interface that represents a way to break ties between possible moves in Reversi.
 */
public interface BreakTies {
  /**
   * Returns the 'best' move from the given possible moves, based on how ties are to be broken.
   * Should *only* be used when the selection will *always* result in a singular, best move.
   * possibleMoves *cannot* be empty, as this does not make sense in the context.
   *
   * @param model         the model that the moves are being played on
   * @param turn          the player that is playing
   * @param possibleMoves the possible moves to examine
   * @return the 'best' move based on how ties are defined to be broken.
   * @throws IllegalArgumentException if possibleMoves is empty
   */
  CustomPoint2D breakTies(ReadOnlyReversiModel model, PlayerTile turn,
                          Set<CustomPoint2D> possibleMoves);
}
