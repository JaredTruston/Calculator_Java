import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.ArrayList;

public class Calculator extends Application {
    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) {

        GridPane root = new GridPane();

        TextField output = new TextField();

        // Contains the main digit keys used in calculation
        ArrayList<Button> digKeys = new ArrayList<Button>();

        // row 1 of the digit key buttons
        digKeys.add(new Button("1"));
        digKeys.add(new Button("2"));
        digKeys.add(new Button("3"));

        // row 2 of the digit key buttons
        digKeys.add(new Button("4"));
        digKeys.add(new Button("5"));
        digKeys.add(new Button("6"));

        // row 3 of the digit key buttons
        digKeys.add(new Button("7"));
        digKeys.add(new Button("8"));
        digKeys.add(new Button("9"));

        // row 4 of the digit key buttons
        digKeys.add(new Button("."));
        digKeys.add(new Button("0"));

        Button clear = new Button("AC"); // Clears current contents of the output TextField

        // Contains main operation keys
        ArrayList<Button> opKeys = new ArrayList<Button>();
        opKeys.add(new Button("+")); // addition operation
        opKeys.add(new Button("-")); // subtraction operation
        opKeys.add(new Button("*")); // multiplication operation
        opKeys.add(new Button("/")); // division operation

        Button compute = new Button("="); // computes the result

        // Adds all button instances as well as the output TextField to the GridPane
        root.addRow(0, new Label(" "), digKeys.get(0), new Label(" "), digKeys.get(1), new Label(" "), digKeys.get(2), new Label(" "), opKeys.get(0), new Label(" "), new Label("Output: "));
        root.addRow(1, new Label(" "), digKeys.get(3), new Label(" "), digKeys.get(4), new Label(" "), digKeys.get(5), new Label(" "), opKeys.get(1), new Label(" "), output);
        root.addRow(2, new Label(" "), digKeys.get(6), new Label(" "), digKeys.get(7), new Label(" "), digKeys.get(8), new Label(" "), opKeys.get(2), new Label(" "), compute);
        root.addRow(3, new Label(" "), digKeys.get(9), new Label(" "), digKeys.get(10), new Label(" "), clear, new Label(" "), opKeys.get(3));


        // Event Handler for Printing Digit Values to the output TextField
        EventHandler<MouseEvent> printingKey = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Button tempButton = (Button) event.getSource();
                output.setText(output.getText() + tempButton.getText());
            }
        };

        // Calls addEventHandler on each digit key of the calculator application
        for (Button b : digKeys) {
            b.addEventHandler(MouseEvent.MOUSE_CLICKED, printingKey);
        }

        // Event Handler for Printing Operation Symbols to the output TextField
        EventHandler<MouseEvent> printingOp = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Button tempButton = (Button) event.getSource();
                output.setText(output.getText() + " " + tempButton.getText() + " ");
            }
        };

        // Calls addEventHandler on each operation symbol of the calculator application
        for (Button b : opKeys) {
            b.addEventHandler(MouseEvent.MOUSE_CLICKED, printingOp);
        }

        // Event Handler for computing the result of the current operation to the output TextField
        EventHandler<MouseEvent> computingResult = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                String result; // Holds the result in String form to be set as TextField output
                ScriptEngineManager mgr = new ScriptEngineManager();
                ScriptEngine engine = mgr.getEngineByName("JavaScript");
                result = output.getText(); // Assigns the current equation to the result variable
                try {
                    result = "" + engine.eval(result); // Evaluates the equation and converts result to String
                } catch (ScriptException e) {
                    e.printStackTrace();
                }
                output.setText(result); // Sets output TextField to current result
            }
        };

        compute.addEventHandler(MouseEvent.MOUSE_CLICKED, computingResult);

        // Event Handler for clearing the output TextField
        EventHandler<MouseEvent> clearAll = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                output.setText(""); // Sets output TextField to empty String
            }
        };

        clear.addEventHandler(MouseEvent.MOUSE_CLICKED, clearAll);


        Scene scene = new Scene(root);
        stage.setScene(scene);

        stage.setX(300);
        stage.setY(150);
        stage.setMinHeight(150);
        stage.setMinWidth(300);
        stage.setTitle("Calculator");
        stage.show();
    }
}
