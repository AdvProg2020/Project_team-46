package view.Menu;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.regex.Matcher;

public class LoginMenu extends Menu {
    public LoginMenu(Menu parentMenu) {
        super("LoginMenu", parentMenu);
    }

    @Override
    public void show() {
        System.out.println("1. Register(create account [Role] [Username]) \n" +
                "2. Log in (login [Username])\n" +
                "3. Log out \n" +
                "4. Back");
    }

    @Override
    public void execute() {
        switch (scanner.nextLine()) {
            case "1":
                register();
                parentMenu.show();
                parentMenu.execute();
                break;
            case "2":
                logIn();
                parentMenu.show();
                parentMenu.execute();
                break;
            case "3":
                logOut();
                mainMenu.show();
                mainMenu.execute();
                break;
            case "4":
                parentMenu.show();
                parentMenu.execute();
                break;
            default:
                System.out.println("Enter a validate number");
                this.execute();
        }
    }

    public void register() {
        String command;
        String regex;
        Matcher matcher;
        while (true) {
            command = scanner.nextLine();
            if (command.matches(regex = "create account (customer|seller|manager) (.+)")) {
                (matcher = getMatcher(regex, command)).find();
                if (controller.getAccountByUsername(matcher.group(2)) != null) {
                    System.out.println("User is already taken");
                } else {
                    controller.createAccount(matcher.group(2), matcher.group(1));
                    System.out.println("Enter password:");
                    controller.setPassword(scanner.nextLine());
                    System.out.println("Enter your name:");
                    controller.setName(scanner.nextLine());
                    System.out.println("Enter your last name:");
                    controller.setLastName(scanner.nextLine());
                    System.out.println("Enter your email:");
                    controller.setEmail(scanner.nextLine());
                    System.out.println("Enter your address:");
                    controller.setAddress(scanner.nextLine());
                    System.out.println("Enter your phone number:");
                    controller.setPhoneNumber(scanner.nextLine());
                    System.out.println("Enter your balance:");
                    controller.setBalance(Long.parseLong(scanner.nextLine()));
                    if (matcher.group(1).equals("seller")) {
                        System.out.println("Enter your company's name:");
                        controller.setCompanyName(scanner.nextLine());
                    }
                    break;
                }
            } else if (command.equals("help")) {
                System.out.println("create account [type] [username] \n" +
                        "help \n" +
                        "back");
            } else if (command.equals("back")) {
                break;
            } else {
                System.out.println("invalid command");
            }
        }
    }

    public void logIn() {
        String username;
        String password;
        String command;
        String regex;
        Matcher matcher;
        while (true) {
            command = scanner.nextLine();
            if (command.matches(regex = "login (.+)")) {
                (matcher = getMatcher(regex, command)).find();
                if (controller.getAccountByUsername(matcher.group(1)) == null) {
                    System.out.println("User not found");
                } else {
                    username = matcher.group(1);
                    System.out.println("Enter password:");
                    password = scanner.nextLine();
                    if (controller.logIn(username, password)) {
                        System.out.println("Successfully logged in");
                        break;
                    } else {
                        System.out.println("Password is wrong");
                    }
                }
            } else if (command.equals("help")) {
                System.out.println("login [username] \n" +
                        "help \n" +
                        "back");
            } else if (command.equals("back")) {
                break;
            } else {
                System.out.println("invalid command");
            }
        }

    }

    public void logOut() {
        if (controller.logOut()) {
            System.out.println("Successfully logged out");
        } else {
            System.out.println("You're already logged out");
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        VBox layout = new VBox(20); VBox registerBox = new VBox(20); VBox loginBox = new VBox(20);
        Scene scene = new Scene(layout,200,200);
        Scene registerScene = new Scene(registerBox,200,200);
        Scene loginScene = new Scene(loginBox,200,200);



        Button registerButton = new Button("Register"); registerButton.setOnAction(event -> {
            registerBox.getChildren().clear();
            HBox nameBox = new HBox(20); TextField nameField = new TextField();
            nameBox.getChildren().addAll(new Label("Enter Username: "),nameField);
            HBox roleBox = new HBox(20);
            CheckBox sellerCheck = new CheckBox("Seller"); CheckBox buyerCheck = new CheckBox("Buyer");
            CheckBox managerCheck = new CheckBox("Manager"); roleBox.getChildren().addAll(new Label("Enter Role: ")
            ,buyerCheck,sellerCheck,managerCheck);
            HBox passBox = new HBox(20); PasswordField passwordField = new PasswordField();
            passBox.getChildren().addAll(new Label("Enter password: "),passwordField);
        });
        Button loginButton = new Button("Login"); loginButton.setOnAction(event -> {

        });
        Button logoutButton = new Button("Logout"); logoutButton.setOnAction(event -> {

        });
        Button backButton = new Button("Back"); backButton.setOnAction(event -> {

        });
/*
        HBox nameBox = new HBox(20);
        TextField nameField = new TextField(); nameBox.getChildren().addAll(new Label("Enter Username: "),nameField);
        HBox passBox = new HBox(20);
        PasswordField passwordField = new PasswordField(); passBox.getChildren().addAll(new Label("Enter Password: "),passwordField);

 */



        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
