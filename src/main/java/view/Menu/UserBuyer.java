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
import model.BuyingLog;
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
    public void execute(int command) throws Exception {
        switch (command) {
            case 1:
                submenus.get(1).start(new Stage());
                break;
            case 2:
                getViewPersonalInfo().start(new Stage());
                break;
            case 3:
                getViewOrders().start(new Stage());
                break;
            case 4:
                getViewBalance().start(new Stage());
                break;
            case 5:
                getViewDiscountCodes().start(new Stage());
                break;
            case 6:
                getViewCart().start(new Stage());
                break;
            case 7:
                this.parentMenu.start(new Stage());
                break;
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
                    public void start(Stage primaryStage) throws Exception {

                    }

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
    } //needs to complete

    private Menu receiveInfo(Menu parentMenu) {
        return new Menu("Receive Info Menu",parentMenu) {
            @Override
            public void start(Stage primaryStage) throws Exception {
                VBox layout = new VBox(20);
                Scene scene = new Scene(layout,200,200);

                Label infoLabel = new Label("Purchase --> Receive Info Menu:");
                HBox addressBox = new HBox(20);
                Label addressLabel = new Label("Enter Your Address: "); TextField addressField = new TextField();
                addressBox.getChildren().addAll(addressLabel,addressField);
                HBox phoneBox = new HBox(20);
                Label phoneLabel = new Label("Enter your phone Number: "); TextField phoneField = new TextField();
                phoneBox.getChildren().addAll(phoneLabel,phoneField);
                Button confirmButton = new Button("Confirm"); confirmButton.setOnAction(event -> {
                    Menu menu = discountConfirm(this,addressField.getText(),phoneField.getText());
                    try {
                        menu.start(primaryStage);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
                Button backButton = new Button("Back"); backButton.setOnAction(event -> {
                    try {
                        this.parentMenu.start(primaryStage);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
                layout.getChildren().addAll(infoLabel,addressBox,phoneBox,confirmButton,backButton);

                primaryStage.setScene(scene);
                primaryStage.show();
            }
        };
    }

    private Menu discountConfirm(Menu parentMenu,String address,String phoneNumber) {
        final Discount[] discount = {null};
        return new Menu("discountCode Confirmation",parentMenu) {
            @Override
            public void start(Stage primaryStage) throws Exception {
                VBox layout = new VBox(20);
                Scene scene = new Scene(layout,200,200);

                Label infoLabel = new Label("Purchase->Receive Info->Discount Code Confirmation Menu");
                HBox discountBox = new HBox(20); Label errorLabel = new Label();
                Label discountLabel = new Label("Enter your discount code: "); TextField discountField = new TextField();
                discountBox.getChildren().addAll(discountLabel,discountField);
                Button discountButton = new Button("Confirm Code"); discountButton.setOnAction(event -> {
                    if (!discountField.getText().isEmpty()) {
                        if (controller.discountCodeConfirmation(discountField.getText())) {
                            errorLabel.setText("Discount Code Confirmed.");
                            discount[0] = controller.getDiscountByCode(discountField.getText());
                        }
                        else
                            errorLabel.setText("Discount Code Denied.");
                    }
                    else {
                        errorLabel.setText("Enter a Code...");
                    }
                });
                Button nextButton = new Button("Next"); nextButton.setOnAction(event -> {
                    Menu menu = payment(this,address,phoneNumber,discount[0]);
                    try {
                        menu.start(primaryStage);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
                Button backButton = new Button("Back"); backButton.setOnAction(event -> {
                    try {
                        this.parentMenu.start(primaryStage);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
                layout.getChildren().addAll(infoLabel,discountBox,discountButton,errorLabel,nextButton,backButton);

                primaryStage.setScene(scene);
                primaryStage.show();
            }
        };
    }

    private Menu payment(Menu parentMenu,String address,String phoneNumber,Discount discount) {
        return new Menu("paymentMenu",parentMenu) {
            @Override
            public void start(Stage primaryStage) throws Exception {
                VBox layout = new VBox(20);
                Scene scene = new Scene(layout,200,200);

                Label infoLabel = new Label("Purchase->Receive Info->Discount Code Confirmation->Payment Menu\n" +
                        "[Product]-----------[Number]\n" +
                        "Total Price: " + controller.showTotalPrice(discount));
                Label errorLabel = new Label();
                Button acceptButton = new Button("Pay it"); acceptButton.setOnAction(event -> {
                    if (controller.showTotalPrice(discount) > controller.getCurrentAccount().getBalance()) {
                        errorLabel.setText("Your balance is not enough!");
                    }
                    else {
                        controller.purchase(discount);
                    }
                    try {
                        currentMenu.start(primaryStage);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
                Button backButton = new Button("Back"); backButton.setOnAction(event -> {
                    try {
                        this.parentMenu.start(primaryStage);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
                layout.getChildren().addAll(infoLabel,acceptButton,errorLabel,backButton);

                primaryStage.setScene(scene);
                primaryStage.show();
            }
        };
    }

    private Menu getViewOrders() {
        return new Menu("view orders",this) {
            @Override
            public void start(Stage primaryStage) throws Exception {
                VBox layout = new VBox(20); VBox detailBox = new VBox(20);
                Scene scene = new Scene(layout,200,200); Scene detailScene = new Scene(detailBox,200,200);
                for (BuyingLog order : controller.viewOrders()) {
                    HBox orderBox = new HBox(20);
                    Label orderLabel = new Label("Customer name: " + order.getCostumerName()
                            + "\nLog Id: " + order.getLogId() + "\nLog date: " + order.getDate());
                    Label productLabel = new Label();
                    for (Product product : order.getSellerPerProduct().keySet()) {
                        productLabel.setText(productLabel.getText() + "\nProduct name: " + product.getName() + "Product seller: " +
                                order.getSellerPerProduct().get(product));
                    }
                    Button detailButton = new Button("Details"); detailButton.setOnAction(event -> {
                        detailBox.getChildren().clear();
                        Label detailLabel = new Label("Customer name: " + order.getCostumerName() +
                                "\nLog Id: " + order.getLogId() + "\nLog date: " + order.getDate() +
                                "\nPaid amount: " + order.getPaidAmount() + "\nDelivery Status: " + order.getDeliveryStatus());
                        Button backButton = new Button("Back"); backButton.setOnAction(event1 -> {
                            primaryStage.setScene(scene);
                        });
                        detailBox.getChildren().addAll(detailLabel,backButton);
                        primaryStage.setScene(detailScene);
                    });
                    orderBox.getChildren().addAll(orderLabel,detailButton); layout.getChildren().addAll(orderBox);
                }
                Button backButton = new Button("Back"); backButton.setOnAction(event -> {
                    try {
                        this.parentMenu.start(primaryStage);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
                layout.getChildren().addAll(backButton);
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
            public void start(Stage primaryStage) throws Exception {

            }

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
        VBox layout = new VBox(20);
        Scene scene = new Scene(layout,200,200);

        Button loginButton = new Button("Login Menu"); loginButton.setOnAction(event -> {
            try {
                execute(1);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        Button infoButton = new Button("Personal Info"); infoButton.setOnAction(event -> {
            try {
                execute(2);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        Button orderButton = new Button("View Orders"); orderButton.setOnAction(event -> {
            try {
                execute(3);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        Button balanceButton = new Button("View Balance"); balanceButton.setOnAction(event -> {
            try {
                execute(4);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        Button discountButton = new Button("Discount Codes"); discountButton.setOnAction(event -> {
            try {
                execute(5);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        Button cartButton = new Button("View Cart"); cartButton.setOnAction(event -> {
            try {
                execute(6);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        Button backButton = new Button("Back"); backButton.setOnAction(event -> {
            try {
                execute(7);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        layout.getChildren().addAll(loginButton,infoButton,orderButton,balanceButton,discountButton,cartButton,backButton);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
