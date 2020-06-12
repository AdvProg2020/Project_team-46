package view.Menu;


import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class UserMenu extends Menu {

    private UserBuyer userBuyer;
    private UserSeller userSeller;
    private UserManager userManager;

    public UserMenu(Menu parentMenu) {
        super("UserMenu", parentMenu);
        userBuyer = new UserBuyer("UserBuyer", parentMenu);
        userSeller = new UserSeller("UserSeller", parentMenu);
        userManager = new UserManager("UserManager", parentMenu);
        submenus.put(1, new LoginMenu(this));
        setSubmenus(submenus);
    }

    public void show() {
        if (controller.getCurrentAccount() == null) {
            System.out.println(this.name + ":");
            for (Integer menuNum : submenus.keySet()) {
                System.out.println(menuNum + ". " + submenus.get(menuNum).getName());
            }
            if (this.parentMenu != null)
                System.out.println((submenus.size() + 1) + ". Back");
            else
                System.out.println((submenus.size() + 1) + ". Exit");
        }
    }

    @Override
    public void execute(int chosenMenu) {
        Menu nextMenu = null;
        if (controller.getCurrentAccount() == null) {
            if (chosenMenu == submenus.size() + 1) {
                if (this.parentMenu == null)
                    System.exit(1);
                else
                    nextMenu = this.parentMenu;
            } else
                nextMenu = submenus.get(chosenMenu);

        } else {
            switch (controller.getCurrentAccount().getRole()) {
                case CUSTOMER:
                    nextMenu = userBuyer;
                    break;
                case SELLER:
                    nextMenu = userSeller;
                    break;
                case MANAGER:
                    nextMenu = userManager;
                    break;
            }
        }
        nextMenu.show();
        nextMenu.execute();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        if (controller.getCurrentAccount() == null) {
            VBox layout = new VBox(20);
            Scene firstScene = new Scene(layout,200,200);

            Button loginButton = new Button("Login Menu");
            Button backButton = new Button("Back");

            loginButton.setOnAction(event -> {
                primaryStage.close();
                execute(1);
            });
            backButton.setOnAction(event -> {
                primaryStage.close();
                execute(2);
            });

            layout.getChildren().addAll(loginButton,backButton);
            primaryStage.setScene(firstScene);
            primaryStage.show();
        }
        else {
            primaryStage.close();
            execute(0);
        }
    }
}
