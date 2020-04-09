package model;

import java.util.List;

public class Product {
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

}
