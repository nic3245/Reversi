package cs3500.reversi;

import cs3500.reversi.controller.ReversiGraphicalController;
import cs3500.reversi.model.BasicReversiModel;
import cs3500.reversi.model.PlayerTile;
import cs3500.reversi.model.ReversiModel;
import cs3500.reversi.player.Player;
import cs3500.reversi.player.ReversiPlayerCreator;
import cs3500.reversi.view.IView;
import cs3500.reversi.view.ReversiGraphicsView;

/**
 * Main class that runs a game of Reversi.
 */
public class Reversi {
  /**
   * Main method that runs a game of Reversi.
   * @param args arguments supplied when called.
   */
  public static void main(String[] args) {
    int size = 11;
    int index = 0;
    if (args.length > 3 || args.length < 2) {
      throw new IllegalArgumentException("Invalid number of arguments!");
    }

    if (args.length == 3) {
      try {
        size = Integer.parseInt(args[0]);
        index++;
      }
      catch (IllegalArgumentException e) {
        System.out.println("Invalid Argument!");
      }
    }

    ReversiModel model = new BasicReversiModel(size);
    IView v = new ReversiGraphicsView(model);
    IView v2 = new ReversiGraphicsView(model);

    Player p1 = Reversi.getPlayer(model, PlayerTile.FIRST, args[index++]);
    Player p2 = Reversi.getPlayer(model,PlayerTile.SECOND, args[index]);


    ReversiGraphicalController c = new ReversiGraphicalController(model, p1, v);
    ReversiGraphicalController c2 = new ReversiGraphicalController(model, p2, v2);
    model.startGame();
  }

  private static Player getPlayer(ReversiModel m, PlayerTile t, String type) {
    switch (type) {
      case "human":
        return ReversiPlayerCreator.createPlayer(m, t, ReversiPlayerCreator.PlayerType.HUMAN);
      case "high-score":
        return ReversiPlayerCreator.createPlayer(m, t, ReversiPlayerCreator.PlayerType.HIGHSCORE);
      case "minimax":
        return ReversiPlayerCreator.createPlayer(m, t, ReversiPlayerCreator.PlayerType.MINIMAX);
      case "corner":
        return ReversiPlayerCreator.createPlayer(m, t, ReversiPlayerCreator.PlayerType.CORNER);
      default:
        System.out.println("Invalid Argument!");
        return ReversiPlayerCreator.createPlayer(m, t, ReversiPlayerCreator.PlayerType.HUMAN);
    }
  }
}
