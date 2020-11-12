package drawings.markers;

import drawings.figures.Rectangle;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.util.Deque;

public class DeleteMarker extends Marker {
    private Deque<Rectangle> rectangles;
    public DeleteMarker(Point2D point, Rectangle rectangle, Deque<Rectangle> rectangles) {
        super(point, rectangle);
        this.rectangles = rectangles;
        circle.setFill(Color.RED);
        circle.setOnMouseClicked(e -> {
            rectangle.getPane().getChildren().remove(rectangle.getGroup());
            rectangles.remove(rectangle);
        });
    }

    @Override
    void setEvent(MouseEvent e) {

    }
}