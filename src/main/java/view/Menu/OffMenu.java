package view.Menu;

import controller.*;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.awt.*;
import java.util.HashMap;

public class OffMenu extends Menu {

    private GoodMenu goodMenu = new GoodMenu(this,null);

    public OffMenu(Menu parentMenu) {
        super("OffMenu", parentMenu);
        HashMap<Integer, Menu> submenus = new HashMap<>();
        submenus.put(1, goodMenu);
        submenus.put(2, new LoginMenu(this));
        this.setSubmenus(submenus);
    }

    @Override
    public void execute() {
        System.out.println(controller.offs());
        switch (scanner.nextLine()) {
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
                this.parentMenu.show();
                this.parentMenu.execute();
                break;
            default:
                System.out.println("Enter a validate number");
                this.execute();
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        VBox vBox = new VBox(20);
        HBox mainLayout = new HBox(20);
        Scene scene = new Scene(vBox,200,200);
        Button goodButton = new Button("Good Menu");
        Button loginButton = new Button("Login Menu");
        HBox layout = new HBox(20);
        Button back = new Button("Back");
        layout.getChildren().addAll(back);
        mainLayout.getChildren().addAll(goodButton, loginButton);
        vBox.getChildren().addAll(mainLayout, layout);
        primaryStage.setScene(scene);
        primaryStage.show();
        loginButton.setOnAction(event -> {
            try {
                submenus.get(2).start(new Stage());
                primaryStage.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        goodButton.setOnAction(event -> {
            try {
                submenus.get(1).start(new Stage());
                primaryStage.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        back.setOnAction(event -> {
            try {
                this.parentMenu.start(new Stage());
                primaryStage.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
