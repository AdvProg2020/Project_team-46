package controller;
import model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Controller {
    private List<Category> categories;
    private List<Product> products;
    private List<Account> accounts;
    private Account currentAccount ;

    public Controller() {
        accounts = new ArrayList<>();
        products = new ArrayList<>();
        categories = new ArrayList<>();
    }

    public Category getCategoryByName(String name) {
        for (Category category : categories) {
            if (category.getName().equals(name)) {
                return category;
            }
        }
        return null;
    }

    public Product getProductById(String productId) {
        for (Product product : products) {
            if (product.getProductId().equals(productId)) {
                return product;
            }
        }
        return null;
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

    public List<Product> getProducts() {
        return products;
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

    public List<Request> manageRequest() {
        return Request.requests;
    }

    public void showDetails(String requestId) {

    }

    public List<Category> manageCategories() {
        return categories;
    }

    public void editCategory(String category,String name,String description) {
        Category category1 = getCategoryByName(category);
        category1.setName(name);
        category1.setDescription(description);
    }

    public void addCategory(String name,String description) {
        categories.add(new Category(name, description, new ArrayList<>()));
    }

    public void removeCategory(String category) {
        categories.remove(getCategoryByName(category));
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
        return getProductById(productId).toString();
    }

    public List<Account> viewBuyers(String productId) {
        return getProductById(productId).getBuyers();
    }

    public void editProduct(String productId) {

    }

    public void addProduct(String[] inputs, Account seller) {
        boolean isAvailable;
        isAvailable = inputs[3].equals("1");
        Product product = new Product(inputs[4], ProductStatus.UNDER_CONSTRUCTION, inputs[0], inputs[1], seller, isAvailable,  inputs[2]);
        products.add(product);
    }

    public void removeProduct(String productId) {

    }

    public List<Category> showCategories() {
        return categories;
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

    public Map<Product, Integer> viewCart() {
        return currentAccount.getCart();
    }

    public void viewProductInCart(String productId) {

    }

    public void increaseProduct(String productId) {

    }

    public void decreaseProduct(String productId) {

    }

    public double showTotalPrice() {
        return 0;
    }

    public void purchase() {

    }

    public boolean discountCodeConfirmation(String code) {
        return true;
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
        Product product = getProductById(productId);
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

    public void sort(String sortingMethod) {

    }

    public void showCurrentSort() {

    }

    public void disableSort() {

    }

    public void showProducts() {

    }//needs to complete

    public String digest(String productId) {
        for (Product product : products) {
            if (product.getProductId().equals(productId)) {
                return product.toString();
            }
        }
        return null;
    }

    public void addToCart(String productId) {
        if (currentAccount.getRole() == Role.CUSTOMER) {
            Map<Product,Integer> cart = currentAccount.getCart();
            cart.put(getProductById(productId),1);
            currentAccount.setCart(cart);
        }
        else
            System.out.println("invalid command");
    }

    public void selectSeller(String userName) {

    } //needs to complete

    public ArrayList<String> attributes(String productId) {
        ArrayList<String> attributes = new ArrayList<>();
        Product product = getProductById(productId);
        attributes.add(product.getName());
        attributes.add(product.getBrandOrCompany());
        attributes.add(product.getProductId());
        attributes.add(product.getDescription());
        attributes.add(product.getCategory().getName());
        attributes.add(Integer.toString(product.getAmount()));
        attributes.add(Double.toString(product.getAverageScore()));
        attributes.add(product.getSeller().getUsername());
        attributes.add(Double.toString(product.getValue()));
        return attributes;
    }

    public ArrayList<String> compare(String firstId,String secondId) {
        ArrayList<String> compare = new ArrayList<>();
        Product product1 = getProductById(firstId);
        Product product2 = getProductById(secondId);
        if (product1.getCategory().equals(product2.getCategory())) {
            compare.add(product1.getName() + "    " + product2.getName());
            compare.add(product1.getBrandOrCompany() + "    " + product2.getBrandOrCompany());
            compare.add(product1.getProductId() + "    " + product2.getProductId());
            compare.add(product1.getDescription() + "    " + product2.getDescription());
            compare.add(product1.getCategory().getName() + "    " + product2.getCategory().getName());
            compare.add(product1.getAverageScore() + "    " + product2.getAverageScore());
            compare.add(product1.getAmount() + "    " + product2.getAmount());
            compare.add(product1.getSeller().getUsername() + "    " + product2.getSeller().getUsername());
            compare.add(product1.getValue() + "    " + product2.getValue());
        }
        else
            System.out.println("Products should be in one category to compare!");

        return compare;
    }

    public List<Comment> comments(String goodId) {
        Product product  = getProductById(goodId);
        return product.getComments();
    }

    public void addComment(String title, String content,String goodId) {
        Product product = getProductById(goodId);
        boolean hasBought = product.hasBought(currentAccount.getUsername());
        Comment comment = new Comment(currentAccount, product, title, content, hasBought,CommentStatus.PENDING_APPROVAL);

    } //needs to complete

    public ArrayList<String> offs() {
        ArrayList<String> offs = new ArrayList<>();
        for (Sale sale : Sale.sales) {
            for (Product product : sale.getProducts()) {
                double number = product.getValue() - (sale.getDiscountAmount() / 100.0)*product.getValue();
                String output = product.getName()+ "   " + "   " + product.getProductId() + "   " +
                        product.getValue() + "   " + number;
                offs.add(output);
            }
        }
        return offs;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public List<Category> getCategories() {
        return categories;
    }
}
