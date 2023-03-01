package puzzles.jam.ptui;

import puzzles.common.ConsoleApplication;
import puzzles.common.Observer;
import puzzles.jam.model.JamModel;

import java.io.PrintWriter;
import java.util.List;

public class JamPTUI extends ConsoleApplication
        implements Observer<JamModel, String> {
    private JamModel model;

    private boolean initialized;

    private PrintWriter out;

    @Override public void init() throws Exception{
        this.initialized = false;
        List<String> paramStrings = super.getArguments();
        this.model = new JamModel(paramStrings.get(0));
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
    public void update(JamModel jamModel, String msg) {
        System.out.println(msg);
        String str = "   ";
        String Dash = "  ";

        for (int c = 0; c < jamModel.getCurrentConfig().getColumns(); c++) {
            str = str + c + " ";
            Dash = Dash + "__";
        }
        System.out.println(str);
        System.out.println(Dash);
        System.out.println(jamModel.getCurrentConfig().toString());
        System.out.println("");
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java JamPTUI filename");
        } else {
            ConsoleApplication.launch(JamPTUI.class, args);
        }
    }
}
