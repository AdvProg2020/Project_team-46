package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Sale {
    private String offId;
    private List<Product> products;
    private Date startingDate;
    private Date endingDate;
    private long discountAmount;

    public Sale(String offId, List<Product> products, Date startingDate, Date endingDate, long discountAmount) {
        this.offId = offId;
        this.products = products;
        this.startingDate = startingDate;
        this.endingDate = endingDate;
        this.discountAmount = discountAmount;
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

    public void setEndingdate(Date endingdate) {
        this.endingDate = endingdate;
    }

    public long getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(long discountAmount) {
        this.discountAmount = discountAmount;
    }

    @Override
    public String toString() {
        return "Sale{" +
                "offId='" + offId + '\'' +
                ", products=" + products +
                ", startingDate=" + startingDate +
                ", endingDate=" + endingDate +
                ", discountAmount=" + discountAmount +
                '}';
    }
}
