package cs3500.reversi.strategy;

import java.util.Optional;

import cs3500.reversi.model.CustomPoint2D;
import cs3500.reversi.model.PlayerTile;
import cs3500.reversi.model.ReadOnlyReversiModel;

/**
 * This interface represents a possible reversi strategy.
 */
public interface ReversiStrategy {
  /**
   * Returns the 'best' move for the player based on the strategy.
   * Returning an empty Optional implies that passing is the only (or best) move.
   * @param model the model to decide where to move on
   * @param turn the player's turn to decide a move for
   * @return a move based on the strategy used
   */
  Optional<CustomPoint2D> chooseMove(ReadOnlyReversiModel model, PlayerTile turn);
}
