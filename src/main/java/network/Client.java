package network;

import controller.Controller;
import javafx.application.Application;
import javafx.stage.Stage;
import model.Request;
import view.Menu.MainMenu;
import view.Menu.Menu;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    protected static Socket socket;
    private static DataInputStream dataInputStream;
    private static DataOutputStream outputStream;

    public static void main(String[] args) {
        MainMenu.setScanner(new Scanner(System.in));
        Controller controller = new Controller();
        MainMenu.setController(controller);
        Request.setController(controller);
        MainMenu mainMenu = new MainMenu();
        Menu.setMainMenu(mainMenu);
        mainMenu.launchApp(args);
    }

    public static Socket getSocket() {
        return socket;
    }

    public static DataInputStream getDataInputStream() {
        return dataInputStream;
    }

    public static DataOutputStream getOutputStream() {
        return outputStream;
    }

    public static void setSocket(Socket socket) {
        Client.socket = socket;
    }

    public static void setDataInputStream(DataInputStream dataInputStream) {
        Client.dataInputStream = dataInputStream;
    }

    public static void setOutputStream(DataOutputStream outputStream) {
        Client.outputStream = outputStream;
    }
}
