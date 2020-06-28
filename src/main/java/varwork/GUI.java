package varwork;

import datawork.Dispatcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;

import java.net.URL;
import java.util.ResourceBundle;

public class GUI implements Initializable {

    @FXML
    public MenuItem open, close, start, stop, reboot, help;
    @FXML
    public Button int0, int1, int2, int3, int4,
            int5, int6, int7, int8, int9;
    @FXML
    public Label label;
    @FXML
    public TableColumn number, time, source, destination, protocol, length;

    private CommandLogic parser;
    public Button[] buttons;

    public GUI(CommandLogic parser) {
        this.parser = parser;
    }

    public void initialize(URL url, ResourceBundle resourceBundle) {
//        buttons = new Button[]{int0, int1, int2, int3, int4, int5, int6, int7, int8, int9};
    }

    public void clkInt(ActionEvent actionEvent) {
        Button btn = (Button) actionEvent.getSource();
        parser.GUICommand(btn.getId().substring(3, 4));

    }

    public void clkStart(ActionEvent actionEvent) {
        parser.GUICommand("start");
    }

    public void clkStop(ActionEvent actionEvent) {
        parser.GUICommand("stop");

    }

    public void clkReboot(ActionEvent actionEvent) {
        parser.GUICommand("reboot");

    }

    public void clkHelp(ActionEvent actionEvent) {
        parser.GUICommand("help");

    }

    public void clkOpen(ActionEvent actionEvent) {
        parser.GUICommand("open");

    }

    public void clkClose(ActionEvent actionEvent) {
        parser.GUICommand("close");

    }

    public void clkExit(ActionEvent actionEvent) {
        System.exit(0);
    }

//    public void update() {
//
//        int coun = 0;
//
//        for (int i = 0; i < 3; i++)
//
//            for (int j = 0; j < 3; j++){
//
//                if(!buttons[coun].getText().equals(ticToe.getArrField()[i][j])) {
//
//                    buttons[coun].setText(Character.toString(ticToe.getArrField()[i][j]));
//
//                }
//
//                coun++;
//
//            }
//
//    }
}

