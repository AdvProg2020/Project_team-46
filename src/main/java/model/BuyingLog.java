package model;

import java.util.*;

public class BuyingLog extends Log {
    private long paidAmount;
    private Map<Product, Account> sellerPerProduct;
    public BuyingLog(String logId, Date date, String costumerName, Map<Product,Account> sellerPerProduct, DeliveryStatus deliveryStatus
            ,long discountAmount, long paidAmount) {
        super(logId, date, costumerName, deliveryStatus, discountAmount);
        this.sellerPerProduct = sellerPerProduct;
        this.paidAmount = paidAmount;
    }

    public long getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(long paidAmount) {
        this.paidAmount = paidAmount;
    }

    public Map<Product, Account> getSellerPerProduct() {
        return sellerPerProduct;
    }

    @Override
    public String toString() {
        return "BuyingLog{" +
                "paidAmount=" + paidAmount +
                ", logId='" + logId + '\'' +
                ", date=" + date +
                ", discountAmount=" + discountAmount +
                ", costumerName='" + costumerName + '\'' +
                ", deliveryStatus=" + deliveryStatus +
                '}';
    }
}
