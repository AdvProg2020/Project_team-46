package view.Menu;


public class UserMenu extends Menu {

    private UserBuyer userBuyer;
    private UserSeller userSeller;
    private UserManager userManager;

    public UserMenu(Menu parentMenu) {
        super("UserMenu", parentMenu);
        userBuyer = new UserBuyer("UserBuyer", this);
        userSeller = new UserSeller("UserSeller", this);
        userManager = new UserManager("UserManager", this);
        submenus.put(1, new LoginMenu(this));
        setSubmenus(submenus);
    }

    public void show() {
        if (controller.getCurrentAccount() == null) {
            System.out.println(this.name + ":");
            for (Integer menuNum : submenus.keySet()) {
                System.out.println(menuNum + ". " + submenus.get(menuNum).getName());
            }
            if (this.parentMenu != null)
                System.out.println((submenus.size() + 1) + ". Back");
            else
                System.out.println((submenus.size() + 1) + ". Exit");
        }
    }

    @Override
    public void execute() {
        Menu nextMenu = null;
        if (controller.getCurrentAccount() == null) {
            int chosenMenu = Integer.parseInt(scanner.nextLine());
            if (chosenMenu == submenus.size() + 1) {
                if (this.parentMenu == null)
                    System.exit(1);
                else
                    nextMenu = this.parentMenu;
            } else
                nextMenu = submenus.get(chosenMenu);

        } else {
            switch (controller.getCurrentAccount().getRole()) {
                case CUSTOMER:
                    nextMenu = userBuyer;
                    break;
                case SELLER:
                    nextMenu = userSeller;
                    break;
                case MANAGER:
                    nextMenu = userManager;
                    break;
            }
        }
        nextMenu.show();
        nextMenu.execute();
    }
}
