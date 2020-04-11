package model;

public class Customer extends Account{
    public Customer(String username) {
        super(username, Role.CUSTOMER);
    }
}
