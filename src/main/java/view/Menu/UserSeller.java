package view.Menu;

import model.*;

import java.util.ArrayList;
import java.util.Date;
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
        submenus.put(9, viewOffs());
        submenus.put(10, viewBalance());

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
                    break;
                case "7":
                    submenus.get(7).show();
                    submenus.get(7).execute();
                    break;
                case "8":
                    submenus.get(8).show();
                    submenus.get(8).execute();
                    break;
                case "9":
                    submenus.get(9).show();
                    submenus.get(9).execute();
                    break;
                case "10":
                    submenus.get(10).show();
                    submenus.get(10).execute();
                    break;
                case "11":
                    parentMenu.show();
                    parentMenu.execute();
                    break;
                default:
                    System.out.println("enter a number in validate range");
                    this.execute();
            }

    }

    private Menu viewBalance() {
        return new Menu("view balance", this) {
            @Override
            public void show() {
                System.out.println("Your current balance is: " + controller.viewBalance());
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

    private void addOff() {
        String command;
        String regex;
        Matcher matcher;
        ArrayList<Product> products = new ArrayList<>();
        Date startingDate;
        Date endingDate;
        int discountPercentage;
        System.out.println("Enter products id's you want to add then enter finish");
        while (!(command = scanner.nextLine()).equalsIgnoreCase("finish")) {
            if (controller.getProductById(command) == null) {
                System.out.println("invalid id");
            } else {
                if (controller.getProductById(command).isInSale()) {
                    System.out.println("Product is already in a sale");
                } else {
                    controller.putProductInSale(command);
                    products.add(controller.getProductById(command));
                }
            }
        }
        System.out.println("Enter a starting date in format YYYY-MM-DD:");
        while (!(command = scanner.nextLine()).matches(regex = "(\\d\\d\\d\\d)-(\\d\\d)-(\\d\\d)")) {
            System.out.println("invalid format");
        }
        (matcher = getMatcher(regex, command)).find();
        startingDate = new Date(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2)), Integer.parseInt(matcher.group(3)));
        System.out.println("Enter an ending date in format YYYY-MM-DD:");
        while (!(command = scanner.nextLine()).matches(regex = "(\\d\\d\\d\\d)-(\\d\\d)-(\\d\\d)")) {
            System.out.println("invalid format");
        }
        (matcher = getMatcher(regex, command)).find();
        endingDate = new Date(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2)), Integer.parseInt(matcher.group(3)));
        System.out.println("Enter discount percentage:");
        discountPercentage = Integer.parseInt(scanner.nextLine());
        while (discountPercentage <= 0 || discountPercentage >= 100) {
            System.out.println("Enter a valid number between 0 and 100!");
            discountPercentage = Integer.parseInt(scanner.nextLine());
        }
        String[] inputs = new String[1];
        inputs[0] = generateId();
        controller.createOff(inputs[0], products, startingDate, endingDate, discountPercentage);
        new SellerRequest(controller. getCurrentAccount(), generateId(), inputs, "add sale");
        System.out.println("Sale created");
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
        String command;
        String regex;
        Matcher matcher;
        Sale sale;
        while (!(command = scanner.nextLine()).equalsIgnoreCase("back")) {
            if (command.matches(regex = "edit (\\S+)")) {
                (matcher = getMatcher(regex, command)).find();
                sale = controller.getSaleById(matcher.group(1));
                if (sale != null) {
                    System.out.println("Enter a field: \n" +
                            "1. products \n" +
                            "2. starting Date \n" +
                            "3. ending Date \n" +
                            "4. discount percentage"
                    );
                    switch (Integer.parseInt(scanner.nextLine())) {
                        case 1:
                            editSaleProducts(sale);
                            break;
                        case 2:
                            editStartingDate(sale);
                            break;
                        case 3:
                            editEndingDate(sale);
                            break;
                        case 4:
                            editDiscountPercentage(sale);
                    }
                } else {
                    System.out.println("invalid id");
                }
            } else if (command.equals("help")){
                System.out.println("edit [offId]" + "\n"
                        + "help" + "\n"
                        + "back");

            } else {
                System.out.println("invalid command");
            }
        }
    }

    private void editDiscountPercentage(Sale sale) {
        System.out.println("Current discount percentage is :" + sale.getDiscountPercentage() + "%");
        System.out.println("Enter new discount percentage:");
        int discountPercentage = Integer.parseInt(scanner.nextLine());
        while (discountPercentage <= 0 || discountPercentage >= 100) {
            System.out.println("Enter a valid number between 0 and 100!");
            discountPercentage = Integer.parseInt(scanner.nextLine());
        }
        String[] inputs = new String[2];
        inputs[0] = String.valueOf(discountPercentage);
        inputs[1] = sale.getOffId();
        new SellerRequest(controller.getCurrentAccount(), generateId(), inputs, "edit discount percentage of sale");
        sale.setSaleStatus(SaleStatus.UNDER_REFORMATION);
        System.out.println("Request sent");
    }

    private void editEndingDate(Sale sale) {
        Matcher matcher;
        String regex;
        System.out.println("Current ending date is: " + sale.getStartingDate());
        System.out.println("Enter new ending date in format YYYY-MM-DD:");
        String date = scanner.nextLine();
        while (!date.matches(regex = "(\\d\\d\\d\\d)-(\\d\\d)-(\\d\\d)")) {
            System.out.println("invalid format");
            date = scanner.nextLine();
        }
        (matcher = getMatcher(regex, date)).find();
        String[] inputs = new String[4];
        inputs[0] = matcher.group(1);
        inputs[1] = matcher.group(2);
        inputs[2] = matcher.group(3);
        inputs[3] = sale.getOffId();
        new SellerRequest(controller.getCurrentAccount(), generateId(), inputs, "edit ending date of sale");
        sale.setSaleStatus(SaleStatus.UNDER_REFORMATION);
        System.out.println("Request sent");
    }

    private void editStartingDate(Sale sale) {
        Matcher matcher;
        String regex;
        System.out.println("Current Starting date is: " + sale.getStartingDate());
        System.out.println("Enter new starting date in format YYYY-MM-DD:");
        String date = scanner.nextLine();
        while (!date.matches(regex = "(\\d\\d\\d\\d)-(\\d\\d)-(\\d\\d)")) {
            System.out.println("invalid format");
            date = scanner.nextLine();
        }
        (matcher = getMatcher(regex, date)).find();
        String[] inputs = new String[4];
        inputs[0] = matcher.group(1);
        inputs[1] = matcher.group(2);
        inputs[2] = matcher.group(3);
        inputs[3] = sale.getOffId();
        new SellerRequest(controller.getCurrentAccount(), generateId(), inputs, "edit starting date of sale");
        sale.setSaleStatus(SaleStatus.UNDER_REFORMATION);
        System.out.println("Request sent");
    }

    private void editSaleProducts(Sale sale) {
        System.out.println("Current sale products:");
        for (Product product : sale.getProducts()) {
            System.out.println(product.getName() + " : " + product.getProductId());
        }
        System.out.println("1. add product\n" +
                "2. remove product");
        switch (scanner.nextLine()) {
            case "1":
                System.out.println("Enter product's id:");
                String id = scanner.nextLine();
                if (controller.getProductById(id) == null) {
                    System.out.println("invalid id");
                } else {
                    String[] inputs = new String[2];
                    inputs[0] = id;
                    inputs[1] = sale.getOffId();
                    new SellerRequest(controller.getCurrentAccount(), generateId(),inputs, "add product to sale");
                    sale.setSaleStatus(SaleStatus.UNDER_REFORMATION);
                }
                break;
            case "2":
                System.out.println("Enter product's id:");
                id = scanner.nextLine();
                if (controller.getProductById(id) == null) {
                    System.out.println("invalid id");
                } else {
                    String[] inputs = new String[2];
                    inputs[0] = id;
                    inputs[1] = sale.getOffId();
                    new SellerRequest(controller.getCurrentAccount(), generateId(), inputs, "remove product from sale");
                    sale.setSaleStatus(SaleStatus.UNDER_REFORMATION);
                    System.out.println("Request sent");
                }
        }
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
                System.out.println("Enter the category of the product or enter skip:\n" +
                        "available categories:");
                for (Category category : controller.getCategories()) {
                    System.out.println(category.getName());
                }
                inputs[7] = scanner.nextLine();
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
                            "6. amount\n" +
                            "7. category"
                    );
                    inputs[0] = product.getProductId();
                    switch (Integer.parseInt(scanner.nextLine())) {
                        case 1:
                            System.out.println("Enter new name:");
                            inputs[1] = scanner.nextLine();
                            new SellerRequest(account, generateId(), inputs, "edit name of product");
                            product.setProductStatus(ProductStatus.UNDER_REFORMATION);
                            System.out.println("Request sent");
                            break;
                        case 2:
                            System.out.println("Enter new brand or company:");
                            inputs[1] = scanner.nextLine();
                            new SellerRequest(account, generateId(), inputs, "edit company of product");
                            product.setProductStatus(ProductStatus.UNDER_REFORMATION);
                            System.out.println("Request sent");
                            break;
                        case 3:
                            System.out.println("Enter new description:");
                            inputs[1] = scanner.nextLine();
                            new SellerRequest(account, generateId(), inputs, "edit description of product");
                            product.setProductStatus(ProductStatus.UNDER_REFORMATION);
                            System.out.println("Request sent");
                            break;
                        case 4:
                            System.out.println("1. enable product\n" +
                                    "2.disable product");
                            inputs[1] = scanner.nextLine();
                            new SellerRequest(account, generateId(), inputs, "edit availability of product");
                            product.setProductStatus(ProductStatus.UNDER_REFORMATION);
                            System.out.println("Request sent");
                            break;
                        case 5:
                            System.out.println("Enter new price:");
                            inputs[1] = scanner.nextLine();
                            new SellerRequest(account, generateId(), inputs, "edit price of product");
                            product.setProductStatus(ProductStatus.UNDER_REFORMATION);
                            System.out.println("Request sent");
                            break;
                        case 6:
                            System.out.println("Enter new amount:");
                            inputs[1] = scanner.nextLine();
                            new SellerRequest(account, generateId(), inputs, "edit amount of product");
                            product.setProductStatus(ProductStatus.UNDER_REFORMATION);
                            System.out.println("Request sent");
                            break;
                        case 7:
                            System.out.println("Enter new category:");
                            command = scanner.nextLine();
                            if (controller.getCategoryByName(command) != null) {
                                inputs[1] = command;
                                new SellerRequest(account, generateId(), inputs, "edit category of product");
                                product.setProductStatus(ProductStatus.UNDER_REFORMATION);
                                System.out.println("Request sent");
                            } else {
                                System.out.println("Category not found");
                            }
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
                System.out.println("1. back");
            }

            @Override
            public void execute() {
                if (Integer.parseInt(scanner.nextLine()) == 1) {
                    parentMenu.show();
                    parentMenu.execute();
                }
                else {
                    System.out.println("Enter a validate number");
                    this.execute();
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
                    parentMenu.execute();
                }
                else {
                    System.out.println("Enter a validate number");
                    this.execute();
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
                    default:
                        System.out.println("Enter a validate number");
                        this.execute();
                }
            }
        };
    }
}
