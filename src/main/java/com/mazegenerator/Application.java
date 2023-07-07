package com.mazegenerator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.List;
import java.util.Objects;


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
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/style.css")).toExternalForm());

        List<String> stylesheets = scene.getStylesheets();
        for (String stylesheet : stylesheets) {
            System.out.println("Loaded style: " + stylesheet);
        }

        //Application layout
        BorderPane menuPane = createMenuPane();

        this.canvas = new Canvas

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
    private void createCanvas() {
        /*
         The size of the entire image:
         Width = (number of horizontal cells * cell size) + (number of horizontal cells * frame size)
         Height = (number of vertical cells * cell size) + (number of vertical cells * frame size)
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
        int[][] mazeTable = maze.dfsMazeCreator();
        this.table = mazeTable;
        this.HEIGHT = mazeTable.length;
        this.WIDTH = mazeTable.length;
    }

    /**
     * GENERAL LAYOUT:
     * BORDERPANE {
     *     CENTER - MazeVisualization
     *     RIGHT - MenuPane
     * <p>
     *     BORDERPANE - MenuPane {
     *         TOP - TitleBox
     *         CENTER - InfoBox
     *         CENTER - MazeBox
     *         BOTTOM - ButtonBox
     *     }
     * }
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
        Label titleLabel = new Label("MAZE INFORMATION");
        titleLabel.getStyleClass().add("titleLabel-style");

        Pane titlePane = new Pane();
        titlePane.setStyle("-fx-background-color: #EDEDED");
        titlePane.getChildren().add(titleLabel);

        return new HBox(titlePane);
    }

    private VBox createInfoBox() {
        // Adding labels with information about image
        widthLabel.setText("Width: " + WIDTH);
        heightLabel.setText("Height: " + HEIGHT);
        pixelSizeLabel.setText("Pixel Size: " + PIXEL_SIZE);

        //Adding .css style
        widthLabel.getStyleClass().add("infoLabel-style");
        heightLabel.getStyleClass().add("infoLabel-style");
        pixelSizeLabel.getStyleClass().add("infoLabel-style");

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
        mazeSizeChoiceBox.getSelectionModel().select(Integer.valueOf("15")); //default size = 15

        mazeSizeChoiceBox.getStyleClass().add("choice-box-style");

        mazeSizeChoiceBox.setOnAction(actionEvent -> this.SELECTED_SIZE_BY_USER = mazeSizeChoiceBox.getValue());

        userInterfaceBox.getChildren().add(mazeSizeChoiceBox);

        return userInterfaceBox;
    }

    private void refreshInfoBox() {
        // Round the PIXEL_SIZE value to two decimal places
        String formattedPixelSize = String.format("%.2f", PIXEL_SIZE);

        widthLabel.setText("Width: " + WIDTH);
        heightLabel.setText("Height: " + HEIGHT);
        pixelSizeLabel.setText("Pixel Size: " + formattedPixelSize);
    }
    private HBox createButtonBox() {
        Button resetButton = new Button("Reset maze");
        Button createMazeButton = new Button("Create a new maze");

        //Adding .css style
        resetButton.getStyleClass().add("button-style");
        createMazeButton.getStyleClass().add("button-style");

        HBox.setMargin(resetButton, new Insets(7));
        HBox.setMargin(createMazeButton, new Insets(7));

        // Button event handling
        resetButton.setOnAction(event -> {
            // Clear the canvas
            gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        });
        createMazeButton.setOnAction(actionEvent -> {
            createDefaultMaze(this.SELECTED_SIZE_BY_USER);
            createCanvas();
            refreshInfoBox();
        });

        return new HBox(resetButton, createMazeButton);
    }

    /**
     * Function draws fields on the board
     * @param gc - Graphical context to drawing
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
                //3D Effect (wall)
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
        return switch (value) {
            case 0, 2 -> Color.DARKGOLDENROD.darker().darker();
            case 1 -> Color.KHAKI.brighter();
            case 3 -> Color.CHARTREUSE;
            case 4 -> Color.CORNFLOWERBLUE;
            default -> Color.LAWNGREEN;
        };
    }

}
