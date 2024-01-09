package cs3500.reversi.model;

import java.util.Objects;

/**
 * Represents an axial point in a hexagonal grid. See README for explanation of axial cord. system.
 */
public class AxialCustomPoint implements CustomPoint2D {
  private final int q;
  private final int r;

  public AxialCustomPoint(int q, int r) {
    this.q = q;
    this.r = r;
  }

  /**
   * Returns the q coordinate of this point.
   *
   * @return the q coordinate
   */
  public final int getDim1() {
    return this.q;
  }

  /**
   * Returns the r coordinate of this point.
   *
   * @return the r coordinate
   */
  public final int getDim2() {
    return this.r;
  }

  @Override
  public boolean equals(Object o) {
    if (o instanceof AxialCustomPoint) {
      AxialCustomPoint other = (AxialCustomPoint) o;
      return this.q == other.q && this.r == other.r;
    } else {
      return false;
    }
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.q, this.r);
  }

  @Override
  public String toString() {
    return this.getDim1() + ", " + this.getDim2();
  }

}
