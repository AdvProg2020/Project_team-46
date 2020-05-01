package view.Menu;

import model.Account;
import model.Role;

import java.util.HashMap;
import java.util.regex.Matcher;

public class UserManager extends Menu {
    public UserManager(String name, Menu parentMenu) {
        super(name, parentMenu);
        HashMap<Integer, Menu> submenus = new HashMap<>();
        submenus.put(1, new LoginMenu(this));
        submenus.put(2, getViewPersonalInfo());
        submenus.put(3, getManageUsers());
        this.setSubmenus(submenus);
    }

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
                getManageUsers().show();
                getManageUsers().execute();
            }
            else if (command.equals("4")) {
                parentMenu.show();
                parentMenu.execute();
                break;
            }
        }
    }

    public void viewPersonalInfo() {
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

    public void manageUsers() {
        String command;
        String regex;
        Matcher matcher;
        while (!(command = scanner.nextLine()).equalsIgnoreCase("end")) {
            if (command.matches(regex = "view (\\S+)")) {
                (matcher = getMatcher(regex, command)).find();
                String field = matcher.group(1);
                controller.view(field);
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

    private Menu getManageUsers() {
        return new Menu("manage users",this) {
            @Override
            public void show() {
                System.out.println(controller.getAccounts());
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
}


