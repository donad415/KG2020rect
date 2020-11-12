package drawings.markers;

import drawings.figures.Rectangle;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;


public class MovingMarker extends Marker {
    private double oldX;
    private double oldY;
    private double oldPosX;
    private double oldPosY;


    public MovingMarker(Point2D point, Rectangle rectangle) {
        super(point, rectangle);
        circle = createCircle(point);
        setOnMouseEvent();
        circle.setOnMousePressed(e -> {
            oldX = e.getSceneX();
            oldY = e.getSceneY();
            oldPosX = rectangle.getPosition().getX();
            oldPosY = rectangle.getPosition().getY();

        });
    }

    @Override
    void setEvent(MouseEvent e) {
        setPoint(new Point2D(rectangle.getWight()/2, rectangle.getHeight()/2));
        rectangle.setPosition(new Point2D(oldPosX * rectangle.getScale() + e.getSceneX() - oldX, oldPosY * rectangle.getScale() + e.getSceneY() - oldY));
        rectangle.getGroup().setLayoutX(rectangle.getCp().getCenter().getX() + rectangle.getPosition().getX());
        rectangle.getGroup().setLayoutY(rectangle.getCp().getCenter().getY() + rectangle.getPosition().getY());
    }
}
