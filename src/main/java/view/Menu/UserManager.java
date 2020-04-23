package view.Menu;

public class UserManager extends Menu{
    public UserManager(String name, Menu parentMenu) {

        super(name, parentMenu);
        submenus.put(1, new LoginMenu(this));
    }



}
