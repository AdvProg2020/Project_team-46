package model;


import java.util.*;

public class Account {
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
    private List<SellingLog> sellingRecords;
    private List<BuyingLog> buyingRecords;
    private List<Sale> offList;
    private Map<Product, Integer> cart;
    private long balance;

    public Account(String username, Role role) {
        this.username = username;
        this.role = role;
        discountCodes = new ArrayList<>();
        sellingRecords = new ArrayList<>();
        buyingRecords = new ArrayList<>();
        offList = new ArrayList<>();
        cart = new HashMap<>();
    }

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }

    public void deleteUser(String username) {

    }

    public void addToBalance(long amount) {
        balance += amount;
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

    public List<SellingLog> getSellingRecords() {
        return sellingRecords;
    }

    public void setSellingRecords(List<SellingLog> sellingRecords) {
        this.sellingRecords = sellingRecords;
    }

    public List<BuyingLog> getBuyingRecords() {
        return buyingRecords;
    }

    public void setBuyingRecords(List<BuyingLog> buyingRecords) {
        this.buyingRecords = buyingRecords;
    }

    public Map<Product, Integer> getCart() {
        return cart;
    }

    public void setCart(Map<Product, Integer> cart) {
        this.cart = cart;
    }

    public List<Sale> getOffList() {
        return offList;
    }

    public void setOffList(List<Sale> offList) {
        this.offList = offList;
    }

    @Override
    public String toString() {
        return "Account{" +
                "username='" + username + '\'' +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                ", companyName='" + companyName + '\'' +
                ", discountCodes=" + discountCodes +
                ", cart=" + cart +
                ", balance=" + balance +
                '}';
    }
}
