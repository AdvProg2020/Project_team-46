package view.Menu;

import java.util.HashMap;

public class ProductsMenu extends Menu{
    private GoodMenu goodMenu = new GoodMenu(this,null);
    private LoginMenu loginMenu = new LoginMenu(this);

    public ProductsMenu(Menu parentMenu) {
        super("productsMenu", parentMenu);
        submenus.put(1,goodMenu);
        submenus.put(2,loginMenu);
        this.setSubmenus(submenus);
    }

    @Override
    public void show() {

    }

    @Override
    public void execute() {
        String chosenMenu = scanner.nextLine().trim();
        if(chosenMenu.matches("(?i)show\\s+product\\s+\\S+")) {
            String[] splitString = chosenMenu.split("\\s+");
            goodMenu.setGoodId(splitString[2]);
            goodMenu.show();
            goodMenu.execute();
        }
        else if(chosenMenu.matches("(?i)create\\s+account\\s+\\S+\\s+\\S+")) {
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
