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

    }

    @Override
    public void execute() {

    }

    public void setGoodId(String goodId) {
        this.goodId = goodId;
    }
}
