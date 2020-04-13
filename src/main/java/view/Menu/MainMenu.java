package view.Menu;

import java.util.HashMap;

public class MainMenu extends Menu{

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

    }
}
