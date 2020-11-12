package drawings.markers;

import drawings.figures.Rectangle;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public abstract class Marker {
    private static final double RADIUS = 4;
    private Point2D point;
    protected Circle circle;
    protected Rectangle rectangle;

    public Marker(Point2D point, Rectangle rectangle) {
        this.point = point;
        this.rectangle = rectangle;
        circle = createCircle(point);
        setOnMouseEvent();
    }

    public Point2D getPoint() {
        return point;
    }

    public void setPoint(Point2D point) {
        this.point = point;
        circle.setCenterX(point.getX());
        circle.setCenterY(point.getY());
    }

    public Circle getCircle() {
        return circle;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    protected static Circle createCircle(Point2D point) {
        return new Circle(point.getX(), point.getY(), RADIUS, Color.LIGHTGRAY);
    }

    protected void setOnMouseEvent() {
        circle.setOnMouseDragged(this::setEvent);
    }

    abstract void setEvent(MouseEvent e);

}
