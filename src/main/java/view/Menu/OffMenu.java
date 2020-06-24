package view.Menu;

import controller.*;
import javafx.stage.Stage;

import java.util.HashMap;

public class OffMenu extends Menu {

    private GoodMenu goodMenu = new GoodMenu(this,null);

    public OffMenu(Menu parentMenu) {
        super("OffMenu", parentMenu);
        HashMap<Integer, Menu> submenus = new HashMap<>();
        submenus.put(1, goodMenu);
        submenus.put(2, new LoginMenu(this));
        this.setSubmenus(submenus);
    }

    @Override
    public void execute() {
        System.out.println(controller.offs());
        switch (scanner.nextLine()) {
            case "1":
                System.out.println("Enter product Id:");
                String goodId = scanner.nextLine();
                goodMenu.setGoodId(goodId);
                goodMenu.show();
                goodMenu.execute();
                break;
            case "2":
                submenus.get(2).show();
                submenus.get(2).execute();
                break;
            case "3":
                this.parentMenu.show();
                this.parentMenu.execute();
                break;
            default:
                System.out.println("Enter a validate number");
                this.execute();
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

    }
}
