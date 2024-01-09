package cs3500.reversi.model;

/**
 * An interface representing an object that wants to know when a model changes turn.
 */
public interface ModelFeatures {
  /**
   * Gets called when the subscribed-to model changes turn.
   * @param t the turn that it now is (post-change)
   */
  void turnChanged(PlayerTile t);
}
