package cs3500.reversi.view;

import java.io.IOException;

/**
 * Represents a textual view for any model of Reversi.
 */
public interface ReversiTextualView {
  /**
   * Renders a model as text.
   *
   * @throws IOException if the rendering fails.
   */
  void render() throws IOException;

}
