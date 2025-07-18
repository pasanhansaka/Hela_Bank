package lk.pasanhansaka.bank.core.service;

import jakarta.ejb.Remote;
import lk.pasanhansaka.bank.core.entity.Transaction;

import java.util.List;

@Remote
public interface TransactionHistory {
    List<Transaction> getTransactionHistoryByCustomerNIC(String customerNIC);
}
