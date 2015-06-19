package ooworkshop.domain;

import no.mesan.ooworkshop.domain.CheckingAccount;
import no.mesan.ooworkshop.domain.Customer;
import no.mesan.ooworkshop.exception.InsufficientFundsException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

public class CheckingAccountTest {

    private final static Long validAccountNumber = 36241604394L;
    private final static Long invalidAccountNumber = 36241604393L;

    private final Customer customer = new Customer();

    @Test
    public void newAccountShouldBeCorrect() throws Exception {
        CheckingAccount account = new CheckingAccount(this.validAccountNumber, 100.0, 50.0 ,
                                                      this.customer);

        assertEquals(36241604394L, account.getAccountNumber());
        assertEquals(100.0, account.getAmount(), 0);
        assertEquals(50.0, account.getCreditLimit(), 0);
        assertSame(this.customer, account.getAccountOwner());
    }

    @Test(expected = IllegalArgumentException.class)
    public void newAccountShouldNotAcceptInvalidAccountNumber() throws Exception {
        new CheckingAccount(this.invalidAccountNumber, 0.0, 100.0, this.customer);
    }

    @Test
    public void depositShouldAddAmount() throws Exception {
        CheckingAccount account = new CheckingAccount(36241604394L, 100.0, 50.0, new Customer());
        account.deposit(100.0);

        assertEquals(200.0, account.getAmount(), 0);
    }

    @Test
    public void withdrawRemovesMoney() throws Exception {
        CheckingAccount account = new CheckingAccount(36241604394L, 100.0, 50.0, new Customer());
        account.withdraw(50.0);

        assertEquals(50.0, account.getAmount(), 0);
    }

    @Test
    public void withdrawAllowsMoreThanCurrentAmountForCheckingAccount() throws Exception {
        CheckingAccount account = new CheckingAccount(36241604394L, 100.0, 70.0, new Customer());
        account.withdraw(150.0);
        assertEquals(-50.0, account.getAmount(), 0);
    }

    @Test(expected=InsufficientFundsException.class)
    public void withdrawDoesNotOwerdrawAmountPlusCredit() throws Exception {
        CheckingAccount account = new CheckingAccount(36241604394L, 100.0, 70.0, new Customer());
        account.withdraw(180.0);
    }
}