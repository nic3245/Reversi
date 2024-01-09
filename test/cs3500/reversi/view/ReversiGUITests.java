package cs3500.reversi.view;

import org.junit.Assert;
import org.junit.Test;

import java.awt.event.InputEvent;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import javax.swing.SwingUtilities;

import java.awt.Robot;
import java.awt.AWTException;

import cs3500.reversi.model.BasicReversiModel;
import cs3500.reversi.model.ReversiModel;

/**
 * Tests for the GUI of Reversi.
 */
public class ReversiGUITests {
  // Sometimes robot fails to click. Almost always works, so not concerned b/c the test.
  @Test
  public void testConsoleOutputWhenSelectingSquare() {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outputStream));

    ReversiModel model = new BasicReversiModel(11);
    SwingUtilities.invokeLater(() -> new ReversiGraphicsView(model));
    try {
      Robot robot = new Robot();
      robot.mouseMove(400, 400);
      // Allow time for GUI to come up
      try {
        Thread.sleep(1000);
      } catch (InterruptedException ex) {
        ex.printStackTrace();
      }
      robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
      robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
      try {
        Thread.sleep(1000);
      } catch (InterruptedException ex) {
        ex.printStackTrace();
      }
      System.setOut(System.out);

      Assert.assertEquals("0 -3", outputStream.toString().trim());
    } catch (AWTException e) {
      throw new RuntimeException(e);
    }
  }
}
