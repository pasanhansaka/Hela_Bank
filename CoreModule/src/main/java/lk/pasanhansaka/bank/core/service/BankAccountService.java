package lk.pasanhansaka.bank.core.service;

import jakarta.ejb.Remote;
import lk.pasanhansaka.bank.core.entity.BankAccount;
import lk.pasanhansaka.bank.core.entity.CustomerAccount;
import lk.pasanhansaka.bank.core.model.BankAccountType;

import java.util.List;

@Remote
public interface BankAccountService {
    BankAccount getBankAccountByAccountNumber(String accountNumber);

    BankAccount getBankAccountByCustomerNIC(String nic);

    void createBankAccount(CustomerAccount customer, BankAccountType bankAccountType);

    void disableBankAccount(String accountNumber);

    void enableBankAccount(String accountNumber);

    List<BankAccount> getCustomersAllBankAccountsByNIC(String nic);

    List<BankAccount> getAllBankAccounts();

    void calculateMonthlyInterest();
}
