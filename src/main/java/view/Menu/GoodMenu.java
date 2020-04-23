package view.Menu;

import controller.*;
import java.util.HashMap;

public class GoodMenu extends Menu {
    private String goodId;
    private LoginMenu loginMenu = new LoginMenu(this);

    public GoodMenu(Menu parentMenu , String goodId) {
        super("GoodMenu", parentMenu);
        submenus.put(1,loginMenu);
        this.goodId = goodId;
    }

    @Override
    public void show() {
        Controller.showProduct(goodId);
    }

    @Override
    public void execute() {
        Menu nextMenu = this;
        String chosenMenu = scanner.nextLine().trim();
        if(chosenMenu.matches("(?i)digest")) {
            Controller.digest();
        }
        else if(chosenMenu.matches("(?i)add\\s+to\\s+cart")) {
            Controller.addToCart();
        }
        else if(chosenMenu.matches("(?i)select\\s+seller\\s+\\S+")) {
            Controller.selectSeller();
        }
        else if(chosenMenu.matches("(?i)attribute")) {
            Controller.attributes();
        }
        else if(chosenMenu.matches("(?i)compare\\s+\\S+")) {
            String[] splitString = chosenMenu.split("\\s+");
            Controller.compare(splitString[1]);
        }
        else if(chosenMenu.matches("(?i)Comments")) {
            Controller.comments();
        }
        else if(chosenMenu.matches("(?i)Add comment")) {
            String title = scanner.nextLine();
            String content = scanner.nextLine();
            Controller.addComment(title,content);
        }
        else if(chosenMenu.matches("(?i)back")) {
            if(this.parentMenu != null) {
                nextMenu = this.parentMenu;
            }
        }
        else if(chosenMenu.matches("(?i)create\\s+account\\s+\\S+\\s+\\S+")) {
            nextMenu = loginMenu;
        }
        nextMenu.show();
        nextMenu.execute();
    }

    public void setGoodId(String goodId) {
        this.goodId = goodId;
    }
}
