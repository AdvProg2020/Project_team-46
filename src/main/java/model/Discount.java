package model;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class Discount {
    private String discountCode;
    private Date startingDate;
    private Date endingDate;
    private int discountPercentage;
    private long maximumDiscount;
    private Map<Account, Integer> discountsPerPerson;
    private List<Account> includedPeople;

}
