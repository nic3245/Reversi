package cs3500.reversi.view;

import java.io.IOException;

import cs3500.reversi.model.AxialCustomPoint;
import cs3500.reversi.model.ReversiModel;

/**
 * Represents a textual view for BasicReversiModel.
 */

public class ReversiTextualViewImplementation implements ReversiTextualView {

  private final ReversiModel model;
  private final Appendable builder;

  public ReversiTextualViewImplementation(ReversiModel model, Appendable builder) {
    this.model = model;
    this.builder = builder;
  }

  private String addSpaces(String s, int numSpaces) {
    return " ".repeat(Math.max(0, numSpaces)) + s;
  }

  @Override
  public void render() throws IOException {
    this.builder.append(this.toString());
  }

  @Override
  public String toString() {
    int widthNeg = (this.model.getBoardSize() / -2);
    int widthPos = (this.model.getBoardSize() / 2);
    StringBuilder stringBuilder = new StringBuilder();
    for (int r = widthNeg; r < 0; r++) {
      String rowString = "";
      for (int q = 0; q < this.model.getRowWidth(r); q++) {
        rowString += getTileString(new AxialCustomPoint(q + widthNeg - r, r));
      }

      finishLine(rowString, r, stringBuilder);
      stringBuilder.append("\n");
    }

    for (int r = 0; r <= widthPos; r++) {
      String rowString = "";
      for (int q = widthNeg; q <= widthPos - r; q++) {
        rowString += getTileString(new AxialCustomPoint(q, r));
      }
      finishLine(rowString, r, stringBuilder);
      if (r != widthPos) {
        stringBuilder.append("\n");
      }
    }
    return stringBuilder.toString();
  }

  private String getTileString(AxialCustomPoint point) {
    String s;
    if (this.model.isSpotEmpty(point)) {
      s = "_ ";
    } else {
      s = (this.model.getTileAt(point).toString());
      s += " ";
    }
    return s;
  }

  private void finishLine(String rowString, int r, StringBuilder stringBuilder) {
    int numSpaces = this.model.getBoardSize() - this.model.getRowWidth(r);
    stringBuilder.append(this.addSpaces(rowString.trim(), numSpaces));
  }
}
