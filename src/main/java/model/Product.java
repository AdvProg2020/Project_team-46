package model;

import java.util.ArrayList;
import java.util.List;

public class Product {
    public static List<Product> products = new ArrayList<>();
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

    public static Product getProductById(String productId) {
        for (Product product : products) {
            if (product.productId.equals(productId)) {
                return product;
            }
        }
        return null;
    }

    public Product(String productId, ProductStatus productStatus, String name, String brandOrCompany, Account seller, boolean isAvailable, Category category, String description) {
        this.productId = productId;
        this.productStatus = productStatus;
        this.name = name;
        this.brandOrCompany = brandOrCompany;
        this.seller = seller;
        this.isAvailable = isAvailable;
        this.category = category;
        this.description = description;
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
}
