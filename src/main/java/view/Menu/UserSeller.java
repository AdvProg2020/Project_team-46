package view.Menu;

import model.*;

import java.util.regex.Matcher;

public class UserSeller extends Menu{
    public UserSeller(String name, Menu parentMenu) {

        super(name, parentMenu);
        submenus.put(1, new LoginMenu(parentMenu));
        submenus.put(2, getViewPersonalInfo());
        submenus.put(3, getCompanyInfo());
        submenus.put(4, getSalesHistory());
        submenus.put(5, getManageProducts());
        submenus.put(6, addProduct());
        submenus.put(7, removeProduct());
        submenus.put(8, showCategories());

    }


    @Override
    public void execute() {
        String command;
            command = scanner.nextLine();
            switch (command) {
                case "1":
                    submenus.get(1).show();
                    submenus.get(1).execute();
                    break;
                case "2":
                    submenus.get(2).show();
                    submenus.get(2).execute();
                    break;
                case "3":
                    submenus.get(3).show();
                    submenus.get(3).execute();
                    break;
                case "4":
                    submenus.get(4).show();
                    submenus.get(4).execute();
                    break;
                case "5":
                    submenus.get(5).show();
                    submenus.get(5).execute();
                    break;
                case "6":
                    submenus.get(6).show();
                    submenus.get(6).execute();
            }
    }

    private Menu viewOffs() {
        return new Menu("view offs", this) {
            @Override
            public void show() {
                for (String off : controller.viewSellersOff()) {
                    System.out.println(off);
                }
                System.out.println("1. view off\n" +
                        "2. edit off\n" +
                        "3. add off\n" +
                        "4. back");
            }

            @Override
            public void execute() {
                switch (scanner.nextLine()) {
                    case "1":
                        viewOff();
                        this.show();
                        this.execute();
                        break;
                    case  "2":
                        editOff();
                        this.show();
                        this.execute();
                        break;
                    case "3":
                        addOff();
                        this.show();
                        this.execute();
                        break;
                    case "4":
                        parentMenu.show();
                        parentMenu.execute();
                }
            }
        };
    }

    private void viewOff() {
        String command;
        String regex;
        Matcher matcher;
        while (!(command = scanner.nextLine()).equalsIgnoreCase("back")) {
            if (command.matches(regex = "view off (\\S+)")) {
                (matcher = getMatcher(regex, command)).find();
                String id = matcher.group(1);
                if (controller.getSaleById(id) != null) {
                    System.out.println(controller.getSaleById(id).toString());
                } else {
                    System.out.println("invalid id");
                }
            } else if (command.equals("help")){
                System.out.println("view [offId]" + "\n"
                        + "help" + "\n"
                        + "back");
            } else {
                System.out.println("invalid command");
            }
        }
    }

    private void editOff() {

    }

    private void addOff() {

    }

    private Menu showCategories() {
        return new Menu("show categories", this) {
            @Override
            public void show() {
                for (Category category : controller.getCategories()) {
                    System.out.println(category.getName());
                }
                System.out.println("1. back");
            }

            @Override
            public void execute() {
                if (scanner.nextLine().equals("1")) {
                    parentMenu.show();
                    parentMenu.execute();
                }
            }
        };
    }

    private Menu removeProduct() {
        return new Menu("remove product", this) {
            @Override
            public void show() {

            }

            @Override
            public void execute() {
                String command;
                String regex;
                Matcher matcher;
                while (!(command = scanner.nextLine()).equalsIgnoreCase("back")) {
                    if (command.matches(regex = "remove product (\\S+)")) {
                        (matcher = getMatcher(regex, command)).find();
                        String id = matcher.group(1);
                        if (controller.getProductById(id) != null) {
                             controller.removeProduct(id);
                            System.out.println("product removed successfully");
                        } else {
                            System.out.println("invalid id");
                        }
                    } else if (command.equals("help")){
                        System.out.println("remove product [productId]" + "\n"
                                + "help" + "\n"
                                + "back");
                    } else {
                        System.out.println("invalid command");
                    }
                }
                parentMenu.show();
                parentMenu.execute();
            }
        };
    }

    private Menu addProduct() {
        return new Menu("add product", this) {
            @Override
            public void show() {

            }

            @Override
            public void execute() {
                String[] inputs = new String[10];
                Account account = controller.getCurrentAccount();
                System.out.println("Enter name of product");
                inputs[0] = scanner.nextLine();
                System.out.println("Enter brand or company of product:");
                inputs[1] = scanner.nextLine();
                System.out.println("Enter price of the product:");
                inputs[2] = scanner.nextLine();
                System.out.println("Enter amount or number of the product:");
                inputs[3] = scanner.nextLine();
                System.out.println("Enter description of product:");
                inputs[4] = scanner.nextLine();
                System.out.println("Is the product available?\n" +
                        "1. yes\n" +
                        "2. no");
                inputs[5] = scanner.nextLine();
                inputs[6] = generateId();
                controller.createProduct(inputs, account);
                new SellerRequest(account, generateId(), inputs, "add product");
                System.out.println("product created");
                parentMenu.show();
                parentMenu.execute();
            }
        };
    }

    private Menu getManageProducts() {
        return new Menu("manage products", this) {
            @Override
            public void show() {
                for (Product product : controller.getAvailableProducts()) {
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
        Account account = controller.getCurrentAccount();
        String[] inputs = new String[2];
        while (!(command = scanner.nextLine()).equalsIgnoreCase("back")) {
            if (command.matches(regex = "edit (\\S+)")) {
                (matcher = getMatcher(regex, command)).find();
                product =  controller.getProductById(matcher.group(1));
                if (product != null) {
                    System.out.println("Enter a field: \n" +
                            "1. name \n" +
                            "2. brand or company \n" +
                            "3. description \n" +
                            "4. availability\n" +
                            "5. value\n" +
                            "6. amount"
                    );
                    inputs[0] = product.getProductId();
                    switch (Integer.parseInt(scanner.nextLine())) {
                        case 1:
                            System.out.println("Enter new name:");
                            inputs[1] = scanner.nextLine();
                            new SellerRequest(account, generateId(), inputs, "edit name of product");
                            product.setProductStatus(ProductStatus.UNDER_REFORMATION);
                            break;
                        case 2:
                            System.out.println("Enter new brand or company:");
                            inputs[1] = scanner.nextLine();
                            new SellerRequest(account, generateId(), inputs, "edit company of product");
                            product.setProductStatus(ProductStatus.UNDER_REFORMATION);
                            break;
                        case 3:
                            System.out.println("Enter new description:");
                            inputs[1] = scanner.nextLine();
                            new SellerRequest(account, generateId(), inputs, "edit description of product");
                            product.setProductStatus(ProductStatus.UNDER_REFORMATION);
                            break;
                        case 4:
                            System.out.println("1. enable product\n" +
                                    "2.disable product");
                            inputs[1] = scanner.nextLine();
                            new SellerRequest(account, generateId(), inputs, "edit availability of product");
                            product.setProductStatus(ProductStatus.UNDER_REFORMATION);
                            break;
                        case 5:
                            System.out.println("Enter new price:");
                            inputs[1] = scanner.nextLine();
                            new SellerRequest(account, generateId(), inputs, "edit price of product");
                            product.setProductStatus(ProductStatus.UNDER_REFORMATION);
                            break;
                        case 6:
                            System.out.println("Enter new amount:");
                            inputs[1] = scanner.nextLine();
                            new SellerRequest(account, generateId(), inputs, "edit amount of product");
                            product.setProductStatus(ProductStatus.UNDER_REFORMATION);
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
                    for (String buyer : controller.viewBuyers(id)) {
                        System.out.println(buyer);
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
                    System.out.println(controller.viewProduct(id));
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
