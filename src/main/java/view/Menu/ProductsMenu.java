package view.Menu;

import model.Product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;

public class ProductsMenu extends Menu{

    private GoodMenu goodMenu = new GoodMenu(this,null);
    private List<Product> listToSort;

    public ProductsMenu(Menu parentMenu) {
        super("productsMenu", parentMenu);
        HashMap<Integer, Menu> submenus = new HashMap<>();
        submenus.put(1, goodMenu);
        submenus.put(2, new LoginMenu(this));
        submenus.put(3, getViewCategories());
        submenus.put(4, getFiltering());
        submenus.put(5, getSorting());
        submenus.put(6, getShowProducts());
        listToSort = controller.disableSort();
        this.setSubmenus(submenus);
    }

    @Override
    public void execute() {
        String command = scanner.nextLine();
        switch (command) {
            case "1":
                System.out.println("Enter product Id:");
                String goodId = scanner.nextLine();
                if (controller.getAvailableProductById(goodId) == null) {
                    System.out.println("product not found");
                    this.show();
                    this.execute();
                } else {
                    goodMenu.setGoodId(goodId);
                    Product product = controller.getProductById(goodId);
                    product.setNumberOfViews(product.getNumberOfViews() + 1);
                    goodMenu.show();
                    goodMenu.execute();
                }
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
            default:
                System.out.println("Enter a validate number");
                this.execute();
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
                if (field.matches("category")) {
                    System.out.println("Enter a category:");
                    String categoryName = scanner.nextLine();
                    controller.filter("by category",categoryName);
                }
                else if (field.matches("name")) {
                    System.out.println("Enter a name:");
                    String name = scanner.nextLine();
                    controller.filter("by name",name);
                }
                else
                    System.out.println("Please Enter a validate filter");

            }
            else if (command.matches("show available filters")) {
                System.out.println("available filters:\n" +
                        "1.by category\n" +
                        "2.by name");
            }
            else if (command.matches("current filters")) {
                System.out.println("Current Filters:");
                if (controller.hasCategoryFilter) {
                    System.out.println("by category:" + controller.filteredCategory);
                }
                if (controller.hasNameFilter) {
                    System.out.println("by name:" + controller.filteredName);
                }
                else
                    System.out.println("No filter yet");
            }
            else if (command.matches("disable filter (\\S+)")) {
                (matcher = getMatcher(regex, command)).find();
                String field = matcher.group(1);
                if (field.equals("category") || field.equals("name")) {
                    controller.disableFilter(field);
                }
                else
                    System.out.println("filter is not valid");
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
                listToSort = controller.sort(field);
            }
            else if (command.matches("show available sorting")) {
                System.out.println("sorting by:\n" +
                        "1.Date\n" +
                        "2.score\n" +
                        "3.number of views");
            }
            else if (command.matches("current sort")) {
                System.out.println(controller.showCurrentSort());
            }
            else if (command.matches("disable sort")) {
                listToSort = controller.disableSort();
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
                switch (scanner.nextLine()) {
                    case "1":
                        manageFilters();
                        this.show();
                        this.execute();
                        break;
                    case "2":
                        this.parentMenu.show();
                        this.parentMenu.execute();
                        break;
                    default:
                        System.out.println("Enter a validate number");
                        this.execute();
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
                switch (scanner.nextLine()) {
                    case "1":
                        manageSorting();
                        this.show();
                        this.execute();
                        break;
                    case "2":
                        this.parentMenu.show();
                        this.parentMenu.execute();
                        break;
                    default:
                        System.out.println("Enter a  validate number");
                        this.execute();
                }
            }
        };
    }

    private Menu getShowProducts() {
        return new Menu("show product",this) {
            @Override
            public void show() {
                List<Product> filtered = new ArrayList<>(listToSort);
                System.out.println("Show Products Menu:");
                System.out.println("1.back");
                for (Product product : listToSort) {
                    if (controller.hasNameFilter) {
                        if (!(product.getName().contains(controller.filteredName))) {
                            filtered.remove(product);
                        }
                    }
                    if (controller.hasCategoryFilter) {
                        if (!(product.getCategory().getName().equals(controller.filteredCategory))) {
                            filtered.remove(product);
                        }
                    }
                }
                for (Product product : filtered) {
                    System.out.println(product.getName() + "    " + product.getProductId() + "    " +
                            product.getCategory().getName() + "    " + product.getNumberOfViews() + "    " +
                            product.getDateModified());
                }
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
