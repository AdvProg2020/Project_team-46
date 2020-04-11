package model;

import java.util.ArrayList;

public class SellerAccount extends Account{
    private String companyInfo;
    private ArrayList<Log> sellingExperience = new ArrayList<>();
    private ArrayList<Product> forSellingProducts = new ArrayList<>();
    private ArrayList<Product> forOffProducts = new ArrayList<>();

    public SellerAccount(String username,String companyInfo) {
        super(username);
        this.companyInfo = companyInfo;
    }
}
