package view.Menu;

import model.Account;
import model.Role;
import model.Product;
import model.Discount;
import java.util.HashMap;
import java.util.regex.Matcher;

public class UserManager extends Menu {
    public UserManager(String name, Menu parentMenu) {
        super(name, parentMenu);
        submenus.put(1, new LoginMenu(parentMenu));
        submenus.put(2, getViewPersonalInfo());
        submenus.put(3, getManageUsers());
        submenus.put(4, getManageProducts());
        submenus.put(5, getCreateDiscountCode());
        submenus.put(6, getViewDiscountCode());
        submenus.put(7, getManageRequest()); //needs to complete
        submenus.put(8, getManageCategory());
        this.setSubmenus(submenus);
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
                getViewPersonalInfo().show();
                getViewPersonalInfo().execute();
                break;
            case "3":
                getManageUsers().show();
                getManageUsers().execute();
                break;
            case "4":
                getManageProducts().show();
                getManageProducts().execute();
                break;
            case "5":
                getCreateDiscountCode().show();
                getCreateDiscountCode().execute();
                break;
            case "6":
                getViewDiscountCode().show();
                getViewDiscountCode().execute();
                break;
            case "7":
                getManageRequest().show();
                getManageRequest().execute();
                break;
            case "8":
                getManageCategory().show();
                getManageCategory().execute();
                break;
            case "9":
                parentMenu.show();
                parentMenu.execute();
                break;
        }

    }

    private void editPersonalInfo() {
        String command;
        String regex;
        Matcher matcher;
        Account currentAccount = controller.getCurrentAccount();
        while (!(command = scanner.nextLine()).equalsIgnoreCase("back")) {
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
            } else if (command.equals("help")){
                System.out.println("edit [field]" + "\n"
                        + "help" + "\n"
                        + "back");

            } else {
                System.out.println("invalid command");
            }
        }
    }

    private void manageUsers() {
        String command;
        String regex;
        Matcher matcher;
        while (!(command = scanner.nextLine()).equalsIgnoreCase("back")) {
            if (command.matches(regex = "view (\\S+)")) {
                (matcher = getMatcher(regex, command)).find();
                String field = matcher.group(1);
                System.out.println(controller.viewUsers(field));
            }
            else if (command.matches(regex = "delete user (\\S+)")) {
                (matcher = getMatcher(regex, command)).find();
                String field = matcher.group(1);
                controller.deleteUser(field);
            }
            else if (command.equalsIgnoreCase("create manager profile")) {
                String userName = scanner.nextLine();
                Role manager = Role.MANAGER;
                Account account = controller.createManager(userName,manager);
                System.out.println("Enter manager name :");
                account.setName(scanner.nextLine());
                System.out.println("Enter manager last name :");
                account.setLastName(scanner.nextLine());
                System.out.println("Enter manager password :");
                account.setPassword(scanner.nextLine());
                System.out.println("Enter manager address :");
                account.setAddress(scanner.nextLine());
                System.out.println("Enter manager Email :");
                account.setEmail(scanner.nextLine());
                System.out.println("Enter manager phone number :");
                account.setPhoneNumber(scanner.nextLine());
            }
            else
                System.out.println("invalid command");
        }
    }

    private void manageProducts() {
        String command;
        String regex;
        Matcher matcher;
        while (!(command = scanner.nextLine()).equalsIgnoreCase("back")) {
            if (command.matches(regex = "remove (\\S+)")) {
                (matcher = getMatcher(regex, command)).find();
                String field = matcher.group(1);
                controller.removeProduct(field);
            }
            else
                System.out.println("invalid command");
        }
    }

    private void viewDiscountCodes() {
        String command;
        String regex;
        Matcher matcher;
        while (!(command = scanner.nextLine()).equalsIgnoreCase("back")) {
            if (command.matches(regex = "view discount code (\\S+)")) {
                (matcher = getMatcher(regex, command)).find();
                String field = matcher.group(1);
                controller.viewDiscountCode(field);
            }
            else if (command.matches(regex = "edit discount code (\\S+)")) {
                (matcher = getMatcher(regex, command)).find();
                String field = matcher.group(1);
                controller.editDiscountCode(field);
            }
            else if (command.matches(regex = "remove discount code (\\S+)")) {
                (matcher = getMatcher(regex, command)).find();
                String field = matcher.group(1);
                controller.removeDiscountCode(field);
            }
            else
                System.out.println("invalid command");
        }
    }

    private void manageRequests() {
        String command;
        String regex;
        Matcher matcher;
        while (!(command = scanner.nextLine()).equalsIgnoreCase("end")) {
            if (command.matches(regex = "details (\\S+)")) {
                (matcher = getMatcher(regex, command)).find();
                String field = matcher.group(1);
                controller.showDetails(field);
            }
            else if (command.matches(regex = "accept (\\S+)")) {
                (matcher = getMatcher(regex, command)).find();
                String field = matcher.group(1);

            }
        }
    } //needs to complete

    private void manageCategory() {
        String command;
        String regex;
        Matcher matcher;
        while (!(command = scanner.nextLine()).equalsIgnoreCase("end")) {
            if (command.matches(regex = "edit (\\S+)")) {
                (matcher = getMatcher(regex, command)).find();
                String newName = scanner.nextLine();
                String newDescription = scanner.nextLine();
                String field = matcher.group(1);
                controller.editCategory(field,newName,newDescription);
            }
            else if (command.matches(regex = "add (\\S+)")) {
                (matcher = getMatcher(regex, command)).find();
                String field = matcher.group(1);
                String description = scanner.nextLine();
                controller.addCategory(field,description);
            }
            else if (command.matches(regex = "remove (\\S+)")) {
                (matcher = getMatcher(regex, command)).find();
                String field = matcher.group(1);
                controller.removeCategory(field);
            }
            else
                System.out.println("invalid command");
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
                                + "role: " + currentAccount.getRole() + "\n"
                        + "1. edit field" + "\n"
                        + "2. back"
                );
            }

            @Override
            public void execute() {
                switch (Integer.parseInt(scanner.nextLine())) {
                    case 1:
                        editPersonalInfo();
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

    private Menu getManageUsers() {
        return new Menu("manage users",this) {
            @Override
            public void show() {
                System.out.println(controller.getAccounts());
                System.out.println(
                        "1. manage users" + "\n"
                        + "2. back");
            }

            @Override
            public void execute() {
                switch (Integer.parseInt(scanner.nextLine())) {
                    case 1:
                        manageUsers();
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

    private Menu getManageProducts() {
        return new Menu("manage products",this) {
            @Override
            public void show() {
                System.out.println(controller.getProducts());
                System.out.println(
                        "1. manage products" + "\n"
                                + "2. back");
            }


            @Override
            public void execute() {
                switch (Integer.parseInt(scanner.nextLine())) {
                    case 1:
                        manageProducts();
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

    private Menu getCreateDiscountCode() {
        return new Menu("create discount code",this) {
            @Override
            public void show() {
                System.out.println("create Discount Code:");
                System.out.println("1.Create discount code \n" +
                        "2.back");
            }

            @Override
            public void execute() {
                switch (Integer.parseInt(scanner.nextLine())) {
                    case 1:
                        controller.createDiscountCode();
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

    private Menu getViewDiscountCode() {
        return new Menu("View Discount Code",this) {
            @Override
            public void show() {
                System.out.println("View Discount Code:");
                System.out.println("1.view discount code [code]\n" +
                        "1.edit discount code [code]\n " +
                        "1.remove discount code [code]\n"+
                        "2.back");
                System.out.println(Discount.discounts);
            }

            @Override
            public void execute() {
                switch (Integer.parseInt(scanner.nextLine())) {
                    case 1:
                        viewDiscountCodes();
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

    private Menu getManageRequest() {
        return new Menu("Manage Request",this) {
            @Override
            public void show() {
                System.out.println("Manage Request:");
                System.out.println("1.details [requestId]\n" +
                        "1.accept [requestId]\n" +
                        "1.decline [requestId]\n" +
                        "2.back");
                System.out.println(controller.manageRequest());
            }

            @Override
            public void execute() {
                switch (Integer.parseInt(scanner.nextLine())) {
                    case 1:

                }
            }
        };
    } //needs to complete request class

    private Menu getManageCategory() {
        return new Menu("Manage Category",this) {

            @Override
            public void show() {
                System.out.println("Manage Categories:");
                System.out.println("1.edit/add/remove\n" +
                        "2.back");
                System.out.println(controller.manageCategories());
            }

            @Override
            public void execute() {
                switch (Integer.parseInt(scanner.nextLine())) {
                    case 1:
                        manageCategory();
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
}


