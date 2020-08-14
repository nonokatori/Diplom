package varwork;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerHelp implements Initializable {

    @FXML
    private Label lblTextHelp;
    private StringBuilder builder = new StringBuilder();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String labelText = null;
//        try (FileReader reader = new FileReader("src/main/resources/help.txt")) {
//            int c;
//            while ((c = reader.read()) != -1) {
//                builder.append((char) c);
//            }
//            reader.close();
//        } catch (IOException ex) {
//            System.out.println(ex.getMessage());
//        }
        lblTextHelp.setText(String.valueOf(123));
    }
}