package controllers;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class TabObrController {
    @FXML
    private Canvas canvas;

    private int[][] tablica;
    private int width;
    private int height;
    private int pixelSize;
    private int frameSize;

    @FXML
    private VBox vbox;

    private Insets insets;

    public void initialize() {
        insets = new Insets(10);
        vbox.setPadding(insets);
    }

    public void initializeCanvas(int[][] tablica, int width, int height, int pixelSize, int frameSize) {
        this.tablica = tablica;
        this.width = width;
        this.height = height;
        this.pixelSize = pixelSize;
        this.frameSize = frameSize;

        drawCanvas();
    }

    private void drawCanvas() {
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // Czyszczenie płótna
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        // Rysowanie labiryntu
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int x = j * pixelSize;
                int y = i * pixelSize;

                // Ustalenie koloru piksela w zależności od wartości w tablicy
                Color color;
                if (tablica[i][j] == 0) {
                    color = Color.WHITE;
                } else {
                    color = Color.BLACK;
                }

                // Rysowanie piksela
                gc.setFill(color);
                gc.fillRect(x, y, pixelSize, pixelSize);

                // Rysowanie ramki wokół piksela, jeśli wymagane
                if (frameSize > 0) {
                    gc.setStroke(Color.GRAY);
                    gc.setLineWidth(frameSize);
                    gc.strokeRect(x, y, pixelSize, pixelSize);
                }
            }
        }
    }

    @FXML
    private void handleButton1Action() {
        // Obsługa akcji przycisku 1
    }

    @FXML
    private void handleButton2MouseEntered(MouseEvent event) {
        // Obsługa zdarzenia najechania na przycisk 2
    }

    @FXML
    private void handleButton2MouseExited(MouseEvent event) {
        // Obsługa zdarzenia zjechania z przycisku 2
    }
}