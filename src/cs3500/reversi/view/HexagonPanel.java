package cs3500.reversi.view;

import java.awt.Dimension;
import java.awt.BasicStroke;
import java.awt.Shape;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.swing.JPanel;
import javax.swing.event.MouseInputAdapter;

import java.awt.Graphics;
import java.awt.Graphics2D;

import cs3500.reversi.model.AxialCustomPoint;
import cs3500.reversi.model.CustomPoint2D;
import cs3500.reversi.model.ReadOnlyReversiModel;

/**
 * Class representing a graphical representation of the board of the given model using a JPanel.
 */
class HexagonPanel extends JPanel {

  private final ReadOnlyReversiModel model;
  private final Map<Shape, CustomPoint2D> points;
  private Optional<CustomPoint2D> selectedPoint;

  /**
   * Constructs a panel that represents the board of this model.
   *
   * @param model the model to represent
   */
  public HexagonPanel(ReadOnlyReversiModel model) {
    this.model = model;
    this.points = new HashMap<>();
    this.selectedPoint = Optional.empty();
  }

  private Dimension getPreferredLogicalSize() {
    return new Dimension(this.model.getBoardSize(), this.model.getBoardSize());
  }

  @Override
  public Dimension getPreferredSize() {
    return new Dimension(1000, 1000);
  }

  public Optional<CustomPoint2D> getSelectedPoint() {
    return this.selectedPoint;
  }

  private AffineTransform transformLogicalToPhysical() {
    AffineTransform ret = new AffineTransform();
    Dimension preferred = this.getPreferredLogicalSize();
    ret.scale(getWidth() / preferred.getWidth(), getHeight() / preferred.getHeight());
    return ret;
  }

  public AffineTransform transformPhysicalToLogical() {
    AffineTransform ret = new AffineTransform();
    Dimension preferred = this.getPreferredLogicalSize();
    ret.scale(preferred.getWidth() / getWidth(), preferred.getHeight() / getHeight());
    return ret;
  }

  private double scaleAttrPhysicalToLogical(double attr) {
    Dimension preferred = this.getPreferredLogicalSize();
    return attr * preferred.getWidth() / getWidth();
  }

  @Override
  protected void paintComponent(Graphics g) {
    this.points.clear();
    int size = this.getWidth() / this.model.getBoardSize() / 2;
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g.create();
    double buffer = this.getWidth() - this.model.getBoardSize() * Math.sqrt(3) * size;
    g2d.transform(transformLogicalToPhysical());
    double x = (double) (size * (this.model.getBoardSize() - 1)) / 2 + buffer / 2;
    double y = (double) (size * (this.model.getBoardSize() - 1)) / 2;
    int widthNeg = (model.getBoardSize() / -2);
    int widthPos = (model.getBoardSize() / 2);
    double offset = x;
    System.out.println("SIZE: " + size);
    for (int r = widthNeg; r < 0; r++) {
      for (int q = 0; q < this.model.getRowWidth(r); q++) {
        this.draw(g2d, this.scaleAttrPhysicalToLogical(x), this.scaleAttrPhysicalToLogical(y),
                this.scaleAttrPhysicalToLogical(size), new AxialCustomPoint(q + widthNeg - r, r));
        x += Math.sqrt(3) * size;
      }
      offset -= (Math.sqrt(3) * size) / 2;
      x = offset;
      y += (1.5 * size);
    }

    for (int r = 0; r <= widthPos; r++) {
      for (int q = widthNeg; q <= widthPos - r; q++) {
        this.draw(g2d, this.scaleAttrPhysicalToLogical(x), this.scaleAttrPhysicalToLogical(y),
                this.scaleAttrPhysicalToLogical(size), new AxialCustomPoint(q, r));
        x += (Math.sqrt(3) * size);
      }
      offset += (Math.sqrt(3) * size) / 2;
      x = offset;
      y += (1.5 * size);
    }
  }


  private void draw(Graphics2D g, double x, double y, double size, CustomPoint2D point) {
    double[] xPoints = new double[6];
    double[] yPoints = new double[6];

    for (int i = 0; i < 6; i++) {
      double angle = 2.0 * Math.PI * (i + 0.5) / 6;
      xPoints[i] = x + (size * Math.cos(angle));
      yPoints[i] = y + (size * Math.sin(angle));
    }

    g.setStroke(new BasicStroke((float) 0.0025 * this.model.getBoardSize()));
    g.setColor(Color.BLACK);


    Path2D hex = new Path2D.Double();
    for (int i = 0; i < 6; i++) {
      if (i == 0) {
        hex.moveTo(xPoints[i], yPoints[i]);
      } else {
        hex.lineTo(xPoints[i], yPoints[i]);
      }
    }
    hex.closePath();
    g.draw(hex);
    g.setColor(Color.LIGHT_GRAY);
    if (this.selectedPoint.isPresent()) {
      if (this.selectedPoint.get().equals(point)) {
        g.setColor(Color.CYAN);
      }
    }
    g.fill(hex);
    this.points.put(hex, point);

    try {
      g.setColor(this.model.getTileAt(point).getColor());
      Ellipse2D.Double circle = new Ellipse2D.Double(x - size / 2, y - size / 2, size, size);
      g.fill(circle);
    } catch (IllegalStateException e) {
      // do nothing - should never actually throw
    }
  }


  /**
   * Selects the tile on this panel at logicalP. If logicalP is not on the board, deselects.
   *
   * @param logicalP the logical coordinates of the tile to select
   */
  public void selectTile(Point2D logicalP) {
    boolean nonFound = true;
    for (Shape s : HexagonPanel.this.points.keySet()) {
      if (s.contains(logicalP)) {
        if (selectedPoint.isPresent() && HexagonPanel.this.selectedPoint.get().equals(
                HexagonPanel.this.points.get(s))) {
          HexagonPanel.this.selectedPoint = Optional.empty();
        } else {
          HexagonPanel.this.selectedPoint = Optional.of(HexagonPanel.this.points.get(s));
        }
        System.out.println((HexagonPanel.this.points.get(s).getDim1()) + " "
                + HexagonPanel.this.points.get(s).getDim2());
        nonFound = false;
      }
    }
    if (nonFound) {
      HexagonPanel.this.selectedPoint = Optional.empty();
    }
    HexagonPanel.this.repaint();
  }

  /**
   * Adds a mouse listener that will call selectTile on the given listener when a click happens.
   * @param features listener to call selectTile on when mouse click
   */
  public void addFeatures(ViewFeatures features) {
    this.addMouseListener(new MouseInputAdapter() {
      @Override
      public void mousePressed(MouseEvent e) {
        Point2D physicalP = e.getPoint();
        Point2D logicalP = transformPhysicalToLogical().transform(physicalP, null);
        features.selectTile(logicalP);
      }
    });
  }
}
