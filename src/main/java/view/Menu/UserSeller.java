package view.Menu;

import model.Account;
import model.Product;
import model.SellerRequest;

import java.util.regex.Matcher;

public class UserSeller extends Menu{
    public UserSeller(String name, Menu parentMenu) {

        super(name, parentMenu);
        submenus.put(1, new LoginMenu(this));
        submenus.put(2, getViewPersonalInfo());
        submenus.put(3, getCompanyInfo());
        submenus.put(4, getSalesHistory());
        submenus.put(5, getManageProducts());
    }


    @Override
    public void execute() {
        String command;
        while (true) {
            command = scanner.nextLine();
            if (command.equals("1")) {
                submenus.get(1).show();
                submenus.get(1).execute();
                break;
            } else if (command.equals("2")) {
                submenus.get(2).show();
                submenus.get(2).execute();
            } else if (command.equals("3")) {
                submenus.get(3).show();
                submenus.get(3).execute();
            }

        }
    }

    private Menu getManageProducts() {
        return new Menu("manage products", this) {
            @Override
            public void show() {
                for (Product product : controller.getProducts()) {
                    System.out.println(product.getName() + " " + product.getProductId());
                }
                System.out.println("1. view product" + "\n" +
                        "2. view buyers \n" +
                        "3. edit product \n" +
                        "4. back");
            }

            @Override
            public void execute() {
                switch (Integer.parseInt(scanner.nextLine())) {
                    case 1:
                        viewProduct();
                        this.show();
                        this.execute();
                        break;
                    case 2:
                        viewBuyers();
                        this.show();
                        this.execute();
                        break;
                    case 3:
                        editProduct();
                        this.show();
                        this.execute();
                    case 4:
                        parentMenu.show();
                        parentMenu.execute();
                }
            }
        };
    }

    private void editProduct() {
        String command;
        String regex;
        Matcher matcher;
        Product product;
        while (!(command = scanner.nextLine()).equalsIgnoreCase("back")) {
            if (command.matches(regex = "edit (\\S+)")) {
                (matcher = getMatcher(regex, command)).find();
                product =  controller.getProductById(matcher.group(1));
                if (product != null) {
                    System.out.println("Enter a field: \n" +
                            "1. name \n" +
                            "2. brand or company \n" +
                            "3. description \n"
                    );
                    switch (Integer.parseInt(scanner.nextLine())) {
                        case 1:
                            System.out.println("Enter new name:");
                            new SellerRequest(controller.getCurrentAccount(), "1-1-" + product.getProductId(), scanner.nextLine());
                            break;
                        case 2:
                            System.out.println("Enter new brand or company:");
                            new SellerRequest(controller.getCurrentAccount(), "1-2-" + product.getProductId(), scanner.nextLine());
                            break;

                        case 3:
                            System.out.println("Enter new description:");
                            new SellerRequest(controller.getCurrentAccount(), "1-3-"+ product.getProductId(), scanner.nextLine());
                            break;
                    }
                } else {
                    System.out.println("invalid id");
                }
            } else if (command.equals("help")){
                System.out.println("edit [productId]" + "\n"
                        + "help" + "\n"
                        + "back");

            } else {
                System.out.println("invalid command");
            }
        }
    }

    private void viewBuyers() {
        String command;
        String regex;
        Matcher matcher;
        while (!(command = scanner.nextLine()).equalsIgnoreCase("back")) {
            if (command.matches(regex = "view buyers (\\S+)")) {
                (matcher = getMatcher(regex, command)).find();
                String id = matcher.group(1);
                if (controller.getProductById(id) != null) {
                    for (Account buyer : controller.getProductById(id).getBuyers()) {
                        System.out.println(buyer.getUsername());
                    }
                } else {
                    System.out.println("invalid id");
                }
            } else if (command.equals("help")){
                System.out.println("view buyers [productId]" + "\n"
                        + "help" + "\n"
                        + "back");

            } else {
                System.out.println("invalid command");
            }
        }
    }

    private void viewProduct() {
        String command;
        String regex;
        Matcher matcher;
        while (!(command = scanner.nextLine()).equalsIgnoreCase("back")) {
            if (command.matches(regex = "view (\\S+)")) {
                (matcher = getMatcher(regex, command)).find();
                String id = matcher.group(1);
                if (controller.getProductById(id) != null) {
                    System.out.println(controller.getProductById(id).toString());
                } else {
                    System.out.println("invalid id");
                }
            } else if (command.equals("help")){
                System.out.println("view [productId]" + "\n"
                        + "help" + "\n"
                        + "back");

            } else {
                System.out.println("invalid command");
            }
        }
    }

    private Menu getSalesHistory() {
        return new Menu("view sales history", this) {
            @Override
            public void show() {
                System.out.println(controller.getCurrentAccount().getSellingRecords().toString());
                System.out.println("1. back");            }

            @Override
            public void execute() {
                if (Integer.parseInt(scanner.nextLine()) == 1) {
                    parentMenu.show();
                    parentMenu.show();
                }
            }
        };
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
                        System.out.println("Enter new phoneNumber:");
                        currentAccount.setPhoneNumber(scanner.nextLine());
                        System.out.println("Field updated");
                        break;
                    case "company":
                        System.out.println("Enter new company name:");
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

    private Menu getCompanyInfo() {
        return new Menu("view company information", this) {
            @Override
            public void show() {
                System.out.println(controller.getCurrentAccount().getCompanyName() + "\n" +
                        "1. back");
            }

            @Override
            public void execute() {
                if (Integer.parseInt(scanner.nextLine()) == 1) {
                    parentMenu.show();
                    parentMenu.show();
                }
            }
        };
    }

    private Menu getViewPersonalInfo() {
        return new Menu("view personal info", this) {
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
}
