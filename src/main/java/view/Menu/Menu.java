package view.Menu;

import controller.Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Menu {
    protected static Controller controller;
    protected String name;
    protected HashMap<Integer, Menu> submenus;
    protected Menu parentMenu;
    protected static Menu mainMenu;
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
        submenus = new HashMap<>();
        this.name = name;
        this.parentMenu = parentMenu;
        allMenus.add(this);
    }

    public static void setController(Controller controller) {
        Menu.controller = controller;
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

    public void show() {
        System.out.println(this.name + ":");
        for (Integer menuNum : submenus.keySet()) {
            System.out.println(menuNum + ". " + submenus.get(menuNum).getName());
        }
        if (this.parentMenu != null)
            System.out.println((submenus.size() + 1) + ". Back");
        else
            System.out.println((submenus.size() + 1) + ". Exit");
    }

    public void execute() {
        Menu nextMenu = null;
        int chosenMenu = Integer.parseInt(scanner.nextLine());
        if (chosenMenu == submenus.size() + 1) {
            if (this.parentMenu == null)
                System.exit(1);
            else
                nextMenu = this.parentMenu;
        } else
            nextMenu = submenus.get(chosenMenu);
        nextMenu.show();
        nextMenu.execute();
    }

    public Matcher getMatcher(String regex, String input) {
        return Pattern.compile(regex).matcher(input);
    }

    public static void setMainMenu(Menu mainMenu) {
        Menu.mainMenu = mainMenu;
    }
}
