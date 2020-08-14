package varwork;


import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import mainclass.MainApp;
import mainclass.Packet;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GUI implements Initializable {

    @FXML
    public MenuItem open, close, start, stop, reboot, save, help;
    @FXML
    public Menu captureMenu;
    @FXML
    public Label label;
    @FXML
    public TableColumn number, time, source, destination, protocol, length;
    @FXML
    public TableView <Packet> packetView;
    @FXML
    public AnchorPane interfaceView;
    @FXML
    public GridPane btnGrid;
    @FXML
    public Button int00,int01,int02,int03,int04,int05,int06,int07,int08,int09;

    private CommandLogic parser;
    private Button [] arrBut = {int00,int01,int02,int03,int04,int05,int06,int07,int08,int09};


    CommandLogic commandLogic;

    public void initialize(URL url, ResourceBundle resourceBundle) {
        commandLogic = MainApp.getCommandLogic();

        packetView.setVisible(false);
        interfaceView.setVisible(true);
        captureMenu.setVisible(false);
        close.setDisable(true);
        save.setDisable(true);

//        setValueButton();
    }

    public void clkInt(ActionEvent actionEvent) {
        Button btn = (Button) actionEvent.getSource();
        parser.GUICommand(btn.getId());

    }

    public void setValueButton () {
        commandLogic.parser(new String[]{"-l"});
        for (int i = 0; i < arrBut.length; i++) {
            if(i < commandLogic.getAllInterface().size()) {
                arrBut[i].setText(commandLogic.getAllInterface().get(i));
            } else {
                arrBut[i].setVisible(false);
            }
        }
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

    public void clkHelp(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/help.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();

        stage.setTitle("Помощь");
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void clkOpen(ActionEvent actionEvent) {
//        parser.GUICommand("open");

    }

    public void clkClose(ActionEvent actionEvent) {
//        parser.GUICommand("close");
    }

    public void clkExit(ActionEvent actionEvent) {
        System.exit(0);
    }

    public void setParser(CommandLogic parser) {
        this.parser = parser;
    }

    public void clkSave(ActionEvent actionEvent) {
    }

    public void updateTable() {

        ObservableList<Packet> newRow = packetView.getItems();
        if (commandLogic.getDispatcher().getOutData().getPacketQueue().size() != 0)
                newRow.add(commandLogic.getDispatcher().getOutData().getPacketQueue().poll());
    }
}

