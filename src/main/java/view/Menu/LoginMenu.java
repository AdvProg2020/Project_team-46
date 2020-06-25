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

    public void register(String password, String name, String last, String email, String address, String phone,
                         String balance, String company, String username, String role, Label error) {

        if (controller.getAccountByUsername(username) != null) {
            error.setText("User is already taken");
        }
        else {
            controller.createAccount(username, role);
            controller.setPassword(password);
            controller.setName(name);
            controller.setLastName(last);
            controller.setEmail(email);
            controller.setAddress(address);
            controller.setPhoneNumber(phone);
            controller.setBalance(Long.parseLong(balance));
            controller.setCompanyName(company);
            try {
                this.parentMenu.start(new Stage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void logIn(String username, String password, Label errorLabel) {
        if (controller.getAccountByUsername(username) == null) {
            errorLabel.setText("User not found");
        } else {
            if (controller.logIn(username, password)) {
                errorLabel.setText("Successfully logged in");
                try {
                    this.parentMenu.start(new Stage());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                errorLabel.setText("Password is wrong");
            }
        }
    }

    public void logOut(Label label) {
        if (controller.logOut()) {
            label.setText("Successfully logged out");
            try {
                mainMenu.start(new Stage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            label.setText("You're already logged out");
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        VBox layout = new VBox(20); VBox registerBox = new VBox(20); VBox loginBox = new VBox(20);
        Scene scene = new Scene(layout,200,200);
        Scene registerScene = new Scene(registerBox,200,200);
        Scene loginScene = new Scene(loginBox,200,200);

        Label situationLabel= new Label();

        Button registerButton = new Button("Register"); registerButton.setOnAction(event -> {
            registerBox.getChildren().clear();
            HBox nameBox = new HBox(20); TextField nameField = new TextField();
            nameBox.getChildren().addAll(new Label("Enter Username: "),nameField);
            HBox roleBox = new HBox(20);
            RadioButton sellerCheck = new RadioButton("Seller"); RadioButton buyerCheck = new RadioButton("Buyer");
            RadioButton managerCheck = new RadioButton("Manager");ToggleGroup checkGroup = new ToggleGroup();
            buyerCheck.setToggleGroup(checkGroup); sellerCheck.setToggleGroup(checkGroup);managerCheck.setToggleGroup(checkGroup);
            roleBox.getChildren().addAll(new Label("Enter Role: "),buyerCheck,sellerCheck,managerCheck);
            HBox passBox = new HBox(20); PasswordField passwordField = new PasswordField();
            passBox.getChildren().addAll(new Label("Enter password: "),passwordField);
            HBox firstNameBox = new HBox(20); TextField firstNameField = new TextField();
            firstNameBox.getChildren().addAll(new Label("Enter your name: "),firstNameBox);
            HBox lastBox = new HBox(20); TextField lastField = new TextField();
            lastBox.getChildren().addAll(new Label("Enter your last name: "),lastField);
            HBox emailBox = new HBox(20); TextField emailField = new TextField();
            emailBox.getChildren().addAll(new Label("Enter your email"),emailField);
            HBox addressBox = new HBox(20); TextField addressField = new TextField();
            addressBox.getChildren().addAll(new Label("Enter your address: "),addressField);
            HBox phoneBox = new HBox(20); TextField phoneField = new TextField();
            phoneBox.getChildren().addAll(new Label("Enter your phone number: "),phoneField);
            HBox balanceBox = new HBox(20); TextField balanceField = new TextField();
            balanceBox.getChildren().addAll(new Label("Enter your balance: (for seller and buyer)"),balanceField);
            HBox companyBox = new HBox(20); TextField companyField = new TextField();
            companyBox.getChildren().addAll(new Label("Enter your Company: (for sellers)"),companyField);
            Label errorLabel = new Label();
            Button confirmButton = new Button("Confirm"); confirmButton.setOnAction(event1 -> {
                if (nameField.getText().isEmpty() || passwordField.getText().isEmpty() || firstNameField.getText().isEmpty() ||
                lastField.getText().isEmpty() || emailField.getText().isEmpty() || (!sellerCheck.isSelected() &&
                        !buyerCheck.isSelected() && !managerCheck.isSelected())) {
                    errorLabel.setText("some required fields did not filled");
                }
                else {
                    if (buyerCheck.isSelected()) register(passwordField.getText(),firstNameField.getText(),lastField.getText()
                            ,emailField.getText(),addressField.getText(),phoneField.getText(),balanceField.getText(),
                            null,nameField.getText(),"customer",errorLabel);
                    if (sellerCheck.isSelected()) register(passwordField.getText(),firstNameField.getText(),lastField.getText()
                            ,emailField.getText(),addressField.getText(),phoneField.getText(),balanceField.getText(),
                            companyField.getText(),nameField.getText(),"seller",errorLabel);
                    if (managerCheck.isSelected()) register(passwordField.getText(),firstNameField.getText(),lastField.getText()
                            ,emailField.getText(),addressField.getText(),phoneField.getText(),"0",
                            null,nameField.getText(),"manager",errorLabel);
                }
            });
            Button backButton = new Button("Back"); backButton.setOnAction(event1 -> {
                primaryStage.setScene(scene);
            });

            registerBox.getChildren().addAll(nameBox,roleBox,passBox,firstNameBox,lastBox,emailBox,addressBox,phoneBox
                    ,balanceBox,companyBox,confirmButton,errorLabel,backButton);
            primaryStage.setScene(registerScene);
        });
        Button loginButton = new Button("Login"); loginButton.setOnAction(event -> {
            loginBox.getChildren().clear();
            HBox nameBox = new HBox(20); TextField nameField = new TextField();
            nameBox.getChildren().addAll(new Label("Enter Username: "),nameField);
            HBox passBox = new HBox(20); PasswordField passwordField = new PasswordField();
            passBox.getChildren().addAll(new Label("Enter Password: "),passwordField);
            Label errorLabel = new Label();
            Button confirmButton = new Button("Login"); confirmButton.setOnAction(event1 -> {
                logIn(nameField.getText(),passwordField.getText(),errorLabel);
                try {
                    this.parentMenu.start(primaryStage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            Button backButton = new Button("Back"); backButton.setOnAction(event1 -> {
                primaryStage.setScene(scene);
            });

            loginBox.getChildren().addAll(nameBox,passBox,confirmButton,errorLabel,backButton);
            primaryStage.setScene(loginScene);
        });
        Button logoutButton = new Button("Logout"); logoutButton.setOnAction(event -> {
            logOut(situationLabel);
        });
        Button backButton = new Button("Back"); backButton.setOnAction(event -> {
            try {
                this.parentMenu.start(primaryStage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        layout.getChildren().addAll(registerButton,loginButton,logoutButton,situationLabel,backButton);

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
