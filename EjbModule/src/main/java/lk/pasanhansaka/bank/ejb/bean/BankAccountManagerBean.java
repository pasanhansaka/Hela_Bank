package lk.pasanhansaka.bank.ejb.bean;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lk.pasanhansaka.bank.core.annotation.LoggedException;
import lk.pasanhansaka.bank.core.entity.BankAccount;
import lk.pasanhansaka.bank.core.entity.CustomerAccount;
import lk.pasanhansaka.bank.core.model.BankAccountStatus;
import lk.pasanhansaka.bank.core.model.BankAccountType;
import lk.pasanhansaka.bank.core.service.BankAccountService;

import java.util.Date;
import java.util.List;
import java.util.Random;

@Stateless
@LoggedException
public class BankAccountManagerBean implements BankAccountService {

    private static final int ACCOUNT_NUMBER_LENGTH = 10;
    private final Random random = new Random();

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public BankAccount getBankAccountByAccountNumber(String accountNumber) {
        try {
            //Find Bank Account by Account Number
            return entityManager.createNamedQuery("BankAccount.findByAccountNumber", BankAccount.class)
                    .setParameter("accountNumber", accountNumber).getSingleResult();

        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public BankAccount getBankAccountByCustomerNIC(String nic) {
        try {
            //Find Bank Account by Customer NIC
            return entityManager.createNamedQuery("BankAccount.findByCustomerNIC", BankAccount.class)
                    .setParameter("nic", nic).getSingleResult();

        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void createBankAccount(CustomerAccount customer, BankAccountType bankAccountType) {
        //Create New Bank Account for Customer
        String newAccountNumber = generateAccountNumber();

        if (newAccountNumber == null) {
            newAccountNumber = generateAccountNumber();
        }

        BankAccount bankAccount = new BankAccount(newAccountNumber, customer, bankAccountType, BankAccountStatus.ACTIVE, new Date());
        entityManager.persist(bankAccount);
        System.out.println("Bank account created successfully");
    }

    @Override
    public void disableBankAccount(String accountNumber) {
        //Disable the customers Bank Account
        BankAccount bankAccount = getBankAccountByAccountNumber(accountNumber);
        bankAccount.setBankAccountStatus(BankAccountStatus.DISABLED);

        entityManager.merge(bankAccount);
        System.out.println("Bank account disabled successfully");
    }

    @Override
    public void enableBankAccount(String accountNumber) {
        //Enable the customers Bank Account
        BankAccount bankAccount = getBankAccountByAccountNumber(accountNumber);
        bankAccount.setBankAccountStatus(BankAccountStatus.ACTIVE);

        entityManager.merge(bankAccount);
        System.out.println("Bank account disabled successfully");
    }

    @Override
    public List<BankAccount> getCustomersAllBankAccountsByNIC(String nic) {
        try {
            //Find a customer's all the Bank Accounts
            return entityManager.createNamedQuery("BankAccount.findByCustomerNIC", BankAccount.class)
                    .setParameter("nic", nic)
                    .getResultList();

        } catch (Exception e) {
            return List.of();
        }
    }

    @Override
    public List<BankAccount> getAllBankAccounts() {
        try {
            //Find All the Bank Accounts.
            return entityManager.createQuery("SELECT b FROM BankAccount b", BankAccount.class)
                    .getResultList();

        } catch (Exception e) {
            return List.of();
        }
    }

    @Override
    public void calculateMonthlyInterest() {

        List<BankAccount> bankAccountList = entityManager.createNamedQuery("SELECT b FROM BankAccount b WHERE b.accountType=:type", BankAccount.class)
                .setParameter("type", BankAccountType.SAVINGS)
                .getResultList();

        for (BankAccount account : bankAccountList) {
            double interestRate = 0.01; // 1% monthly interest
            double interest = account.getBalance() * interestRate;
            account.setBalance(account.getBalance() + interest);
            entityManager.merge(account);
        }

        System.out.println("Monthly interest calculated and applied.");
    }

    private String generateAccountNumber() {
        String generatedAccountNumber = null;
        boolean isUnique = false;
        long startTime = System.currentTimeMillis();
        long maxAttempts = 1000;

        while (!isUnique) {

            //Exceed Time Limit.
            if (System.currentTimeMillis() - startTime > 10000) {
                System.out.println("Failed to create Unique Account Number within time. Try again later.");
                throw new RuntimeException("Failed to create Unique Account Number within time. Try again later.");
            }

            //Exceed Max attempting Limit.
            if (maxAttempts-- <= 0) {
                System.out.println("Failed to create Unique Account Number after trying " + maxAttempts + " attempts.");
                throw new RuntimeException("Failed to create Unique Account Number after trying " + maxAttempts + " attempts.");
            }

            //Generate Account Number
            StringBuilder stringBuilder = new StringBuilder(ACCOUNT_NUMBER_LENGTH);
            for (int i = 0; i < ACCOUNT_NUMBER_LENGTH; i++) {
                stringBuilder.append(random.nextInt(10));
            }
            generatedAccountNumber = stringBuilder.toString();

            //Check Generated Account Number is already in use.
            if (getBankAccountByAccountNumber(generatedAccountNumber) == null) {
                isUnique = true;

            } else {
                System.out.println("Generated Account Number is Already in use.");
            }

        }

        //Return Generated Account Number
        return generatedAccountNumber;
    }
}
