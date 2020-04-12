package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Sale {
    public static List<Sale> sales = new ArrayList<>();
    private String offId;
    private List<Product> products;
    private Date startingDate;
    private Date endingdate;
    private long discountAmount;

    public Sale(String offId, List<Product> products, Date startingDate, Date endingdate, long discountAmount) {
        sales.add(this);
        this.offId = offId;
        this.products = products;
        this.startingDate = startingDate;
        this.endingdate = endingdate;
        this.discountAmount = discountAmount;
    }

    public static Sale getSaleById(String offId) {
        for (Sale sale : sales) {
            if (sale.offId.equals(offId)) {
                return sale;
            }
        }
        return null;
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
        return endingdate;
    }

    public void setEndingdate(Date endingdate) {
        this.endingdate = endingdate;
    }

    public long getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(long discountAmount) {
        this.discountAmount = discountAmount;
    }
}
