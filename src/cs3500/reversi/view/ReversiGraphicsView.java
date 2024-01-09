package cs3500.reversi.view;


import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Point2D;

import javax.swing.JOptionPane;
import javax.swing.JFrame;

import cs3500.reversi.model.ReadOnlyReversiModel;

/**
 * Class representing a pointy-top axial cord. system graphical view of a game of Reversi.
 */
public class ReversiGraphicsView extends JFrame implements IView {
  private final HexagonPanel hex;

  /**
   * Constructs a graphical view using the model, with a default size of (1000, 1000).
   *
   * @param model the model to base the view on
   */
  public ReversiGraphicsView(ReadOnlyReversiModel model) {
    super();
    this.setTitle("Reversi!");
    this.setSize(700, 700);
    this.hex = new HexagonPanel(model);
    this.add(this.hex);
  }

  @Override
  public void addFeatures(ViewFeatures features) {
    // Add mouse listener for selecting
    hex.addFeatures(features);
    // Add key listener for passing and placing a tile
    this.addKeyListener(new KeyListener() {
      @Override
      public void keyTyped(KeyEvent e) {
        // don't want to do anything, but must be implemented
      }

      @Override
      public void keyPressed(KeyEvent e) {
        if (e.getKeyChar() == KeyEvent.VK_SPACE) {
          features.pass();
        } else if (e.getKeyCode() == 'P') {
          features.placeTile(hex.getSelectedPoint());
        }
      }

      @Override
      public void keyReleased(KeyEvent e) {
        // don't want to do anything, but must be implemented
      }
    });
  }

  @Override
  public void showMessage(String s) {
    JOptionPane.showMessageDialog(this, s);
  }

  @Override
  public void selectTile(Point2D logicalP) {
    hex.selectTile(logicalP);
  }

  @Override
  public void display(boolean b) {
    this.setVisible(b);
  }

  @Override
  public void update() {
    this.repaint();
  }
}
