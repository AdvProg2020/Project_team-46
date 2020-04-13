package view.Menu;

import java.util.HashMap;

public class MainMenu extends Menu{
    private LoginMenu loginMenu = new LoginMenu(this);
    private ProductsMenu productsMenu = new ProductsMenu(this);
    private OffMenu offMenu = new OffMenu(this);

    public MainMenu() {
        super("MainMenu", null);
        HashMap<Integer, Menu> submenus = new HashMap<>();
        submenus.put(1, new OffMenu(this));
        submenus.put(2, new ProductsMenu(this));
        submenus.put(3, new UserMenu(this));
        this.setSubmenus(submenus);
    }

    @Override
    public void show() {

    }

    @Override
    public void execute() {
        String chosenMenu = scanner.nextLine().trim();
        if(chosenMenu.matches("(?i)create\\s+account\\s+\\S+\\s+\\S+")) {
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
        else if(chosenMenu.matches("offs")) {
            offMenu.show();
            offMenu.execute();
        }
        else if(chosenMenu.matches("products")) {
            productsMenu.show();
            productsMenu.execute();
        }
    }
}
