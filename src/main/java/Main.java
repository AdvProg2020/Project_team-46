import controller.Controller;
import view.Menu.MainMenu;
import view.Menu.Menu;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        MainMenu.setScanner(new Scanner(System.in));
        MainMenu.setController(new Controller());
        Menu mainMenu = new MainMenu();
        mainMenu.show();
        mainMenu.execute();
    }
}
