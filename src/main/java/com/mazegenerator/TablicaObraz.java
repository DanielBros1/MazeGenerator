package com.mazegenerator;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.stage.Stage;

public class TablicaObraz extends Application {
    private static final int WIDTH = 20;
    private static final int HEIGHT = 20;
    private static final int PIXEL_SIZE = 35;
    private static final int FRAME_SIZE = 2; // Rozmiar ramki w pikselach

    private static int[][] tablica = {
            {2, 1, 1, 1, 1, 2, 1, 1, 1, 2, 1, 1, 1, 2, 1, 1, 1, 1, 1, 1},
            {3, 1, 2, 2, 1, 2, 1, 2, 1, 1, 2, 1, 2, 1, 1, 2, 1, 2, 2, 1},
            {2, 2, 1, 2, 1, 2, 1, 1, 2, 1, 2, 1, 2, 1, 2, 2, 1, 1, 1, 2},
            {1, 1, 1, 2, 1, 1, 1, 2, 1, 1, 2, 1, 2, 1, 2, 1, 2, 2, 1, 1},
            {1, 2, 1, 1, 2, 1, 2, 1, 1, 2, 2, 1, 1, 1, 1, 1, 2, 1, 1, 2},
            {1, 2, 2, 1, 1, 2, 1, 2, 1, 1, 1, 2, 1, 2, 1, 2, 1, 1, 2, 1},
            {1, 1, 1, 2, 2, 1, 1, 1, 2, 2, 1, 1, 2, 1, 1, 2, 1, 2, 1, 1},
            {1, 2, 1, 2, 1, 1, 2, 1, 2, 1, 2, 1, 1, 2, 2, 1, 1, 1, 1, 2},
            {2, 1, 1, 2, 1, 2, 2, 1, 1, 1, 2, 2, 1, 1, 1, 2, 1, 2, 1, 1},
            {1, 1, 2, 1, 1, 2, 1, 2, 2, 1, 1, 1, 2, 2, 1, 2, 1, 1, 2, 1},
            {4, 2, 2, 1, 2, 1, 1, 1, 1, 2, 1, 2, 0, 2, 1, 1, 2, 1, 2, 1},
            {1, 1, 1, 1, 2, 2, 1, 2, 1, 2, 1, 1, 2, 1, 2, 1, 1, 1, 2, 1},
            {1, 2, 1, 2, 1, 1, 1, 2, 2, 1, 2, 1, 1, 1, 1, 2, 2, 2, 2, 1},
            {1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 2, 1, 2, 1, 1, 1, 1},
            {2, 1, 1, 2, 1, 1, 2, 2, 1, 2, 1, 2, 1, 1, 2, 1, 1, 2, 2, 2},
            {1, 2, 1, 1, 2, 2, 1, 1, 1, 1, 2, 1, 2, 1, 1, 2, 1, 2, 1, 1},
            {1, 1, 2, 1, 1, 1, 2, 1, 2, 1, 1, 1, 2, 1, 2, 1, 1, 2, 2, 1},
            {2, 1, 1, 2, 2, 1, 1, 2, 1, 1, 2, 1, 2, 1, 1, 2, 1, 1, 1, 1},
            {0, 2, 1, 1, 2, 1, 2, 1, 1, 2, 1, 1, 1, 2, 1, 2, 2, 2, 1, 2},
            {0, 0, 2, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 2, 1, 1, 1, 1, 1, 1}
    };

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Tablica Obraz");

        Group root = new Group();

        // Tworzenie MENU
        BorderPane menuPane = new BorderPane();
        menuPane.setPadding(new Insets(10));
        menuPane.setStyle("-fx-background-color: #EDEDED");


        HBox titleBox = new HBox();
        titleBox.setPadding(new Insets(5));
        titleBox.setStyle("-fx-background-color: #8B4513; -fx-alignment: center;");

        // Napis w MENU
        Label titleLabel = new Label("INFORMACJE O LABIRYNCIE");
        titleLabel.setStyle("-fx-text-fill: white; -fx-font-size: 16; -fx-padding: 5;");
        titleLabel.setGraphic(new Ellipse(10, 5, 200, 25));
        titleLabel.setGraphicTextGap(10);

        titleBox.getChildren().add(titleLabel);


        // Dodawanie etykiet z informacjami o obrazku
        Label widthLabel = new Label("Width: " + WIDTH);
        Label heightLabel = new Label("Height: " + HEIGHT);
        Label pixelSizeLabel = new Label("Pixel Size: " + PIXEL_SIZE);

        //Tworzenie przyciskow
        Button button1 = new Button("Przycisk 1");
        Button button2 = new Button("Przycisk 2");

        HBox.setMargin(button1, new Insets(5));
        HBox.setMargin(button2, new Insets(5));

        HBox infoBox = new HBox(widthLabel, heightLabel, pixelSizeLabel);
        infoBox.setSpacing(10);
        infoBox.setPadding(new Insets(10));

        HBox buttonBox = new HBox(button1, button2);
        buttonBox.setSpacing(10);
        buttonBox.setPadding(new Insets(10));

        menuPane.setTop(titleBox);
        menuPane.setCenter(infoBox);
        menuPane.setBottom(buttonBox);

        Canvas canvas = new Canvas
                /**
                 *  Rozmiar calego obrazka to:
                 *  @Szerokosc (ilosc pol w poziomie * rozmiar pola) + (ilosc pol w poziomie * rozmiar ramki)
                 *  @Wysokosc (ilosc pol w pionie * rozmiar pola) + (ilosc pol w pionie * rozmiar ramki)
                 */
                (WIDTH * PIXEL_SIZE + (WIDTH * FRAME_SIZE),
                        HEIGHT * PIXEL_SIZE + (HEIGHT * FRAME_SIZE));
        GraphicsContext gc = canvas.getGraphicsContext2D();

        drawPixels(gc);

        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(canvas);
        borderPane.setRight(menuPane);

        root.getChildren().add(borderPane);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    private void drawPixels(GraphicsContext gc) {
        //todo Sprawdzic i ewentualnie zamienic "x" z "y"
        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                int pixelValue = tablica[y][x];
                Color pixelColor = getColorForValue(pixelValue);
                gc.setFill(pixelColor);
                gc.fillRect(
                        x * (PIXEL_SIZE + FRAME_SIZE),
                        y * (PIXEL_SIZE + FRAME_SIZE),
                        PIXEL_SIZE,
                        PIXEL_SIZE
                );
                gc.setStroke(Color.BLACK); // Kolor ramki
                gc.setLineWidth(FRAME_SIZE); // Grubosc ramki
                gc.strokeRect(
                        x * (PIXEL_SIZE + FRAME_SIZE),
                        y * (PIXEL_SIZE + FRAME_SIZE),
                        PIXEL_SIZE,
                        PIXEL_SIZE
                );
            }
        }
    }

    private Color getColorForValue(int value) {
        switch (value) {
            case 0:
            case 2:
                return Color.DARKGOLDENROD.darker().darker(); // Ustawienie ciemniejszego koloru niż DARKGOLDENROD
            case 1:
                return Color.KHAKI.brighter();
            case 3:
                return Color.LIGHTGOLDENRODYELLOW;
            case 4:
                return Color.CORNFLOWERBLUE;
            default:
                return Color.LAWNGREEN;
        }
    }
}
