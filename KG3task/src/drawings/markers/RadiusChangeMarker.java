package drawings.markers;

import drawings.figures.Rectangle;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;

public class RadiusChangeMarker extends Marker {
    public RadiusChangeMarker(Point2D point, Rectangle rectangle) {
        super(point, rectangle);
    }

    @Override
    void setEvent(MouseEvent e) {
        setPoint(new Point2D((int)e.getX(), rectangle.getHeight()/2));

        double minLength = Math.min(rectangle.getWight(), rectangle.getHeight());

        if((getPoint().getX()-rectangle.getWight())>0 && getPoint().getX()-rectangle.getWight()<= minLength/2) {
            rectangle.setRadius(getPoint().getX() - rectangle.getWight());
        }
        circle.setOnMouseReleased(e1 -> {
            rectangle.clearFigure();
            rectangle.drawRect();
        });
    }
}
