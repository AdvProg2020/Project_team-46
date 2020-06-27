package view.Menu;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.HashMap;

public class MainMenu extends Menu{

    public MainMenu() {
        super("MainMenu", null);
        submenus.put(1, new UserMenu(this));
        submenus.put(2, new ProductsMenu(this));
        submenus.put(3, new OffMenu(this));
        submenus.put(4, new LoginMenu(this));
        this.setSubmenus(submenus);
    }

    public void launchApp(String[] args) {
        Application.launch(args);
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        //Layout
        BackgroundFill backgroundFill = new BackgroundFill(Color.BLUE, CornerRadii.EMPTY, Insets.EMPTY);
        Background background = new Background(backgroundFill);
        HBox mainLayout = new HBox(20); mainLayout.setBackground(background);
        VBox layout1 = new VBox(20);
        VBox layout2 = new VBox(20);
        HBox layout3 = new HBox(20);
        mainLayout.getChildren().addAll(layout1,layout2, layout3);
        Scene scene = new Scene(mainLayout,250,200);

        //Buttons
        Button loginButton = new Button("Login Menu"); loginButton.setStyle("-fx-font-family: Tahoma;-fx-font-weight: bold");
        Button productButton = new Button("Product Menu"); productButton.setStyle("-fx-font-family: Tahoma;-fx-font-weight: bold");
        Button offButton = new Button("Off Menu"); offButton.setStyle("-fx-font-family: Tahoma;-fx-font-weight: bold");
        Button userButton = new Button("User Menu"); userButton.setStyle("-fx-font-family: Tahoma;-fx-font-weight: bold");
        Button exit = new Button("Exit"); exit.setStyle("-fx-font-family: Tahoma;-fx-font-weight: bold");

        loginButton.setOnAction(event -> {
            try {
                submenus.get(4).start(new Stage());
                primaryStage.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        productButton.setOnAction(event -> {
            try {
                submenus.get(2).start(new Stage());
                primaryStage.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        userButton.setOnAction(event -> {
            try {
                submenus.get(1).start(new Stage());
                primaryStage.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        offButton.setOnAction(event -> {
            try {
                submenus.get(3).start(new Stage());
                primaryStage.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        exit.setOnAction(event -> primaryStage.close());

        layout1.getChildren().addAll(loginButton,productButton);
        layout2.getChildren().addAll(userButton,offButton);
        layout3.getChildren().add(exit);

        primaryStage.setScene(scene);
        primaryStage.show();
    }


}
