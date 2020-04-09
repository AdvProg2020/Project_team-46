package model;

import java.util.ArrayList;
import java.util.List;

public class Product {
    private static List<Product> products = new ArrayList<>();
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

    public static void removeProductById(String id) {

    }

}
