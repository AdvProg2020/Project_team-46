package view.Menu;

public class UserBuyer extends Menu{
    public UserBuyer(String name, Menu parentMenu) {
        super(name, parentMenu);
        submenus.put(1, new LoginMenu(this));
    }


}
