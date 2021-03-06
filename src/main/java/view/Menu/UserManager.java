package view.Menu;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.*;

import java.util.ArrayList;
import java.util.Date;
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

    private void editPersonalInfo(String command,String input) {
        Account currentAccount = controller.getCurrentAccount();
        switch (command) {
            case "password":
                currentAccount.setPassword(input);
                break;
            case "name":
                currentAccount.setName(input);
                break;
            case "lastName":
                currentAccount.setLastName(input);
                break;
            case "email":
                currentAccount.setEmail(input);
                break;
            case "address":
                currentAccount.setAddress(input);
                break;
            case "phoneNumber":
                currentAccount.setPhoneNumber(input);
                break;
            case "company":
                currentAccount.setCompanyName(input);
                break;
            case "balance":
                currentAccount.setBalance(Long.parseLong(input));
                break;
        }


    }

    public void register(String password, String name, String last, String email, String address, String phone,
                         String balance, String company, String username, String role, Label error) {

        if (controller.getAccountByUsername(username) != null) {
            error.setText("User is already taken");
        }
        else {
            controller.createAccount(username, role);
            controller.setPassword(password);
            controller.setName(name);
            controller.setLastName(last);
            controller.setEmail(email);
            controller.setAddress(address);
            controller.setPhoneNumber(phone);
            controller.setBalance(Long.parseLong(balance));
            controller.setCompanyName(company);
            try {
                this.parentMenu.start(new Stage());
            } catch (Exception e) {
                e.printStackTrace();
            }
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

    private Menu getViewPersonalInfo() {
        return new Menu("view personal info",this) {

            @Override
            public void start(Stage primaryStage) throws Exception {
                VBox layout = new VBox(20);
                VBox editLayout = new VBox(20);
                Scene editScene = new Scene(editLayout,200,200);
                Scene scene = new Scene(layout,200,200);

                HBox passBox = new HBox(20,new Label("password:"));
                PasswordField passwordField = new PasswordField();
                passBox.getChildren().addAll(passwordField);
                HBox nameBox = new HBox(20,new Label("name"));TextField nameField = new TextField();
                nameBox.getChildren().addAll(nameField);
                HBox lastBox = new HBox(20,new Label("last name"));TextField lastField = new TextField();
                lastBox.getChildren().addAll(lastField);
                HBox emailBox = new HBox(20,new Label("E-mail"));TextField emailField = new TextField();
                emailBox.getChildren().addAll(emailField);
                HBox addressBox = new HBox(20,new Label("address"));TextField addressField = new TextField();
                addressBox.getChildren().addAll(addressField);
                HBox numberBox = new HBox(20,new Label("phone number"));TextField numberField = new TextField();
                numberBox.getChildren().addAll(numberField);
                HBox companyBox = new HBox(20,new Label("company"));TextField companyField = new TextField();
                companyBox.getChildren().addAll(companyField);
                Button updateButton = new Button("Update");Button backButton = new Button("Back");
                updateButton.setOnAction(event -> {
                    if (!passwordField.getText().isEmpty()) {
                        editPersonalInfo("password",passwordField.getText());
                        try {
                            this.parentMenu.start(primaryStage);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    if (!nameField.getText().isEmpty()) {
                        editPersonalInfo("name",nameField.getText());
                        try {
                            this.parentMenu.start(primaryStage);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    if (!lastField.getText().isEmpty()) {
                        editPersonalInfo("lastName",lastField.getText());
                        try {
                            this.parentMenu.start(primaryStage);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    if (!emailField.getText().isEmpty()) {
                        editPersonalInfo("email",emailField.getText());
                        try {
                            this.parentMenu.start(primaryStage);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    if (!addressField.getText().isEmpty()) {
                        editPersonalInfo("address",addressField.getText());
                        try {
                            this.parentMenu.start(primaryStage);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    if (!numberField.getText().isEmpty()) {
                        editPersonalInfo("phoneNumber",numberField.getText());
                        try {
                            this.parentMenu.start(primaryStage);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    if (!companyField.getText().isEmpty()) {
                        editPersonalInfo("company",companyField.getText());
                        try {
                            this.parentMenu.start(primaryStage);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                backButton.setOnAction(event -> {
                    try {
                        this.parentMenu.start(primaryStage);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
                editLayout.getChildren().addAll(passBox,nameBox,lastBox,emailBox,addressBox,numberBox,updateButton,backButton);

                Account currentAccount = controller.getCurrentAccount();
                Label info = new Label("username: " + currentAccount.getUsername() + "\n"
                        + "password: " + currentAccount.getPassword() + "\n"
                        + "name: " + currentAccount.getName() + "\n"
                        + "lastName: " + currentAccount.getLastName() + "\n"
                        + "email: " + currentAccount.getEmail() + "\n"
                        + "address: " + currentAccount.getAddress() + "\n"
                        + "phoneNumber: " + currentAccount.getPhoneNumber() + "\n"
                        + "role: " + currentAccount.getRole());
                Button editButton = new Button("Edit Field");
                editButton.setOnAction(event -> {
                    primaryStage.setScene(editScene);
                });
                Button backButton2 = new Button("Back");backButton2.setOnAction(event -> {
                    try {
                        this.parentMenu.start(new Stage());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });

                layout.getChildren().addAll(info,editButton,backButton2);
                primaryStage.setScene(scene);
                primaryStage.show();
            }
        };
    } //done

    private Menu getManageUsers() {
        return new Menu("manage users",this) {
            @Override
            public void start(Stage primaryStage) throws Exception {
                VBox layout = new VBox(20);Scene scene = new Scene(layout,200,200);
                VBox viewBox = new VBox(20);Scene viewScene = new Scene(viewBox,200,200);
                VBox managerBox = new VBox(20);Scene managerScene = new Scene(managerBox,200,200);

                for (Account account : controller.getAccounts()) {
                    HBox accountBox = new HBox(20);
                    Label infoLabel = new Label("username: " + account.getUsername() + "\nname: " + account.getName()
                            + "\nrole: " + account.getRole());
                    Button viewButton = new Button("View"); viewButton.setOnAction(event -> {
                        viewBox.getChildren().clear();
                        Label detailLabel = new Label("username: " + account.getUsername() + "\nname: " + account.getName()
                                + "\nrole: " + account.getRole() + "\nlast name: " + account.getLastName() +
                                "\nphone number: " + account.getPhoneNumber() + "\nemail: " + account.getEmail());
                        Button back1Button = new Button("Back"); back1Button.setOnAction(event1 -> {
                            primaryStage.setScene(scene);
                        });
                        viewBox.getChildren().addAll(detailLabel,back1Button);
                        primaryStage.setScene(viewScene);
                    });
                    Button deleteButton = new Button("Delete"); deleteButton.setOnAction(event -> {
                        controller.deleteUser(account.getUsername());
                    });
                    accountBox.getChildren().addAll(infoLabel,viewButton,deleteButton);
                    layout.getChildren().add(accountBox);
                }
                Button managerButton = new Button("Create new manager"); managerButton.setOnAction(event -> {
                    managerBox.getChildren().clear();
                    HBox nameBox = new HBox(20); TextField nameField = new TextField();
                    nameBox.getChildren().addAll(new Label("Enter Username: "),nameField);
                    HBox passBox = new HBox(20); PasswordField passwordField = new PasswordField();
                    passBox.getChildren().addAll(new Label("Enter password: "),passwordField);
                    HBox firstNameBox = new HBox(20); TextField firstNameField = new TextField();
                    firstNameBox.getChildren().addAll(new Label("Enter your name: "),firstNameField);
                    HBox lastBox = new HBox(20); TextField lastField = new TextField();
                    lastBox.getChildren().addAll(new Label("Enter your last name: "),lastField);
                    HBox emailBox = new HBox(20); TextField emailField = new TextField();
                    emailBox.getChildren().addAll(new Label("Enter your email"),emailField);
                    HBox addressBox = new HBox(20); TextField addressField = new TextField();
                    addressBox.getChildren().addAll(new Label("Enter your address: "),addressField);
                    HBox phoneBox = new HBox(20); TextField phoneField = new TextField();
                    phoneBox.getChildren().addAll(new Label("Enter your phone number: "),phoneField);
                    Label errorLabel = new Label();
                    Button confirmButton = new Button("Confirm"); confirmButton.setOnAction(event1 -> {
                        if (nameField.getText().isEmpty() || passwordField.getText().isEmpty() || firstNameField.getText().isEmpty() ||
                                lastField.getText().isEmpty() || emailField.getText().isEmpty()) {
                            errorLabel.setText("some required fields did not filled");
                        }
                        else {
                            register(passwordField.getText(),firstNameField.getText(),lastField.getText()
                                    ,emailField.getText(),addressField.getText(),phoneField.getText(),"0",
                                    null,nameField.getText(),"manager",errorLabel);
                            primaryStage.close();
                        }
                    });
                    Button backButton = new Button("Back"); backButton.setOnAction(event1 -> {
                        primaryStage.setScene(scene);
                    });

                    managerBox.getChildren().addAll(nameBox,passBox,firstNameBox,lastBox,emailBox,addressBox,phoneBox
                            ,confirmButton,errorLabel,backButton);
                    primaryStage.setScene(managerScene);
                });
                Button backButton = new Button("Back"); backButton.setOnAction(event -> {
                    try {
                        this.parentMenu.start(primaryStage);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
                layout.getChildren().addAll(managerButton,backButton);

                primaryStage.setScene(scene);
                primaryStage.show();
            }
        };
    } //done

    private Menu getManageProducts() {
        return new Menu("manage products",this) {
            @Override
            public void start(Stage primaryStage) throws Exception {
                VBox layout = new VBox(20);
                Scene scene = new Scene(layout,200,200);

                for (Product product : controller.getProducts()) {
                    HBox productBox = new HBox(20);
                    Label infoLabel = new Label("Product Id: " + product.getProductId() + "\nCategory: " + product.getCategory()
                            +"\nName: "+ product.getName() + "\nseller username: " + product.getSeller().getUsername());
                    Button removeButton = new Button("Remove"); removeButton.setOnAction(event -> {
                        controller.removeProduct(product.getProductId());
                    });
                    productBox.getChildren().addAll(infoLabel,removeButton);
                    layout.getChildren().add(productBox);
                }
                Button backButton = new Button("Back"); backButton.setOnAction(event -> {
                    try {
                        this.parentMenu.start(primaryStage);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });

                layout.getChildren().add(backButton);
                primaryStage.setScene(scene);
                primaryStage.show();
            }
        };
    } //done

    private Menu getCreateDiscountCode() {
        return new Menu("create discount code",this) {
            @Override
            public void start(Stage primaryStage) throws Exception {
                VBox layout = new VBox(20);
                Scene scene = new Scene(layout,200,200);

                HBox codeBox = new HBox(20); TextField codeField = new TextField();
                codeBox.getChildren().addAll(new Label("Enter Discount Code: "),codeField);
                HBox startBox = new HBox(20); TextField startField = new TextField("year/month/day");
                startBox.getChildren().addAll(new Label("Enter starting date: "),startField);
                HBox endBox = new HBox(20); TextField endField = new TextField("year/month/day");
                endBox.getChildren().addAll(new Label("Enter ending date: "),endField);
                HBox percentBox = new HBox(20); TextField percentField = new TextField();
                percentBox.getChildren().addAll(new Label("Enter Discount Percent: "),percentField);
                HBox maxBox = new HBox(20); TextField maxField = new TextField();
                maxBox.getChildren().addAll(new Label("Enter maximum discount amount: "),maxField);
                Label errorLabel = new Label();
                Button createButton = new Button("Create Discount"); createButton.setOnAction(event -> {
                    createDiscountCode(codeField.getText(),startField.getText(),endField.getText(),percentField.getText()
                            ,maxField.getText(),errorLabel);
                });
                Button backButton = new Button("Back"); backButton.setOnAction(event -> {
                    try {
                        this.parentMenu.start(primaryStage);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });

                layout.getChildren().addAll(codeBox,startBox,endBox,percentBox,maxBox,createButton,errorLabel,backButton);
                primaryStage.setScene(scene);
                primaryStage.show();
            }
        };
    } //done

    private void createDiscountCode(String code, String startingDate,
                                    String endingDate, String percentage, String maxDiscount, Label error) {
        try {
            String[] split1 = startingDate.split("/");
            Date start = new Date(Integer.parseInt(split1[0]),Integer.parseInt(split1[1]), Integer.parseInt(split1[2]));
            String[] split2 = endingDate.split("/");
            Date end = new Date(Integer.parseInt(split2[0]),Integer.parseInt(split2[1]), Integer.parseInt(split2[2]));
            int percent = Integer.parseInt(percentage);
            long max = Long.parseLong(maxDiscount);
            controller.createDiscountCode(code,start,end,percent,max);
        }
        catch (NumberFormatException numberFormatException) {
            error.setText("input should be a number\n" +
                    "try again");
        }
        catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
            error.setText("please Enter the date like this pattern (year/month/day)\n" +
                    "try again");
        }
    }

    private Menu getViewDiscountCode() {
        return new Menu("View Discount Code",this) {
            @Override
            public void start(Stage primaryStage) throws Exception {
                VBox layout = new VBox(20); VBox editLayout = new VBox(20);
                Scene scene = new Scene(layout,200,200); Scene editScene = new Scene(editLayout,200,200);
                VBox detailBox = new VBox(20); Scene detailScene = new Scene(detailBox,200,200);

                for (Discount discount : controller.getDiscountList()) {
                    HBox discountBox = new HBox(20);
                    Label infoLabel = new Label("Discount Code: " + discount.getCode() + "\nDiscount Percentage: "
                            + discount.getDiscountPercentage() + "\nMaximum Discount: " + discount.getMaximumDiscount());
                    Button detailButton = new Button("Details"); detailButton.setOnAction(event -> {
                        detailBox.getChildren().clear();
                        Label detailLabel = new Label("Discount Code: " + discount.getCode() + "\nDiscount Percentage: "
                                + discount.getDiscountPercentage() + "\nMaximum Discount: " + discount.getMaximumDiscount()
                                + "\nStarting Date: " + discount.getStartingDate() + "\nEnding Date: " + discount.getEndingDate());
                        Button backButton = new Button("Back"); backButton.setOnAction(event1 -> {
                            primaryStage.setScene(scene);
                        });
                        detailBox.getChildren().addAll(infoLabel,backButton);
                        primaryStage.setScene(detailScene);
                    });
                    Button editButton = new Button("Edit"); editButton.setOnAction(event -> {
                        editLayout.getChildren().clear();

                    });
                    Button removeButton = new Button("Remove"); removeButton.setOnAction(event -> {
                        controller.removeDiscountCode(discount);
                    });
                    discountBox.getChildren().addAll(infoLabel,detailButton,editButton,removeButton);
                    layout.getChildren().add(discountBox);
                }
                Button backButton = new Button("Back"); backButton.setOnAction(event -> {
                    try {
                        this.parentMenu.start(primaryStage);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
                layout.getChildren().add(backButton);

                primaryStage.setScene(scene);
                primaryStage.show();
            }

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
    } //to do

    private Menu getManageRequest() {
        return new Menu("Manage Request",this) {
            @Override
            public void start(Stage primaryStage) throws Exception {
                VBox layout = new VBox(20); VBox detailBox = new VBox(20);
                Scene scene = new Scene(layout,200,200);
                Scene detailScene = new Scene(detailBox,200,200);

                for (Request request : Request.requests) {
                    HBox requestBox = new HBox(20);
                    Label infoLabel = new Label();
                    infoLabel.setText(infoLabel.getText() + "\n" + "Request: "
                            + request.getDetails() + "  From: " + request.getAccount().getUsername() + "  request ID: "
                            + request.getRequestId());
                    Button acceptButton = new Button("Accept"); acceptButton.setOnAction(event -> {
                        request.acceptRequest();
                    });
                    Button declineButton = new Button("Decline"); declineButton.setOnAction(event -> {
                        request.declineRequest();
                    });
                    Button detailButton = new Button("Details"); detailButton.setOnAction(event -> {
                        detailBox.getChildren().clear();
                        Label detailLabel = new Label("Request: "
                                + request.getDetails() + "\nFrom: " + request.getAccount().getUsername() + "\nrequest ID: "
                                + request.getRequestId() + "\nSender Email: " + request.getAccount().getEmail() + "\nCompany: "
                                + request.getAccount().getCompanyName() + "\nSender Phone Number: " + request.getAccount().getPhoneNumber());
                        Button backButton = new Button("Back"); backButton.setOnAction(event1 -> {
                            primaryStage.setScene(scene);
                        });
                        detailBox.getChildren().addAll(detailLabel,backButton);
                        primaryStage.setScene(detailScene);
                    });
                    requestBox.getChildren().addAll(infoLabel,acceptButton,declineButton,detailButton);

                    layout.getChildren().add(requestBox);
                }
                Button backButton = new Button("Back"); backButton.setOnAction(event -> {
                    try {
                        this.parentMenu.start(primaryStage);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
                layout.getChildren().add(backButton);
                primaryStage.setScene(scene);
                primaryStage.show();
            }
        };
    } //done

    private Menu getManageCategory() {
        return new Menu("Manage Category",this) {

            @Override
            public void start(Stage primaryStage) throws Exception {
                VBox layout = new VBox(20); VBox editLayout = new VBox(20);
                Scene scene = new Scene(layout,200,200);
                Scene editScene = new Scene(editLayout,200,200);

                ArrayList<Button> buttons = new ArrayList<>();
                for (Category category : controller.manageCategories()) {
                    Button button = new Button(category.getName()); button.setOnAction(event -> {
                        editLayout.getChildren().clear();
                        HBox nameBox = new HBox(20);
                        Label nameLabel = new Label("Enter new name: "); TextField nameField = new TextField(category.getName());
                        nameBox.getChildren().addAll(nameLabel,nameField);
                        HBox descriptionBox = new HBox(20);
                        Label descriptionLabel = new Label("Enter new description");TextField descriptionField = new TextField(category.getDescription());
                        descriptionBox.getChildren().addAll(descriptionLabel,descriptionField);
                        Button confirmButton = new Button("Confirm"); confirmButton.setOnAction(event1 -> {
                            controller.editCategory(category.getName(),nameField.getText(),descriptionField.getText());
                        });
                        Button removeButton = new Button("Remove Category"); removeButton.setOnAction(event1 -> {
                            controller.removeCategory(category.getName());
                            primaryStage.setScene(scene);
                        });
                        Button backButton = new Button("Back"); backButton.setOnAction(event1 -> {
                            primaryStage.setScene(scene);
                        });
                        editLayout.getChildren().addAll(nameBox,descriptionBox,confirmButton,backButton);

                        primaryStage.setScene(editScene);
                    });
                    buttons.add(button);
                }
                Button addButton = new Button("Add Category"); addButton.setOnAction(event -> {
                    editLayout.getChildren().clear();
                    HBox nameBox = new HBox(20);
                    Label nameLabel = new Label("Enter name: "); TextField nameField = new TextField();
                    nameBox.getChildren().addAll(nameLabel,nameField);
                    HBox descriptionBox = new HBox(20);
                    Label descriptionLabel = new Label("Enter description: "); TextField descriptionField = new TextField();
                    descriptionBox.getChildren().addAll(descriptionLabel,descriptionField);
                    Button confirmButton = new Button("Confirm"); confirmButton.setOnAction(event1 -> {
                        controller.addCategory(nameField.getText(),descriptionField.getText());
                    });
                    Button backButton = new Button("Back"); backButton.setOnAction(event1 -> {
                        primaryStage.setScene(scene);
                    });
                    editLayout.getChildren().addAll(nameBox,descriptionBox,confirmButton,backButton);

                    primaryStage.setScene(editScene);
                });
                Button backButton = new Button("Back"); backButton.setOnAction(event -> {
                    try {
                        this.parentMenu.start(primaryStage);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
                for (Button button : buttons) {
                    layout.getChildren().add(button);
                }
                layout.getChildren().addAll(addButton,backButton);

                primaryStage.setScene(scene);
                primaryStage.show();
            }
        };
    } //done

    @Override
    public void start(Stage primaryStage) throws Exception {
        VBox layout = new VBox(20);
        Scene scene = new Scene(layout,200,200);

        Button loginButton = new Button("Login Menu"); loginButton.setOnAction(event -> {
            try {
                submenus.get(1).start(primaryStage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        Button personalButton = new Button("Personal Info"); personalButton.setOnAction(event -> {
            try {
                submenus.get(2).start(primaryStage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        Button usersButton = new Button("Manage Users"); usersButton.setOnAction(event -> {
            try {
                submenus.get(3).start(primaryStage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        Button productButton = new Button("Manage Products"); productButton.setOnAction(event -> {
            try {
                submenus.get(4).start(primaryStage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        Button createDiscountButton = new Button("Create Discount Code"); createDiscountButton.setOnAction(event -> {
            try {
                submenus.get(5).start(primaryStage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        Button viewDiscountButton = new Button("View Discount Codes"); viewDiscountButton.setOnAction(event -> {
            try {
                submenus.get(6).start(primaryStage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        Button requestButton = new Button("Manage Request"); requestButton.setOnAction(event -> {
            try {
                submenus.get(7).start(primaryStage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        Button categoryButton = new Button("Manage Category"); categoryButton.setOnAction(event -> {
            try {
                submenus.get(8).start(primaryStage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        Button backButton = new Button("Back");backButton.setOnAction(event -> {
            try {
                this.parentMenu.start(primaryStage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        layout.getChildren().addAll(loginButton,personalButton,usersButton,productButton,createDiscountButton,
                viewDiscountButton,requestButton,categoryButton,backButton);

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}


