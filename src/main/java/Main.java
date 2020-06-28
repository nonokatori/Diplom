import datawork.Dispatcher;

import datawork.LogicSniffer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import varwork.CommandLogic;
import varwork.GUI;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);

        LogicSniffer logicSniffer = new LogicSniffer();
        Dispatcher dispatcher = new Dispatcher(logicSniffer);
        Dispatcher.InData inData = dispatcher.getInData();
        Dispatcher.OutData outData = dispatcher.getOutData();
        logicSniffer.setData(inData, outData);
        CommandLogic commandLogic = new CommandLogic(dispatcher);
        GUI gui = new GUI(commandLogic);
//
//        String os = System.getProperty("os.name").toLowerCase();
//
//        if ("win".equals(os)) {
//            launch(args);
//            while (true) {
//                Platform.runLater(() -> {
//                    gui.update();
//                });
//            }
//        }
//        else {
//            commandLogic.startParser();
//        }
    }


    @Override

    public void init() throws Exception {
        System.out.println("Application inits");
        super.init();
    }

    @Override

    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/resources/in.fxml"));
        primaryStage.setTitle("Программный модуль анализа сетевого трафика");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}

