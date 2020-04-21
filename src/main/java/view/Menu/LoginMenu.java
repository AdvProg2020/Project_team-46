package view.Menu;

import java.util.regex.Matcher;

public class LoginMenu extends Menu{
    public LoginMenu(Menu parentMenu) {
        super("LoginMenu", parentMenu);
    }

    @Override
    public void show() {
        System.out.println("1. Register \n" +
                "2. Log in \n" +
                "3. Log out \n" +
                "4. Back");
    }

    @Override
    public void execute() {
        switch (Integer.parseInt(scanner.nextLine())) {
            case 1:
                register();
                break;
            case 2:
                logIn();
                break;
            case 3:
                logOut();
        }
        this.parentMenu.show();
        this.parentMenu.execute();
    }

    public void register() {
        String command;
        String regex;
        Matcher matcher;
        while (true) {
            command = scanner.nextLine();
            if (command.matches(regex = "create account (customer|seller|manager) (\\S+)")) {
                (matcher = getMatcher(regex, command)).find();
                if (controller.getAccountByUsername(matcher.group(2)) != null) {
                    System.out.println("User is already taken");
                } else {
                    controller.createAccount(matcher.group(2), matcher.group(1));
                    System.out.println("Enter password:");
                    controller.setPassword(scanner.nextLine());
                    System.out.println("Enter your name:");
                    controller.setName(scanner.nextLine());
                    System.out.println("Enter your email:");
                    controller.setEmail(scanner.nextLine());
                    System.out.println("Enter your address:");
                    controller.setAddress(scanner.nextLine());
                    System.out.println("Enter your phone number:");
                    controller.setPhoneNumber(scanner.nextLine());
                    if (matcher.group(1).equals("seller")) {
                        System.out.println("Enter your company's name:");
                        controller.setCompanyName(scanner.nextLine());
                    }
                    break;
                }
            } else if (command.equals("help")) {
                System.out.println("create account [type] [username] \n" +
                        "help \n" +
                        "back");
            } else if (command.equals("back")){
                break;
            } else {
                System.out.println("invalid command");
            }
        }
    }

    public void logIn() {

    }

    public void logOut() {

    }
}
