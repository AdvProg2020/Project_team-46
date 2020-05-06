package view.Menu;

import model.Account;
import model.Product;
import model.ProductStatus;
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
        label:
        while (true) {
            command = scanner.nextLine();
            switch (command) {
                case "1":
                    submenus.get(1).show();
                    submenus.get(1).execute();
                    break label;
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
            }

        }
    }

    private Menu addProduct() {
        return new Menu("add product", this) {
            @Override
            public void show() {

            }

            @Override
            public void execute() {
                String[] inputs = new String[5];
                Account account = controller.getCurrentAccount();
                System.out.println("Enter name of product");
                inputs[0] = scanner.nextLine();
                System.out.println("Enter brand or company of product:");
                inputs[1] = scanner.nextLine();
                System.out.println("Enter description of product:");
                inputs[2] = scanner.nextLine();
                System.out.println("Is the product available?\n" +
                        "1. yes" +
                        "2. no");
                inputs[3] = scanner.nextLine();
                inputs[4] = generateId();
                new SellerRequest(account, generateId(), inputs, "add product");
            }
        };
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
        Account account = controller.getCurrentAccount();
        String[] inputs = new String[2];
        while (!(command = scanner.nextLine()).equalsIgnoreCase("back")) {
            if (command.matches(regex = "edit (\\S+)")) {
                (matcher = getMatcher(regex, command)).find();
                product =  controller.getProductById(matcher.group(1));
                inputs[0] = product.getProductId();
                if (product != null) {
                    System.out.println("Enter a field: \n" +
                            "1. name \n" +
                            "2. brand or company \n" +
                            "3. description \n" +
                            "4. availability"
                    );
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
