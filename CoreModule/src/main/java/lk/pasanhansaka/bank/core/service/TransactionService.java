package lk.pasanhansaka.bank.core.service;

import jakarta.ejb.Remote;

@Remote
public interface TransactionService {
    void depositTo(String accountNumber, double amount, String description);

    void withdrawFrom(String accountNumber, double amount, String description);

    void transferFunds(String toAccountNumber, String fromAccountNumber, double amount,String description);
}
