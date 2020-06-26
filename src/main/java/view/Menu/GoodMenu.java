package view.Menu;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.scene.Node;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Comment;
import model.Product;

import java.util.HashMap;
import java.util.regex.Matcher;

public class GoodMenu extends Menu {
    private String goodId;

    public GoodMenu(Menu parentMenu , String goodId) {
        super("GoodMenu", parentMenu);
        HashMap<Integer, Menu> submenus = new HashMap<>();
        submenus.put(1, new LoginMenu(this));
        submenus.put(2, getDigest());
        submenus.put(3, getAttributes());
        submenus.put(4, getCompareProducts());
        submenus.put(5, getComments());
        this.setSubmenus(submenus);
        this.goodId = goodId;
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
                getDigest().show();
                getDigest().execute();
                break;
            case "3":
                getAttributes().show();
                getAttributes().execute();
                break;
            case "4":
                getCompareProducts().show();
                getCompareProducts().execute();
                break;
            case "5":
                getComments().show();
                getComments().execute();
                break;
            case "6":
                this.parentMenu.show();
                this.parentMenu.execute();
                break;
            default:
                System.out.println("Enter a validate number");
                this.execute();
        }
    }

    private void manageDigest() {
        String command;
        String regex;
        Matcher matcher;
        while (!(command = scanner.nextLine()).equalsIgnoreCase("back")) {
            if (command.matches("add to cart")) {
                controller.addToCart(goodId);
                System.out.println("product added to cart");
            }
            else
                System.out.println("invalid command");
        }
    }

    private Menu getDigest() {
        return new Menu("digest Menu",this) {
            @Override
            public void start(Stage primaryStage) throws Exception {
                VBox layout = new VBox(20);
                Scene scene = new Scene(layout, 200, 200);
                Label label = new Label();
                HBox layout1 = new HBox(20);
                Button add = new Button("Add to Cart");
                Button back = new Button("Back");
                add.setOnAction(event -> {
                    controller.digest(goodId);
                    label.setText("product added to cart");
                });
                back.setOnAction(event -> {
                    try {
                        this.parentMenu.start(new Stage());
                        primaryStage.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
                layout1.getChildren().addAll(add, back);
                layout.getChildren().addAll(layout1, label);
                primaryStage.setScene(scene);
                primaryStage.show();
            }

            @Override
            public void show() {
                System.out.println("Digest Menu:");
                System.out.println("1.add to cart" +
                        "2.back");
                System.out.println(controller.digest(goodId));
            }

            @Override
            public void execute() {
                switch (Integer.parseInt(scanner.nextLine())) {
                    case 1:
                        manageDigest();
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

    private Menu getAttributes() {
        return new Menu("attributes menu",this) {
            @Override
            public void start(Stage primaryStage) throws Exception {
                VBox layout = new VBox(20);
                Label label = new Label();
                StringBuilder stringBuilder = new StringBuilder();
                for (String attribute : controller.attributes(goodId)) {
                    stringBuilder.append(attribute).append("\n");
                }
                label.setText(stringBuilder.toString());
                Button back = new Button("Back");
                back.setOnAction(event -> {
                    try {
                        this.parentMenu.start(new Stage());
                        primaryStage.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
                layout.getChildren().addAll(label, back);
                Scene scene = new Scene(layout, 200, 200);
                primaryStage.setScene(scene);
                primaryStage.show();
            }

            @Override
            public void show() {
                System.out.println("Attributes Menu:");
                System.out.println("1.back");
                for (String attribute : controller.attributes(goodId)) {
                    System.out.println(attribute);
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

    private Menu getCompareProducts() {
        return new Menu("Compare Menu:",this) {
            @Override
            public void start(Stage primaryStage) throws Exception {
                VBox entranceLayout = new VBox(20);
                VBox mainLayout = new VBox(20);
                HBox layout1 = new HBox(20);
                TextField productId = new TextField();
                Button backButton = new Button("Back");
                Button confirm = new Button("Confirm");
                layout1.getChildren().addAll(new Label("Enter id of another product:"), productId);
                HBox layout2 = new HBox(20);
                Label errorLabel = new Label();
                Scene mainScene = new Scene(mainLayout, 300, 100);
                Scene entranceScene = new Scene(entranceLayout, 300, 150);
                layout2.getChildren().addAll(confirm, backButton);
                entranceLayout.getChildren().addAll(layout1, layout2, errorLabel);
                backButton.setOnAction(event -> {
                    try {
                        this.parentMenu.start(new Stage());
                        primaryStage.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
                confirm.setOnAction(event -> {
                    Button back = new Button("Back");
                    back.setOnAction(event1 -> {
                        try {
                            this.parentMenu.start(new Stage());
                            primaryStage.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
                    if (controller.getProductById(productId.toString()) == null)  {
                        errorLabel.setText("ID is not valid");
                    } else {
                        StringBuilder stringBuilder = new StringBuilder();
                        Label label = new Label();
                        for (String s : controller.compare(goodId, productId.toString())) {
                            stringBuilder.append(s).append("\n");
                        }
                        label.setText(stringBuilder.toString());
                        mainLayout.getChildren().addAll(label, back);
                        primaryStage.setScene(mainScene);
                    }
                });
                primaryStage.setScene(entranceScene);
                primaryStage.setScene(mainScene);
            }

            @Override
            public void show() {
                System.out.println("Compare Menu");
                System.out.println("1.back");
                String input = scanner.nextLine();
                if (controller.getProductById(input) != null) {
                    for (String productAttribute : controller.compare(goodId, input)) {
                        System.out.println(productAttribute);
                    }
                }
                else {
                    System.out.println("invalid id");
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

    private Menu getComments() {
        return new Menu("Comments",this) {
            @Override
            public void start(Stage primaryStage) throws Exception {

            }

            @Override
            public void show() {
                System.out.println("Comments Menu:");
                for (Comment comment : controller.comments(goodId)) {
                    System.out.println(comment.getUser().getUsername());
                    System.out.println(comment.getTitle());
                    System.out.println(comment.getBody());
                }
                System.out.println("1.Add comment\n" +
                        "2.back");
            }

            @Override
            public void execute() {
                switch (Integer.parseInt(scanner.nextLine())) {
                    case 1:
                        System.out.println("Enter a title:");
                        String title = scanner.nextLine();
                        System.out.println("Enter your comment:");
                        String content = scanner.nextLine();
                        controller.addComment(title,content,goodId);
                        System.out.println("Comment added");
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

    public void setGoodId(String goodId) {
        this.goodId = goodId;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        VBox entranceLayout = new VBox(20);
        VBox mainLayout = new VBox(20);
        HBox layout1 = new HBox(20);
        TextField productId = new TextField();
        Button backButton = new Button("Back");
        Button confirm = new Button("Confirm");
        layout1.getChildren().addAll(new Label("Enter id of the product:"), productId);
        HBox layout2 = new HBox(20);
        Label errorLabel = new Label();
        Scene mainScene = new Scene(mainLayout, 300, 100);
        Scene entranceScene = new Scene(entranceLayout, 300, 150);
        layout2.getChildren().addAll(confirm, backButton);
        entranceLayout.getChildren().addAll(layout1, layout2, errorLabel);
        backButton.setOnAction(event -> {
            try {
                this.parentMenu.start(new Stage());
                primaryStage.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        confirm.setOnAction(event -> {
            Button LoginMenu = new Button("Login Menu");
            LoginMenu.setOnAction(event1 -> {
                try {
                    submenus.get(1).start(new Stage());
                    primaryStage.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            Button digest = new Button("Digest");
            digest.setOnAction(event1 -> {
                try {
                    submenus.get(2).start(new Stage());
                    primaryStage.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            Button attribute = new Button("Attributes");
            attribute.setOnAction(event1 -> {
                try {
                    submenus.get(3).start(new Stage());
                    primaryStage.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            });
            Button compare = new Button("Compare Products");
            compare.setOnAction(event1 -> {
                try {
                    submenus.get(4).start(new Stage());
                    primaryStage.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            Button comment = new Button("Comments");
            comment.setOnAction(event1 -> {
                try {
                    submenus.get(5).start(new Stage());
                    primaryStage.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            Button back = new Button("Back");
            back.setOnAction(event1 -> {
                try {
                    this.parentMenu.start(new Stage());
                    primaryStage.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            HBox hBox1 = new HBox(20);
            HBox hBox2 = new HBox(20);
            hBox1.getChildren().addAll(LoginMenu, digest, attribute);
            hBox2.getChildren().addAll(compare, comment, back);
            mainLayout.getChildren().addAll(hBox1, hBox2);
            if (controller.getProductById(productId.toString()) == null) {
                errorLabel.setText("ID is not valid");
            } else {
                goodId = productId.toString();
                primaryStage.setScene(mainScene);
            }
        });
        primaryStage.setScene(entranceScene);
        primaryStage.show();
    }
    }
