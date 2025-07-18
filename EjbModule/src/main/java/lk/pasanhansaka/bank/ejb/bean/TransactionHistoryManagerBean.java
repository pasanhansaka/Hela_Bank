package lk.pasanhansaka.bank.ejb.bean;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lk.pasanhansaka.bank.core.annotation.LoggedException;
import lk.pasanhansaka.bank.core.entity.Transaction;
import lk.pasanhansaka.bank.core.service.TransactionHistory;

import java.util.List;

@Stateless
@LoggedException
public class TransactionHistoryManagerBean implements TransactionHistory {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Transaction> getTransactionHistoryByCustomerNIC(String customerNIC) {

        return entityManager.createNamedQuery("Transaction.findTransactionsByCustomerNIC", Transaction.class)
                .setParameter("nic", customerNIC)
                .getResultList();
    }
}
