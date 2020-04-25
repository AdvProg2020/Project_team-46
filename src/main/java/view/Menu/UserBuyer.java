package view.Menu;

import java.util.regex.Matcher;

public class UserBuyer extends Menu{
    public UserBuyer(String name, Menu parentMenu) {
        super(name, parentMenu);
        submenus.put(1, new LoginMenu(this));
    }




}
