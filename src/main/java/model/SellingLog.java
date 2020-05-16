package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SellingLog extends Log {
    private long receivedAmount;
    private Product soldProduct;
    private String sellerName;
    public SellingLog(String logId, Date date, String costumerName, String sellerName, Product soldProduct, DeliveryStatus deliveryStatus,long discountAmount, long receivedAmount) {
        super(logId, date, costumerName, deliveryStatus, discountAmount);
        this.soldProduct = soldProduct;
        this.receivedAmount = receivedAmount;
        this.sellerName = sellerName;
    }

    public long getReceivedAmount() {
        return receivedAmount;
    }

    public void setReceivedAmount(long receivedAmount) {
        this.receivedAmount = receivedAmount;
    }

    @Override
    public String toString() {
        return "SellingLog{" +
                "receivedAmount=" + receivedAmount +
                ", soldProduct=" + soldProduct +
                ", sellerName='" + sellerName + '\'' +
                ", logId='" + logId + '\'' +
                ", date=" + date +
                ", discountAmount=" + discountAmount +
                ", costumerName='" + costumerName + '\'' +
                ", deliveryStatus=" + deliveryStatus +
                '}';
    }
}
