package view.Menu;

import controller.*;

import java.util.HashMap;

public class OffMenu extends Menu {
    private GoodMenu sub = new GoodMenu(this,null);
    private LoginMenu loginMenu = new LoginMenu(this);

    public OffMenu(Menu parentMenu) {
        super("OffMenu", parentMenu);
        submenus.put(1,sub);
        submenus.put(2,loginMenu);
        this.setSubmenus(submenus);
    }

    @Override
    public void show() {
        Controller.offs();
    }

    @Override
    public void execute() {
        String chosenMenu = scanner.nextLine().trim();
        if(chosenMenu.matches("(?i)show\\s+product\\s+\\S+")) {
            String[] splitString = chosenMenu.split("\\s+");
            sub.setGoodId(splitString[2]);
            sub.show();
            sub.execute();
        }
        else if(chosenMenu.matches("(?i)create\\s+account\\s+\\S+\\s+\\S+")) {
            String[] splitString = chosenMenu.split("\\s");
            loginMenu.show();
            loginMenu.execute();
        }
        else if(chosenMenu.matches("(?i)back")) {
            if(this.parentMenu != null) {
                Menu nextMenu = this.parentMenu;
                nextMenu.show();
                nextMenu.execute();
            }
        }
    }
}
