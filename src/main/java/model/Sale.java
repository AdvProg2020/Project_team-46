package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Sale {
    private String offId;
    private List<Product> products;
    private Date startingDate;
    private Date endingDate;
    private int discountPercentage;
    private SaleStatus saleStatus;

    public Sale(String offId, List<Product> products, Date startingDate, Date endingDate, int discountPercentage) {
        this.offId = offId;
        this.products = products;
        this.startingDate = startingDate;
        this.endingDate = endingDate;
        this.discountPercentage = discountPercentage;
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public void removeProduct(Product product) {
        products.remove(product);
    }

    public boolean hasProduct(Product product) {
        return products.contains(product);
    }

    public String getOffId() {
        return offId;
    }

    public void setOffId(String offId) {
        this.offId = offId;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public Date getStartingDate() {
        return startingDate;
    }

    public void setStartingDate(Date startingDate) {
        this.startingDate = startingDate;
    }

    public Date getEndingdate() {
        return endingDate;
    }

    public void setEndingdate(Date endingDate) {
        this.endingDate = endingDate;
    }

    public int getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(int discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public SaleStatus getSaleStatus() {
        return saleStatus;
    }

    public void setSaleStatus(SaleStatus saleStatus) {
        this.saleStatus = saleStatus;
    }

    @Override
    public String toString() {
        return "Sale{" +
                "offId='" + offId + '\'' +
                ", products=" + products +
                ", startingDate=" + startingDate +
                ", endingDate=" + endingDate +
                ", discountPercentage=" + discountPercentage +
                '}';
    }
}
