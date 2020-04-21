package view.Menu;

import java.util.HashMap;

public class MainMenu extends Menu{

    public MainMenu() {
        super("MainMenu", null);
        HashMap<Integer, Menu> submenus = new HashMap<>();
        submenus.put(1, new UserMenu(this));
        submenus.put(2, new ProductsMenu(this));
        submenus.put(3, new OffMenu(this));
        submenus.put(4, new LoginMenu(this));
        this.setSubmenus(submenus);
    }


}
