package model;


import java.util.ArrayList;
import java.util.List;

public class Account {
    private static List<Account> accounts = new ArrayList<>();
    private String username;
    private String name;
    private String lastName;
    private String email;
    private String address;
    private String phoneNumber;
    private String password;
    private Role role;
    private String companyName;
    private List<Discount> discountCodes;
    private List<Log> sellingRecords;
    private List<Log> buyingRecords;

    public Account(String username) {
        this.username = username;
        accounts.add(this);
        discountCodes = new ArrayList<>();
        sellingRecords = new ArrayList<>();
        buyingRecords = new ArrayList<>();
    }

    public static boolean isThereAcountWithName(String username) {
        for (Account account : accounts) {
            if (account.username.equals(username)) {
                return true;
            }
        }
        return false;
    }

    public static Account getAccountByUsername(String username) {
        for (Account account : accounts) {
            if (account.username.equals(username)) {
                return account;
            }
        }
        return null;
    }

    public static Account login(String username, String password) {
        for (Account account : accounts) {
            if (account.username.equals(username) && account.password.equals(password)) {
                return account;
            }
        }
        return null;
    }

    public void deleteUser(String username) {

    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public List<Discount> getDiscountCodes() {
        return discountCodes;
    }

    public void setDiscountCodes(List<Discount> discountCodes) {
        this.discountCodes = discountCodes;
    }

    public List<Log> getSellingRecords() {
        return sellingRecords;
    }

    public void setSellingRecords(List<Log> sellingRecords) {
        this.sellingRecords = sellingRecords;
    }

    public List<Log> getBuyingRecords() {
        return buyingRecords;
    }

    public void setBuyingRecords(List<Log> buyingRecords) {
        this.buyingRecords = buyingRecords;
    }
}
