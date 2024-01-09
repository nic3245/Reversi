package cs3500.reversi.strategy;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import cs3500.reversi.model.CustomPoint2D;
import cs3500.reversi.model.PlayerTile;
import cs3500.reversi.model.ReadOnlyReversiModel;

/**
 * Represents a filter that leaves only the moves that flip the most tiles.
 */
class FilterMovesToBestScore implements MoveFilter {
  public Set<CustomPoint2D> filterMoves(ReadOnlyReversiModel model, PlayerTile turn,
                                        Set<CustomPoint2D> possibleMoves) {
    Iterator<CustomPoint2D> iterator = possibleMoves.iterator();
    Set<CustomPoint2D> toReturn = new HashSet<>();
    CustomPoint2D bestMoveSoFar = iterator.next();
    int bestScoreDiffSoFar = model.getScoreIfMovePlayed(bestMoveSoFar, turn)
            - model.playerScore(turn);
    toReturn.add(bestMoveSoFar);

    while (iterator.hasNext()) {
      CustomPoint2D move = iterator.next();
      int scoreDiff = model.getScoreIfMovePlayed(move, turn) - model.playerScore(turn);
      if (scoreDiff > bestScoreDiffSoFar) {
        toReturn = new HashSet<>(Set.of(move));
        bestScoreDiffSoFar = scoreDiff;
      } else if (scoreDiff == bestScoreDiffSoFar) {
        toReturn.add(move);
      }
    }
    return toReturn;
  }
}
