package view.Menu;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;

public class UserSeller extends Menu{

    public UserSeller(String name, Menu parentMenu) {

        super(name, parentMenu);
        submenus.put(1, new LoginMenu(parentMenu));
        submenus.put(2, getViewPersonalInfo());
        submenus.put(3, getCompanyInfo());
        submenus.put(4, getSalesHistory());
        submenus.put(5, getManageProducts());
        submenus.put(6, addProduct());
        submenus.put(7, removeProduct());
        submenus.put(8, showCategories());
        submenus.put(9, viewOffs());
        submenus.put(10, viewBalance());
    }

    @Override
    public void execute(int command) {
        switch (command) {
            case 1:
                try {
                    submenus.get(1).start(new Stage());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case 2:
                try {
                    submenus.get(2).start(new Stage());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case 3:
                try {
                    submenus.get(3).start(new Stage());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case 4:
                try {
                    submenus.get(4).start(new Stage());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case 5:
                try {
                    submenus.get(5).start(new Stage());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case 6:
                try {
                    submenus.get(6).start(new Stage());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case 7:
                try {
                    submenus.get(7).start(new Stage());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case 8:
                try {
                    submenus.get(8).start(new Stage());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case 9:
                try {
                    submenus.get(9).start(new Stage());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case 10:
                try {
                    submenus.get(10).start(new Stage());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case 11:
                try {
                    parentMenu.start(new Stage());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            }
    }

    private Menu viewBalance() {
        return new Menu("view balance", this) {
            @Override
            public void start(Stage primaryStage) throws Exception {
                VBox layout = new VBox(20);
                Scene scene = new Scene(layout,200,200);
                Label balanceLabel = new Label("Your current balance is: " + controller.viewBalance());
                Button backButton = new Button("Back");
                backButton.setOnAction(event -> {
                    try {
                        this.parentMenu.start(primaryStage);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
                layout.getChildren().addAll(balanceLabel,backButton);
                primaryStage.setScene(scene);
                primaryStage.show();
            }

            @Override
            public void show() {
                System.out.println();
                System.out.println("1. back");
            }

            @Override
            public void execute() {
                if (scanner.nextLine().equals("1")) {
                    parentMenu.show();
                    parentMenu.execute();
                }
            }
        };
    }

    private Menu viewOffs() {
        return new Menu("view offs", this) {
            @Override
            public void start(Stage primaryStage) throws Exception {
                VBox layout = new VBox(20);Scene scene = new Scene(layout,200,200);
                HBox idBox = new HBox(20); TextField idField = new TextField();Label infoLabel = new Label();
                VBox addLayout = new VBox(20); Scene addScene = new Scene(addLayout,500,500);

                HBox proIdBox = new HBox(20); TextField idProField = new TextField();
                proIdBox.getChildren().addAll(new Label("Enter product Id: "),idProField);
                HBox startDateBox = new HBox(20); TextField startDateField = new TextField();
                startDateBox.getChildren().addAll(new Label("Enter starting Date: "),startDateField);
                HBox endDateBox = new HBox(20); TextField endDateField = new TextField();
                endDateBox.getChildren().addAll(new Label("Enter ending Date: "),endDateField);
                HBox percentBox = new HBox(20); TextField percentField = new TextField();
                percentBox.getChildren().addAll(new Label("Enter percentage: "),percentField);
                Label errorAdd = new Label();
                Button createButton = new Button("Create"); createButton.setOnAction(event -> {
                    addOff(idField.getText(),startDateField.getText(),endDateField.getText(),percentField.getText(),errorAdd);
                });
                Button backButtonNew = new Button("Back"); backButtonNew.setOnAction(event -> {
                    try {
                        this.parentMenu.start(primaryStage);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
                addLayout.getChildren().addAll(proIdBox,startDateBox,endDateBox,percentBox,createButton,errorAdd,backButtonNew);

                for (Sale sale : controller.getCurrentAccount().getOffList()) {
                    infoLabel.setText(infoLabel.getText() + "\n" + sale.getOffId() + "      " + sale.getDiscountPercentage() +
                            "       " + sale.getStartingDate() + "        " + sale.getEndingdate());
                }

                idBox.getChildren().addAll(new Label("Enter product id: "),idField);
                Button editButton = new Button("Edit"); editButton.setOnAction(event -> {

                });
                Button addButton = new Button("Add"); addButton.setOnAction(event -> {
                    primaryStage.setScene(addScene);
                });
                Button backButton = new Button("Back"); backButton.setOnAction(event -> {
                    try {
                        this.parentMenu.start(primaryStage);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });

                layout.getChildren().addAll(infoLabel,idBox,editButton,addButton,backButton);
                primaryStage.setScene(scene);
                primaryStage.show();
            }
        };
    } //graphic is needed for products

    private void addOff(String id,String start,String end,String percent,Label errorLabel) {
        String regex;
        Matcher matcher;
        ArrayList<Product> products = new ArrayList<>();
        Date startingDate;
        Date endingDate;
        int discountPercentage;
        if (controller.getProductById(id) == null) {
            errorLabel.setText("invalid id");
        } else {
            if (controller.getProductById(id).isInSale()) {
                errorLabel.setText("Product is already in a sale");
            } else {
                controller.putProductInSale(id);
                products.add(controller.getProductById(id));
            }
        }
        if (!(start).matches(regex = "(\\d\\d\\d\\d)-(\\d\\d)-(\\d\\d)")) {
            errorLabel.setText("invalid format");
        }
        (matcher = getMatcher(regex, start)).find();
        startingDate = new Date(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2)), Integer.parseInt(matcher.group(3)));
        if (!(end).matches(regex = "(\\d\\d\\d\\d)-(\\d\\d)-(\\d\\d)")) {
            errorLabel.setText("invalid format");
        }
        (matcher = getMatcher(regex, end)).find();
        endingDate = new Date(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2)), Integer.parseInt(matcher.group(3)));
        discountPercentage = Integer.parseInt(percent);
        if (discountPercentage <= 0 || discountPercentage >= 100) {
            errorLabel.setText("Enter a valid number between 0 and 100!");
            discountPercentage = Integer.parseInt(percent);
        }
        String[] inputs = new String[1];
        inputs[0] = generateId();
        controller.createOff(inputs[0], products, startingDate, endingDate, discountPercentage);
        new SellerRequest(controller. getCurrentAccount(), generateId(), inputs, "add sale");
        errorLabel.setText("Sale created");
    }

    private void viewOff() {
        String command;
        String regex;
        Matcher matcher;
        while (!(command = scanner.nextLine()).equalsIgnoreCase("back")) {
            if (command.matches(regex = "view off (\\S+)")) {
                (matcher = getMatcher(regex, command)).find();
                String id = matcher.group(1);
                if (controller.getSaleById(id) != null) {
                    System.out.println(controller.getSaleById(id).toString());
                } else {
                    System.out.println("invalid id");
                }
            } else if (command.equals("help")){
                System.out.println("view [offId]" + "\n"
                        + "help" + "\n"
                        + "back");
            } else {
                System.out.println("invalid command");
            }
        }
    }

    private void editOff() {
        String command;
        String regex;
        Matcher matcher;
        Sale sale;
        while (!(command = scanner.nextLine()).equalsIgnoreCase("back")) {
            if (command.matches(regex = "edit (\\S+)")) {
                (matcher = getMatcher(regex, command)).find();
                sale = controller.getSaleById(matcher.group(1));
                if (sale != null) {
                    System.out.println("Enter a field: \n" +
                            "1. products \n" +
                            "2. starting Date \n" +
                            "3. ending Date \n" +
                            "4. discount percentage"
                    );
                    switch (Integer.parseInt(scanner.nextLine())) {
                        case 1:
                            editSaleProducts(sale);
                            break;
                        case 2:
                            editStartingDate(sale);
                            break;
                        case 3:
                            editEndingDate(sale);
                            break;
                        case 4:
                            editDiscountPercentage(sale);
                    }
                } else {
                    System.out.println("invalid id");
                }
            } else if (command.equals("help")){
                System.out.println("edit [offId]" + "\n"
                        + "help" + "\n"
                        + "back");

            } else {
                System.out.println("invalid command");
            }
        }
    }

    private void editDiscountPercentage(Sale sale) {
        System.out.println("Current discount percentage is :" + sale.getDiscountPercentage() + "%");
        System.out.println("Enter new discount percentage:");
        int discountPercentage = Integer.parseInt(scanner.nextLine());
        while (discountPercentage <= 0 || discountPercentage >= 100) {
            System.out.println("Enter a valid number between 0 and 100!");
            discountPercentage = Integer.parseInt(scanner.nextLine());
        }
        String[] inputs = new String[2];
        inputs[0] = String.valueOf(discountPercentage);
        inputs[1] = sale.getOffId();
        new SellerRequest(controller.getCurrentAccount(), generateId(), inputs, "edit discount percentage of sale");
        sale.setSaleStatus(SaleStatus.UNDER_REFORMATION);
        System.out.println("Request sent");
    }

    private void editEndingDate(Sale sale) {
        Matcher matcher;
        String regex;
        System.out.println("Current ending date is: " + sale.getStartingDate());
        System.out.println("Enter new ending date in format YYYY-MM-DD:");
        String date = scanner.nextLine();
        while (!date.matches(regex = "(\\d\\d\\d\\d)-(\\d\\d)-(\\d\\d)")) {
            System.out.println("invalid format");
            date = scanner.nextLine();
        }
        (matcher = getMatcher(regex, date)).find();
        String[] inputs = new String[4];
        inputs[0] = matcher.group(1);
        inputs[1] = matcher.group(2);
        inputs[2] = matcher.group(3);
        inputs[3] = sale.getOffId();
        new SellerRequest(controller.getCurrentAccount(), generateId(), inputs, "edit ending date of sale");
        sale.setSaleStatus(SaleStatus.UNDER_REFORMATION);
        System.out.println("Request sent");
    }

    private void editStartingDate(Sale sale) {
        Matcher matcher;
        String regex;
        System.out.println("Current Starting date is: " + sale.getStartingDate());
        System.out.println("Enter new starting date in format YYYY-MM-DD:");
        String date = scanner.nextLine();
        while (!date.matches(regex = "(\\d\\d\\d\\d)-(\\d\\d)-(\\d\\d)")) {
            System.out.println("invalid format");
            date = scanner.nextLine();
        }
        (matcher = getMatcher(regex, date)).find();
        String[] inputs = new String[4];
        inputs[0] = matcher.group(1);
        inputs[1] = matcher.group(2);
        inputs[2] = matcher.group(3);
        inputs[3] = sale.getOffId();
        new SellerRequest(controller.getCurrentAccount(), generateId(), inputs, "edit starting date of sale");
        sale.setSaleStatus(SaleStatus.UNDER_REFORMATION);
        System.out.println("Request sent");
    }

    private void editSaleProducts(Sale sale) {
        System.out.println("Current sale products:");
        for (Product product : sale.getProducts()) {
            System.out.println(product.getName() + " : " + product.getProductId());
        }
        System.out.println("1. add product\n" +
                "2. remove product");
        switch (scanner.nextLine()) {
            case "1":
                System.out.println("Enter product's id:");
                String id = scanner.nextLine();
                if (controller.getProductById(id) == null) {
                    System.out.println("invalid id");
                } else {
                    String[] inputs = new String[2];
                    inputs[0] = id;
                    inputs[1] = sale.getOffId();
                    new SellerRequest(controller.getCurrentAccount(), generateId(),inputs, "add product to sale");
                    sale.setSaleStatus(SaleStatus.UNDER_REFORMATION);
                }
                break;
            case "2":
                System.out.println("Enter product's id:");
                id = scanner.nextLine();
                if (controller.getProductById(id) == null) {
                    System.out.println("invalid id");
                } else {
                    String[] inputs = new String[2];
                    inputs[0] = id;
                    inputs[1] = sale.getOffId();
                    new SellerRequest(controller.getCurrentAccount(), generateId(), inputs, "remove product from sale");
                    sale.setSaleStatus(SaleStatus.UNDER_REFORMATION);
                    System.out.println("Request sent");
                }
        }
    }

    private Menu showCategories() {
        return new Menu("show categories", this) {
            @Override
            public void start(Stage primaryStage) throws Exception {
                VBox layout = new VBox(20);
                Scene scene = new Scene(layout,200,200);
                Label categoryLabel = new Label();
                for (Category category : controller.getCategories()) {
                    categoryLabel.setText(categoryLabel.getText() + "\n" + category.getName());
                }
                Button backButton = new Button("Back");
                backButton.setOnAction(event -> {
                    try {
                        this.parentMenu.start(primaryStage);
                        primaryStage.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
                layout.getChildren().addAll(categoryLabel,backButton);

                primaryStage.setScene(scene);
                primaryStage.show();
            }

            @Override
            public void show() {

                System.out.println("1. back");
            }

            @Override
            public void execute() {
                if (scanner.nextLine().equals("1")) {
                    parentMenu.show();
                    parentMenu.execute();
                }
            }
        };
    }

    private Menu removeProduct() {
        return new Menu("remove product", this) {
            @Override
            public void start(Stage primaryStage) throws Exception {
                VBox layout = new VBox(20);
                Scene scene = new Scene(layout,200,200);
                HBox layout1 = new HBox(20);
                Label message = new Label();
                Label label = new Label("Enter id of the product: ");
                TextField textField = new TextField();
                layout1.getChildren().addAll(label, textField);
                Button back = new Button("Back");
                back.setOnAction(event -> {
                    try {
                        this.parentMenu.start(new Stage());
                        primaryStage.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
                Button confirm = new Button("Confirm");
                confirm.setOnAction(event -> {
                    if (controller.getProductById(textField.getText()) == null) {
                        message.setText("ID is not valid");
                    } else {
                        controller.removeProduct(textField.getText());
                        message.setText("Done!");
                    }
                });
                HBox layout2 = new HBox(20);
                layout2.getChildren().addAll(confirm, back);
                layout.getChildren().addAll(layout1, layout2, message);
                primaryStage.setScene(scene);
                primaryStage.show();
            }

            @Override
            public void execute() {
                String command;
                String regex;
                Matcher matcher;
                while (!(command = scanner.nextLine()).equalsIgnoreCase("back")) {
                    if (command.matches(regex = "remove product (\\S+)")) {
                        (matcher = getMatcher(regex, command)).find();
                        String id = matcher.group(1);
                        if (controller.getProductById(id) != null) {
                             controller.removeProduct(id);
                            System.out.println("product removed successfully");
                        } else {
                            System.out.println("invalid id");
                        }
                    } else if (command.equals("help")){
                        System.out.println("remove product [productId]" + "\n"
                                + "help" + "\n"
                                + "back");
                    } else {
                        System.out.println("invalid command");
                    }
                }
                parentMenu.show();
                parentMenu.execute();
            }
        };
    } //graphic is needed for products

    private Menu addProduct() {
        return new Menu("add product", this) {
            @Override
            public void start(Stage primaryStage) throws Exception {
                Account account = controller.getCurrentAccount();
                VBox layout = new VBox(20);
                Scene scene = new Scene(layout,200,200);
                HBox nameBox = new HBox(20,new Label("Enter name of product"));
                TextField nameField = new TextField(); nameBox.getChildren().add(nameField);
                HBox companyBox = new HBox(20,new Label("Enter brand or company of product"));
                TextField companyField = new TextField(); companyBox.getChildren().add(companyField);
                HBox priceBox = new HBox(20,new Label("Enter price of the product"));
                TextField priceField = new TextField(); priceBox.getChildren().add(priceField);
                HBox amountBox = new HBox(20,new Label("Enter amount or number of the product"));
                TextField amountField = new TextField(); amountBox.getChildren().add(amountField);
                HBox descripBox = new HBox(20,new Label("Enter description of product"));
                TextField descripField = new TextField(); descripBox.getChildren().add(descripField);
                CheckBox available = new CheckBox("Is the product available?");
                HBox categoryBox = new HBox(20,new Label("Enter the category of the product"));
                TextField categoryField = new TextField(); categoryBox.getChildren().add(categoryField);
                String[] input = new String[10];
                Button confirmButton = new Button("Confirm");
                Button backButton = new Button("Back");
                Label errorLabel = new Label();
                confirmButton.setOnAction(event -> {
                    if (nameField.getText().isEmpty() || companyField.getText().isEmpty() || priceField.getText().isEmpty()
                    || amountField.getText().isEmpty() || descripField.getText().isEmpty() || categoryField.getText().isEmpty()) {
                        errorLabel.setText("complete all fields");
                    }
                    else {
                        input[0] = nameField.getText();
                        input[1] = companyField.getText();
                        input[2] = priceField.getText();
                        input[3] = amountField.getText();
                        input[4] = descripField.getText();
                        if (available.isSelected())
                            input[5] = "1";
                        else
                            input[5] = "2";
                        input[6] = generateId();
                        input[7] = categoryField.getText();
                        controller.createProduct(input, account);
                        new SellerRequest(account, generateId(), input, "add product");
                        try {
                            this.parentMenu.start(primaryStage);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                backButton.setOnAction(event -> {
                    try {
                        this.parentMenu.start(primaryStage);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });

                layout.getChildren().addAll(nameBox,companyBox,priceBox,amountBox,descripBox,available,categoryBox,errorLabel,confirmButton,backButton);
                primaryStage.setScene(scene);
                primaryStage.show();
            }
        };
    }

    private Menu getManageProducts() {
        return new Menu("manage products", this) {
            @Override
            public void start(Stage primaryStage) throws Exception {
                VBox mainLayout = new VBox(20);
                VBox mainLayout1 = new VBox(20);
                VBox mainLayout2 = new VBox(20);
                VBox mainLayout3 = new VBox(20);
                Scene mainScene = new Scene(mainLayout,200,200);
                Scene scene1 = new Scene(mainLayout1, 200, 200);
                Scene scene2 = new Scene(mainLayout2, 200, 200);
                Scene scene3 = new Scene(mainLayout3, 200, 200);
                Label productsLabel = new Label();
                for (Product product : controller.getAvailableProducts()) {
                    productsLabel.setText(productsLabel.getText() + "\n" + product.getName() + " " + product.getProductId());
                }
                HBox layout1 = new HBox(20);
                HBox layout2 = new HBox(20);
                Button viewProduct = new Button("View Product");
                Button viewBuyers = new Button("View Buyers");
                Button editProduct = new Button("Edit Product");
                Button back = new Button("Back");
                back.setOnAction(event -> {
                    try {
                        this.parentMenu.start(new Stage());
                        primaryStage.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
                layout1.getChildren().addAll(viewProduct, viewBuyers);
                layout2.getChildren().addAll(editProduct, back);
                mainLayout.getChildren().addAll(productsLabel, layout1, layout2);
                viewProduct.setOnAction(event -> {
                    HBox subLayout1 = new HBox(20);
                    Label message = new Label();
                    Label label = new Label("Enter id of the product: ");
                    TextField textField = new TextField();
                    subLayout1.getChildren().addAll(label, textField);
                    Button backButton = new Button("Back");
                    backButton.setOnAction(event1 -> {
                        try {
                            primaryStage.setScene(mainScene);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
                    Button confirm = new Button("Confirm");
                    confirm.setOnAction(event1 -> {
                        if (controller.getProductById(textField.getText()) == null) {
                            message.setText("ID is not valid");
                        } else {
                            message.setText(controller.viewProduct(textField.getText()));
                        }
                    });
                    HBox subLayout2 = new HBox(20);
                    subLayout2.getChildren().addAll(confirm, backButton);
                    mainLayout1.getChildren().addAll(subLayout1, subLayout2, message);
                    primaryStage.setScene(scene1);
                });
                viewBuyers.setOnAction(event -> {
                    HBox subLayout1 = new HBox(20);
                    Label message = new Label();
                    Label label = new Label("Enter id of the product: ");
                    TextField textField = new TextField();
                    subLayout1.getChildren().addAll(label, textField);
                    Button backButton = new Button("Back");
                    backButton.setOnAction(event1 -> {
                        try {
                            primaryStage.setScene(mainScene);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
                    Button confirm = new Button("Confirm");
                    confirm.setOnAction(event1 -> {
                        if (controller.getProductById(textField.getText()) == null) {
                            message.setText("ID is not valid");
                        } else {
                            message.setText(controller.viewBuyers(textField.getText()).toString());
                        }
                    });
                    HBox subLayout2 = new HBox(20);
                    subLayout2.getChildren().addAll(confirm, backButton);
                    mainLayout2.getChildren().addAll(subLayout1, subLayout2, message);
                    primaryStage.setScene(scene2);
                });
                editProduct.setOnAction(event -> {
                    HBox idBox = new HBox(20, new Label("Enter id of the product:"));
                    TextField idField  = new TextField(); idBox.getChildren().add(idBox);
                    HBox nameBox = new HBox(20,new Label("Enter name of product"));
                    TextField nameField = new TextField(); nameBox.getChildren().add(nameField);
                    HBox companyBox = new HBox(20,new Label("Enter brand or company of product"));
                    TextField companyField = new TextField(); companyBox.getChildren().add(companyField);
                    HBox priceBox = new HBox(20,new Label("Enter price of the product"));
                    TextField priceField = new TextField(); priceBox.getChildren().add(priceField);
                    HBox amountBox = new HBox(20,new Label("Enter amount or number of the product"));
                    TextField amountField = new TextField(); amountBox.getChildren().add(amountField);
                    HBox descripBox = new HBox(20,new Label("Enter description of product"));
                    TextField descripField = new TextField(); descripBox.getChildren().add(descripField);
                    CheckBox available = new CheckBox("Is the product available?");
                    HBox categoryBox = new HBox(20,new Label("Enter the category of the product"));
                    TextField categoryField = new TextField(); categoryBox.getChildren().add(categoryField);
                    HBox buttonBox = new HBox(20);
                    Button backButton = new Button("Back");
                    Button confirm = new Button("confirm");
                    Label message = new Label();
                    buttonBox.getChildren().addAll(backButton, confirm);
                    backButton.setOnAction(event1 -> {
                        try {
                            primaryStage.setScene(mainScene);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
                    confirm.setOnAction(event1 -> {
                        Product product = controller.getAvailableProductById(idField.getText());
                        if (product == null) {
                            message.setText("id not valid!");
                        } else {
                            if (!categoryField.getText().isEmpty()) {
                                product.setCategory(controller.getCategoryByName(categoryField.getText()));
                                message.setText("Done!");
                            }
                            if (!nameField.getText().isEmpty()) {
                                product.setName(nameField.getText());
                                message.setText("Done!");
                            }
                            if (!priceField.getText().isEmpty()) {
                                product.setValue(Long.parseLong(priceField.getText()));
                                message.setText("Done!");
                            }
                            if (!companyField.getText().isEmpty()) {
                                product.setBrandOrCompany(companyField.getText());
                                message.setText("Done!");
                            }
                            if (!descripField.getText().isEmpty()) {
                                product.setDescription(descripField.getText());
                                message.setText("Done!");
                            }
                            if (!amountField.getText().isEmpty()) {
                                product.setAmount(Integer.parseInt(amountField.getText()));
                                message.setText("Done!");
                            }
                            if (available.isSelected()) {
                                product.setAvailable(true);
                                message.setText("Done");
                            }
                        }
                    });
                    mainLayout3.getChildren().addAll(idBox, nameBox, companyBox, priceBox, amountBox, descripBox, available, categoryBox, buttonBox, message);
                });
                primaryStage.setScene(mainScene);
                primaryStage.show();
            }

            @Override
            public void show() {

                System.out.println("1. view product" + "\n" +
                        "2. view buyers \n" +
                        "3. edit product \n" +
                        "4. back");
            }

            @Override
            public void execute() {
                switch (Integer.parseInt(scanner.nextLine())) {
                    case 1:
                        viewProduct();
                        this.show();
                        this.execute();
                        break;
                    case 2:
                        viewBuyers();
                        this.show();
                        this.execute();
                        break;
                    case 3:
                        editProduct();
                        this.show();
                        this.execute();
                    case 4:
                        parentMenu.show();
                        parentMenu.execute();
                }
            }
        };
    } //graphic is needed for products

    private void editProduct() {
        String command;
        String regex;
        Matcher matcher;
        Product product;
        Account account = controller.getCurrentAccount();
        String[] inputs = new String[2];
        while (!(command = scanner.nextLine()).equalsIgnoreCase("back")) {
            if (command.matches(regex = "edit (\\S+)")) {
                (matcher = getMatcher(regex, command)).find();
                product =  controller.getProductById(matcher.group(1));
                if (product != null) {
                    System.out.println("Enter a field: \n" +
                            "1. name \n" +
                            "2. brand or company \n" +
                            "3. description \n" +
                            "4. availability\n" +
                            "5. value\n" +
                            "6. amount\n" +
                            "7. category"
                    );
                    inputs[0] = product.getProductId();
                    switch (Integer.parseInt(scanner.nextLine())) {
                        case 1:
                            System.out.println("Enter new name:");
                            inputs[1] = scanner.nextLine();
                            new SellerRequest(account, generateId(), inputs, "edit name of product");
                            product.setProductStatus(ProductStatus.UNDER_REFORMATION);
                            System.out.println("Request sent");
                            break;
                        case 2:
                            System.out.println("Enter new brand or company:");
                            inputs[1] = scanner.nextLine();
                            new SellerRequest(account, generateId(), inputs, "edit company of product");
                            product.setProductStatus(ProductStatus.UNDER_REFORMATION);
                            System.out.println("Request sent");
                            break;
                        case 3:
                            System.out.println("Enter new description:");
                            inputs[1] = scanner.nextLine();
                            new SellerRequest(account, generateId(), inputs, "edit description of product");
                            product.setProductStatus(ProductStatus.UNDER_REFORMATION);
                            System.out.println("Request sent");
                            break;
                        case 4:
                            System.out.println("1. enable product\n" +
                                    "2.disable product");
                            inputs[1] = scanner.nextLine();
                            new SellerRequest(account, generateId(), inputs, "edit availability of product");
                            product.setProductStatus(ProductStatus.UNDER_REFORMATION);
                            System.out.println("Request sent");
                            break;
                        case 5:
                            System.out.println("Enter new price:");
                            inputs[1] = scanner.nextLine();
                            new SellerRequest(account, generateId(), inputs, "edit price of product");
                            product.setProductStatus(ProductStatus.UNDER_REFORMATION);
                            System.out.println("Request sent");
                            break;
                        case 6:
                            System.out.println("Enter new amount:");
                            inputs[1] = scanner.nextLine();
                            new SellerRequest(account, generateId(), inputs, "edit amount of product");
                            product.setProductStatus(ProductStatus.UNDER_REFORMATION);
                            System.out.println("Request sent");
                            break;
                        case 7:
                            System.out.println("Enter new category:");
                            command = scanner.nextLine();
                            if (controller.getCategoryByName(command) != null) {
                                inputs[1] = command;
                                new SellerRequest(account, generateId(), inputs, "edit category of product");
                                product.setProductStatus(ProductStatus.UNDER_REFORMATION);
                                System.out.println("Request sent");
                            } else {
                                System.out.println("Category not found");
                            }
                    }
                } else {
                    System.out.println("invalid id");
                }
            } else if (command.equals("help")){
                System.out.println("edit [productId]" + "\n"
                        + "help" + "\n"
                        + "back");

            } else {
                System.out.println("invalid command");
            }
        }
    }

    private void viewBuyers() {
        String command;
        String regex;
        Matcher matcher;
        while (!(command = scanner.nextLine()).equalsIgnoreCase("back")) {
            if (command.matches(regex = "view buyers (\\S+)")) {
                (matcher = getMatcher(regex, command)).find();
                String id = matcher.group(1);
                if (controller.getProductById(id) != null) {
                    for (String buyer : controller.viewBuyers(id)) {
                        System.out.println(buyer);
                    }
                } else {
                    System.out.println("invalid id");
                }
            } else if (command.equals("help")){
                System.out.println("view buyers [productId]" + "\n"
                        + "help" + "\n"
                        + "back");
            } else {
                System.out.println("invalid command");
            }
        }
    }

    private void viewProduct() {
        String command;
        String regex;
        Matcher matcher;
        while (!(command = scanner.nextLine()).equalsIgnoreCase("back")) {
            if (command.matches(regex = "view (\\S+)")) {
                (matcher = getMatcher(regex, command)).find();
                String id = matcher.group(1);
                if (controller.getProductById(id) != null) {
                    System.out.println(controller.viewProduct(id));
                } else {
                    System.out.println("invalid id");
                }
            } else if (command.equals("help")){
                System.out.println("view [productId]" + "\n"
                        + "help" + "\n"
                        + "back");

            } else {
                System.out.println("invalid command");
            }
        }
    }

    private Menu getSalesHistory() {
        return new Menu("view sales history", this) {
            @Override
            public void start(Stage primaryStage) throws Exception {
                VBox layout = new VBox(20);
                Scene scene = new Scene(layout,200,200);
                Label history = new Label(controller.getCurrentAccount().getSellingRecords().toString());
                Button backButton = new Button("Back");
                backButton.setOnAction(event -> {
                    try {
                        this.parentMenu.start(primaryStage);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
                layout.getChildren().addAll(history,backButton);
                primaryStage.setScene(scene);
                primaryStage.show();
            }

        };
    }

    private void editPersonalInfo(String command,String input) {
        Account currentAccount = controller.getCurrentAccount();
                switch (command) {
                    case "password":
                        currentAccount.setPassword(input);
                        break;
                    case "name":
                        currentAccount.setName(input);
                        break;
                    case "lastName":
                        currentAccount.setLastName(input);
                        break;
                    case "email":
                        currentAccount.setEmail(input);
                        break;
                    case "address":
                        currentAccount.setAddress(input);
                        break;
                    case "phoneNumber":
                        currentAccount.setPhoneNumber(input);
                        break;
                    case "company":
                        currentAccount.setCompanyName(input);
                        break;
                    case "balance":
                        currentAccount.setBalance(Long.parseLong(input));
                        break;
                }


    }

    private Menu getCompanyInfo() {
        return new Menu("view company information", this) {
            @Override
            public void start(Stage primaryStage) throws Exception {
                VBox layout = new VBox(20);
                Scene scene = new Scene(layout,200,200);
                Label companyInfo = new Label(controller.getCurrentAccount().getCompanyName() + "\n");
                Button backButton = new Button("Back");
                backButton.setOnAction(event -> {
                    try {
                        this.parentMenu.start(primaryStage);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });

                layout.getChildren().addAll(companyInfo,backButton);
                primaryStage.setScene(scene);
                primaryStage.show();
            }
        };
    }

    private Menu getViewPersonalInfo() {
        return new Menu("view personal info", this) {
            @Override
            public void start(Stage primaryStage) throws Exception {
                VBox layout = new VBox(20);
                VBox editLayout = new VBox(20);
                Scene editScene = new Scene(editLayout,200,200);
                Scene scene = new Scene(layout,200,200);

                HBox passBox = new HBox(20,new Label("password:"));PasswordField passwordField = new PasswordField();
                passBox.getChildren().addAll(passwordField);
                HBox nameBox = new HBox(20,new Label("name"));TextField nameField = new TextField();
                nameBox.getChildren().addAll(nameField);
                HBox lastBox = new HBox(20,new Label("last name"));TextField lastField = new TextField();
                lastBox.getChildren().addAll(lastField);
                HBox emailBox = new HBox(20,new Label("E-mail"));TextField emailField = new TextField();
                emailBox.getChildren().addAll(emailField);
                HBox addressBox = new HBox(20,new Label("address"));TextField addressField = new TextField();
                addressBox.getChildren().addAll(addressField);
                HBox numberBox = new HBox(20,new Label("phone number"));TextField numberField = new TextField();
                numberBox.getChildren().addAll(numberField);
                HBox companyBox = new HBox(20,new Label("company"));TextField companyField = new TextField();
                companyBox.getChildren().addAll(companyField);
                Button updateButton = new Button("Update");Button backButton = new Button("Back");
                updateButton.setOnAction(event -> {
                    if (!passwordField.getText().isEmpty()) {
                        editPersonalInfo("password",passwordField.getText());
                        try {
                            this.parentMenu.start(primaryStage);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    if (!nameField.getText().isEmpty()) {
                        editPersonalInfo("name",nameField.getText());
                        try {
                            this.parentMenu.start(primaryStage);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    if (!lastField.getText().isEmpty()) {
                        editPersonalInfo("lastName",lastField.getText());
                        try {
                            this.parentMenu.start(primaryStage);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    if (!emailField.getText().isEmpty()) {
                        editPersonalInfo("email",emailField.getText());
                        try {
                            this.parentMenu.start(primaryStage);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    if (!addressField.getText().isEmpty()) {
                        editPersonalInfo("address",addressField.getText());
                        try {
                            this.parentMenu.start(primaryStage);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    if (!numberField.getText().isEmpty()) {
                        editPersonalInfo("phoneNumber",numberField.getText());
                        try {
                            this.parentMenu.start(primaryStage);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    if (!companyField.getText().isEmpty()) {
                        editPersonalInfo("company",companyField.getText());
                        try {
                            this.parentMenu.start(primaryStage);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });backButton.setOnAction(event -> {
                    try {
                        this.parentMenu.start(primaryStage);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
                editLayout.getChildren().addAll(passBox,nameBox,lastBox,emailBox,addressBox,numberBox,updateButton,backButton);

                Account currentAccount = controller.getCurrentAccount();
                Label info = new Label("username: " + currentAccount.getUsername() + "\n"
                        + "password: " + currentAccount.getPassword() + "\n"
                        + "name: " + currentAccount.getName() + "\n"
                        + "lastName: " + currentAccount.getLastName() + "\n"
                        + "email: " + currentAccount.getEmail() + "\n"
                        + "address: " + currentAccount.getAddress() + "\n"
                        + "phoneNumber: " + currentAccount.getPhoneNumber() + "\n"
                        + "role: " + currentAccount.getRole());
                Button editButton = new Button("Edit Field");
                editButton.setOnAction(event -> {
                    primaryStage.setScene(editScene);
                });
                Button backButton2 = new Button("Back");backButton2.setOnAction(event -> {
                    try {
                        this.parentMenu.start(new Stage());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });

                layout.getChildren().addAll(info,editButton,backButton);
                primaryStage.setScene(scene);
                primaryStage.show();
            }

        };
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        VBox layout = new VBox(20);
        Scene scene = new Scene(layout,200,200);

        Button loginButton = new Button("Login Button");loginButton.setOnAction(event -> execute(1));
        Button personalButton = new Button("Personal Info");personalButton.setOnAction(event -> execute(2));
        Button companyButton = new Button("Company Info");companyButton.setOnAction(event -> execute(3));
        Button salesHistoryButton = new Button("Sales History");salesHistoryButton.setOnAction(event -> execute(4));
        Button addButton = new Button("Add Product");addButton.setOnAction(event -> execute(6));
        Button removeButton = new Button("Remove Product");removeButton.setOnAction(event -> execute(7));
        Button manageProductButton = new Button("Manage Products");manageProductButton.setOnAction(event -> execute(5));
        Button balanceButton = new Button("View Balance");balanceButton.setOnAction(event -> execute(10));
        Button offButton = new Button("View Off");offButton.setOnAction(event -> execute(9));
        Button categoryButton = new Button("Show Categories");categoryButton.setOnAction(event -> execute(8));
        Button backButton = new Button("Back");backButton.setOnAction(event -> execute(11));

        layout.getChildren().addAll(loginButton,personalButton,companyButton,salesHistoryButton,addButton,removeButton
        ,manageProductButton,balanceButton,offButton,categoryButton,backButton);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
