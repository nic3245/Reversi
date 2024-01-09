package cs3500.reversi.player;

import java.util.HashSet;
import java.util.Optional;

import cs3500.reversi.model.CustomPoint2D;
import cs3500.reversi.model.PlayerTile;
import cs3500.reversi.model.ReadOnlyReversiModel;
import cs3500.reversi.strategy.ReversiStrategy;

/**
 * A class representing an AI player for a game of reversi.
 */
public class ReversiMachine implements Player {
  private final ReadOnlyReversiModel m;
  private final PlayerTile player;

  private final HashSet<PlayerActions> listeners;
  private final ReversiStrategy strat;

  /**
   * Constructor that builds a machine player based on the given model, player, and strategy.
   * @param m model to read from
   * @param player which player it should be
   * @param strat strategy it should use to place
   */
  public ReversiMachine(ReadOnlyReversiModel m, PlayerTile player, ReversiStrategy strat) {
    this.m = m;
    this.player = player;
    this.strat = strat;
    this.listeners = new HashSet<>();
  }

  @Override
  public void subscribeForPlayerWantsToTakeTurn(PlayerActions listener) {
    this.listeners.add(listener);
  }

  @Override
  public PlayerTile getPlayerRepresentation() {
    return this.player;
  }

  @Override
  public void takeTurn() {
    Optional<CustomPoint2D> move = strat.chooseMove(m, this.getPlayerRepresentation());
    for (PlayerActions listener : listeners) {
      if (move.isEmpty()) {
        listener.pass();
      }
      else {
        listener.placeTile(move);
      }
    }
  }
}
