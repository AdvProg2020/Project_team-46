package view.Menu;

public class UserSeller extends Menu{
    public UserSeller(String name, Menu parentMenu) {

        super(name, parentMenu);
        submenus.put(1, new LoginMenu(this));
    }

    @Override
    public void show() {

    }

    @Override
    public void execute() {

    }
}
