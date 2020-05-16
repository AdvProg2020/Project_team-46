package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Log {
    public static List<Log> logs = new ArrayList<>();
    protected String logId;
    protected Date date;
    protected long discountAmount;
    protected String costumerName;
    protected DeliveryStatus deliveryStatus;

    public Log(String logId, Date date, String costumerName, DeliveryStatus deliveryStatus, long discountAmount) {
        logs.add(this);
        this.logId = logId;
        this.date = date;
        this.costumerName = costumerName;
        this.deliveryStatus = deliveryStatus;
        this.discountAmount = discountAmount;
    }

    public String getLogId() {
        return logId;
    }

    public void setLogId(String logId) {
        this.logId = logId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public long getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(long discountAmount) {
        this.discountAmount = discountAmount;
    }

    public String getCostumerName() {
        return costumerName;
    }

    public void setCostumerName(String costumerName) {
        this.costumerName = costumerName;
    }

    public DeliveryStatus getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(DeliveryStatus deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }
}
