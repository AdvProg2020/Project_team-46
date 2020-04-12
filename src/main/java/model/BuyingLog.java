package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BuyingLog extends Log {
    private long paidAmount;
    private List<Product> boughtProducts;

    public BuyingLog(String logId, Date date, String costumerName, String sellerName, DeliveryStatus deliveryStatus,long discountAmount, long paidAmount) {
        super(logId, date, costumerName, sellerName, deliveryStatus, discountAmount);
        boughtProducts = new ArrayList<>();
        this.paidAmount = paidAmount;
    }

    public long getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(long paidAmount) {
        this.paidAmount = paidAmount;
    }

    public List<Product> getBoughtProducts() {
        return boughtProducts;
    }

    public void setBoughtProducts(List<Product> boughtProducts) {
        this.boughtProducts = boughtProducts;
    }

    @Override
    public String toString() {
        return "BuyingLog{" +
                "paidAmount=" + paidAmount +
                ", boughtProducts=" + boughtProducts +
                ", logId='" + logId + '\'' +
                ", date=" + date +
                ", discountAmount=" + discountAmount +
                ", costumerName='" + costumerName + '\'' +
                ", sellerName='" + sellerName + '\'' +
                ", deliveryStatus=" + deliveryStatus +
                '}';
    }
}
