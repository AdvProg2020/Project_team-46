package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SellingLog extends Log {
    private long receivedAmount;
    private List<Product> soldProducts;
    public SellingLog(String logId, Date date, String costumerName, String sellerName, DeliveryStatus deliveryStatus,long discountAmount, long receivedAmount) {
        super(logId, date, costumerName, sellerName, deliveryStatus, discountAmount);
        soldProducts = new ArrayList<>();
        this.receivedAmount = receivedAmount;
    }

    public long getReceivedAmount() {
        return receivedAmount;
    }

    public void setReceivedAmount(long receivedAmount) {
        this.receivedAmount = receivedAmount;
    }

    public List<Product> getSoldProducts() {
        return soldProducts;
    }

    public void setSoldProducts(List<Product> soldProducts) {
        this.soldProducts = soldProducts;
    }
}
