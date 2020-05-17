package view.Menu;

import model.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
        submenus.put(7, getManageRequest());
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
            default:
                System.out.println("enter a number in validate range");
                this.execute();
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
                System.out.println("Enter manager username :");
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
                controller.addUserManager(account);
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
                System.out.println(controller.viewDiscountCode(field));
            }
            else if (command.matches(regex = "edit discount code (\\S+)")) {
                (matcher = getMatcher(regex, command)).find();
                String field = matcher.group(1);
                if (controller.getDiscountByCode(field) != null) {
                    Discount discount = controller.getDiscountByCode(field);
                    System.out.println("Enter a field to edit:(starting date/ending date/" +
                            "discount percentage/max discount/add an account/remove an account)");
                    String input = scanner.nextLine();
                    switch (input) {
                        case "starting date":
                            System.out.println("Enter new date(year/month/day)");
                            String inputDate = scanner.nextLine();
                            String[] split = inputDate.split("/");
                            Date date = new Date(Integer.parseInt(split[0]),Integer.parseInt(split[1]),
                                    Integer.parseInt(split[2]));
                            discount.setStartingDate(date);
                            break;
                        case "ending date":
                            System.out.println("Enter new date(year/month/day)");
                            String inputDate1 = scanner.nextLine();
                            String[] split1 = inputDate1.split("/");
                            Date date1 = new Date(Integer.parseInt(split1[0]),Integer.parseInt(split1[1]),
                                    Integer.parseInt(split1[2]));
                            discount.setEndingDate(date1);
                            break;
                        case "discount percentage":
                            System.out.println("Enter new percentage(a positive Integer)");
                            discount.setDiscountPercentage(Integer.parseInt(scanner.nextLine()));
                            break;
                        case "max discount":
                            System.out.println("Enter new max discount(a positive Double)");
                            discount.setMaximumDiscount(scanner.nextLong());
                            break;
                        case "add an account":
                            System.out.println("Enter a user name:");
                            String userName = scanner.nextLine();
                            if (controller.getAccountByUsername(userName) != null) {
                                Account account = controller.getAccountByUsername(userName);
                                List<Discount> help = new ArrayList<>(account.getDiscountCodes());
                                List<Account> accountList = new ArrayList<>(discount.getIncludedPeople());
                                help.add(discount);
                                accountList.add(account);
                                account.setDiscountCodes(help);
                                discount.setIncludedPeople(accountList);
                            }
                            else
                                System.out.println("username is invalid");
                            break;
                        case "remove an account":
                            System.out.println("Enter a username");
                            String username = scanner.nextLine();
                            if (controller.getAccountByUsername(username) != null) {
                                Account account = controller.getAccountByUsername(username);
                                List<Discount> discountList = new ArrayList<>(account.getDiscountCodes());
                                List<Account> accountList = new ArrayList<>(discount.getIncludedPeople());
                                discountList.remove(discount);
                                accountList.remove(account);
                                account.setDiscountCodes(discountList);
                                discount.setIncludedPeople(accountList);
                                System.out.println("Successfully removed");
                            }
                            else
                                System.out.println("username is invalid");
                            break;
                        default:
                            System.out.println("invalid field");
                    }
                }
                else
                    System.out.println("code is invalid");
            }
            else if (command.matches(regex = "remove discount code (\\S+)")) {
                (matcher = getMatcher(regex, command)).find();
                String field = matcher.group(1);
                if (controller.getDiscountByCode(field) != null) {
                    Discount discount = controller.getDiscountByCode(field);
                    controller.removeDiscountCode(discount);
                }
                else
                    System.out.println("code is invalid");
            }
            else
                System.out.println("invalid command");
        }
    }


    private void manageCategory() {
        String command;
        String regex;
        Matcher matcher;
        while (!(command = scanner.nextLine()).equalsIgnoreCase("back")) {
            if (command.matches(regex = "edit (\\S+)")) {
                (matcher = getMatcher(regex, command)).find();
                System.out.println("Enter new name:");
                String newName = scanner.nextLine();
                System.out.println("Enter new description:");
                String newDescription = scanner.nextLine();
                String field = matcher.group(1);
                controller.editCategory(field,newName,newDescription);
            }
            else if (command.matches(regex = "add (\\S+)")) {
                (matcher = getMatcher(regex, command)).find();
                String field = matcher.group(1);
                System.out.println("add description for " + field);
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
                    default:
                        System.out.println("Enter a validate number");
                        this.execute();
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
                        "1. view [username]/delete user [username]/create manager profile" + "\n"
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
                    default:
                        System.out.println("Enter a validate number");
                        this.execute();
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
                        "1. remove [product id]" + "\n"
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
                    default:
                        System.out.println("Enter a validate number");
                        this.execute();
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
                        createDiscountCode();
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

    private void createDiscountCode() {
        boolean continueLoop = true;
        do {
            try {
                System.out.println("Please Enter Discount Code:");
                String code = scanner.nextLine();
                System.out.println("Please Enter starting date:(year/month/day)");
                String startingDate = scanner.nextLine();
                String[] split1 = startingDate.split("/");
                Date start = new Date(Integer.parseInt(split1[0]),Integer.parseInt(split1[1]),
                        Integer.parseInt(split1[2]));
                System.out.println("Please Enter ending date:(year/month/day)");
                String endingDate = scanner.nextLine();
                String[] split2 = endingDate.split("/");
                Date end = new Date(Integer.parseInt(split2[0]),Integer.parseInt(split2[1]),
                        Integer.parseInt(split2[2]));
                System.out.println("Please Enter discount percentage:(a positive Integer lower than 100)");
                int percent = Integer.parseInt(scanner.nextLine());
                System.out.println("Please Enter maximum discount amount:");
                long max = Long.parseLong(scanner.nextLine());
                continueLoop = false;
                controller.createDiscountCode(code,start,end,percent,max);
            }
            catch (NumberFormatException numberFormatException) {
                System.out.println("input should be a number\n" +
                        "try again");
            }
            catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
                System.out.println("please Enter the date like this pattern (year/month/day)\n" +
                        "try again");
            }
        } while (continueLoop);
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
                System.out.println(controller.getDiscountList());
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
                    default:
                        System.out.println("Enter a validate number");
                        this.execute();
                }
            }
        };
    }

    private Menu getManageRequest() {
        return new Menu("Manage Request",this) {
            @Override
            public void show() {
                for (Request request : Request.requests) {
                    System.out.println("Request: " + request.getDetails() + "  From: " + request.getAccount() + "  request ID: "
                            + request.getRequestId());
                }
                System.out.println("1.show details\n" +
                        "2.accept request\n" +
                        "3.decline request\n" +
                        "4.back");
            }

            @Override
            public void execute() {
                switch (scanner.nextLine()) {
                    case "1":
                        showRequestDetails();
                        this.show();
                        this.execute();
                    case "2":
                        acceptRequest();
                        this.show();
                        this.execute();
                    case "3":
                        declineRequest();
                        this.show();
                        this.execute();
                    case "4":
                        parentMenu.show();
                        parentMenu.execute();
                    default:
                        System.out.println("Enter a validate number");
                        this.execute();
                }
            }
        };
    }

    private void declineRequest() {
        String command;
        String regex;
        Matcher matcher;
        Request request;
        while (!(command = scanner.nextLine()).equalsIgnoreCase("back")) {
            if (command.matches(regex = "decline (\\S+)")) {
                (matcher = getMatcher(regex, command)).find();
                String id = matcher.group(1);
                request = Request.getRequestById(id);
                if (request != null) {
                    request.acceptRequest();
                    System.out.println("Request declined");
                } else {
                    System.out.println("invalid id");
                }
            } else if (command.equals("help")){
                System.out.println("decline [requestId]" + "\n"
                        + "help" + "\n"
                        + "back");
            } else {
                System.out.println("invalid command");
            }
        }
    }

    private void acceptRequest() {
        String command;
        String regex;
        Matcher matcher;
        Request request;
        while (!(command = scanner.nextLine()).equalsIgnoreCase("back")) {
            if (command.matches(regex = "accept (\\S+)")) {
                (matcher = getMatcher(regex, command)).find();
                String id = matcher.group(1);
                request = Request.getRequestById(id);
                if (request != null) {
                    request.acceptRequest();
                    System.out.println("Request accepted");
                } else {
                    System.out.println("invalid id");
                }
            } else if (command.equals("help")){
                System.out.println("accept [requestId]" + "\n"
                        + "help" + "\n"
                        + "back");
            } else {
                System.out.println("invalid command");
            }
        }
    }

    private void showRequestDetails() {
        String command;
        String regex;
        Matcher matcher;
        Request request;
        while (!(command = scanner.nextLine()).equalsIgnoreCase("back")) {
            if (command.matches(regex = "details (\\S+)")) {
                (matcher = getMatcher(regex, command)).find();
                String id = matcher.group(1);
                request = Request.getRequestById(id);
                if (request != null) {
                    System.out.println("Request: " + request.getDetails() + "  From: " + request.getAccount() + "  request ID: "
                            + request.getRequestId());
                } else {
                    System.out.println("invalid id");
                }
            } else if (command.equals("help")){
                System.out.println("details [requestId]" + "\n"
                        + "help" + "\n"
                        + "back");
            } else {
                System.out.println("invalid command");
            }
        }
    }

    private Menu getManageCategory() {
        return new Menu("Manage Category",this) {

            @Override
            public void show() {
                System.out.println("Manage Categories:");
                System.out.println("1.edit/add/remove [category name]\n" +
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
                    default:
                        System.out.println("Enter a validate number");
                        this.execute();
                }
            }
        };
    }
}


