package model;

import java.util.ArrayList;
import java.util.List;

public class Account {
    protected static List<Account> accounts = new ArrayList<>();
    protected String username;
    protected String name;
    protected String lastName;
    protected String email;
    protected String address;
    protected String phoneNumber;
    protected String password;

    public Account(String username, Role role) {
        this.username = username;
        this.role = role;
        accounts.add(this);
    }

    public static boolean isThereAccountWithName(String username) {
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

}
