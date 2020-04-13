package view.Menu;

import controller.*;

import java.util.HashMap;

public class OffMenu extends Menu {
    private GoodMenu sub = new GoodMenu(this,null);

    public OffMenu(Menu parentMenu) {
        super("OffMenu", parentMenu);
        HashMap<Integer, Menu> submenus = new HashMap<>();
        submenus.put(1,sub);
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
        /*
        if (chosenMenu.equalsIgnoreCase("back") || chosenMenu.equalsIgnoreCase("exit")) {
            if (this.parentMenu == null)
                System.exit(1);
            else
                nextMenu = this.parentMenu;
        } else
            nextMenu = ;
        nextMenu.show();
        nextMenu.execute();

         */
    }
}
