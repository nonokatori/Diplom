package varwork;

import datawork.Dispatcher;

import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;
/*
Команды

-ip.src 192.168.5.111 трафик с ip 192.168.5.111
-ip.dst 192.168.5.111 трафик на ip 192.168.5.111
-port.src 6666 - порт отправителя 6666
-port.dst 7777 - порт получателя 7777

-f filename - сохранение в файл (если не пишем в файл то только выводим на экран)
-hex сохранение/отображение в шестнадцатиричном формате
 */

public class CommandLogic {

    private Dispatcher dispatcher;
    private String[] data;
    private Scanner scanner;

    private String interfaceNum;

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
                dispatcher.allInterface();
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
            } else if ("help".equals(inData)) {
                String testFilePath = "src/main/resources/test.txt";
                try {
                    Process process = Runtime.getRuntime().exec("cmd /c notepad.exe " + testFilePath);
                    process.waitFor();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                break;
            } else if (dispatcher.getInData().getData().containsKey(inData[i])) {
                dispatcher.getInData().getData().put(inData[i],
                        i == inData.length - 1 || inData[i + 1].contains("-") ? "true" : inData[i + 1]);
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

        scanner = new Scanner(System.in);
        String inData = scanner.nextLine().toLowerCase();
        String[] splitData = inData.split(" ");
        parser(splitData);
    }

    public void GUICommand(String inData) {
        if (inData.equals("start")) dispatcher.start();
        else if (inData.equals("stop")) dispatcher.stop();
        else if (inData.equals("reboot")) dispatcher.reboot();
        else if (inData.equals("save")) dispatcher.save();
    }

}
