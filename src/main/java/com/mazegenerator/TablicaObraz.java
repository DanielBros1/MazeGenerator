package com.mazegenerator;

import javafx.application.Application;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;


public class TablicaObraz extends Application {
 //   private static final int WIDTH = 20;
 //   private static final int HEIGHT = 20;

    private int WIDTH;
    private int HEIGHT;
    private static final int PIXEL_SIZE = 35;
    private int FRAME_SIZE = 1; // Rozmiar ramki w pikselach

    private int[][] tabl;

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

        //Creating a default Maze

        MazeTest mazeTest = new MazeTest(13);

        int[][] tabMaze = mazeTest.dfsMazeCreator();

        this.tabl = tabMaze;

        this.HEIGHT = tabMaze.length;
        this.WIDTH = tabMaze.length;

        primaryStage.setTitle("Tablica Obraz");
        Group root = new Group(); //todo wyjasnic

        /**
         *  OGÓLNY UKŁAD:
         *  BORDERPANE {
         *      CENTER - MazeVisualization
         *      RIGHT - MenuPane
         *
         *      BORDERPANE - MenuPane {
         *          TOP - TitleBox
         *          CENTER - InfoBox
         *          CENTER - MazeBox
         *          BOTTOM - ButtonBox
         *          }
         *      }
         *
         */

        // menuPane
        BorderPane menuPane = new BorderPane();
        menuPane.setPadding(new Insets(10));
        menuPane.setStyle("-fx-background-color: #EDEDED");
        //     BorderPane menuPane = createMenuPane();

        // Napis w MENU
        Label titleLabel = new Label("INFORMACJE O LABIRYNCIE");
        titleLabel.setStyle("-fx-background-color: #8B4513; -fx-text-fill: white; -fx-font-size: 16; -fx-padding: 5;");
        titleLabel.setPrefWidth(250);
        titleLabel.setAlignment(Pos.CENTER);

        Pane titlePane = new Pane();
        titlePane.setStyle("-fx-background-color: #EDEDED");
        titlePane.getChildren().add(titleLabel);

        // Dodawanie etykiet z informacjami o obrazku
        Label widthLabel = new Label("Width: " + WIDTH);
        Label heightLabel = new Label("Height: " + HEIGHT);
        Label pixelSizeLabel = new Label("Pixel Size: " + PIXEL_SIZE);

        //Tworzenie przyciskow
        Button button1 = new Button("Przycisk 1");
        Button button2 = new Button("Przycisk 2");

        HBox.setMargin(button1, new Insets(5));
        HBox.setMargin(button2, new Insets(5));


        //TitleBox
        HBox titleBox = new HBox(titlePane);
        titleBox.setSpacing(10);
        titleBox.setPadding(new Insets(10));

        //InfoBox
        HBox infoBox = new HBox(widthLabel, heightLabel, pixelSizeLabel);
        infoBox.setSpacing(10);
        infoBox.setPadding(new Insets(10));

        //MazeBox
        BorderPane mazeBox = new BorderPane();

        //ButtonBox
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

   //     this.tabl = tablica;

        // Przed tym nalezy wczytac tablice
        drawPixels(gc);

        /**
        Obsluga zdarzen powinna byc przeniesiona do package'u 'controllers'

         */

        // Obsługa zdarzenia przycisku 1
        button1.setOnAction(event -> {
            // Czyszczenie płótna
            gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        });


        button2.setOnMouseEntered(mouseEvent -> {
            button2.setText("Najechany");
        });
        button2.setOnMouseExited(mouseEvent -> {
            button2.setText("Przycisk 2");
        });
  /*      button2.setOnAction(actionEvent -> {
            //Stworz nowy labirynt
            MazeTest maze2 = new MazeTest(14); //todo zczytuj z TextField

            this.tabl = maze2.dfsMazeCreator();

            this.HEIGHT = this.tabl.length;
            this.WIDTH = this.tabl.length;

            // Zmiana rozmiaru płótna
            canvas.setWidth(this.HEIGHT);
            canvas.setHeight(this.WIDTH);

            // Pobranie nowego GraphicsContext
            GraphicsContext ga = canvas.getGraphicsContext2D();

            // Ponowne wypełnienie płótna
            drawPixels(ga);
        }); */


        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(canvas);
        borderPane.setRight(menuPane);

        root.getChildren().add(borderPane);
        primaryStage.setScene(new Scene(root));

        primaryStage.show();
    }

  //  private BorderPane createMenuPane() {
//    }

    private void drawPixels(GraphicsContext gc) {
        //todo Sprawdzic i ewentualnie zamienic "x" z "y"
        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                int pixelValue = tabl[y][x];
                Color pixelColor = getColorForValue(pixelValue);
                //Dla scianki zwiekszamy rozmiar ramki

                //todo Efekt 3D - to consider
            /*    if (pixelValue == 2) {
                    FRAME_SIZE = 3;
                }
                else {
                    FRAME_SIZE = 1;
                }

                ///
*/

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

    private void loadArrayFromFile(String filename) {
        try {
            File file = new File(filename);
            Scanner scanner = new Scanner(file);

            int height = scanner.nextInt();
            int width = scanner.nextInt();

            this.HEIGHT = scanner.nextInt();
            this.WIDTH = scanner.nextInt();

            this.tabl = new int[height][width];

            for (int row = 0; row < height; row++) {
                for (int col = 0; col < width; col++) {
                    this.tabl[row][col] = scanner.nextInt();
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
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
                return Color.CHARTREUSE;
            case 4:
                return Color.CORNFLOWERBLUE;
            default:
                return Color.LAWNGREEN;
        }
    }

}
