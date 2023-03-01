package puzzles.hoppers.ptui;

import puzzles.common.ConsoleApplication;
import puzzles.common.Observer;
import puzzles.hoppers.model.HoppersModel;
import puzzles.jam.model.JamModel;
import puzzles.jam.ptui.JamPTUI;

import java.io.PrintWriter;
import java.util.List;

public class HoppersPTUI extends ConsoleApplication
        implements Observer<HoppersModel, String> {
    private HoppersModel model;

    private boolean initialized;

    private PrintWriter out;

    @Override public void init() throws Exception{
        this.initialized = false;
        List<String> paramStrings = super.getArguments();
        this.model = new HoppersModel(paramStrings.get(0));
        this.model.addObserver( this );


    }

    @Override
    public void start(PrintWriter console) throws Exception {
        this.out = console;
        this.initialized = true;
        super.setOnCommand("h", 0, "hint next move",
                args -> this.model.hint());
        super.setOnCommand("l", 1, "load new puzzle file",
                args -> this.model.load(args[0]));
        super.setOnCommand("s", 2, "select cell at r, c",
                args -> this.model.select(Integer.parseInt(args[0]), Integer.parseInt(args[1])));
        super.setOnCommand("q", 0 , "quit the game", args -> this.model.quit());
        super.setOnCommand("r", 0, "reset the current game", args -> this.model.reset());
    }

    @Override
    public void update(HoppersModel hopModel, String msg) {
        System.out.println(msg);
        String str = "   ";
        String Dash = "  ";

        for (int c = 0; c < hopModel.getCurrentConfig().getDimC(); c++) {
            str = str + c + " ";
            Dash = Dash + "__";
        }
        System.out.println(str);
        System.out.println(Dash);
        System.out.println(hopModel.getCurrentConfig().toString());
        System.out.println("");
    }


    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java HoppersPTUI filename");
        }else {
            ConsoleApplication.launch(HoppersPTUI.class, args);
        }
    }
}
