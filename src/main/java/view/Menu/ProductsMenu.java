package view.Menu;

import java.util.HashMap;

public class ProductsMenu extends Menu{
    private GoodMenu goodMenu = new GoodMenu(this,null);

    public ProductsMenu(Menu parentMenu) {
        super("productsMenu", parentMenu);
        HashMap<Integer, Menu> submenus = new HashMap<>();
        submenus.put(1,goodMenu);
        this.setSubmenus(submenus);
    }

    @Override
    public void show() {

    }

    @Override
    public void execute() {

    }
}
