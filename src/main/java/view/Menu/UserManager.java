package view.Menu;

import model.Account;

import java.util.regex.Matcher;

public class UserManager extends Menu {
    public UserManager(String name, Menu parentMenu) {
        super(name, parentMenu);
        submenus.put(1, new LoginMenu(this));
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
            } else if (command.equals("2")) {
                parentMenu.show();
                parentMenu.execute();
                break;
            } else if (command.equals("view personal info")) {
                viewPersonalInfo();
            }
        }
    }

    public void viewPersonalInfo() {
        String command;
        String regex;
        Matcher matcher;
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
        while (true) {
            command = scanner.nextLine();
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
            } else if (command.equals("help")) {
                System.out.println("login [username] \n" +
                        "help \n" +
                        "back");
            } else if (command.equals("back")) {
                break;
            } else {
                System.out.println("invalid command");
            }
        }
    }
}


