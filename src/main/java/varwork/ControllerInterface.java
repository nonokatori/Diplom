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
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import mainclass.MainApp;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerInterface implements Initializable {

    @FXML
    public MenuItem open, close, start, stop, reboot, save, help;
    @FXML
    public Menu captureMenu;
    @FXML
    public Label label;
    @FXML
    public ListView <String> interfaceList, packetsList;
    @FXML
    public AnchorPane interfaceView;

    private CommandLogic parser;

    private ObservableList devs;
    private ObservableList packets;

    CommandLogic commandLogic;

    public void initialize(URL url, ResourceBundle resourceBundle) {
        commandLogic = MainApp.getCommandLogic();
        parser.getAllInterface();


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

    public void setInterfaceList () {


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

    public void updatePacketsView() {



    }

//    class Interface extends ListCell<PcapIf> {
//        protected void updateItem(PcapIf item, boolean empty) {
//            super.updateItem(item, empty);
//            setGraphic(null);
//            setText(null);
//            if (item != null) {
//                HBox hBox = new HBox();
//                Text desc = new Text(item.getDescription());// 网卡信息
//                // 网卡地址
//                String addr = item.getAddresses().toString();
//                Text padding = new Text(":      ");
//                // 对地址字符串进行分割获取我们需要的字符串
//                System.out.println("addr:" + addr);
//                Text address = new Text(addr.split("]")[0].split("\\[")[3]);
//                hBox.getChildren().addAll(desc, padding, address);
//                setGraphic(hBox);
//            }
//        }
//
//    }
}

