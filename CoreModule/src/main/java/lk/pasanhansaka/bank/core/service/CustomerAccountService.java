package lk.pasanhansaka.bank.core.service;

import jakarta.ejb.Remote;
import lk.pasanhansaka.bank.core.entity.CustomerAccount;

@Remote
public interface CustomerAccountService {
    CustomerAccount getCustomerByUsername(String username);

    CustomerAccount getCustomerByNIC(String nic);

    void registerCustomerAccount(CustomerAccount customerAccount);

    void updateCustomerAccount(CustomerAccount customerAccount);

    void disableCustomerAccount(CustomerAccount customerAccount);

    void enableCustomerAccount(CustomerAccount customerAccount);

    void deleteCustomerAccount(CustomerAccount customerAccount);

    boolean findCustomerAccount(String username, String password);
}
