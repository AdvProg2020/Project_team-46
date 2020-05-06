package model;

import java.util.ArrayList;
import java.util.List;

public class Product {
    private List<Account> buyers;
    private String productId;
    private ProductStatus productStatus;
    private String name;
    private String brandOrCompany;
    private Account seller;
    private boolean isAvailable;
    private Category category;
    private String description;
    private double averageScore;
    private List<Comment> comments;
    private List<Score> scores;


    public Product(String productId, ProductStatus productStatus, String name, String brandOrCompany, Account seller,
                   boolean isAvailable, String description) {
        this.productId = productId;
        this.productStatus = productStatus;
        this.name = name;
        this.brandOrCompany = brandOrCompany;
        this.seller = seller;
        this.isAvailable = isAvailable;
        this.description = description;
        buyers = new ArrayList<>();
        comments = new ArrayList<>();
        scores = new ArrayList<>();
        this.averageScore = 0;
    }

    public static void removeProductById(String id) {

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
