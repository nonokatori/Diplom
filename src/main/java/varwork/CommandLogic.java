package varwork;

import datawork.Dispatcher;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Pattern;
/*
Команды

-ip.src 192.168.5.111 трафик с ip 192.168.5.111
-ip.dst 192.168.5.111 трафик на ip 192.168.5.111
-port.src 6666 - порт отправителя 6666
-port.dst 7777 - порт получателя 7777

-f filename - сохранение в файл (если не пишем в файл то только выводим на экран)
-hex сохранение/отображение в шестнадцатиричном формате
 */

public class  CommandLogic {

    private Dispatcher dispatcher;
    private String[] data;
    private Scanner scanner;
    private ArrayList<String> allInterface;


    public ArrayList<String> getAllInterface() {
        return allInterface;
    }

    private String interfaceNum;

    private static final String IP_PATTERN =
    "((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)";
    private final Pattern pattern = Pattern.compile(IP_PATTERN);

    private boolean IPValidate (String ip) {
        return pattern.matcher(ip).matches();
    }

    public CommandLogic(Dispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    private void printData() {

    }

    public void parser(String[] inData) {

        if (inData.length == 0) {
            //нулевые фильтры или пустой запрос
        }

        for (int i = 0; i < inData.length; i++) {
            if (Arrays.asList(inData).contains("-l")) {
                this.allInterface = dispatcher.allInterface();
                break;
            } else if ("-help".equals(inData[i])) {
                try (FileReader reader = new FileReader("src/main/resources/help.txt")) {
                    int c;
                    while ((c = reader.read()) != -1) {
                        System.out.print((char) c);
                    }
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
                break;
            } else if  (allInterface.contains(inData[i])) {
                if (!allInterface.contains(dispatcher.getInData())) {
                    dispatcher.getInData().setInterfaceName(inData[i]);
                } else {

                }
            } else if (dispatcher.getInData().getFilterMap().containsKey(inData[i])) {

                if (dispatcher.getInData().getFilterMap().get(inData[i]).equals("ip") && !IPValidate(inData[i+1])) {
                    System.out.println("Некорректный ip");
                    break;
                }

                if (i == inData.length - 1 || inData[i + 1].contains("-")) {
                    dispatcher.getInData().getFilterMap().put(inData[i],"true");
                } else {
                    dispatcher.getInData().getFilterMap().put(inData[i], inData[i + 1]);
                    i++;
                }
                continue;
            } else {
                //подсветить красным строку фильтрации, если через GUI
                System.out.println("Некорректный запрос");
                break;
            }
        }
        //-i eth0 -src -port 8080 -udp
    }

    public void startParser() {
        System.out.println("Sniffer starting...");

        // запуск сниффера...
        scanner = new Scanner(System.in);
        String inData = scanner.nextLine().toLowerCase();
        parser(inData.split(" "));
        printData();
    }

    public void GUICommand(String inData) {
        if (inData.equals("start")) dispatcher.start();
        else if (inData.equals("stop")) dispatcher.stop();
        else if (inData.equals("reboot")) dispatcher.reboot();
        else if (inData.equals("save")) dispatcher.save();
        else if (inData.contains("int0")) {

        }
    }

    public Dispatcher getDispatcher() {
        return dispatcher;
    }

}
