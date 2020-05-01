package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Discount {
    public static List<Discount> discounts = new ArrayList<>();
    private String code;
    private Date startingDate;
    private Date endingDate;
    private int discountPercentage;
    private long maximumDiscount;
    private Map<Account, Integer> discountsPerPerson;
    private List<Account> includedPeople;

    public Discount(String discountCode, Date startingDate, Date endingDate, int discountPercentage, long maximumDiscount, List<Account> includedPeople) {
        discounts.add(this);
        discountsPerPerson = new HashMap<>();
        for (Account includedPerson : includedPeople) {
            discountsPerPerson.put(includedPerson, 0);
        }
        this.code = discountCode;
        this.startingDate = startingDate;
        this.endingDate = endingDate;
        this.discountPercentage = discountPercentage;
        this.maximumDiscount = maximumDiscount;

    }


    public String getCode() {
        return code;
    }

    public Date getStartingDate() {
        return startingDate;
    }

    public Date getEndingDate() {
        return endingDate;
    }

    public int getDiscountPercentage() {
        return discountPercentage;
    }

    public long getMaximumDiscount() {
        return maximumDiscount;
    }

    public Map<Account, Integer> getDiscountsPerPerson() {
        return discountsPerPerson;
    }

    public List<Account> getIncludedPeople() {
        return includedPeople;
    }

    public static Discount getDiscountByCode(String code) {
        for (Discount discount : discounts) {
            if (discount.code.equals(code)) {
                return discount;
            }
        }
        return null;
    }

    public static void removeDiscountByCode(String code) {
        for (Discount discount : discounts) {
            if (discount.code.equals(code)) {
                discounts.remove(discount);
                break;
            }
        }
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setStartingDate(Date startingDate) {
        this.startingDate = startingDate;
    }

    public void setEndingDate(Date endingDate) {
        this.endingDate = endingDate;
    }

    public void setDiscountPercentage(int discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public void setMaximumDiscount(long maximumDiscount) {
        this.maximumDiscount = maximumDiscount;
    }

    @Override
    public String toString() {
        return "Discount{" +
                "code='" + code + '\'' +
                ", startingDate=" + startingDate +
                ", endingDate=" + endingDate +
                ", discountPercentage=" + discountPercentage +
                ", maximumDiscount=" + maximumDiscount +
                ", discountsPerPerson=" + discountsPerPerson +
                ", includedPeople=" + includedPeople +
                '}';
    }
}
