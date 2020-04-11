package model;

import java.util.ArrayList;

public class BuyerAccount extends Account {
    private ArrayList<Log> shoppingExperience = new ArrayList<>();
    private ArrayList<Discount> discountList = new ArrayList<>();
    private double money;

    public BuyerAccount(String username, double money) {
        super(username);
        this.money = money;
    }
}
