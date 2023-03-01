package puzzles.jam.gui;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import puzzles.common.Observer;
import puzzles.jam.model.Car;
import puzzles.jam.model.JamModel;

import java.io.File;


import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class JamGUI extends Application  implements Observer<JamModel, String>  {
    /** The resources directory is located directly underneath the gui package */
    private final static String RESOURCES_DIR = "resources/";

    // for demonstration purposes
    private final static String X_CAR_COLOR = "#DF0101";
    private final static int BUTTON_FONT_SIZE = 30;
    private final static int ICON_SIZE = 75;


    private final static char EMPTY = '.';
    private final static char X = 'X';
    private boolean initialized;
    private JamModel jamModel;
    private String file;
    private HashMap<Character, Car> map;
    private FileChooser fileChooser;
    private Label topLabel;
    private GridPane gridPane;
    private BorderPane borderPane;
    private Window stage;
    private List<Character> usedLetters;
    private HashMap<Character, String> hexStrings;
    private Stage stage1;
    private File ff;






    public void init() throws IOException {
        String filename = getParameters().getRaw().get(0);
        file = filename;
        this.initialized = false;
        this.jamModel= new JamModel(filename);
        map = this.jamModel.getCurrentConfig().getMapCars();
        this.stage = stage;
        usedLetters = new ArrayList<>();
        hexStrings = new HashMap<>();
        this.jamModel.addObserver(this);





    }

    public Label fly(){
        fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(System.getProperty("user.dir") + "\\data\\jam"));
        String[] ar = file.split("/");
        topLabel = new Label("Loaded: " + ar[ar.length - 1]);
        topLabel.setAlignment(Pos.CENTER);
        return topLabel;
    }

    public void getFile(){
        fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(System.getProperty("user.dir") + "\\data\\jam"));
        File filee = fileChooser.showOpenDialog(this.stage);
        if (filee == null){
            return;
        }
        this.ff = filee.getAbsoluteFile();
        jamModel.load(this.ff.toString());

    }


    @Override
    public void start(Stage stage) throws Exception {

        stage1 = stage;
        Scene scene = new Scene(borderPane());
        stage1.setScene(scene);
        stage.show();
    }

    public BorderPane borderPane(){
        borderPane = new BorderPane();
        borderPane.setBottom(buttons());
        borderPane.setTop(fly());
        borderPane.setCenter(makeGridPane());
        return borderPane;
    }

    public HBox buttons(){
        HBox hBox = new HBox();
        Button load = new Button("Load");

        load.addEventHandler(ActionEvent.ANY, (ActionEvent event) -> getFile());

        Button reset = new Button("Reset");
        reset.addEventHandler(ActionEvent.ANY, (ActionEvent event) -> jamModel.reset());

        Button hint = new Button("Hint");
        hint.addEventHandler(ActionEvent.ANY, (ActionEvent event) -> jamModel.hint());

        hBox.getChildren().addAll(load, reset, hint);
        hBox.setAlignment(Pos.CENTER);

        return hBox;
    }

    public GridPane makeGridPane(){
        gridPane = new GridPane();
        this.make();
        borderPane.setCenter(gridPane);
        return gridPane;
    }

    public void make(){
        char[][] grid1 = jamModel.getCurrentConfig().getGrid();
        int rows = this.jamModel.getCurrentConfig().getRows();
        int columns = this.jamModel.getCurrentConfig().getColumns();





        char letter = '-';
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                int row = r;
                int col = c;
                letter = grid1[r][c];
                Button button = new Button(String.valueOf(letter));


                Random ran = new Random();
                int one =  ran.nextInt(9);
                int two =  ran.nextInt(9);
                int three =  ran.nextInt(9);
                int four =  ran.nextInt(9);
                int five =  ran.nextInt(9);
                int six =  ran.nextInt(9);
                String color = String.valueOf("#" + one + two + three + four + five + six);
                int randomColor = (one + two + three + four + five + six);


                button.setMaxSize(ICON_SIZE, ICON_SIZE);
                button.setMinSize(ICON_SIZE, ICON_SIZE);
                button.addEventHandler(ActionEvent.ANY, (ActionEvent event) -> jamModel.select(row, col));
                gridPane.add(button, c, r);
                if(letter != EMPTY && letter != X && !usedLetters.contains(letter)){
                    usedLetters.add(letter);
                    hexStrings.put(letter, color);
                    button.setStyle(
                            "-fx-font-size: " + BUTTON_FONT_SIZE + ";" +
                                    "-fx-background-color: " + color + ";" +
                                    "-fx-font-weight: bold;" +
                                    "fx-text-inner-color-: #000000;");
                } else if (!usedLetters.isEmpty() && usedLetters.contains(letter)){
                    String hex = hexStrings.get(letter);
                    button.setStyle(
                            "-fx-font-size: " + BUTTON_FONT_SIZE + ";" +
                                    "-fx-background-color: " + hex + ";" +
                                    "-fx-font-weight: bold;" +
                                    "fx-text-inner-color-: #000000;");

                } else if(letter == X){
                    button.setStyle(
                            "-fx-font-size: " + BUTTON_FONT_SIZE + ";" +
                                    "-fx-background-color: " + X_CAR_COLOR + ";" +
                                    "-fx-font-weight: bold;" +
                                    "fx-text-inner-color-: #000000;");
                } else if(letter == EMPTY){
                    button.setText("");
                    button.setStyle(
                            "-fx-font-size: " + BUTTON_FONT_SIZE + ";" +
                                    "-fx-font-weight: bold;" +
                                    "fx-text-inner-color-: #000000;");

                }
            }
        }
    }

    @Override
    public void update(JamModel jamModel, String msg) {
        topLabel.setText(msg);
//        System.out.println(msg);
//        fly(file);
        this.makeGridPane();
        stage1.sizeToScene();






    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
