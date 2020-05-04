package model;

import java.util.ArrayList;
import java.util.List;

public class Category {
    private String name;
    private String description;
    private List<Product> products;


    public Category(String name, String description, List<Product> products) {
        this.products = new ArrayList<>();
        this.name = name;
        this.description = description;
        this.products = products;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    @Override
    public String toString() {
        return "Category{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", products=" + products +
                '}';
    }
}
