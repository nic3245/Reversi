package cs3500.reversi.strategy;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import cs3500.reversi.model.AxialCustomPoint;
import cs3500.reversi.model.CustomPoint2D;
import cs3500.reversi.model.PlayerTile;
import cs3500.reversi.model.ReadOnlyReversiModel;

/**
 * Represents a filter that removes any moves next to a corner from possible moves.
 */
class FilterMovesRemoveNextToCorner implements MoveFilter {
  @Override
  public Set<CustomPoint2D> filterMoves(ReadOnlyReversiModel model, PlayerTile turn,
                                        Set<CustomPoint2D> possibleMoves) {
    Set<CustomPoint2D> workingCopyPossibleMoves = new HashSet<>(possibleMoves);
    Iterator<CustomPoint2D> iterator = workingCopyPossibleMoves.iterator();
    int halfBoardSize = (model.getBoardSize() - 1) / 2;
    Set<CustomPoint2D> corners = new HashSet<>();
    corners.add(new AxialCustomPoint(0, halfBoardSize));
    corners.add(new AxialCustomPoint(-halfBoardSize, 0));
    corners.add(new AxialCustomPoint(-halfBoardSize, halfBoardSize));
    corners.add(new AxialCustomPoint(halfBoardSize, -halfBoardSize));
    corners.add(new AxialCustomPoint(0, -halfBoardSize));
    corners.add(new AxialCustomPoint(halfBoardSize, 0));
    Set<CustomPoint2D> neighbors = this.getNeighbors(corners);
    while (iterator.hasNext()) {
      CustomPoint2D point = iterator.next();
      if (neighbors.contains(point)) {
        iterator.remove();
      }
    }
    return workingCopyPossibleMoves;
  }

  private Set<CustomPoint2D> getNeighbors(Set<CustomPoint2D> corners) {
    Set<CustomPoint2D> neighbors = new HashSet<>();
    for (CustomPoint2D corner : corners) {
      for (int q = -1; q < 2; q++) {
        for (int r = -1; r < 2; r++) {
          if (q != r) {
            neighbors.add(new AxialCustomPoint(corner.getDim1() + q, corner.getDim2() + r));
          }
        }
      }
    }
    return neighbors;
  }
}
