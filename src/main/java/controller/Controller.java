package controller;
import model.*;

import java.util.*;

public class Controller {
    private List<Category> categories;
    private List<Product> products;
    private List<Product> availableProducts;
    private List<Account> accounts;
    private List<Sale> sales;
    private Account currentAccount;
    private String currentSortingMethod = "by number of views";
    //private ArrayList<String> currentFilter;
    public boolean hasCategoryFilter = false;
    public boolean hasNameFilter = false;
    public String filteredCategory;
    public String filteredName;

    public Controller() {
        accounts = new ArrayList<>();
        products = new ArrayList<>();
        availableProducts = new ArrayList<>();
        //currentFilter = new ArrayList<>();
        categories = new ArrayList<>();
        sales = new ArrayList<>();
    }

    public Category getCategoryByName(String name) {
        for (Category category : categories) {
            if (category.getName().equals(name)) {
                return category;
            }
        }
        return null;
    }

    public Sale getSaleById(String offId) {
        for (Sale sale : sales) {
            if (sale.getOffId().equals(offId)) {
                return sale;
            }
        }
        return null;
    }

    public Product getProductById(String productId) {
        for (Product product : availableProducts) {
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

    public Discount getDiscountByCode(String code) {
        for (Discount discount : Discount.discounts) {
            if (discount.getCode().equals(code)) {
                return discount;
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

    public void setNameOfProduct(String productId, String newName) {
        getProductById(productId).setName(newName);
        getProductById(productId).setProductStatus(ProductStatus.CONFIRMED);
    }

    public void setCompanyOfProduct(String productId, String company) {
        getProductById(productId).setBrandOrCompany(company);
        getProductById(productId).setProductStatus(ProductStatus.CONFIRMED);
    }

    public void setDescriptionOfProduct(String productId, String description) {
        getProductById(productId).setDescription(description);
        getProductById(productId).setProductStatus(ProductStatus.CONFIRMED);
    }

    public void setAvailabilityOfProduct(String productId, boolean isAvailable) {
        getProductById(productId).setAvailable(isAvailable);
        getProductById(productId).setProductStatus(ProductStatus.CONFIRMED);
    }

    public void setValueOfProduct(String productId, long value) {
        getProductById(productId).setValue(value);
        getProductById(productId).setProductStatus(ProductStatus.CONFIRMED);
    }

    public void setAmountOfProduct(String productId, int amount) {
        getProductById(productId).setAmount(amount);
        getProductById(productId).setProductStatus(ProductStatus.CONFIRMED);
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

    public ArrayList<String> viewBuyers(String productId) {
        ArrayList<String> buyers = new ArrayList<>();
        for (Account buyer : getProductById(productId).getBuyers()) {
            buyers.add(buyer.getName());
        }
        return buyers;
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
        List<Product> help = new ArrayList<>(products);
        for (Product product : help) {
            if (product.getCategory().getName().equals(category)) {
                products.remove(product);
            }
        }
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

    public List<Product> getAvailableProducts() {
        return availableProducts;
    }

    public void addProduct(String productId) {
        getProductById(productId).setProductStatus(ProductStatus.CONFIRMED);
        availableProducts.add(getProductById(productId));
    }

    public void createProduct(String[] inputs, Account seller) {
        boolean isAvailable = inputs[5].equals("1");
        Product product = new Product(inputs[6], ProductStatus.UNDER_CONSTRUCTION, Long.parseLong(inputs[2]) ,
                Integer.parseInt(inputs[3]), inputs[0], inputs[1], seller, null, inputs[4], isAvailable);
        products.add(product);

    }

    public void removeProduct(String productId) {
        products.remove(getProductById(productId));
        availableProducts.remove(getProductById(productId));
    }

    public ArrayList<String> viewSellersOff() {
        ArrayList<String> offs = new ArrayList<>();
        for (Sale sale : currentAccount.getOffList()) {
            System.out.println("offId: " + sale.getOffId());
            for (Product product : sale.getProducts()) {
                double number = product.getValue() - (sale.getDiscountPercentage() / 100.0)*product.getValue();
                String output = product.getName()+ "   " + "   " + product.getProductId() + "   " +
                        product.getValue() + "   " + number;
                offs.add(output);
            }
        }
        return offs;
    }

    public List<Sale> viewOffs() {
        return currentAccount.getOffList();
    }

    public String viewOff(String offId) {
        return getSaleById(offId).toString();
    }

    public void editStartingDateOfSale(int year, int month, int day, String offId) {
        getSaleById(offId).setStartingDate(new Date(year, month, day));
        getSaleById(offId).setSaleStatus(SaleStatus.CONFIRMED);
    }

    public void editEndingDateOfSale(int year, int month, int day, String offId) {
        getSaleById(offId).setEndingdate(new Date(year, month, day));
        getSaleById(offId).setSaleStatus(SaleStatus.CONFIRMED);
    }

    public void editDiscountPercentageOfSale(int discountPercentage, String offId) {
        getSaleById(offId).setDiscountPercentage(discountPercentage);
        getSaleById(offId).setSaleStatus(SaleStatus.CONFIRMED);
    }

    public void addProductToSale(String offId, String productId) {
        getSaleById(offId).addProduct(getProductById(productId));
        getSaleById(offId).setSaleStatus(SaleStatus.CONFIRMED);
    }

    public void removeProductFromSale(String offId, String productId) {
        getSaleById(offId).removeProduct(getProductById(productId));
        getSaleById(offId).setSaleStatus(SaleStatus.CONFIRMED);
    }

    public long viewBalance() {
        return currentAccount.getBalance();
    }

    public Map<Product, Integer> viewCart() {
        return currentAccount.getCart();
    }

    public void increaseProduct(String productId) {
        if (getProductById(productId) != null) {
            Product product = getProductById(productId);
            Map<Product,Integer> cart = currentAccount.getCart();
            if (currentAccount.getCart().containsKey(product)) {
                if (product.getAmount() > 0) {
                    int amount = currentAccount.getCart().get(product);
                    cart.replace(product,amount,amount+1);
                    currentAccount.setCart(cart);
                }
                else
                    System.out.println("Sorry. not enough amount of this product.\n" +
                            "Please try later");
            }
            else
                System.out.println("this product is not in the cart yet");
        }
        else
            System.out.println("product Id is invalid");
    }

    public void decreaseProduct(String productId) {
        if (getProductById(productId) != null) {
            Product product = getProductById(productId);
            Map<Product,Integer> cart = currentAccount.getCart();
            if (currentAccount.getCart().containsKey(product)) {
                int amount = currentAccount.getCart().get(product);
                cart.replace(product,amount,amount-1);
                currentAccount.setCart(cart);
            }
            else
                System.out.println("this product is not in the cart yet");
        }
        else
            System.out.println("product Id is invalid");
    }

    public double showTotalPrice() {
        return 0;
    } //needs to complete

    public void purchase(String address,String phoneNumber,Discount discount) {

    } //needs to complete

    public boolean discountCodeConfirmation(String code) {
        if (getDiscountByCode(code) != null) {
            Discount discount = getDiscountByCode(code);
            return discount.getIncludedPeople().contains(currentAccount);
        }
        return false;
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

    public void filter(String filter,String detail) {
        if (filter.equals("by category")) {
            if (getCategoryByName(detail) != null) {
                hasCategoryFilter = true;
                filteredCategory = detail;
            }
            else
                System.out.println("category is invalid");
        }
        else if (filter.equals("by name")) {
            hasNameFilter = true;
            filteredName = detail;
        }
    }

    public void disableFilter(String filter) {
        if (filter.equals("by category")) {
            hasCategoryFilter = false;
            filteredCategory = null;
        }
        else if (filter.equals("by name")) {
            hasNameFilter = false;
            filteredName = null;
        }
    }

    public List<Product> sort(String sortingMethod) {
        if ("by Date".equals(sortingMethod)) {
            for (int i = 0; i < products.size(); i++) {
                for (int j = i + 1; j < products.size(); j++) {
                    if (products.get(i).getDateModified().compareTo(products.get(j).getDateModified()) < 0) {
                        Collections.swap(products, i, j);
                    }
                }
            }
            currentSortingMethod = "by Date";
        } else if ("by score".equals(sortingMethod)) {
            for (int i = 0; i < products.size(); i++) {
                for (int j = i + 1; j < products.size(); j++) {
                    if (products.get(i).getAverageScore() < products.get(j).getAverageScore()) {
                        Collections.swap(products, i, j);
                    }
                }
            }
            currentSortingMethod = "by score";
        } else if ("by number of views".equals(sortingMethod)) {
            for (int i = 0; i < products.size(); i++) {
                for (int j = i + 1; j < products.size(); j++) {
                    if (products.get(i).getNumberOfViews() < products.get(j).getNumberOfViews()) {
                        Collections.swap(products, i, j);
                    }
                }
            }
            currentSortingMethod = "by number of views";
        }
        return products;
    }

    public String showCurrentSort() {
        return currentSortingMethod;
    }

    public List<Product> disableSort() {
        for (int i=0;i<products.size();i++) {
            for (int j=i+1;j<products.size();j++) {
                if (products.get(i).getNumberOfViews() < products.get(j).getNumberOfViews()) {
                    Collections.swap(products,i,j);
                }
            }
        }
        currentSortingMethod = "by number of views";
        return products;
    }

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
        for (Sale sale : sales) {
            for (Product product : sale.getProducts()) {
                double number = product.getValue() - (sale.getDiscountPercentage() / 100.0)*product.getValue();
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
