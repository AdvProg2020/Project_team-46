package view.Menu;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
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
            public void start(Stage primaryStage) throws Exception {
                VBox layout = new VBox(20);
                Button back = new Button("Back");
                back.setOnAction(event -> {
                    try {
                        parentMenu.start(new Stage());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
                Label label = new Label();
                label.setText(controller.getCategories().toString());
                layout.getChildren().addAll(label, back);
                Scene scene = new Scene(layout, 200, 200);
                primaryStage.setScene(scene);
                primaryStage.show();
            }

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
            public void start(Stage primaryStage) throws Exception {
                VBox mainLayout = new VBox(20);
                VBox filter1Layout = new VBox(20);
                VBox filter2Layout = new VBox(20);
                VBox currentFilters = new VBox(20);
                VBox disableFilter = new VBox(20);
                Scene mainScene = new Scene(mainLayout, 200, 200);
                Scene scene1 = new Scene(filter1Layout, 200, 200);
                Scene scene2 = new Scene(currentFilters, 200, 200);
                Scene scene3 = new Scene(disableFilter, 200, 200);
                Scene scene4 = new Scene(filter2Layout, 200, 200);
                Button filterByCategory = new Button("Filter by Category");
                filterByCategory.setOnAction(event -> {
                    HBox layout1 = new HBox(20);
                    HBox layout2 = new HBox(20);
                    Button back = new Button("Back");
                    TextField category = new TextField();
                    Label message = new Label();
                    back.setOnAction(event1 -> {
                        try {
                            primaryStage.setScene(mainScene);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
                    Button confirm = new Button("Confirm");
                    confirm.setOnAction(event1 -> {
                        message.setText(controller.filter("by category", category.toString()));
                    });
                    layout1.getChildren().addAll(new Label("Enter a Category: "), category);
                    layout2.getChildren().addAll(confirm, back);
                    filter1Layout.getChildren().addAll(layout1, layout2, message);
                    primaryStage.setScene(scene1);

                });
                Button filterByName = new Button("Filter by Name");
                filterByName.setOnAction(event -> {
                    HBox layout1 = new HBox(20);
                    HBox layout2 = new HBox(20);
                    Button back = new Button("Back");
                    TextField category = new TextField();
                    Label message = new Label();
                    back.setOnAction(event1 -> {
                        try {
                            primaryStage.setScene(mainScene);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
                    Button confirm = new Button("Confirm");
                    confirm.setOnAction(event1 -> {
                        message.setText(controller.filter("by name", category.toString()));
                    });
                    layout1.getChildren().addAll(new Label("Enter a name: "), category);
                    layout2.getChildren().addAll(confirm, back);
                    filter2Layout.getChildren().addAll(layout1, layout2, message);
                    primaryStage.setScene(scene4);
                });
                Button currentFilter = new Button("Current Filters");
                currentFilter.setOnAction(event -> {
                    Label label = new Label();
                    StringBuilder stringBuilder = new StringBuilder();
                    Button back = new Button("Back");
                    back.setOnAction(event1 -> {
                        try {
                            primaryStage.setScene(mainScene);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
                    stringBuilder.append("Current Filters: \n");
                    if (controller.hasCategoryFilter) {
                        stringBuilder.append("by category: ").append(controller.filteredCategory);
                    }
                    if (controller.hasNameFilter) {
                        stringBuilder.append("by name: ").append(controller.filteredName);
                    }
                    else
                        stringBuilder.append("No filter yet");
                    label.setText(stringBuilder.toString());
                    currentFilters.getChildren().addAll(label);
                    primaryStage.setScene(scene2);
                });
                Button disableButton = new Button("Disable Filter");
                disableButton.setOnAction(event -> {
                    HBox layout1 = new HBox(20);
                    HBox layout2 = new HBox(20);
                    Button back = new Button("Back");
                    Label message = new Label();
                    back.setOnAction(event1 -> {
                        try {
                            primaryStage.setScene(mainScene);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
                    Button ByName = new Button("By Name");
                    ByName.setOnAction(event1 -> {
                        controller.disableFilter("name");
                        message.setText("Successful");
                    });
                    Button ByCategory = new Button("By Category");
                    ByCategory.setOnAction(event1 -> {
                        controller.disableFilter("category");
                        message.setText("Successful");
                    });
                    layout1.getChildren().addAll(ByCategory, ByName);
                    layout2.getChildren().addAll(back);
                    disableFilter.getChildren().addAll(layout1, layout2, message);
                    primaryStage.setScene(scene3);
                });
                HBox layout1 = new HBox(20);
                HBox layout2 = new HBox(20);
                layout1.getChildren().addAll(filterByCategory, filterByName);
                layout2.getChildren().addAll(currentFilter, disableButton);
                mainLayout.getChildren().addAll(layout1, layout2);
                primaryStage.setScene(mainScene);
                primaryStage.show();
            }

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
            public void start(Stage primaryStage) throws Exception {

            }

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
            public void start(Stage primaryStage) throws Exception {
                List<Product> filtered = new ArrayList<>(listToSort);
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
                Label label = new Label();
                StringBuilder stringBuilder = new StringBuilder();
                for (Product product : filtered) {
                    stringBuilder.append(product.getName()).append("    ").append(product.getProductId()).append("    ")
                            .append(product.getCategory().getName()).append("    ").append(product.getNumberOfViews())
                            .append("    ").append(product.getDateModified()).append("\n");
                }
                label.setText(stringBuilder.toString());
                Button back = new Button("Back");
                back.setOnAction(event1 -> {
                    try {
                        parentMenu.start(new Stage());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
                VBox layout = new VBox(20);
                layout.getChildren().addAll(label, back);
                Scene scene = new Scene(layout, 200, 200);
                primaryStage.setScene(scene);
                primaryStage.show();
            }

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

    @Override
    public void start(Stage primaryStage) throws Exception {
        VBox mainLayout = new VBox(20);
        HBox layout1 = new HBox(20);
        HBox layout2 = new HBox(20);
        HBox layout3 =  new HBox(20);
        HBox layout4 = new HBox(20);
        Button button = new Button("Good Menu");
        button.setOnAction(event -> {
            try {
                submenus.get(1).start(new Stage());
                primaryStage.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        Button button1 = new Button("Login Menu");
        button1.setOnAction(event -> {
            try {
                submenus.get(2).start(new Stage());
                primaryStage.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        Button button2 = new Button("View Categories");
        button2.setOnAction(event -> {
            try {
                submenus.get(3).start(new Stage());
                primaryStage.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        Button button3 = new Button("Filtering");
        button3.setOnAction(event -> {
            try {
                submenus.get(4).start(new Stage());
                primaryStage.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        Button button4 = new Button("Sorting");
        button4.setOnAction(event -> {
            try {
                submenus.get(5).start(new Stage());
                primaryStage.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        Button button5 = new Button("Show Products");
        button5.setOnAction(event -> {
            try {
                submenus.get(6).start(new Stage());
                primaryStage.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        Button button6 = new Button("Back");
        button6.setOnAction(event -> {
            try {
                parentMenu.start(new Stage());
                primaryStage.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        layout1.getChildren().addAll(button, button1);
        layout2.getChildren().addAll(button2, button3);
        layout3.getChildren().addAll(button4, button5);
        layout4.getChildren().addAll(button6);
        mainLayout.getChildren().addAll(layout1, layout2, layout3, layout4);
        Scene scene = new Scene(mainLayout, 200, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
