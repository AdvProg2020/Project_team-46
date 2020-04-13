package view.Menu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public abstract class Menu {
    private String name;
    protected HashMap<Integer, Menu> submenus;
    protected Menu parentMenu;
    public static Scanner scanner;
    protected static ArrayList<Menu> allMenus;

    public void setName(String name) {
        this.name = name;
    }

    public void setSubmenus(HashMap<Integer, Menu> submenus) {
        this.submenus = submenus;
    }

    public static void setScanner(Scanner scanner) {
        Menu.scanner = scanner;
    }

    static {
        allMenus = new ArrayList<>();
    }

    public Menu(String name, Menu parentMenu) {
        this.name = name;
        this.parentMenu = parentMenu;
        allMenus.add(this);
    }

    public void setParentMenu(Menu parentMenu) {
        this.parentMenu = parentMenu;
    }

    public Menu getMenuByName(String name) {
        for (Integer integer : submenus.keySet()) {
            if(submenus.get(integer).getName().equalsIgnoreCase(name)) {
                return submenus.get(integer);
            }
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public abstract void show();

    public abstract void execute();

}
