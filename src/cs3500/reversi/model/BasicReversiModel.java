package cs3500.reversi.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static java.lang.Math.abs;

/**
 * A basic model for the game of Reversi.
 * Has two players, uses a hexagon of pointy-top hexagons, and follows normal Reversi rules.
 */
public class BasicReversiModel implements ReversiModel {
  private final int size;  // INVARIANT: size is always odd
  private final Map<CustomPoint2D, PlayerTile> board;
  private final Set<ModelFeatures> modelFeatures;
  private PlayerTile turn;
  private int passInARowCount;

  /**
   * Creates a model for the game of Reversi in a given state defined by board and turn.
   * Size must be odd, or the shape does not work.
   *
   * @param size  the size of the board (number of rows, width of longest row)
   * @param board the state of the board
   * @param turn  whose turn it currently is
   */
  public BasicReversiModel(int size, Map<CustomPoint2D, PlayerTile> board, PlayerTile turn) {
    this.board = board;
    this.turn = turn;
    if (size % 2 == 0 || size < 5) { // invariant holds as oddness is checked
      throw new IllegalArgumentException("Invalid NumRows!");
    }
    this.size = size;
    this.modelFeatures = new HashSet<>();
    this.passInARowCount = 0;
  }

  /**
   * Creates a model for the game of Reversi in the default starting state.
   * Size must be odd, or the shape does not work.
   *
   * @param size the size of the board (number of rows, width of longest row)
   */
  public BasicReversiModel(int size) {
    this.board = new HashMap<>();
    this.modelFeatures = new HashSet<>();
    this.turn = PlayerTile.SECOND;  //changes back to first after pieces are placed
    if (size % 2 == 0 || size < 5) { // invariant holds as oddness is checked
      throw new IllegalArgumentException("Invalid NumRows!");
    }
    this.size = size;
    for (int neighbor_q = -1; neighbor_q < 2; neighbor_q++) {
      for (int neighbor_r = -1; neighbor_r < 2; neighbor_r++) {
        if (neighbor_q != neighbor_r) {
          this.board.put(new AxialCustomPoint(neighbor_q, neighbor_r), this.turn);
          this.pass(this.turn);
        }
      }
      this.pass(this.turn);
    }
    this.passInARowCount = 0;
  }

  @Override
  public void startGame() {
    this.alertTurn(this.turn);
  }

  @Override
  public void placeTile(CustomPoint2D point, PlayerTile t) {
    if (this.turn.equals(t)) {
      if (this.validMove(point, this.turn)) {
        this.board.put(point, this.turn);
        this.flipTiles(point, this.turn);
        this.changeTurn(this.turn);
        this.passInARowCount = 0;
      } else {
        throw new IllegalStateException("Invalid move.");
      }
    } else {
      throw new IllegalStateException("Wrong turn to play that tile!");
    }
  }

  private void changeTurn(PlayerTile t) {
    if (t == this.turn) {
      this.turn = this.turn.getOpposite();
    } else {
      throw new IllegalStateException("Cannot pass on someone else's turn.");
    }
    this.alertTurn(this.turn);
  }

  @Override
  public void pass(PlayerTile t) {
    this.changeTurn(t);
    this.passInARowCount += 1;
  }

  @Override
  public PlayerTile getTurn() {
    return this.turn;
  }

  @Override
  public PlayerTile getTileAt(CustomPoint2D point) {
    if (!this.isSpotEmpty(point)) {
      return this.board.get(point);
    }
    throw new IllegalStateException("Invalid tile coordinates!");
  }

  @Override
  public boolean isGameOver() {
    return this.passInARowCount >= 2
            || (!this.playerCanMove(this.turn) && !this.playerCanMove(this.turn.getOpposite()));
  }

  @Override
  public PlayerTile whoIsWinning() {
    if (this.playerScore(this.turn) > this.playerScore(this.turn.getOpposite())) {
      return this.turn;
    } else {
      return this.turn.getOpposite();
    }
  }

  @Override
  public boolean playerCanMove(PlayerTile t) {
    return !this.playerMoves(t).isEmpty();
  }

  @Override
  public Set<CustomPoint2D> playerMoves(PlayerTile t) {
    Set<CustomPoint2D> toReturn = new HashSet<>();
    int halfBoardSize = (this.getBoardSize() - 1) / 2;  // relies on invariant of odd size
    int r = -halfBoardSize;
    for (; r < 1; r++) {
      for (int q = halfBoardSize; q >= -halfBoardSize - r; q--) {
        if (this.validMove(new AxialCustomPoint(q, r), t)) {
          toReturn.add(new AxialCustomPoint(q, r));
        }
      }
    }
    for (r = 1; r < halfBoardSize; r++) {
      for (int q = -halfBoardSize; q <= halfBoardSize - r; q++) {
        if (this.validMove(new AxialCustomPoint(q, r), t)) {
          toReturn.add(new AxialCustomPoint(q, r));
        }
      }
    }
    return toReturn;
  }

  @Override
  public int playerScore(PlayerTile tile) {
    int score = 0;
    for (CustomPoint2D point : this.board.keySet()) {
      if (this.board.get(point).equals(tile)) {
        score++;
      }
    }
    return score;
  }

  @Override
  public boolean isSpotEmpty(CustomPoint2D point) {
    if (this.validCoordinates(point)) {
      return !this.board.containsKey(point);
    } else {
      throw new IllegalArgumentException("Cannot check empty of non-existent spot.");
    }
  }

  /**
   * Gets the size of the board, a.k.a. the number of rows.
   *
   * @return the size of the board
   */
  @Override
  public int getBoardSize() {
    return this.size;
  }

  @Override
  public int getRowWidth(int row) {
    if (validCoordinates(new AxialCustomPoint(0, row))) {
      return this.size - abs(row);
    } else {
      throw new IllegalArgumentException("Tried to get width of non-existent row.");
    }
  }

  /**
   * Returns a copy of the board.
   */
  @Override
  public Map<CustomPoint2D, PlayerTile> getBoard() {
    return new HashMap<>(this.board);
  }

  @Override
  public int getScoreIfMovePlayed(CustomPoint2D moveToPlay, PlayerTile turn) {
    ReversiModel copyModel = new BasicReversiModel(this.size, this.getBoard(), turn);
    copyModel.placeTile(moveToPlay, turn);
    return copyModel.playerScore(turn) - this.playerScore(turn);
  }

  /**
   * Flips the tile at the given coordinates.
   *
   * @param point the location of the tile to flip
   */
  private void flipTile(AxialCustomPoint point) {
    if (!this.isSpotEmpty(point)) {
      this.board.put(point, this.board.get(point).getOpposite());
    } else {
      throw new IllegalArgumentException("Tried to getOpposite an empty tile");
    }
  }

  /**
   * Returns whether the coordinates are valid.
   *
   * @param point the point to check for validity
   * @return true if valid, false if not
   */
  private boolean validCoordinates(CustomPoint2D point) {
    return abs(point.getDim1()) <= (this.getBoardSize() - 1) / 2  // relies on odd size invariant
            && abs(point.getDim2()) <= (this.getBoardSize() - 1) / 2;
  }

  /**
   * Returns whether the move is valid.
   *
   * @param point the location of the move
   * @param t     the player making the move
   * @return true iff the move is valid
   */
  private boolean validMove(CustomPoint2D point, PlayerTile t) {
    int q = point.getDim1();
    int r = point.getDim2();

    if (this.isSpotEmpty(point)) {
      // Check all neighbors for isOpposite player
      for (int neighbor_q = -1; neighbor_q < 2; neighbor_q++) {
        for (int neighbor_r = -1; neighbor_r < 2; neighbor_r++) {
          if (neighbor_r != neighbor_q && this.validCoordinates(
                  new AxialCustomPoint(q + neighbor_q, r + neighbor_r))) {
            // Make sure tile in spot
            if (!this.isSpotEmpty(new AxialCustomPoint(q + neighbor_q, r + neighbor_r))
                    && this.getTileAt(new AxialCustomPoint(
                    q + neighbor_q, r + neighbor_r)).isOpposite(t)) {
              // If there is a tile, check if there is a valid line for that direction
              if (checkValidLine(new AxialCustomPoint(q, r), neighbor_q, neighbor_r, t)) {
                return true;
              }
            }
          }
        }
      }
      return false;
    }
    return false;
  }

  /**
   * Checks if there is a valid line in the given direction.
   *
   * @param point     the location to check
   * @param neighborQ the q-axis coordinate of the neighbor, relative to q
   * @param neighborR the r-axis coordinate of the neighbor, relative to r
   * @param t         the player tile that would be at q, r
   * @return true if valid line, false if not
   */
  private boolean checkValidLine(CustomPoint2D point, int neighborQ, int neighborR, PlayerTile t) {
    // Get the neighbor tile
    PlayerTile neighbor = this.getTileAt(new AxialCustomPoint(
            point.getDim1() + neighborQ, point.getDim2() + neighborR));
    int q = point.getDim1();
    int r = point.getDim2();
    // while it is the isOpposite color,
    while (neighbor.isOpposite(t)) {
      // Get the neighbor's neighbor
      q = q + neighborQ;
      r = r + neighborR;

      // And check that it is valid spot and not empty
      if (this.validCoordinates(new AxialCustomPoint(q + neighborQ, r + neighborR))
              && !this.isSpotEmpty(new AxialCustomPoint(q + neighborQ, r + neighborR))) {
        // After resetting neighbor, while loop will check player again
        neighbor = this.getTileAt(new AxialCustomPoint(q + neighborQ, r + neighborR));
      } else {
        // Any empties mean not valid line
        // Running out of space before finding opposite means invalid line
        return false;
      }

    }
    // If it ever goes to same color tile, return true b/c valid line
    return true;
  }

  /**
   * Flips all tiles that need flipped when a tile of type t is placed at point.
   *
   * @param point the given location
   * @param t     the player making the move
   */
  private void flipTiles(CustomPoint2D point, PlayerTile t) {
    int q = point.getDim1();
    int r = point.getDim2();
    // Check all neighbors for isOpposite player
    for (int neighbor_q = -1; neighbor_q < 2; neighbor_q++) {
      for (int neighbor_r = -1; neighbor_r < 2; neighbor_r++) {
        if (neighbor_r != neighbor_q && this.validCoordinates(
                new AxialCustomPoint(q + neighbor_q, r + neighbor_r))) {
          // Make sure tile in spot
          if (!this.isSpotEmpty(new AxialCustomPoint(q + neighbor_q, r + neighbor_r))) {
            // If there is a tile, check if there is a valid line for that direction
            if (checkValidLine(new AxialCustomPoint(q, r), neighbor_q, neighbor_r, t)) {
              int working_q = q;
              int working_r = r;
              // If there is a valid line in that direction, flip all opposite tiles
              while (!this.isSpotEmpty(new AxialCustomPoint(
                      working_q + neighbor_q, working_r + neighbor_r))
                      && this.getTileAt(new AxialCustomPoint(
                      working_q + neighbor_q, working_r + neighbor_r)) != t) {
                working_q = working_q + neighbor_q;
                working_r = working_r + neighbor_r;
                this.flipTile(new AxialCustomPoint(working_q, working_r));
              }
            }
          }
        }
      }
    }
  }

  private void alertTurn(PlayerTile t) {
    for (ModelFeatures listener : this.modelFeatures) {
      listener.turnChanged(t);
    }
  }

  @Override
  public void subscribeForTurnNotifs(ModelFeatures t) {
    this.modelFeatures.add(t);
  }
}
