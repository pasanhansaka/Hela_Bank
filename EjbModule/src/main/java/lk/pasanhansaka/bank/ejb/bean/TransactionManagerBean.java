package lk.pasanhansaka.bank.ejb.bean;

import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lk.pasanhansaka.bank.core.annotation.LoggedException;
import lk.pasanhansaka.bank.core.entity.BankAccount;
import lk.pasanhansaka.bank.core.entity.Transaction;
import lk.pasanhansaka.bank.core.entity.TransactionHistory;
import lk.pasanhansaka.bank.core.exception.AccountException;
import lk.pasanhansaka.bank.core.exception.InsufficientFundsException;
import lk.pasanhansaka.bank.core.model.BankAccountStatus;
import lk.pasanhansaka.bank.core.model.TransactionType;
import lk.pasanhansaka.bank.core.service.TransactionService;

import java.util.Date;

@Stateless
@LoggedException
public class TransactionManagerBean implements TransactionService {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void depositTo(String accountNumber, double amount, String description) {
        try {
            //Deposit Money to Bank Account
            BankAccount bankAccount = entityManager.createNamedQuery("BankAccount.findByAccountNumber", BankAccount.class)
                    .setParameter("accountNumber", accountNumber)
                    .getSingleResult();

            //Check Account validity
            if (bankAccount == null || bankAccount.getBankAccountStatus() != BankAccountStatus.ACTIVE) {
                throw new AccountException("Account is not found or inactive.");
            }

            //Deposit to Account
            bankAccount.setBalance(bankAccount.getBalance() + amount);
            entityManager.merge(bankAccount);

            //Make Transaction
            Transaction transaction = new Transaction(bankAccount, bankAccount, TransactionType.DEPOSIT, amount, new Date(), description);
            entityManager.persist(transaction);

            //Make And Save Transaction History
            TransactionHistory transactionHistory = new TransactionHistory(transaction, bankAccount.getOwnerNic(), new Date());
            entityManager.persist(transactionHistory);

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void withdrawFrom(String accountNumber, double amount, String description) {
        try {
            //Withdraw money From Bank Account
            BankAccount bankAccount = entityManager.createNamedQuery("BankAccount.findByAccountNumber", BankAccount.class)
                    .setParameter("accountNumber", accountNumber)
                    .getSingleResult();

            //Check Account validity
            if (bankAccount == null || bankAccount.getBankAccountStatus() != BankAccountStatus.ACTIVE) {
                throw new AccountException("Account is not found or inactive.");
            }

            //Check account balance
            if (bankAccount.getBalance() < amount) {
                throw new AccountException("Insufficient funds.");
            }

            //Withdraw from Account
            bankAccount.setBalance(bankAccount.getBalance() - amount);
            entityManager.merge(bankAccount);

            //Make Transaction
            Transaction transaction = new Transaction(bankAccount, bankAccount, TransactionType.WITHDRAWAL, amount, new Date(), description);
            entityManager.persist(transaction);

            //Make And Save Transaction History
            TransactionHistory transactionHistory = new TransactionHistory(transaction, bankAccount.getOwnerNic(), new Date());
            entityManager.persist(transactionHistory);

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void transferFunds(String toAccountNumber, String fromAccountNumber, double amount, String description) {
        try {
            //Transfer Funds to an Account from an Account
            BankAccount fromBankAccount = entityManager.createNamedQuery("BankAccount.findByAccountNumber", BankAccount.class)
                    .setParameter("accountNumber", fromAccountNumber)
                    .getSingleResult();

            BankAccount toBankAccount = entityManager.createNamedQuery("BankAccount.findByAccountNumber", BankAccount.class)
                    .setParameter("accountNumber", toAccountNumber)
                    .getSingleResult();

            //Check Sender's Account validity
            if (fromBankAccount == null || fromBankAccount.getBankAccountStatus() != BankAccountStatus.ACTIVE) {
                throw new AccountException("Sender's Account is not found or inactive.");
            }

            //Check Receiver's Account validity
            if (toBankAccount == null || toBankAccount.getBankAccountStatus() != BankAccountStatus.ACTIVE) {
                throw new AccountException("Receiver's Account is not found or inactive.");
            }

            //Check transfer's account balance
            if (fromBankAccount.getBalance() < amount) {
                throw new InsufficientFundsException("Insufficient funds.");
            }

            //Withdraw from Account
            fromBankAccount.setBalance(fromBankAccount.getBalance() - amount);
            entityManager.merge(fromBankAccount);

            //Deposit to Account
            toBankAccount.setBalance(toBankAccount.getBalance() + amount);
            entityManager.merge(toBankAccount);

            //Make Transaction
            Transaction transaction = new Transaction(fromBankAccount, toBankAccount, TransactionType.TRANSFER, amount, new Date(), description);
            entityManager.persist(transaction);

            //Make And Save Sender's and Receiver's History
            TransactionHistory senderHistory = new TransactionHistory(transaction, fromBankAccount.getOwnerNic(), new Date());
            TransactionHistory receiverHistory = new TransactionHistory(transaction, toBankAccount.getOwnerNic(), new Date());
            entityManager.persist(senderHistory);
            entityManager.persist(receiverHistory);

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

    }
}
