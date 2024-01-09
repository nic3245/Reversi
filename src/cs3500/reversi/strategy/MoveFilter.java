package cs3500.reversi.strategy;

import java.util.Set;

import cs3500.reversi.model.CustomPoint2D;
import cs3500.reversi.model.PlayerTile;
import cs3500.reversi.model.ReadOnlyReversiModel;

/**
 * Represents a method of filtering possible moves to a smaller set of possible moves in Reversi.
 */
public interface MoveFilter {
  /**
   * Filters the given possible moves to a smaller set of possible moves.
   * Should be used when unsure of whether there could be a tie between moves, based on how the
   * filtering is defined. The size of the return set is not guaranteed in any way.
   *
   * @param model         the model the moves would be made on
   * @param turn          the player who would make the moves
   * @param possibleMoves the possible moves to filter down from
   * @return the set of best possible moves, all of which have same value according to definition
   *         the size can be zero, if no moves fit the criteria or no possible moves are given
   */
  Set<CustomPoint2D> filterMoves(ReadOnlyReversiModel model, PlayerTile turn,
                                 Set<CustomPoint2D> possibleMoves);
}
