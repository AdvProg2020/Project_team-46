package view.Menu;

import model.Comment;

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
            else if (command.matches(regex = "select seller (\\S+)")) {
                (matcher = getMatcher(regex, command)).find();
                String field = matcher.group(1);
                controller.selectSeller(field);
            }
            else
                System.out.println("invalid command");
        }
    }

    private Menu getDigest() {
        return new Menu("digest Menu",this) {
            @Override
            public void show() {
                System.out.println("Digest Menu:");
                System.out.println("1.add to cart/select seller [seller username]\n" +
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
            public void show() {
                System.out.println("Compare Menu");
                System.out.println("1.back");
                String input = scanner.nextLine();
                for (String productAttribute : controller.compare(goodId, input)) {
                    System.out.println(productAttribute);
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
}
