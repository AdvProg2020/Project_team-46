import controller.Controller;
import javafx.application.Application;
import javafx.stage.Stage;
import model.Request;
import view.Menu.MainMenu;
import view.Menu.Menu;

import java.util.Scanner;

public class Main {
    public static void main(String[] args)  {
        MainMenu.setScanner(new Scanner(System.in));
        Controller controller = new Controller();
        MainMenu.setController(controller);
        Request.setController(controller);
        MainMenu mainMenu = new MainMenu();
        Menu.setMainMenu(mainMenu);
        mainMenu.launchApp(args);
    }
}
