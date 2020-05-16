package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Product {
    private List<Account> buyers = new ArrayList<>();
    private String productId;
    private ProductStatus productStatus;
    private long value;
    private int amount;
    private String name;
    private String brandOrCompany;
    private Account seller;
    private boolean isAvailable;
    private boolean isInSale = false;
    private Category category;
    private String description;
    private double averageScore;
    private List<Comment> comments = new ArrayList<>();
    private List<Score> scores = new ArrayList<>();
    private int numberOfViews = 0;
    private Date dateModified;


    public Product(String productId, ProductStatus productStatus, long value, int amount,
                   String name, String brandOrCompany, Account seller, Category category, String description, boolean isAvailable) {
        this.productId = productId;
        this.productStatus = productStatus;
        this.value = value;
        this.amount = amount;
        this.name = name;
        this.brandOrCompany = brandOrCompany;
        this.seller = seller;
        this.category = category;
        this.description = description;
        this.isAvailable = isAvailable;
        this.dateModified = new Date();
    }

    public static void removeProductById(String id) {

    }

    public boolean isInSale() {
        return isInSale;
    }

    public void setInSale(boolean inSale) {
        isInSale = inSale;
    }

    public List<Score> getScores() {
        return scores;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public ProductStatus getProductStatus() {
        return productStatus;
    }

    public void setProductStatus(ProductStatus productStatus) {
        this.productStatus = productStatus;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrandOrCompany() {
        return brandOrCompany;
    }

    public void setBrandOrCompany(String brandOrCompany) {
        this.brandOrCompany = brandOrCompany;
    }

    public Account getSeller() {
        return seller;
    }

    public void setSeller(Account seller) {
        this.seller = seller;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getAverageScore() {
        return averageScore;
    }

    public void setAverageScore(double averageScore) {
        this.averageScore = averageScore;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<Account> getBuyers() {
        return buyers;
    }

    public void setBuyers(List<Account> buyers) {
        this.buyers = buyers;
    }

    public void setScores(Score score) {
        scores.add(score);
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }

    public boolean hasBought(String userName) {
        for (Account buyer : buyers) {
            if (buyer.getUsername().equals(userName)) {
                return true;
            }
        }
        return false;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getNumberOfViews() {
        return numberOfViews;
    }

    public Date getDateModified() {
        return dateModified;
    }

    public void setNumberOfViews(int numberOfViews) {
        this.numberOfViews = numberOfViews;
    }

    public void setDateModified(Date dateModified) {
        this.dateModified = dateModified;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId='" + productId + '\'' +
                ", productStatus=" + productStatus +
                ", name='" + name + '\'' +
                ", brandOrCompany='" + brandOrCompany + '\'' +
                ", seller=" + seller +
                ", isAvailable=" + isAvailable +
                ", category=" + category +
                ", description='" + description + '\'' +
                ", averageScore=" + averageScore +
                ", comments=" + comments +
                '}';
    }
}
