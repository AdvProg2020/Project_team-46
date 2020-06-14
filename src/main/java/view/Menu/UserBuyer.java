package view.Menu;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Account;
import model.Discount;
import model.Product;

import java.util.HashMap;
import java.util.Objects;
import java.util.regex.Matcher;

public class UserBuyer extends Menu{

    private final Menu currentMenu;

    public UserBuyer(String name, Menu parentMenu) {
        super(name, parentMenu);
        HashMap<Integer, Menu> submenus = new HashMap<>();
        submenus.put(1, new LoginMenu(parentMenu));
        submenus.put(2, getViewPersonalInfo());
        submenus.put(3, getViewOrders());
        submenus.put(4, getViewBalance());
        submenus.put(5, getViewDiscountCodes());
        submenus.put(6, getViewCart());
        this.setSubmenus(submenus);
        currentMenu = this;
    }

    @Override
    public void execute() {
        String command = scanner.nextLine();
        switch (command) {
            case "1":
                submenus.get(1).show();
                submenus.get(1).execute();
                break;
            case "2":
                getViewPersonalInfo().show();
                getViewPersonalInfo().execute();
                break;
            case "3":
                getViewOrders().show();
                getViewOrders().execute();
                break;
            case "4":
                getViewBalance().show();
                getViewBalance().execute();
                break;
            case "5":
                getViewDiscountCodes().show();
                getViewDiscountCodes().execute();
                break;
            case "6":
                getViewCart().show();
                getViewCart().execute();
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

    private void manageOrders() {
        String command;
        String regex;
        Matcher matcher;
        while (!(command = scanner.nextLine()).equalsIgnoreCase("back")) {
            if (command.matches(regex = "show order (\\S+)")) {
                (matcher = getMatcher(regex, command)).find();
                String field = matcher.group(1);
                System.out.println(controller.showOrder(field));
            }
            else if (command.matches(regex = "rate (\\S+) (\\d)")) {
                (matcher = getMatcher(regex, command)).find();
                String field1 = matcher.group(1);
                String field2 = matcher.group(2);
                controller.rateProduct(field1,Integer.parseInt(field2));
            }
        }
    }

    private Menu manageCart(Menu parentMenu) {
        String command;
        String regex;
        Matcher matcher;
        while (!(command = scanner.nextLine()).equalsIgnoreCase("back")) {
            if (command.matches("show products")) {
                for (Product product : controller.viewCart().keySet()) {
                    System.out.println(product.getName() +"   "+ product.getProductId() +"   "+
                            controller.viewCart().get(product));
                }
            }
            else if (command.matches(regex = "view (\\S+)")) {
                (matcher = getMatcher(regex, command)).find();
                String field1 = matcher.group(1);
                GoodMenu goodMenu = new GoodMenu(parentMenu,null);
                goodMenu.setGoodId(field1);
                goodMenu.show();
                goodMenu.execute();
            }
            else if (command.matches(regex = "increase (\\S+)")) {
                (matcher = getMatcher(regex, command)).find();
                String field = matcher.group(1);
                controller.increaseProduct(field);
            }
            else if (command.matches(regex = "decrease (\\S+)")) {
                (matcher = getMatcher(regex, command)).find();
                String field = matcher.group(1);
                controller.decreaseProduct(field);
            }
            else if (command.matches("show total price")) {
                System.out.println(controller.showTotalPrice());
            }
            else if (command.matches("purchase")) {
                return new Menu("purchase Menu",parentMenu) {
                    @Override
                    public void show() {
                        System.out.println("Purchase Menu:");
                        System.out.println("1.Next(receiving info)\n" +
                                "2.back");
                    }

                    @Override
                    public void execute() {
                        switch (Integer.parseInt(scanner.nextLine())) {
                            case 1:
                                Menu menu = receiveInfo(this);
                                menu.show();
                                menu.execute();
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
        }
        return null;
    }

    private Menu receiveInfo(Menu parentMenu) {
        return new Menu("Receive Info Menu",parentMenu) {
            @Override
            public void show() {
                System.out.println("Purchase->Receive Info Menu:");
                System.out.println("1.Next(receiving Discount Code\n)" +
                        "2.back");
            }

            @Override
            public void execute() {
                System.out.println("Enter your Address:");
                String address = scanner.nextLine();
                System.out.println("Enter your phone Number:");
                String phoneNumber = scanner.nextLine();
                switch (Integer.parseInt(scanner.nextLine())) {
                    case 1:
                        Menu menu = discountConfirm(this,address,phoneNumber);
                        menu.show();
                        menu.execute();
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

    private Menu discountConfirm(Menu parentMenu,String address,String phoneNumber) {
        return new Menu("discountCode Confirmation",parentMenu) {
            @Override
            public void show() {
                System.out.println("Purchase->Receive Info->Discount Code Confirmation Menu:");
            }

            @Override
            public void execute() {
                System.out.println("Enter your discount code:(or type \"no discount code\" to continue)");
                String discount = scanner.nextLine();
                Discount discount1 = null;
                if (!discount.equals("no discount code")) {
                    if (controller.discountCodeConfirmation(discount)) {
                        System.out.println("Discount Code Confirmed.");
                        discount1 = controller.getDiscountByCode(discount);
                    }
                    else
                        System.out.println("Discount Code Denied.");
                }
                System.out.println("1.Next(payment)\n" +
                        "2.back");
                switch (Integer.parseInt(scanner.nextLine())) {
                    case 1:
                        Menu menu = payment(this,address,phoneNumber,discount1);
                        menu.show();
                        menu.execute();
                        break;
                    case 2:
                        this.parentMenu.show();
                        this.parentMenu.execute();
                        break;
                }
            }
        };
    }

    private Menu payment(Menu parentMenu,String address,String phoneNumber,Discount discount) {
        return new Menu("paymentMenu",parentMenu) {
            @Override
            public void show() {
                System.out.println("Purchase->Receive Info->Discount Code Confirmation->Payment Menu:");
                System.out.println("[Product]-----[Number]");
                for (Product product : controller.viewCart().keySet()) {
                    System.out.println(product.getName() + "-----" + controller.viewCart().get(product));
                }
                System.out.println("---------------------");
                System.out.println("Total Price:" + controller.showTotalPrice(discount));
                System.out.println("Are you sure to pay" + controller.showTotalPrice(discount) + "?");
                System.out.println("1.YES\n" +
                        "2.back");
            }

            @Override
            public void execute() {
                switch (Integer.parseInt(scanner.nextLine())) {
                    case 1:
                        if (controller.showTotalPrice() > controller.getCurrentAccount().getBalance()) {
                            System.out.println("Your balance is not enough!");
                        } else {
                            controller.purchase(discount);
                        }
                        currentMenu.show();
                        currentMenu.execute();
                        break;
                    case 2:
                        this.parentMenu.show();
                        this.parentMenu.execute();
                        break;
                }
            }
        };
    }

    private Menu getViewPersonalInfo() {
        return new Menu("view personal info",this) {

            @Override
            public void start(Stage primaryStage) throws Exception {
                VBox layout = new VBox(20);
                VBox editLayout = new VBox(20);
                Scene editScene = new Scene(editLayout,200,200);
                Scene scene = new Scene(layout,200,200);

                HBox passBox = new HBox(20,new Label("password:"));
                PasswordField passwordField = new PasswordField();
                passBox.getChildren().addAll(passwordField);
                HBox nameBox = new HBox(20,new Label("name"));
                TextField nameField = new TextField();
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

    private Menu getViewOrders() {
        return new Menu("view orders",this) {
            @Override
            public void start(Stage primaryStage) throws Exception {
                VBox layout = new VBox(20);
                Scene scene = new Scene(layout,200,200);
                Label orderLabel = new Label(controller.viewOrders().toString());
                Button backButton = new Button("Back");
                backButton.setOnAction(event -> {
                    try {
                        this.parentMenu.start(primaryStage);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
                layout.getChildren().addAll(orderLabel,backButton);
                primaryStage.setScene(scene);
                primaryStage.show();
            }

            @Override
            public void show() {
                System.out.println("View Orders Menu:");
                System.out.println("1.show order [orderId]/rate [productId] [1-5]");
                System.out.println();
            }

            @Override
            public void execute() {
                switch (Integer.parseInt(scanner.nextLine())) {
                    case 1:
                        manageOrders();
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
    } //needs to complete

    private Menu getViewBalance() {
        return new Menu("View Balance",this) {
            @Override
            public void start(Stage primaryStage) throws Exception {
                VBox layout = new VBox(20);
                Scene scene = new Scene(layout,200,200);
                Label balanceLabel = new Label(String.valueOf(controller.viewBalance()));
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
        };
    }

    private Menu getViewDiscountCodes() {
        return new Menu("View Discount Code:",this) {
            @Override
            public void start(Stage primaryStage) throws Exception {
                VBox layout = new VBox(20);
                Scene scene = new Scene(layout,200,200);
                Label discountLabel = new Label(controller.getCurrentAccount().getDiscountCodes().toString());
                Button backButton = new Button("Back");
                backButton.setOnAction(event -> {
                    try {
                        this.parentMenu.start(primaryStage);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
                layout.getChildren().addAll(discountLabel,backButton);
                primaryStage.setScene(scene);
                primaryStage.show();
            }
        };
    }

    private Menu getViewCart() {
        return new Menu("View Cart",this) {

            @Override
            public void show() {
                System.out.println("View Cart:");
                System.out.println("1.show products/view [productId]/increase [productId]/decrease [productId]" +
                        "show price/purchase\n" +
                        "2.back");
            }

            @Override
            public void execute() {
                switch (Integer.parseInt(scanner.nextLine())) {
                    case 1:
                        Menu menu = manageCart(this);
                        if (menu != null) {
                            Objects.requireNonNull(menu).show();
                            Objects.requireNonNull(menu).execute();
                        }
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
    } //needs to complete

    @Override
    public void start(Stage primaryStage) throws Exception {

    }
}
