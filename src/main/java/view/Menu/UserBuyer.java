package view.Menu;

import model.Account;
import model.Discount;
import model.Product;

import java.util.HashMap;
import java.util.Objects;
import java.util.regex.Matcher;

public class UserBuyer extends Menu{

    private final Menu currentMenu;

    public UserBuyer(String name, Menu parentMenu) {
        super(name, parentMenu);
        HashMap<Integer, Menu> submenus = new HashMap<>();
        submenus.put(1, new LoginMenu(parentMenu));
        submenus.put(2, getViewPersonalInfo());
        submenus.put(3, getViewOrders());
        submenus.put(4, getViewBalance());
        submenus.put(5, getViewDiscountCodes());
        submenus.put(6, getViewCart());
        this.setSubmenus(submenus);
        currentMenu = this;
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
                getViewOrders().show();
                getViewOrders().execute();
                break;
            case "4":
                getViewBalance().show();
                getViewBalance().execute();
                break;
            case "5":
                getViewDiscountCodes().show();
                getViewDiscountCodes().execute();
                break;
            case "6":
                getViewCart().show();
                getViewCart().execute();
                break;
            case "7":
                this.parentMenu.show();
                this.parentMenu.execute();
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

    private void manageOrders() {
        String command;
        String regex;
        Matcher matcher;
        while (!(command = scanner.nextLine()).equalsIgnoreCase("back")) {
            if (command.matches(regex = "show order (\\S+)")) {
                (matcher = getMatcher(regex, command)).find();
                String field = matcher.group(1);
                System.out.println(controller.showOrder(field));
            }
            else if (command.matches(regex = "rate (\\S+) (\\d)")) {
                (matcher = getMatcher(regex, command)).find();
                String field1 = matcher.group(1);
                String field2 = matcher.group(2);
                controller.rateProduct(field1,Integer.parseInt(field2));
            }
        }
    }

    private Menu manageCart(Menu parentMenu) {
        String command;
        String regex;
        Matcher matcher;
        while (!(command = scanner.nextLine()).equalsIgnoreCase("back")) {
            if (command.matches("show products")) {
                for (Product product : controller.viewCart().keySet()) {
                    System.out.println(product.getName() + product.getProductId() + controller.viewCart().get(product));
                }
            }
            else if (command.matches(regex = "view (\\S+)")) {
                (matcher = getMatcher(regex, command)).find();
                String field1 = matcher.group(1);
                GoodMenu goodMenu = new GoodMenu(parentMenu,null);
                goodMenu.setGoodId(field1);
                goodMenu.show();
                goodMenu.execute();
            }
            else if (command.matches(regex = "increase (\\S+)")) {
                (matcher = getMatcher(regex, command)).find();
                String field = matcher.group(1);
                controller.increaseProduct(field);
            }
            else if (command.matches(regex = "decrease (\\S+)")) {
                (matcher = getMatcher(regex, command)).find();
                String field = matcher.group(1);
                controller.decreaseProduct(field);
            }
            else if (command.matches("show total price")) {
                System.out.println(controller.showTotalPrice());
            }
            else if (command.matches("purchase")) {
                return new Menu("purchase Menu",parentMenu) {
                    @Override
                    public void show() {
                        System.out.println("Purchase Menu:");
                        System.out.println("1.Next(receiving info)\n" +
                                "2.back");
                    }

                    @Override
                    public void execute() {
                        switch (Integer.parseInt(scanner.nextLine())) {
                            case 1:
                                Menu menu = receiveInfo(this);
                                menu.show();
                                menu.execute();
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
        return null;
    }

    private Menu receiveInfo(Menu parentMenu) {
        return new Menu("Receive Info Menu",parentMenu) {
            @Override
            public void show() {
                System.out.println("Purchase->Receive Info Menu:");
                System.out.println("1.Next(receiving Discount Code\n)" +
                        "2.back");
            }

            @Override
            public void execute() {
                System.out.println("Enter your Address:");
                String address = scanner.nextLine();
                System.out.println("Enter your phone Number:");
                String phoneNumber = scanner.nextLine();
                switch (Integer.parseInt(scanner.nextLine())) {
                    case 1:
                        Menu menu = discountConfirm(this,address,phoneNumber);
                        menu.show();
                        menu.execute();
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

    private Menu discountConfirm(Menu parentMenu,String address,String phoneNumber) {
        return new Menu("discountCode Confirmation",parentMenu) {
            @Override
            public void show() {
                System.out.println("Purchase->Receive Info->Discount Code Confirmation Menu:");
            }

            @Override
            public void execute() {
                System.out.println("Enter your discount code:(or type \"no discount code\" to continue)");
                String discount = scanner.nextLine();
                Discount discount1 = null;
                if (!discount.equals("no discount code")) {
                    if (controller.discountCodeConfirmation(discount)) {
                        System.out.println("Discount Code Confirmed.");
                        discount1 = controller.getDiscountByCode(discount);
                    }
                    else
                        System.out.println("Discount Code Denied.");
                }
                System.out.println("1.Next(payment)\n" +
                        "2.back");
                switch (Integer.parseInt(scanner.nextLine())) {
                    case 1:
                        Menu menu = payment(this,address,phoneNumber,discount1);
                        menu.show();
                        menu.execute();
                        break;
                    case 2:
                        this.parentMenu.show();
                        this.parentMenu.execute();
                        break;
                }
            }
        };
    }

    private Menu payment(Menu parentMenu,String address,String phoneNumber,Discount discount) {
        return new Menu("paymentMenu",parentMenu) {
            @Override
            public void show() {
                System.out.println("Purchase->Receive Info->Discount Code Confirmation->Payment Menu:");
                System.out.println("[Product]-----[Number]");
                for (Product product : controller.viewCart().keySet()) {
                    System.out.println(product.getName() + "-----" + controller.viewCart().get(product));
                }
                System.out.println("---------------------");
                System.out.println("Total Price:" + controller.showTotalPrice());
                System.out.println("Are you sure to pay" + controller.showTotalPrice() + "?");
                System.out.println("1.YES\n" +
                        "2.back");
            }

            @Override
            public void execute() {
                switch (Integer.parseInt(scanner.nextLine())) {
                    case 1:
                        controller.purchase(address,phoneNumber,discount);
                        currentMenu.show();
                        currentMenu.execute();
                        break;
                    case 2:
                        this.parentMenu.show();
                        this.parentMenu.execute();
                        break;
                }
            }
        };
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

    private Menu getViewOrders() {
        return new Menu("view orders",this) {
            @Override
            public void show() {
                System.out.println("View Orders Menu:");
                System.out.println("1.show order [orderId]/rate [productId] [1-5]");
                System.out.println(controller.viewOrders());
            }

            @Override
            public void execute() {
                switch (Integer.parseInt(scanner.nextLine())) {
                    case 1:
                        manageOrders();
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

    private Menu getViewBalance() {
        return new Menu("View Balance",this) {
            @Override
            public void show() {
                System.out.println("View Balance:");
                System.out.println("1.back");
                System.out.println(controller.viewBalance());
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

    private Menu getViewDiscountCodes() {
        return new Menu("View Discount Code:",this) {
            @Override
            public void show() {
                System.out.println("View Discount Code:");
                System.out.println("1.back");
                System.out.println(controller.getCurrentAccount().getDiscountCodes());
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

    private Menu getViewCart() {
        return new Menu("View Cart",this) {

            @Override
            public void show() {
                System.out.println("View Cart:");
                System.out.println("1.show products/view [productId]/increase [productId]/decrease [productId]" +
                        "show price/purchase\n" +
                        "2.back");
            }

            @Override
            public void execute() {
                switch (Integer.parseInt(scanner.nextLine())) {
                    case 1:
                        Menu menu = manageCart(this);
                        if (menu != null) {
                            Objects.requireNonNull(menu).show();
                            Objects.requireNonNull(menu).execute();
                        }
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
