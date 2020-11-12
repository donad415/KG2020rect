package drawings.figures;

import drawings.CoordinatePlane;
import drawings.curves.CurveDrawer;
import drawings.fill.FillOvalDrawer;
import drawings.line.LineDrawer;
import drawings.line.VuLineDrawer;
import drawings.markers.*;
import drawings.pixel.RectanglePixelDrawer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Deque;
import java.util.List;
import java.util.stream.Collectors;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class Rectangle implements IFigure {
    private double height;
    private double wight;
    private double radius;
    private Pane pane;
    private Group group;
    private final LineDrawer lineDrawer;
    private final CurveDrawer fillDrawer;
    private final RectanglePixelDrawer pixelDrawer;
    private final List<Marker> markers;
    private Group innerCircleGroup;
    private Group innerLightsGroup;
    private boolean isMarkersShows;
    private CoordinatePlane cp;
    private Point2D position;
    private double scale;

    public double getRadius() {
        return this.radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public double getHeight(){return this.height;}

    public void setHeight(double h){
        this.height=h;
    }

    public double getWight(){return this.wight;}

    public void setWight(double w){
        this.wight=w;
    }

    public Group getGroup() {
        return this.group;
    }

    public Pane getPane() {
        return this.pane;
    }

    public double getScale() {
        return this.scale;
    }

    public void setScale(double scale) {
        this.scale = scale;
    }

    public CoordinatePlane getCp() {
        return this.cp;
    }

    public Point2D getPosition() {
        return this.position;
    }

    public void setPosition(Point2D position) {
        this.position = position;
    }

    public Rectangle(Point2D center, double radius, double wight, double height, Pane pane, Deque<Rectangle> rectangles, CoordinatePlane cp, double scale) {
        this.height = height;
        this.wight = wight;
        this.radius = radius;
        this.pane = pane;
        this.cp = cp;
        this.group = new Group();
        this.innerCircleGroup = new Group();
        this.innerLightsGroup = new Group();
        this.scale = scale;
        this.position = new Point2D(center.getX() / scale, center.getY() / scale);
        Color color = Color.AQUA;
        this.markers = new ArrayList();
        this.lineDrawer = new VuLineDrawer(new RectanglePixelDrawer(1), this.innerLightsGroup);
        this.fillDrawer = new FillOvalDrawer(new RectanglePixelDrawer(1), this.innerCircleGroup);
        this.pixelDrawer = new RectanglePixelDrawer(1);
        this.markers.add(new MovingMarker(new Point2D(this.wight/2, this.height/2), this));
        this.markers.add(new RadiusChangeMarker(new Point2D(this.wight, this.height/2), this));
        this.markers.add(new SizeChangingMarker(new Point2D(this.wight, this.height), this));
        this.markers.add(new DeleteMarker(new Point2D(wight/2, 0.0D), this, rectangles));
        this.lineDrawer.setColor(color);
        this.fillDrawer.setColor(color);

        this.group.getChildren().addAll(new Node[]{this.innerCircleGroup, this.innerLightsGroup});
        pane.getChildren().add(this.group);
        this.group.setOnMouseClicked((e) -> {
            if (e.getButton() == MouseButton.PRIMARY && !this.isMarkersShows) {
                this.isMarkersShows = true;
                this.showMarkers();
            } else {
                this.hideMarkers();
                this.isMarkersShows = false;
            }

        });
        this.group.setCursor(Cursor.HAND);
    }

    public void draw() {
        this.drawRect();
        this.group.setLayoutX(this.position.getX() * this.scale + this.cp.getCenter().getX());
        this.group.setLayoutY(this.position.getY() * this.scale + this.cp.getCenter().getY());
    }

    public void drawRect() {
        for(int i=(int)(this.radius*this.scale); i<(int)((this.wight-this.radius))*this.scale+1; i++){
            for(int j=0; j<(int)(this.height*this.scale)+1; j++){
                pixelDrawer.draw(i, j, this.innerLightsGroup, Color.AQUA);
            }
        }
        for(int i=0; i<(int)(this.wight*this.scale)+1; i++){
            for(int j=(int)(this.radius*this.scale); j<(int)((this.height-this.radius)*this.scale)+1; j++){
                pixelDrawer.draw(i, j, this.innerLightsGroup, Color.AQUA);
            }
        }

        fillDrawer.draw(this.radius*scale, this.radius*scale, this.radius * scale, this.radius * scale);

        fillDrawer.draw(this.radius*scale, (this.height-this.radius)*scale, this.radius * scale, this.radius * scale);

        fillDrawer.draw((this.wight-this.radius)*scale, (this.height-this.radius)*scale, this.radius * scale, this.radius * scale);

        fillDrawer.draw((this.wight-this.radius)*scale, this.radius*scale, this.radius * scale, this.radius * scale);
    }

    public void redraw() {
        this.clearFigure();
        this.drawRect();
        this.group.setLayoutX(this.position.getX() * this.scale + this.cp.getCenter().getX());
        this.group.setLayoutY(this.position.getY() * this.scale + this.cp.getCenter().getY());
    }

    public List<Marker> getMarkers() {
        return this.markers;
    }

    public void moveMarker(Point2D from, Point2D to) {
    }

    public void clearFigure() {
        this.clearCircle();
        this.clearLights();
    }

    public void clearLights() {
        this.innerLightsGroup.getChildren().clear();
    }

    public void clearCircle() {
        this.innerCircleGroup.getChildren().clear();
    }

    private void showMarkers() {
        this.group.getChildren().addAll((Collection)this.markers.stream().map(Marker::getCircle).collect(Collectors.toList()));
    }

    private void hideMarkers() {
        this.group.getChildren().removeAll((Collection)this.markers.stream().map(Marker::getCircle).collect(Collectors.toList()));
    }
}