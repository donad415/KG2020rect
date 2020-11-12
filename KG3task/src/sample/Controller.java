package sample;

import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

import drawings.CoordinatePlane;
import drawings.figures.Rectangle;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private AnchorPane innerAnchorPane;

    @FXML
    private Button buttonClear;

    @FXML
    private TextField radius;

    @FXML
    private Button ScaleButton;

    @FXML
    private TextField ScaleTextField;

    @FXML
    private TextField rectHeight;

    @FXML
    private TextField rectWight;

    @FXML
    private TextField angleTextField;

    private final Deque<Rectangle> list = new LinkedList<>();

    @FXML
    void initialize() {
        CoordinatePlane cp = new CoordinatePlane(innerAnchorPane, 100);
        cp.draw();
        buttonClear.setOnAction(e -> {
            innerAnchorPane.getChildren().removeAll(innerAnchorPane.getChildren().stream().skip(1).collect(Collectors.toList()));
            list.clear();
        });

        /*ScaleButton.setOnAction(e -> {
            double scale = Double.parseDouble(ScaleTextField.getText());
            cp.clear();
            cp.setScale(100 * scale);
            cp.draw();
            double sc = list.getFirst().getScale() > scale ? 1 / list.getFirst().getScale() : list.getFirst().getScale();
            list.forEach(x -> x.setPosition(new Point2D(x.getPosition().getX() * sc, x.getPosition().getY() * sc)));
            list.forEach(x -> x.setScale(scale));
            list.forEach(Rectangle::redraw);
        });*/

        innerAnchorPane.setOnMouseClicked(e -> {
            if (e.isControlDown()) {
                try {
                    list.addFirst(new Rectangle(new Point2D(e.getX() - cp.getCenter().getX(), e.getY() - cp.getCenter().getY()), Integer.parseInt(radius.getText()),
                            Integer.parseInt(rectWight.getText()), Integer.parseInt(rectHeight.getText()), innerAnchorPane, list, cp, 1));
                    list.getFirst().draw();
                } catch (Exception ex) {
                    alert(ex.getMessage());
                }
            }
        });

        double[] x = new double[1];
        double[] y = new double[1];
        double[] cpX = new double[1];
        double[] cpY = new double[1];
        innerAnchorPane.setOnMousePressed(e -> {
            if (e.isShiftDown()) {
                x[0] = e.getSceneX() - 192;
                y[0] = e.getSceneY();
                cpX[0] = cp.getCenter().getX();
                cpY[0] = cp.getCenter().getY();
            }
        });

        innerAnchorPane.setOnMouseDragged(e -> {
            if (e.isShiftDown()) {
                cp.setCenter(new Point2D(cpX[0] + e.getSceneX() - 192 - x[0], cpY[0] + e.getSceneY() - y[0]));
                cp.translate();
                list.forEach(rectangle -> {
                    rectangle.getGroup().setLayoutX(cp.getCenter().getX() + rectangle.getPosition().getX() * rectangle.getScale());
                    rectangle.getGroup().setLayoutY(cp.getCenter().getY() + rectangle.getPosition().getY() * rectangle.getScale());
                });
            }
        });
        innerAnchorPane.setMaxWidth(1200);
        innerAnchorPane.setMaxHeight(800);
    }

    private void alert(String str) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Attention");
        alert.setHeaderText(null);
        alert.setContentText(str);
        alert.showAndWait();
    }
}