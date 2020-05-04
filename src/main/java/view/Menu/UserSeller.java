package view.Menu;

import model.Account;

import java.util.HashMap;
import java.util.regex.Matcher;

public class UserSeller extends Menu{
    public UserSeller(String name, Menu parentMenu) {
        super(name, parentMenu);
        HashMap<Integer, Menu> submenus = new HashMap<>();
        submenus.put(1, new LoginMenu(this));
        submenus.put(2, getViewPersonalInfo());
        submenus.put(3, getViewCompanyInfo());
        submenus.put(4, getViewSalesHistory());
        this.setSubmenus(submenus);
    }

    @Override
    public void execute() {
        String command;
        String regex;
        Matcher matcher;
        while (true) {
            command = scanner.nextLine();
            if (command.equals("1")) {
                submenus.get(1).show();
                submenus.get(1).execute();
                break;
            }
            else if (command.equals("2")) {
                getViewPersonalInfo().show();
                getViewPersonalInfo().execute();
            }

            else if (command.equals("3")) {
                getViewCompanyInfo().show();
                getViewCompanyInfo().execute();
            }

            else if (command.equals("4")) {
                getViewSalesHistory().show();
                getViewSalesHistory().execute();
            }
            /*
            else if (command.equals("5")) {
                getCreateDiscountCode().show();
                getCreateDiscountCode().execute();
            }
            else if (command.equals("6")) {
                getViewDiscountCode().show();
                getViewDiscountCode().execute();
            }
            else if (command.equals("7")) {
                getManageRequest().show();
                getManageRequest().execute();
            }
            else if (command.equals("8")) {
                getManageCategory().show();
                getManageCategory().execute();
            }

             */
            else if (command.equals("9")) {
                parentMenu.show();
                parentMenu.execute();
                break;
            }
        }
    }

    private void viewPersonalInfo() {
        String command;
        String regex;
        Matcher matcher;
        Account currentAccount = controller.getCurrentAccount();
        while (!(command = scanner.nextLine()).equalsIgnoreCase("end")) {
            if (command.matches(regex = "edit (\\S+)")) {
                (matcher = getMatcher(regex, command)).find();
                String field = matcher.group(1);
                switch (field) {
                    case "password":
                        System.out.println("Enter new password:");
                        currentAccount.setPassword(scanner.nextLine());
                        System.out.println("Field updated");
                        break;
                    case "name":
                        System.out.println("Enter new name:");
                        currentAccount.setName(scanner.nextLine());
                        System.out.println("Field updated");
                        break;
                    case "lastName":
                        System.out.println("Enter new lastName:");
                        currentAccount.setLastName(scanner.nextLine());
                        System.out.println("Field updated");
                        break;
                    case "email":
                        System.out.println("Enter new email:");
                        currentAccount.setEmail(scanner.nextLine());
                        System.out.println("Field updated");
                        break;
                    case "address":
                        System.out.println("Enter new address:");
                        currentAccount.setAddress(scanner.nextLine());
                        System.out.println("Field updated");
                        break;
                    case "phoneNumber":
                        System.out.println("Enter new phoneNumber");
                        currentAccount.setPhoneNumber(scanner.nextLine());
                        System.out.println("Field updated");
                        break;
                    default:
                        System.out.println("Invalid field");
                }
            } else {
                System.out.println("invalid command");
            }
        }
    }

    private Menu getViewPersonalInfo() {
        return new Menu("view personal info",this) {

            @Override
            public void show() {
                Account currentAccount = controller.getCurrentAccount();
                System.out.println(
                        "username: " + currentAccount.getUsername() + "\n"
                                + "password: " + currentAccount.getPassword() + "\n"
                                + "name: " + currentAccount.getName() + "\n"
                                + "lastName: " + currentAccount.getLastName() + "\n"
                                + "email: " + currentAccount.getEmail() + "\n"
                                + "address: " + currentAccount.getAddress() + "\n"
                                + "phoneNumber: " + currentAccount.getPhoneNumber() + "\n"
                                + "role: " + currentAccount.getRole()
                );
            }

            @Override
            public void execute() {
                switch (Integer.parseInt(scanner.nextLine())) {
                    case 1:
                        viewPersonalInfo();
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

    private Menu getViewCompanyInfo() {
        return new Menu("Company Info",this) {
            @Override
            public void show() {
                System.out.println("Company Info:");
                System.out.println("1.back");
                System.out.println(controller.viewCompanyInformation());
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

    private Menu getViewSalesHistory() {
        return new Menu("view sales history",this) {
            @Override
            public void show() {
                System.out.println("view sales history:");
                System.out.println("1.back");
                System.out.println(controller.viewSalesHistory());
            }

            @Override
            public void execute() {
                if (Integer.parseInt(scanner.nextLine()) == 1) {
                    parentMenu.show();
                    parentMenu.execute();
                }
            }
        };
    }
}
