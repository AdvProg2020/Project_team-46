package model;

import java.util.ArrayList;

public class ManagerAccount extends Account {
    private ArrayList<Discount> discountCodeList = new ArrayList<>();
    private ArrayList<Category> categoryList = new ArrayList<>();


    public ManagerAccount(String username) {
        super(username);
    }
}
