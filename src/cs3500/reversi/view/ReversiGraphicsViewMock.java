package cs3500.reversi.view;

import java.awt.geom.Point2D;

/**
 * Mock view that builds a log for testing purposes.
 */
public class ReversiGraphicsViewMock implements IView {
  private final StringBuilder in;

  /**
   * Constructor to build the mock.
   * @param in StringBuilder to keep track with.a
   */
  public ReversiGraphicsViewMock(StringBuilder in) {
    this.in = in;
  }

  @Override
  public void addFeatures(ViewFeatures features) {
    this.in.append("\nFeatures added to view");
  }

  @Override
  public void showMessage(String s) {
    this.in.append("\nError: ").append(s);
  }

  @Override
  public void selectTile(Point2D logicalP) {
    this.in.append("\nTile selected at: ").append(logicalP.getX());
    this.in.append(",").append(logicalP.getY());
  }

  @Override
  public void display(boolean b) {
    this.in.append("\nDisplaying view");
  }

  @Override
  public void update() {
    this.in.append("\nView updated");
  }
}
