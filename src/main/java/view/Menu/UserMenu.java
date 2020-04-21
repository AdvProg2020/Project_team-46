package view.Menu;

import java.util.HashMap;

public class UserMenu extends Menu {
    public UserMenu(Menu parentMenu) {
        super("UserMenu", parentMenu);
        HashMap<Integer, Menu> submenus = new HashMap<>();
        submenus.put(1, new LoginMenu(this));
        setSubmenus(submenus);
    }

    @Override
    public void show() {
        System.out.println(this.name + ":");
        if (controller.getCurrentAccount() == null) {
            for (Integer menuNum : submenus.keySet()) {
                System.out.println(menuNum + ". " + submenus.get(menuNum).getName());
            }
            System.out.println("2. Back");
        }
    }

    @Override
    public void execute() {
        if (controller.getCurrentAccount() == null) {
            Menu nextMenu = null;
            int chosenMenu = Integer.parseInt(scanner.nextLine());
            if (chosenMenu == submenus.size() + 1) {
                if (this.parentMenu == null)
                    System.exit(1);
                else
                    nextMenu = this.parentMenu;
            } else
                nextMenu = submenus.get(chosenMenu);
            nextMenu.show();
            nextMenu.execute();
        }
    }
}
