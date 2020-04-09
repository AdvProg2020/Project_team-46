package model;


import java.util.List;

public class Account {
    private String username;
    private String name;
    private String lastName;
    private String email;
    private String address;
    private String phoneNumber;
    private String password;
    private Role role;
    private String companyName;
    private List<Discount> discountCodes;
    private List<Log> sellingRecords;
    private List<Log> buyingRecords;

}
