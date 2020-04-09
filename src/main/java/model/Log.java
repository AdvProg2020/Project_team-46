package model;

import java.util.Date;
import java.util.List;

public class Log {
    private String logId;
    private Date date;
    private long paidAmount;
    private long recievedAmount;
    private long discountAmount;
    private List<Product> soldProducts;
    private List<Product> boughtProducts;
    private String costumerName;
    private String sellerName;
    private DeliveryStatus deliveryStatus;


}
