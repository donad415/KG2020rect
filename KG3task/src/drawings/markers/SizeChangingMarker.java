package drawings.markers;

import drawings.figures.Rectangle;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;

public class SizeChangingMarker extends Marker {

    public SizeChangingMarker(Point2D point, Rectangle rectangle) {
        super(point, rectangle);
    }

    @Override
    void setEvent(MouseEvent e) {
        setPoint(new Point2D((int)e.getX(), (int)e.getY()));

        rectangle.setWight(getPoint().getX());
        rectangle.setHeight(getPoint().getY());

        circle.setOnMouseReleased(e1 -> {
            rectangle.clearFigure();
            rectangle.drawRect();

            Marker mkMove = rectangle.getMarkers().get(0);
            mkMove.setPoint(new Point2D(getPoint().getX()/2, getPoint().getY()/2));
            mkMove.getCircle().setCenterX(mkMove.getPoint().getX());
            mkMove.getCircle().setCenterY(mkMove.getPoint().getY());

            Marker mkRadius = rectangle.getMarkers().get(1);
            mkRadius.setPoint(new Point2D(getPoint().getX(), getPoint().getY()/2));
            mkRadius.getCircle().setCenterX(mkRadius.getPoint().getX());
            mkRadius.getCircle().setCenterY(mkRadius.getPoint().getY());

            Marker mkDelete = rectangle.getMarkers().get(3);
            mkDelete.setPoint(new Point2D(getPoint().getX()/2, 0));
            mkDelete.getCircle().setCenterX(mkDelete.getPoint().getX());
        });
    }
}
