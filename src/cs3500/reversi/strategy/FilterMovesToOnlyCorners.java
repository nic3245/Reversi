package cs3500.reversi.strategy;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import cs3500.reversi.model.CustomPoint2D;
import cs3500.reversi.model.PlayerTile;
import cs3500.reversi.model.ReadOnlyReversiModel;

/**
 * Represents a filter that only leaves moves that play in a corner.
 */
class FilterMovesToOnlyCorners implements MoveFilter {
  @Override
  public Set<CustomPoint2D> filterMoves(ReadOnlyReversiModel model, PlayerTile turn,
                                        Set<CustomPoint2D> possibleMoves) {
    Set<CustomPoint2D> workingCopyPossibleMoves = new HashSet<>(possibleMoves);
    Iterator<CustomPoint2D> iterator = workingCopyPossibleMoves.iterator();
    int halfBoardSize = (model.getBoardSize() - 1) / 2;
    while (iterator.hasNext()) {
      CustomPoint2D point = iterator.next();
      //Corners on middle row
      boolean cond1 = point.getDim2() == 0 && Math.abs(point.getDim1()) == halfBoardSize;
      //Corners on q axis (top-left, bottom-right)
      boolean cond2 = point.getDim1() == 0 && Math.abs(point.getDim2()) == halfBoardSize;
      //Corners on implied s axis (top-right, bottom-left)
      boolean cond3 = Math.abs(point.getDim1()) == halfBoardSize && point.getDim1()
              + point.getDim2() == 0;
      if (!(cond1 || cond2 || cond3)) {
        iterator.remove();
      }
    }
    return workingCopyPossibleMoves;
  }
}
