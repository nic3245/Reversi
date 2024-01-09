package cs3500.reversi.model;

/**
 * Represents a point in a two-dimensional space.
 */
public interface CustomPoint2D {
  /**
   * Returns the first dimension coordinate of this point.
   * @return the first dimension coordinate
   */
  int getDim1();

  /**
   * Returns the second dimension coordinate of this point.
   * @return the second dimension coordinate
   */
  int getDim2();
}
