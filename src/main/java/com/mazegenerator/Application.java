package com.mazegenerator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.util.List;


public class Application extends javafx.application.Application {

    private int WIDTH;
    private int HEIGHT;
    private double PIXEL_SIZE = 35;
    private double FRAME_SIZE = 1;
    private int SELECTED_SIZE_BY_USER = 15; //default size

    private int[][] table;

    private GraphicsContext gc;
    private Canvas canvas;

    Label widthLabel = new Label("Width: ");
    Label heightLabel = new Label("Height: ");
    Label pixelSizeLabel = new Label("Pixel Size: ");


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        //Creating a default maze
        createDefaultMaze(15);

        primaryStage.setTitle("Maze generator - App");
        Group root = new Group();

        // Creating scene and load style.css
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());

        List<String> stylesheets = scene.getStylesheets();
        for (String stylesheet : stylesheets) {
            System.out.println("Załadowano styl: " + stylesheet);
        }


        //Application layout
        BorderPane menuPane = createMenuPane();

        this.canvas = new Canvas
                /**
                 *  The size of the entire image:
                 *  @Width (number of horizontal cells * cell size) + (number of horizontal cells * frame size)
                 *  @Height (number of vertical cells * cell size) + (number of vertical cells * frame size)
                 */
                (WIDTH * PIXEL_SIZE + (WIDTH * FRAME_SIZE),
                        HEIGHT * PIXEL_SIZE + (HEIGHT * FRAME_SIZE));
        this.gc = canvas.getGraphicsContext2D();

        System.out.println("Canvas width = " + this.canvas.getWidth());
        System.out.println("Canvas height = " + this.canvas.getHeight());
        drawPixels(this.gc);

        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(canvas);
        borderPane.setRight(menuPane);

        root.getChildren().add(borderPane);



        primaryStage.setScene(scene);

        primaryStage.show();
    }


    //@Todo - wszystkie style powinny byc w osobnym pliku (np. css)
    //@Todo - wszystkie interakcje (przyciskow) powinny byc w osobnym pliku (package - controller)
    private void createCanvas() {
        /**
         *  Rozmiar calego obrazka to:
         *  @Szerokosc (ilosc pol w poziomie * rozmiar pola) + (ilosc pol w poziomie * rozmiar ramki)
         *  @Wysokosc (ilosc pol w pionie * rozmiar pola) + (ilosc pol w pionie * rozmiar ramki)
         */

        this.FRAME_SIZE = countFrameSize();
        this.PIXEL_SIZE = countPixelSize();

        this.canvas.setWidth(WIDTH * PIXEL_SIZE + (WIDTH * FRAME_SIZE));
        this.canvas.setHeight(HEIGHT * PIXEL_SIZE + (HEIGHT * FRAME_SIZE));

        this.gc = canvas.getGraphicsContext2D();

        drawPixels(this.gc);
    }

    private double countFrameSize() {
        double size = 540;
        return size/ (this.WIDTH * 36);
    }

    private double countPixelSize(){
        return countFrameSize() * 35;
    }

    private void createDefaultMaze(int size) {
        MazeGenerator maze = new MazeGenerator(size);
        int[][] tabMaze = maze.dfsMazeCreator();
        this.table = tabMaze;
        this.HEIGHT = tabMaze.length;
        this.WIDTH = tabMaze.length;
    }

    /**
     *  OGÓLNY UKŁAD:
     *  BORDERPANE {
     *      CENTER - MazeVisualization
     *      RIGHT - MenuPane
     * <p>
     *      BORDERPANE - MenuPane {
     *          TOP - TitleBox
     *          CENTER - InfoBox
     *          CENTER - MazeBox
     *          BOTTOM - ButtonBox
     *          }
     *      }
     *
     */
    private BorderPane createMenuPane() {
        BorderPane menuPane = new BorderPane();
        menuPane.setPadding(new Insets(10));
        menuPane.setStyle("-fx-background-color: #9966CC");

        HBox titleBox = createTitleBox();
        titleBox.setSpacing(10);
        titleBox.setPadding(new Insets(10));

        VBox infoBox = createInfoBox();
        VBox userInterfaceBox = createUIBox();
        HBox buttonBox = createButtonBox();

        menuPane.setTop(titleBox);
        menuPane.setCenter(infoBox);
        menuPane.setRight(userInterfaceBox);
        menuPane.setBottom(buttonBox);

        return menuPane;
    }

    private HBox createTitleBox() {
        // Napis w MENU
        Label titleLabel = new Label("INFORMACJE O LABIRYNCIE");
        titleLabel.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 16px; -fx-padding: 5px; -fx-border-color: #000000; -fx-border-width: 2px; -fx-border-radius: 5px;");
        titleLabel.setPrefWidth(300);
        titleLabel.setAlignment(Pos.CENTER);

        Pane titlePane = new Pane();
        titlePane.setStyle("-fx-background-color: #EDEDED");
        titlePane.getChildren().add(titleLabel);

        return new HBox(titlePane);
    }

    private VBox createInfoBox() {
        // Dodawanie etykiet z informacjami o obrazku
        widthLabel.setText("Width: " + WIDTH);
        heightLabel.setText("Height: " + HEIGHT);
        pixelSizeLabel.setText("Pixel Size: " + PIXEL_SIZE);

        //STYL
        String labelStyle = "-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 16; -fx-padding: 5; -fx-border-color: black; -fx-border-width: 2px; -fx-border-radius: 5px;";

        widthLabel.setStyle(labelStyle);
        heightLabel.setStyle(labelStyle);
        pixelSizeLabel.setStyle(labelStyle);

        VBox infoBox = new VBox(widthLabel, heightLabel, pixelSizeLabel);
        infoBox.setSpacing(10);
        infoBox.setPadding(new Insets(10));

        return infoBox;
    }

    private VBox createUIBox() {

        VBox userInterfaceBox = new VBox();
        userInterfaceBox.setSpacing(10);
        userInterfaceBox.setPadding(new Insets(10));

        ObservableList<Integer> numbers = FXCollections.observableArrayList();
        for (int i = 4; i <= 40 ; i++) {
            numbers.add(i);
        }

        ChoiceBox<Integer> mazeSizeChoiceBox = new ChoiceBox<>(numbers);
        mazeSizeChoiceBox.getSelectionModel().select(Integer.valueOf("15")); //domyslna wartosc = 15

        //STYL
        mazeSizeChoiceBox.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 10px; -fx-border-color: #000000; -fx-border-width: 2px;");

        mazeSizeChoiceBox.setOnAction(actionEvent -> {
            int selectedNumber = mazeSizeChoiceBox.getValue();
            System.out.println("Wybrana liczba: " + selectedNumber);
            this.SELECTED_SIZE_BY_USER = selectedNumber;

        });

        userInterfaceBox.getChildren().add(mazeSizeChoiceBox);

        return userInterfaceBox;

    }

    private void refreshInfoBox() {
        // Zaokrąglenie wartości PIXEL_SIZE do dwóch miejsc po przecinku
        String formattedPixelSize = String.format("%.2f", PIXEL_SIZE);

        widthLabel.setText("Width: " + WIDTH);
        heightLabel.setText("Height: " + HEIGHT);
        pixelSizeLabel.setText("Pixel Size: " + formattedPixelSize);
    }
    private HBox createButtonBox() {
        //Tworzenie przyciskow
        Button button1 = new Button("Resetuj labirynt");
        Button button2 = new Button("Wygeneruj nowy labirynt");

        //Dodawanie stylu CSS
 //       String originalButtonStyle = "-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 10px; -fx-border-color: #000000; -fx-border-width: 2px;";
//        button1.setStyle(originalButtonStyle);
        button1.getStyleClass().add("button-style");
        button2.getStyleClass().add("button-style");

        button1.setScaleX(1.05);
        button1.setScaleY(1.05);
        Font font1 = button1.getFont();
        button1.setFont(Font.font(font1.getName(), FontWeight.BOLD, font1.getSize() + 2));
        button1.setEffect(new DropShadow());
        ///////////////////////

        // Zdarzenia
  //      button1.setOnMouseEntered(e -> button1.setStyle(originalButtonStyle + "-fx-background-color: #45a049;"));
  //      button1.setOnMouseExited(e -> button1.setStyle(originalButtonStyle));
  //      button1.setOnMousePressed(e -> button1.setStyle(originalButtonStyle + "-fx-background-color: #3e8e41;"));
  //      button1.setOnMouseReleased(e -> button1.setStyle(originalButtonStyle + "-fx-background-color: #45a049;"));


        // STYL
   //     button2.setStyle(originalButtonStyle);

        button2.setScaleX(1.05);
        button2.setScaleY(1.05);
        Font font2 = button2.getFont();
        button2.setFont(Font.font(font2.getName(), FontWeight.BOLD, font2.getSize() + 2));
        button2.setEffect(new DropShadow());
        /////////////////

        // Zdarzenia
   //     button2.setOnMouseEntered(e -> button2.setStyle(originalButtonStyle + "-fx-background-color: #45a049;"));
   //     button2.setOnMouseExited(e -> button2.setStyle(originalButtonStyle));
   //     button2.setOnMousePressed(e -> button2.setStyle(originalButtonStyle + "-fx-background-color: #3e8e41;"));
   //     button2.setOnMouseReleased(e -> button2.setStyle(originalButtonStyle + "-fx-background-color: #45a049;"));

        HBox.setMargin(button1, new Insets(7));
        HBox.setMargin(button2, new Insets(7));

        /**
         Obsluga zdarzen powinna byc przeniesiona do package'u 'controllers'
         */

        // Obsługa zdarzenia przycisku 1
        button1.setOnAction(event -> {
            // Czyszczenie płótna
            gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        });

        //Tworzymy labirynt o rozmiarze podanym przez uzytkownika
        button2.setOnAction(actionEvent -> {
            createDefaultMaze(this.SELECTED_SIZE_BY_USER);
            createCanvas();
            refreshInfoBox();
        });

        return new HBox(button1, button2);
    }

    /**
     * Funkcja rysuje pola na planszy
     * @param gc - Kontekst graficzny, na ktorym rysujemy
     */

    private void drawPixels(GraphicsContext gc) {
        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                int pixelValue = table[y][x];
                Color pixelColor = getColorForValue(pixelValue);

                gc.setFill(pixelColor);
                gc.fillRect(
                        x * (PIXEL_SIZE + FRAME_SIZE),
                        y * (PIXEL_SIZE + FRAME_SIZE),
                        PIXEL_SIZE,
                        PIXEL_SIZE
                );

                gc.setStroke(Color.BLACK); // Frame color
                gc.setLineWidth(FRAME_SIZE); // Frame size
                gc.strokeRect( //Function draws rectangle
                        x * (PIXEL_SIZE + FRAME_SIZE),
                        y * (PIXEL_SIZE + FRAME_SIZE),
                        PIXEL_SIZE,
                        PIXEL_SIZE
                );
            }
        }

        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                int pixelValue = table[y][x];
                Color pixelColor = getColorForValue(pixelValue);
                gc.setFill(pixelColor);
                //Dla scian Efekt 3D
                double modifier = PIXEL_SIZE / 15;
                if (pixelValue == 2 || pixelValue == 0) {
                    gc.fillRect(
                            x * (PIXEL_SIZE + FRAME_SIZE) + modifier,
                            y * (PIXEL_SIZE + FRAME_SIZE) + modifier,
                            PIXEL_SIZE + modifier,
                            PIXEL_SIZE + modifier
                    );
                    gc.setStroke(Color.BLACK); // Frame color
                    gc.setLineWidth(FRAME_SIZE); // Frame size
                    gc.strokeRect( //Function draws rectangle
                            x * (PIXEL_SIZE + FRAME_SIZE) + modifier,
                            y * (PIXEL_SIZE + FRAME_SIZE) + modifier,
                            PIXEL_SIZE + modifier,
                            PIXEL_SIZE + modifier
                    );
                }
            }
        }
    }

    private Color getColorForValue(int value) {
        switch (value) {
            case 0:
            case 2:
                return Color.DARKGOLDENROD.darker().darker();
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
