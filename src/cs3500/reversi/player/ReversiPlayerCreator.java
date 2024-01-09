package cs3500.reversi.player;

import cs3500.reversi.model.PlayerTile;
import cs3500.reversi.model.ReversiModel;
import cs3500.reversi.strategy.MiniMax;
import cs3500.reversi.strategy.PlaceForCornerOptimalThenHighestScore;
import cs3500.reversi.strategy.PlaceForHighestScoreStrategy;

/**
 * Factory class that makes an object of type Player.
 */
public class ReversiPlayerCreator {

  /**
   * Represents the type of player to create.
   * If the type is a strategy:
   *  HIGHSCORE: Places the tile in the spot with the highest score.
   *  MINIMAX: Places the tile in the spot that minimizes the opponent's score.
   *  CORNER: Places the tile in the spot with the highest score, but prioritizes corners.
   *  Then a machine player is created with the given strategy.
   * If the type is HUMAN, then a human player is created.
   */
  public enum PlayerType {
    HIGHSCORE, MINIMAX, CORNER, HUMAN
  }

  /**
   * Creates the player with the given model, player (first/second), and type (defined above).
   * @param model model to read from
   * @param player which player it should be
   * @param strategy how it should decide to play (see above)
   * @return
   */
  public static Player createPlayer(ReversiModel model, PlayerTile player, PlayerType strategy) {
    switch (strategy) {
      case HIGHSCORE:
        return new ReversiMachine(model, player, new PlaceForHighestScoreStrategy());
      case MINIMAX:
        return new ReversiMachine(model, player, new MiniMax());
      case CORNER:
        return new ReversiMachine(model, player, new PlaceForCornerOptimalThenHighestScore());
      case HUMAN:
        return new ReversiPlayer(player);
      default:
        throw new IllegalArgumentException("Invalid strategy");
    }
  }
}
