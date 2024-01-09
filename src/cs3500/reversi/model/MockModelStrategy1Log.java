package cs3500.reversi.model;

import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * A mock model that records possible moves asked for and moves asked for score of.
 */
public class MockModelStrategy1Log implements ReversiModel {
  private final StringBuilder log;

  public MockModelStrategy1Log(StringBuilder log) {
    this.log = Objects.requireNonNull(log);
  }

  @Override
  public PlayerTile getTurn() {
    return null;
  }

  @Override
  public PlayerTile getTileAt(CustomPoint2D point) {
    return null;
  }

  @Override
  public boolean isGameOver() {
    return false;
  }

  @Override
  public PlayerTile whoIsWinning() {
    return null;
  }

  @Override
  public boolean playerCanMove(PlayerTile t) {
    return false;
  }

  @Override
  public Set<CustomPoint2D> playerMoves(PlayerTile t) {
    log.append("\nRequested possible player moves for player: ").append(t.toString());
    log.append("\nMoves given to model: ");
    log.append("\nMove 1: ").append(2).append(",").append(3);
    log.append("\nMove 2: ").append(3).append(",").append(3);
    log.append("\nMove 3: ").append(4).append(",").append(3);
    return Set.of(new AxialCustomPoint(2, 3),
            new AxialCustomPoint(3, 3), new AxialCustomPoint(4, 3));
  }

  @Override
  public int playerScore(PlayerTile t) {
    return 0;
  }

  @Override
  public boolean isSpotEmpty(CustomPoint2D point) {
    return false;
  }

  @Override
  public int getBoardSize() {
    return 0;
  }

  @Override
  public int getRowWidth(int row) {
    return 0;
  }

  @Override
  public Map<CustomPoint2D, PlayerTile> getBoard() {
    return null;
  }

  @Override
  public int getScoreIfMovePlayed(CustomPoint2D moveToPlay, PlayerTile turn) {
    log.append("\nRequested score for move: ");
    log.append(moveToPlay.getDim1()).append(",").append(moveToPlay.getDim2());
    log.append("\nScore returned for move: ").append(moveToPlay.getDim1());
    return moveToPlay.getDim1();
  }

  @Override
  public void startGame() {
    // empty b/c mock
  }

  @Override
  public void placeTile(CustomPoint2D point, PlayerTile t) {
    // empty b/c mock
  }

  @Override
  public void pass(PlayerTile t) {
    // empty b/c mock
  }

  @Override
  public void subscribeForTurnNotifs(ModelFeatures listener) {
    // empty b/c mock
  }
}
