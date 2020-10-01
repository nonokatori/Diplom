package mainclass;

import datawork.Dispatcher;

import datawork.LogicSniffer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import varwork.CommandLogic;
import varwork.ControllerInterface;


public class MainApp extends Application {
    public static CommandLogic getCommandLogic() {
        return commandLogic;
    }

    static private CommandLogic commandLogic;

    public static void main(String[] args) {

        LogicSniffer logicSniffer = new LogicSniffer();
        Dispatcher dispatcher = new Dispatcher(logicSniffer);
        Dispatcher.InData inData = dispatcher.getInData();
        Dispatcher.OutData outData = dispatcher.getOutData();
        logicSniffer.setData(inData, outData);
        CommandLogic commandLogic = new CommandLogic(dispatcher);
        MainApp.commandLogic = commandLogic;

        String os = System.getProperty("os.name").toLowerCase();

        if (os.contains("win")) {
            commandLogic.startParser(); //сюда кинуть аргс, тогда при запуске будут задаваться параметры работы программы
        }
        else {
            launch(args);
        }
    }


//    @Override
//    public void init() throws Exception {
//        System.out.println("Application inits");
//        super.init();
//    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/in.fxml"));
        Parent root = loader.load();

        primaryStage.setTitle("Программный модуль анализа сетевого трафика");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

        ControllerInterface controller = loader.getController();


    }


}

