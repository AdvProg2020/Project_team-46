package view.Menu;

import java.util.HashMap;
import java.util.regex.Matcher;

public class ProductsMenu extends Menu{

    private GoodMenu goodMenu = new GoodMenu(this,null);

    public ProductsMenu(Menu parentMenu) {
        super("productsMenu", parentMenu);
        HashMap<Integer, Menu> submenus = new HashMap<>();
        submenus.put(1, goodMenu);
        submenus.put(2, new LoginMenu(this));
        submenus.put(3, getViewCategories());
        submenus.put(4, getFiltering());
        submenus.put(5, getSorting());
        submenus.put(6, getShowProducts());
        this.setSubmenus(submenus);
    }

    @Override
    public void execute() {
        String command = scanner.nextLine();
        switch (command) {
            case "1":
                System.out.println("Enter product Id:");
                String goodId = scanner.nextLine();
                goodMenu.setGoodId(goodId);
                goodMenu.show();
                goodMenu.execute();
                break;
            case "2":
                submenus.get(2).show();
                submenus.get(2).execute();
                break;
            case "3":
                getViewCategories().show();
                getViewCategories().execute();
                break;
            case "4":
                getFiltering().show();
                getFiltering().execute();
                break;
            case "5":
                getSorting().show();
                getSorting().execute();
                break;
            case "6":
                getShowProducts().show();
                getShowProducts().execute();
                break;
            case "7":
                this.parentMenu.show();
                this.parentMenu.execute();
                break;
        }
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

    private void manageFilters() {
        String command;
        String regex;
        Matcher matcher;
        while (!(command = scanner.nextLine()).equalsIgnoreCase("back")) {
            if (command.matches(regex = "filter (\\S+)")) {
                (matcher = getMatcher(regex, command)).find();
                String field = matcher.group(1);
                controller.filter(field);
            }
            else if (command.matches("show available filters")) {
                controller.showAvailableFilters();
            }
            else if (command.matches("current filters")) {
                controller.showCurrentFilters();
            }
            else if (command.matches("disable filter (\\S+)")) {
                (matcher = getMatcher(regex, command)).find();
                String field = matcher.group(1);
                controller.disableFilter(field);
            }
            else
                System.out.println("invalid command");
        }
    }

    private void manageSorting() {
        String command;
        String regex;
        Matcher matcher;
        while (!(command = scanner.nextLine()).equalsIgnoreCase("back")) {
            if (command.matches(regex = "sort (\\S+)")) {
                (matcher = getMatcher(regex, command)).find();
                String field = matcher.group(1);
                controller.sort(field);
            }
            else if (command.matches("show available sorts")) {
                System.out.println("sorting by:\n" +
                        "1.Date\n" +
                        "2.score\n" +
                        "3.number of views");
            }
            else if (command.matches("current sort")) {
                controller.showCurrentSort();
            }
            else if (command.matches("disable sort")) {
                controller.disableSort();
            }
        }
    }

    private Menu getFiltering() {
        return new Menu("filtering Menu",this) {
            @Override
            public void show() {
                System.out.println("Filtering Menu:");
                System.out.println("1.show available filters/\n" +
                        "filter[an available filter]/\n" +
                        "current filters/\n" +
                        "disable filter[a selected filter]\n" +
                        "2.back");

            }

            @Override
            public void execute() {
                switch (Integer.parseInt(scanner.nextLine())) {
                    case 1:
                        manageFilters();
                        this.show();
                        this.execute();
                        break;
                    case 2:
                        this.parentMenu.show();
                        this.parentMenu.execute();
                        break;
                }
            }
        };
    }

    private Menu getSorting() {
        return new Menu("Sorting Menu",this) {
            @Override
            public void show() {
                System.out.println("Sorting Menu:");
                System.out.println("1.show available sorting/sort[an available sort]/current sort/disable sort\n" +
                        "2.back");
            }

            @Override
            public void execute() {
                switch (Integer.parseInt(scanner.nextLine())) {
                    case 1:
                        manageSorting();
                        this.show();
                        this.execute();
                        break;
                    case 2:
                        this.parentMenu.show();
                        this.parentMenu.execute();
                        break;
                }
            }
        };
    }

    private Menu getShowProducts() {
        return new Menu("show product",this) {
            @Override
            public void show() {
                System.out.println("Show Products Menu:");
                System.out.println("1.back");
                controller.showProducts();
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
