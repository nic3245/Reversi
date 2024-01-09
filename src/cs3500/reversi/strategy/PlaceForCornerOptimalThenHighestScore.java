package cs3500.reversi.strategy;

import java.util.Optional;
import java.util.Set;

import cs3500.reversi.model.CustomPoint2D;
import cs3500.reversi.model.PlayerTile;
import cs3500.reversi.model.ReadOnlyReversiModel;

/**
 * Represents a strategy that will follow the rules below:
 * If a corner is available, place in the corner.
 * If no corner is available, place in the highest scoring position that is not next to a corner.
 * If there are multiple of those, choose the uppermost leftmost move of them.
 */
public class PlaceForCornerOptimalThenHighestScore implements ReversiStrategy {
  @Override
  public Optional<CustomPoint2D> chooseMove(ReadOnlyReversiModel model, PlayerTile t) {
    Set<CustomPoint2D> possibleMoves = model.playerMoves(t);
    if (possibleMoves.isEmpty()) {
      return Optional.empty();
    } else {
      Set<CustomPoint2D> movesWithoutNextToCorner =
              new FilterMovesRemoveNextToCorner().filterMoves(model, t, possibleMoves);
      if (!movesWithoutNextToCorner.isEmpty()) {
        possibleMoves = movesWithoutNextToCorner;
      }
      Set<CustomPoint2D> cornerMoves =
              new FilterMovesToOnlyCorners().filterMoves(model, t, possibleMoves);
      if (!cornerMoves.isEmpty()) {
        possibleMoves = cornerMoves;
      }
      //Filter to best score will always return a length > 0 when given possibleMoves.size() > 0
      Set<CustomPoint2D> bestScoringMoves =
              new FilterMovesToBestScore().filterMoves(model, t, possibleMoves);
      return Optional.of(new TieBreakByUpperLeft().breakTies(model, t, bestScoringMoves));
    }
  }
}
