package test;

import controller.Controller;
import model.Account;
import model.Role;
import org.junit.Assert;
import org.junit.Test;

public class UnitTest {
    private Controller controller = new Controller();
    private Account account = new Account("username", Role.CUSTOMER);

    @Test
    public void getUserNameTest() {
        controller.setCurrentAccount(account);
        Assert.assertEquals(controller.getCurrentAccount().getUsername(), "username");
    }
}
