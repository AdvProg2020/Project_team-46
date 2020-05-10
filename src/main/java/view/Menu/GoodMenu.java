package view.Menu;

import controller.*;
import java.util.HashMap;
import java.util.regex.Matcher;

public class GoodMenu extends Menu {
    private String goodId;

    public GoodMenu(Menu parentMenu , String goodId) {
        super("GoodMenu", parentMenu);
        HashMap<Integer, Menu> submenus = new HashMap<>();
        submenus.put(1, new LoginMenu(this));
        submenus.put(2, getDigest());
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
        }
    }

    private void manageDigest() {
        String command;
        String regex;
        Matcher matcher;
        while (!(command = scanner.nextLine()).equalsIgnoreCase("back")) {
            if (command.matches("add to cart")) {
                controller.addToCart(goodId);
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
                super.show();
            }

            @Override
            public void execute() {
                super.execute();
            }
        };
    }

    public void setGoodId(String goodId) {
        this.goodId = goodId;
    }
}
