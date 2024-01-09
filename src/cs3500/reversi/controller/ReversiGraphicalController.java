package cs3500.reversi.controller;

import java.awt.geom.Point2D;
import java.util.Optional;

import cs3500.reversi.model.CustomPoint2D;
import cs3500.reversi.model.ModelFeatures;
import cs3500.reversi.model.PlayerTile;
import cs3500.reversi.model.ReversiModel;
import cs3500.reversi.player.Player;
import cs3500.reversi.view.IView;
import cs3500.reversi.view.ViewFeatures;

/**
 * Class representing a controller for a game of Reversi with a GUI.
 */
public class ReversiGraphicalController implements ViewFeatures, ModelFeatures {
  private final ReversiModel m;
  private final Player p;
  private final IView v;

  /**
   * Constructor for the controller, where the passed arguments are what it is controlling.
   *
   * @param m model
   * @param p player
   * @param v view
   */
  public ReversiGraphicalController(ReversiModel m, Player p, IView v) {
    this.m = m;
    this.p = p;
    this.v = v;
    this.v.addFeatures(this);
    this.m.subscribeForTurnNotifs(this);
    this.p.subscribeForPlayerWantsToTakeTurn(this);
    this.v.display(true);
  }

  @Override
  public void pass() {
    if (!this.m.isGameOver()) {
      try {
        m.pass(p.getPlayerRepresentation());
      } catch (IllegalStateException e) {
        v.showMessage(e.getMessage());
      }
    }
  }

  @Override
  public void placeTile(Optional<CustomPoint2D> move) {
    if (!this.m.isGameOver()) {
      if (move.isEmpty()) {
        v.showMessage("Cannot place without a location selected!");
      } else {
        try {
          m.placeTile(move.get(), p.getPlayerRepresentation());
        } catch (IllegalStateException e) {
          v.showMessage(e.getMessage());
        }
      }
    }
  }

  @Override
  public void selectTile(Point2D logicalP) {
    if (!this.m.isGameOver()) {
      this.v.selectTile(logicalP);
    }
  }

  @Override
  public void turnChanged(PlayerTile t) {
    this.v.update();
    if (this.m.isGameOver()) {
      if (m.whoIsWinning().equals(PlayerTile.FIRST)) {
        v.showMessage("Game Over!\nThe Winner Is Player One!");
      } else {
        v.showMessage("Game Over!\nThe Winner Is Player Two!");
      }
    } else {
      if (t.equals(p.getPlayerRepresentation())) {
        p.takeTurn();
      }
    }
  }
}
