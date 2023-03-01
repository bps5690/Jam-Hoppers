package puzzles.hoppers.gui;

import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import puzzles.common.Observer;
import puzzles.hoppers.model.HoppersModel;

import javafx.application.Application;
import javafx.stage.Stage;
import puzzles.jam.model.Car;
import puzzles.jam.model.JamModel;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class HoppersGUI extends Application implements Observer<HoppersModel, String>
{
    /**
     * The resources directory is located directly underneath the gui package
     */
    private final static String RESOURCES_DIR = "resources/";

    // for demonstration purposes
    private Image redFrog = new Image(getClass().getResourceAsStream(RESOURCES_DIR + "red_frog.png"));
    private Image greenFrog = new Image(getClass().getResourceAsStream(RESOURCES_DIR + "green_frog.png"));
    private Image emptyPad = new Image(getClass().getResourceAsStream(RESOURCES_DIR + "lily_pad.png"));
    private Image waterSpace = new Image(getClass().getResourceAsStream(RESOURCES_DIR + "water.png"));

    private String file;
    private boolean initialized;
    private HoppersModel hopsModel;
    private FileChooser fileChooser;
    private Label topLabel;
    private GridPane gridPane;
    private BorderPane borderPane;
    private Window stage;
    private List<Character> usedLetters;
    private Stage stage1;
    private final static int BUTTON_FONT_SIZE = 30;
    private final static int ICON_SIZE = 75;
    private final static char EMPTY = '.';
    private final static char INVALID = '*';
    private final static char GREEN = 'G';
    private final static char RED = 'R';
    private File tile;

    public void init() throws IOException
    {
        String filename = getParameters().getRaw().get(0);
        file = filename;
        this.initialized = false;
        this.hopsModel = new HoppersModel(filename);
        this.stage = stage;
        usedLetters = new ArrayList<>();
        this.hopsModel.addObserver(this);
    }

    @Override
    public void start(Stage stage) throws Exception
    {
        //Button button = new Button();
        //button.setGraphic(new ImageView(redFrog));
        stage1 = stage;
        Scene scene = new Scene(borderPane());
        stage.setScene(scene);
        stage.show();
    }

    public void getFile(){
        fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(System.getProperty("user.dir") + "\\data\\hoppers"));
//        try
//        {
            File filee = fileChooser.showOpenDialog(this.stage);
            if (filee == null){
                return;
            }
            this.tile = filee.getAbsoluteFile();
//        }catch (NullPointerException n){
//            System.out.println("File No Comprehendo");
//        }
        hopsModel.load(this.tile.toString());

    }

    public Label fly()
    {

        String[] ar = file.split("/");
        topLabel = new Label("Loaded: " + ar[ar.length - 1]);
        topLabel.setAlignment(Pos.CENTER);
        return topLabel;
    }

    public BorderPane borderPane()
    {
        borderPane = new BorderPane();
        borderPane.setBottom(buttons());
        borderPane.setTop(fly());
        borderPane.setCenter(makeGridPane());
        return borderPane;
    }

    public HBox buttons()
    {
        HBox hBox = new HBox();
        Button load = new Button("Load");


        load.addEventHandler(ActionEvent.ANY, (ActionEvent event) -> getFile());

        Button reset = new Button("Reset");
        reset.addEventHandler(ActionEvent.ANY, (ActionEvent event) -> hopsModel.reset());

        Button hint = new Button("Hint");
        hint.addEventHandler(ActionEvent.ANY, (ActionEvent event) -> hopsModel.hint());

        hBox.getChildren().addAll(load, reset, hint);
        hBox.setAlignment(Pos.CENTER);

        return hBox;
    }

    public GridPane makeGridPane()
    {
        gridPane = new GridPane();
        this.make();
        borderPane.setCenter(gridPane);
        return gridPane;
    }

    public void make()
    {
        char[][] board = hopsModel.getCurrentConfig().getBoard();
        int rows = this.hopsModel.getCurrentConfig().getDimR();
        int columns = this.hopsModel.getCurrentConfig().getDimC();


        char letter = '-';
        for (int r = 0; r < rows; r++)
        {
            for (int c = 0; c < columns; c++)
            {
                int row = r;
                int col = c;
                letter = board[r][c];
                Button button = new Button();
                if (letter == EMPTY)
                {
                    button.setGraphic(new ImageView(emptyPad));
                } else if (letter == GREEN)
                {
                    button.setGraphic(new ImageView(greenFrog));
                } else if (letter == RED)
                {
                    button.setGraphic(new ImageView(redFrog));
                } else if (letter == INVALID)
                {
                    button.setGraphic(new ImageView(waterSpace));
                }

                button.setMaxSize(ICON_SIZE, ICON_SIZE);
                button.setMinSize(ICON_SIZE, ICON_SIZE);
                button.addEventHandler(ActionEvent.ANY, (ActionEvent event) -> hopsModel.select(row, col));
                gridPane.add(button, c, r);


            }
        }
    }


    @Override
    public void update(HoppersModel hoppersModel, String msg)
    {
        topLabel.setText(msg);
        this.makeGridPane();
        stage1.sizeToScene();
    }

    public static void main(String[] args)
    {
        if (args.length != 1)
        {
            System.out.println("Usage: java HoppersGUI filename");
        } else
        {
            Application.launch(args);
        }
    }
}
