package cs3500.reversi.strategy;

import java.util.Iterator;
import java.util.Set;

import cs3500.reversi.model.CustomPoint2D;
import cs3500.reversi.model.PlayerTile;
import cs3500.reversi.model.ReadOnlyReversiModel;

/**
 * Represents a tie-break by uppermost leftmost.
 * That is, a tile that has a lower second dimension (y in Cartesian, r in axial) wins.
 * If those are tied, a tile that has a lower first dimension (x in Cartesian, q in axial) wins.
 */
public class TieBreakByUpperLeft implements BreakTies {
  @Override
  public CustomPoint2D breakTies(ReadOnlyReversiModel model, PlayerTile turn,
                                 Set<CustomPoint2D> possibleMoves) {
    if (possibleMoves.isEmpty()) {
      throw new IllegalArgumentException("Have to have moves to choose from.");
    }
    Iterator<CustomPoint2D> iterator = possibleMoves.iterator();
    CustomPoint2D bestMoveSoFar = iterator.next();

    while (iterator.hasNext()) {
      CustomPoint2D move = iterator.next();
      if (move.getDim2() < bestMoveSoFar.getDim2()) {
        bestMoveSoFar = move;
      } else if (move.getDim2() == bestMoveSoFar.getDim2()
              && move.getDim1() < bestMoveSoFar.getDim1()) {
        bestMoveSoFar = move;
      }
    }

    return bestMoveSoFar;
  }
}
