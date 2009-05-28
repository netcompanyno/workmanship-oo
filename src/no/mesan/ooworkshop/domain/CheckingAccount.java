package no.mesan.ooworkshop.domain;

import no.mesan.ooworkshop.exception.InnsufficientFundsException;
import no.mesan.ooworkshop.util.AccountUtil;

public class CheckingAccount implements Account {
    private long accountNumber;
    private Double amount;
    private Double creditLimit;
    private Customer accountOwner;

    public CheckingAccount(long accountNumber,
                           Double initialAmount,
                           Double creditLimit,
                           Customer accountOwner) {
        if (!AccountUtil.validAccountNumber(accountNumber)) {
            throw new IllegalArgumentException();
        }

        this.accountNumber = accountNumber;
        this.amount = initialAmount;
        this.creditLimit = creditLimit;
        this.accountOwner = accountOwner;
    }

    public Double getCreditLimit() {
        return creditLimit;
    }

    @Override
    public long getAccountNumber() {
        return accountNumber;
    }

    @Override
    public Customer getAccountOwner() {
        return accountOwner;
    }

    @Override
    public Double getAmount() {
        return amount;
    }

    @Override
    public void deposit(double depositAmount) {
        amount += depositAmount;
    }
    
    @Override
    public void withdraw(double withdrawAmount) throws InnsufficientFundsException {
        if (amount + creditLimit < withdrawAmount) {
            throw new InnsufficientFundsException();
        }
        amount -= withdrawAmount;
    }
}
