package view.Menu;

import java.util.HashMap;

public class ProductsMenu extends Menu{

    public ProductsMenu(Menu parentMenu) {
        super("productsMenu", parentMenu);
        HashMap<Integer, Menu> submenus = new HashMap<>();
        submenus.put(1, new GoodMenu(this,null));
        submenus.put(2, new LoginMenu(this));
        this.setSubmenus(submenus);
    }

    @Override
    public void execute() {

    }

    private Menu getViewCategories() {
        return new Menu("view categories",this) {
            @Override
            public void show() {
                System.out.println("view categories Menu:");
                System.out.println("1.back");
                System.out.println(controller.getCategories());
            }

            @Override
            public void execute() {
                if (Integer.parseInt(scanner.nextLine()) == 1) {
                    this.parentMenu.show();
                    this.parentMenu.execute();
                }
            }
        };
    }
}
