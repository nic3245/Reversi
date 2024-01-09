package cs3500.reversi.strategy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import cs3500.reversi.model.BasicReversiModel;
import cs3500.reversi.model.CustomPoint2D;
import cs3500.reversi.model.PlayerTile;
import cs3500.reversi.model.ReadOnlyReversiModel;
import cs3500.reversi.model.ReversiModel;

/*
 * Design Decision: The spec states "This approach
 * minimizes the maximum move the opponent can make,
 * and is known as a minimax strategy." For this strategy I have
 * interpreted minimizing the opponents best possible by giving the move
 * that assuming the opponent plays the best possible move, it minimizes
 * how good the move is. The ranking of moves is based on the score that the
 * player can get from the move.
 */

/**
 * This represents a type of Reversi Strategy that minimumizes the maximum move
 * an opponent can make.
 */
public class MiniMax implements ReversiStrategy {

  // gets the index of the smallest value in the bestOpponentScores
  private static ArrayList<Integer> getMinIndex(ArrayList<Integer> bestOpponentScores) {
    ArrayList<Integer> minIndices = new ArrayList<>();
    int min = Collections.min(bestOpponentScores);
    for (int i = 0; i < bestOpponentScores.size(); i++) {
      if (bestOpponentScores.get(i) == min) {
        minIndices.add(i);
      }
    }
    return minIndices;
  }

  /**
   * Returns the 'best' move for the player based on the strategy.
   * Returning an empty Optional implies that passing is the only (or best) move.
   *
   * @param model the model to decide where to move on
   * @param turn  the player's turn to decide a move for
   * @return a move based on the strategy used
   */
  @Override
  public Optional<CustomPoint2D> chooseMove(ReadOnlyReversiModel model, PlayerTile turn) {
    Set<CustomPoint2D> possibleMoves = model.playerMoves(turn);
    ArrayList<CustomPoint2D> possibleMovesToList = new ArrayList<>(possibleMoves);
    ArrayList<ReversiModel> boards = new ArrayList<>();
    if (possibleMoves.isEmpty()) {
      return Optional.empty();
    }


    for (CustomPoint2D point : possibleMoves) {
      ReversiModel board = new BasicReversiModel(model.getBoardSize(), model.getBoard(),
              model.getTurn());
      board.placeTile(point, turn);
      boards.add(board);
    }

    ArrayList<Integer> bestOpponentMoves = getMaxScoresOfBoards(boards);

    ArrayList<Integer> indexesForPlayer = getMinIndex(bestOpponentMoves);
    Set<CustomPoint2D> bestMovesForPlayer = new HashSet<>();
    for (Integer index : indexesForPlayer) {
      bestMovesForPlayer.add(possibleMovesToList.get(index));
    }
    return Optional.of(new TieBreakByUpperLeft().breakTies(model, turn, bestMovesForPlayer));
  }

  private ArrayList<Integer> getMaxScoresOfBoards(ArrayList<ReversiModel> boards) {
    ArrayList<Integer> bestOpponentMoves = new ArrayList<>();
    List<ReversiStrategy> strategies = Arrays.asList(new PlaceForCornerOptimalThenHighestScore(),
            new PlaceForHighestScoreStrategy());
    for (ReversiModel board : boards) {
      PlayerTile opponent = board.getTurn();
      Set<CustomPoint2D> opponentMoves = new HashSet<>();
      for (ReversiStrategy strategy : strategies) {
        Optional<CustomPoint2D> bestMove = strategy.chooseMove(board, opponent);
        bestMove.ifPresent(opponentMoves::add);
      }
      if (opponentMoves.isEmpty()) {
        bestOpponentMoves.add(0);
      } else {
        CustomPoint2D bestMove =
                new TieBreakByUpperLeft().breakTies(board, opponent,
                        new FilterMovesToBestScore().filterMoves(board, opponent, opponentMoves));
        bestOpponentMoves.add(board.getScoreIfMovePlayed(bestMove, opponent));
      }
    }
    return bestOpponentMoves;
  }

}
