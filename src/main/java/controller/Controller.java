package controller;
import model.*;

import java.util.ArrayList;
import java.util.List;

public class Controller {
    private List<Account> accounts;
    private Account currentAccount ;

    public Controller() {
        accounts = new ArrayList<>();
    }

    public Account getAccountByUsername(String username) {
        for (Account account : accounts) {
            if (account.getUsername().equals(username)) {
                return account;
            }
        }
        return null;
    }

    public Account getCurrentAccount() {
        return currentAccount;
    }

    public boolean logIn(String username, String password) {
        if (getAccountByUsername(username).getPassword().equals(password)) {
            currentAccount = getAccountByUsername(username);
            return true;
        } else {
            return false;
        }
    }

    public boolean logOut() {
        if (currentAccount == null) {
            return false;
        } else {
            currentAccount = null;
            return true;
        }
    }

    public void createAccount(String username, String type) {
        Role role = null;
        switch (type) {
            case  "customer":
                role = Role.CUSTOMER;
                break;
            case "seller":
                role = Role.SELLER;
                break;
            case "manager":
                role = Role.MANAGER;
        }
        currentAccount = new Account(username, role);
        accounts.add(currentAccount);
    }

    public void setPassword(String password) {
        currentAccount.setPassword(password);
    }

    public void setName(String name) {
        currentAccount.setName(name);
    }

    public void setLastName(String lastName) {
        currentAccount.setLastName(lastName);
    }

    public void setAddress(String address) {
        currentAccount.setAddress(address);
    }

    public void setEmail(String email) {
        currentAccount.setEmail(email);
    }

    public void setPhoneNumber(String phoneNumber) {
        currentAccount.setPhoneNumber(phoneNumber);
    }

    public void setCompanyName(String companyName) {
        currentAccount.setCompanyName(companyName);
    }

    public void login(String username, String password) {

    }

    public void edit(String field) {

    }

    public void viewPersonalInfo() {

    }

    public void view(String user) {

    }

    public void deleteUser(String user) {

    }

    public Account createManager(String userName,Role role) {
        return new Account(userName,Role.MANAGER);
    }


    public void createDiscountCode() {

    }

    public void viewDiscountCodes() {

    }

    public void viewDiscountCode(String discountCode) {

    }

    public void editDiscountCode(String discountCode) {

    }

    public void removeDiscountCode(String discountCode) {

    }

    public void manageRequest() {

    }

    public void showDetails(String requestId) {

    }

    public void manageCategories() {

    }

    public void editCategory(String category,String name,String description) {
        Category category1 = Category.getCategoryByName(category);
        category1.setName(name);
        category1.setDescription(description);
    }

    public void addCategory(String name,String description) {
        ArrayList<Product> list = new ArrayList<>();
        Category.categories.add(new Category(name,description,list));
    }

    public void removeCategory(String category) {
        Category category1 = Category.getCategoryByName(category);
        for (Product product : category1.getProducts()) {
            if(Product.products.contains(product)) {
                Product.products.remove(product);
            }
        }
        Category.categories.remove(category1);
    }

    public String viewCompanyInformation() {
        return currentAccount.getCompanyName();
    }

    public List<SellingLog> viewSalesHistory() {
       return currentAccount.getSellingRecords();
    }

    public void manageProducts() {

    }

    public String viewProduct(String productId) {
        Product product = Product.getProductById(productId);
        return product.toString();
    }

    public List<Account> viewBuyers(String productId) {
        Product product = Product.getProductById(productId);
        return product.getBuyers();
    }

    public void editProduct(String productId) {

    }

    public void addProduct(String productId) {

    }

    public void removeProduct(String productId) {

    }

    public List<Category> showCategories() {
        return Category.categories;
    }

    public List<Sale> viewOffs() {
        return currentAccount.getOffList();
    }

    public String viewOff(String offId) {
        Sale sale = Sale.getSaleById(offId);
        return sale.toString();
    }

    public void editOff(String offId) {

    }

    public void addOff(){

    }

    public long viewBalance() {
        return currentAccount.getBalance();
    }

    public void viewCart() {

    }

    public void viewProductsInCart() {

    }

    public void viewProductInCart(String productId) {

    }

    public void increaseProduct(String productId) {

    }

    public void decreaseProduct(String productId) {

    }

    public void showTotalPrice() {

    }

    public void purchase() {

    }

    public void confirmPurchase() {

    }

    public List<BuyingLog> viewOrders() {
        return currentAccount.getBuyingRecords();
    }

    public String showOrder(String orderId) {
        for (BuyingLog buyingRecord : currentAccount.getBuyingRecords()) {
            if(buyingRecord.getLogId().equals(orderId)) {
                return buyingRecord.toString();
            }
        }
        return null;
    }

    public void rateProduct(String productId, double score) {
        Product product = Product.getProductById(productId);
        Score score1 = new Score(currentAccount,score,product);
        product.setScores(score1);
    }

    public void goToProductsPage() {

    }

    public void showAvailableFilters() {

    }

    public void filter(String filter) {

    }

    public void showCurrentFilters() {

    }

    public void disableFilter(String filter) {

    }

    public void goToSortingPage() {

    }

    public void sort(String sortingMethod) {

    }

    public void showCurrentSort() {

    }

    public void disableSort(String sortingMethod) {

    }

    public void showProducts() {

    }

    public static void showProduct(String productId) {

    }

    public static void digest() {

    }

    public static void addToCart() {

    }

    public static void selectSeller() {

    }

    public static void attributes() {

    }

    public static void compare(String productId) {

    }

    public static void comments() {

    }

    public static void addComment(String title, String content) {

    }

    public static void offs() {

    }

    public List<Account> getAccounts() {
        return accounts;
    }
}
