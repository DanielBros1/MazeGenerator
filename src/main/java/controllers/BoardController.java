package controllers;

import javafx.fxml.FXML;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class BoardController {

    @FXML
    public Rectangle cell_0_0;
    public Rectangle cell_1_0;
    public Rectangle cell_2_0;
    public Rectangle cell_3_0;
    public Rectangle cell_4_0;
    public Rectangle cell_5_0;
    public Rectangle cell_6_0;
    public Rectangle cell_7_0;
    @FXML
    private void initialize() {
        // Inicjalizuj planszę, jeśli potrzebne

        // Dodaj obsługę kliknięcia na pole planszy
        cell_0_0.setOnMouseClicked(event -> {
            Rectangle clickedCell = (Rectangle) event.getSource();
            clickedCell.setFill(getRandomColor());
        });
    }

    private Color getRandomColor() {
        double red = Math.random();
        double green = Math.random();
        double blue = Math.random();

        return new Color(red, green, blue, 1);
    }
}

