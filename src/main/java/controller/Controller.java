package controller;
import model.*;

import java.util.*;

public class Controller {
    private List<Category> categories;
    private List<Product> products;
    private List<Product> availableProducts;
    private List<Account> accounts;
    private List<Sale> sales;
    private List<Sale> availableSales;
    private List<Discount> discountList;
    private Account currentAccount;
    private String currentSortingMethod = "by number of views";
    public boolean hasCategoryFilter = false;
    public boolean hasNameFilter = false;
    public String filteredCategory;
    public String filteredName;

    public Controller() {
        accounts = new ArrayList<>();
        products = new ArrayList<>();
        availableProducts = new ArrayList<>();
        categories = new ArrayList<>();
        sales = new ArrayList<>();
        availableSales = new ArrayList<>();
        discountList = new ArrayList<>();
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

    public Sale getAvailableSaleById(String offId) {
        for (Sale sale : availableSales) {
            if (sale.getOffId().equals(offId)) {
                return sale;
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

    public Product getAvailableProductById(String productId) {
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
        for (Discount discount : discountList) {
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
        getAvailableProductById(productId).setName(newName);
        getAvailableProductById(productId).setProductStatus(ProductStatus.CONFIRMED);
    }

    public void setCompanyOfProduct(String productId, String company) {
        getAvailableProductById(productId).setBrandOrCompany(company);
        getAvailableProductById(productId).setProductStatus(ProductStatus.CONFIRMED);
    }

    public void setDescriptionOfProduct(String productId, String description) {
        getAvailableProductById(productId).setDescription(description);
        getAvailableProductById(productId).setProductStatus(ProductStatus.CONFIRMED);
    }

    public void setAvailabilityOfProduct(String productId, boolean isAvailable) {
        getAvailableProductById(productId).setAvailable(isAvailable);
        getAvailableProductById(productId).setProductStatus(ProductStatus.CONFIRMED);
    }

    public void setValueOfProduct(String productId, long value) {
        getAvailableProductById(productId).setValue(value);
        getAvailableProductById(productId).setProductStatus(ProductStatus.CONFIRMED);
    }

    public void setAmountOfProduct(String productId, int amount) {
        getAvailableProductById(productId).setAmount(amount);
        getAvailableProductById(productId).setProductStatus(ProductStatus.CONFIRMED);
    }

    public void setCategoryOfProduct(String productId, Category category) {
        getAvailableProductById(productId).setCategory(category);
        getAvailableProductById(productId).setProductStatus(ProductStatus.CONFIRMED);
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

    public void setBalance(long balance) {
        currentAccount.setBalance(balance);
    }

    public String viewUsers(String user) {
        if (getAccountByUsername(user) != null) {
            Account account = getAccountByUsername(user);
            return account.toString();
        }
        return "no user with this user name";
    }

    public ArrayList<String> viewBuyers(String productId) {
        ArrayList<String> buyers = new ArrayList<>();
        for (Account buyer : getAvailableProductById(productId).getBuyers()) {
            buyers.add(buyer.getName());
        }
        return buyers;
    }

    public void deleteUser(String user) {
        if (getAccountByUsername(user) != null) {
            Account account = getAccountByUsername(user);
            accounts.remove(account);
        }
        else
            System.out.println("no user with this user name");
    }

    public void addUserManager(Account account) {
        accounts.add(account);
    }

    public Account createManager(String userName,Role role) {
        return new Account(userName,Role.MANAGER);
    }

    public void createDiscountCode(String code,Date start,Date end,int percent,long max) {
        List<Account> accountList = new ArrayList<>();
        Discount discount = new Discount(code,start,end,percent,max,accountList);
        discountList.add(discount);
    }

    public String viewDiscountCode(String discountCode) {
        if (getDiscountByCode(discountCode) != null) {
            Discount discount = getDiscountByCode(discountCode);
            return discount.toString();
        }
        return null;
    }

    public void removeDiscountCode(Discount discount) {
        discountList.remove(discount);
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

    public String viewProduct(String productId) {
        return getAvailableProductById(productId).toString();
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
                Integer.parseInt(inputs[3]), inputs[0], inputs[1], seller, getCategoryByName(inputs[7]), inputs[4], isAvailable);
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

    public void addOff(String offId) {
        getSaleById(offId).setSaleStatus(SaleStatus.CONFIRMED);
        availableSales.add(getSaleById(offId));
    }

    public void createOff(String offId, ArrayList<Product> products, Date startingDate, Date endingDate, int discountPercentage) {
        Sale sale = new Sale(offId,products, startingDate, endingDate, discountPercentage);
        sales.add(sale);
        sale.setSaleStatus(SaleStatus.UNDER_CONSTRUCTION);
    }

    public List<Sale> viewOffs() {
        return currentAccount.getOffList();
    }

    public String viewOff(String offId) {
        return getAvailableSaleById(offId).toString();
    }

    public void editStartingDateOfSale(int year, int month, int day, String offId) {
        getSaleById(offId).setStartingDate(new Date(year, month, day));
        getSaleById(offId).setSaleStatus(SaleStatus.CONFIRMED);
    }

    public void editEndingDateOfSale(int year, int month, int day, String offId) {
        getAvailableSaleById(offId).setEndingdate(new Date(year, month, day));
        getAvailableSaleById(offId).setSaleStatus(SaleStatus.CONFIRMED);
    }

    public void editDiscountPercentageOfSale(int discountPercentage, String offId) {
        getAvailableSaleById(offId).setDiscountPercentage(discountPercentage);
        getAvailableSaleById(offId).setSaleStatus(SaleStatus.CONFIRMED);
    }

    public void addProductToSale(String offId, String productId) {
        getAvailableSaleById(offId).addProduct(getAvailableProductById(productId));
        getAvailableSaleById(offId).setSaleStatus(SaleStatus.CONFIRMED);
    }

    public void removeProductFromSale(String offId, String productId) {
        getAvailableSaleById(offId).removeProduct(getAvailableProductById(productId));
        getAvailableSaleById(offId).setSaleStatus(SaleStatus.CONFIRMED);
    }

    public long viewBalance() {
        return currentAccount.getBalance();
    }

    public Map<Product, Integer> viewCart() {
        return currentAccount.getCart();
    }

    public void increaseProduct(String productId) {
        if (getAvailableProductById(productId) != null) {
            Product product = getAvailableProductById(productId);
            Map<Product,Integer> cart = currentAccount.getCart();
            if (currentAccount.getCart().containsKey(product)) {
                if (product.getAmount() > 0) {
                    int amount = currentAccount.getCart().get(product);
                    cart.replace(product,amount,amount+1);
                    currentAccount.setCart(cart);
                    product.setAmount(product.getAmount() - 1);
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
        if (getAvailableProductById(productId) != null) {
            Product product = getAvailableProductById(productId);
            Map<Product,Integer> cart = currentAccount.getCart();
            if (currentAccount.getCart().containsKey(product)) {
                int amount = currentAccount.getCart().get(product);
                cart.replace(product,amount,amount-1);
                currentAccount.setCart(cart);
                product.setAmount(product.getAmount() + 1);
            }
            else
                System.out.println("this product is not in the cart yet");
        }
        else
            System.out.println("product Id is invalid");
    }

    public void putProductInSale(String productId) {
        getAvailableProductById(productId).setInSale(true);
    }

    public Sale getSaleOfProduct(Product product) {
        for (Sale availableSale : availableSales) {
            if (availableSale.hasProduct(product)) {
                return availableSale;
            }
        }
        return null;
    }

    public long showTotalPrice(Discount discount) {
        long totalPrice = 0;
        Sale sale;
        Date date = new Date();
        for (Product product : currentAccount.getCart().keySet()) {
            for (int i = 0; i < currentAccount.getCart().get(product); i++) {
                sale = getSaleOfProduct(product);
                if (sale != null && sale.getStartingDate().before(date) && sale.getEndingdate().after(date)) {
                    totalPrice += (product.getValue() * (100 - getSaleOfProduct(product).getDiscountPercentage()) / 100);
                } else {
                    totalPrice += product.getValue();
                }
            }
        }
        if (discount != null && discount.getStartingDate().before(date) && discount.getEndingDate().after(date)) {
            totalPrice -= showDiscountAmount(totalPrice, discount);
        }
        return totalPrice;
    }

    public long showTotalPrice() {
        long totalPrice = 0;
        Sale sale;
        Date date = new Date();
        for (Product product : currentAccount.getCart().keySet()) {
            for (int i = 0; i < currentAccount.getCart().get(product); i++) {
                sale = getSaleOfProduct(product);
                if (sale != null && sale.getStartingDate().before(date) && sale.getEndingdate().after(date)) {
                    totalPrice += (product.getValue() * (100 - getSaleOfProduct(product).getDiscountPercentage()) / 100);
                } else {
                    totalPrice += product.getValue();
                }
            }
        }
        return totalPrice;
    }

    public long showDiscountAmount(long totalPrice, Discount discount) {
        if (discount != null) {
            if ((100 - discount.getDiscountPercentage()) * totalPrice > discount.getMaximumDiscount()) {
                return discount.getMaximumDiscount();
            } else {
                return totalPrice * discount.getDiscountPercentage();
            }
        } else {
            return 0;
        }
    }

    public void purchase(Discount discount) {
        currentAccount.addToBalance(-showTotalPrice(discount));
        Map<Product, Account> sellerPerProduct = new HashMap<>();
        for (Product product : currentAccount.getCart().keySet()) {
            for (int i = 0; i < currentAccount.getCart().get(product); i++) {
                product.getSeller().addToBalance(receivedMoney(product));
                product.getSeller().getSellingRecords().add(new SellingLog(generateId(), new Date(), currentAccount.getUsername(),
                        product.getSeller().getUsername(), product, DeliveryStatus.IN_PROCESS, showDiscountAmount(product.getValue(), discount),
                        receivedMoney(product)));
            }
            sellerPerProduct.put(product, product.getSeller());
        }
        currentAccount.getBuyingRecords().add(new BuyingLog(generateId(), new Date(), currentAccount.getUsername()
                ,sellerPerProduct, DeliveryStatus.IN_PROCESS, showDiscountAmount(showTotalPrice(), discount),showTotalPrice(discount)));
        currentAccount.getCart().clear();
        discountList.remove(discount);
    }

    public long receivedMoney(Product product) {
        Sale sale = getSaleOfProduct(product);
        Date date = new Date();
        if (sale != null && sale.getStartingDate().before(date) && sale.getEndingdate().after(date)) {
            return (100 - getSaleOfProduct(product).getDiscountPercentage()) * product.getValue();
        } else {
            return product.getValue();
        }
    }

    public boolean discountCodeConfirmation(String code) {
        Discount discount = getDiscountByCode(code);
        Date date = new Date();
        if (discount != null && discount.getStartingDate().before(date) && discount.getEndingDate().after(date)) {
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
        Product product = getAvailableProductById(productId);
        Score score1 = new Score(currentAccount,score,product);
        product.setAverageScore(product.getAverageScore() * product.getScores().size() + score / (product.getScores().size() + 1));
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
        if (filter.equals("category")) {
            hasCategoryFilter = false;
            filteredCategory = null;
        }
        else if (filter.equals("name")) {
            hasNameFilter = false;
            filteredName = null;
        }
    }

    public List<Product> sort(String sortingMethod) {
        if ("Date".equals(sortingMethod)) {
            for (int i = 0; i < products.size(); i++) {
                for (int j = i + 1; j < products.size(); j++) {
                    if (products.get(i).getDateModified().compareTo(products.get(j).getDateModified()) < 0) {
                        Collections.swap(products, i, j);
                    }
                }
            }
            currentSortingMethod = "by Date";
        } else if ("score".equals(sortingMethod)) {
            for (int i = 0; i < products.size(); i++) {
                for (int j = i + 1; j < products.size(); j++) {
                    if (products.get(i).getAverageScore() < products.get(j).getAverageScore()) {
                        Collections.swap(products, i, j);
                    }
                }
            }
            currentSortingMethod = "by score";
        } else if ("views".equals(sortingMethod)) {
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
            if (getProductById(productId).getAmount() > 0) {
                Map<Product,Integer> cart = currentAccount.getCart();
                cart.put(getAvailableProductById(productId),1);
                currentAccount.setCart(cart);
                getProductById(productId).setAmount(getProductById(productId).getAmount() - 1);
            }
            else
                System.out.println("not available");
        }
        else
            System.out.println("invalid command");
    }

    public void selectSeller(String userName) {

    } //needs to complete

    public void setCurrentAccount(Account currentAccount) {
        this.currentAccount = currentAccount;
    }

    public ArrayList<String> attributes(String productId) {
        ArrayList<String> attributes = new ArrayList<>();
        Product product = getAvailableProductById(productId);
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
        Product product1 = getAvailableProductById(firstId);
        Product product2 = getAvailableProductById(secondId);
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
        Product product  = getAvailableProductById(goodId);
        return product.getComments();
    }

    public void addComment(String title, String content,String goodId) {
        Product product = getAvailableProductById(goodId);
        boolean hasBought = product.hasBought(currentAccount.getUsername());
        Comment comment = new Comment(currentAccount, product, title, content, hasBought,CommentStatus.PENDING_APPROVAL);
        List<Comment> commentList = new ArrayList<>(product.getComments());
        commentList.add(comment);
        product.setComments(commentList);
    }

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

    public static String generateId() {
        String chars = "1234567890";
        StringBuilder string = new StringBuilder();
        Random rnd = new Random();
        while (string.length() < 10) {
            int index = (int) (rnd.nextFloat() * chars.length());
            string.append(chars.charAt(index));
        }
        return string.toString();
    }

    public List<Discount> getDiscountList() {
        return discountList;
    }
}
