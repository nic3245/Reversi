package cs3500.reversi.strategy;

import java.util.Optional;
import java.util.Set;

import cs3500.reversi.model.CustomPoint2D;
import cs3500.reversi.model.PlayerTile;
import cs3500.reversi.model.ReadOnlyReversiModel;

/**
 * Represents a strategy that selects the move with the highest score. If there are multiple,
 * selects the uppermost leftmost move of them.
 */
public class PlaceForHighestScoreStrategy implements ReversiStrategy {
  @Override
  public Optional<CustomPoint2D> chooseMove(ReadOnlyReversiModel model, PlayerTile t) {
    Set<CustomPoint2D> possibleMoves = model.playerMoves(t);
    if (possibleMoves.isEmpty()) {
      return Optional.empty();
    } else {
      //Filter to best score will always return a length > 0 when given possibleMoves.size() > 0
      Set<CustomPoint2D> bestScoringMoves =
              new FilterMovesToBestScore().filterMoves(model, t, possibleMoves);
      return Optional.of(new TieBreakByUpperLeft().breakTies(model, t, bestScoringMoves));
    }
  }
}
