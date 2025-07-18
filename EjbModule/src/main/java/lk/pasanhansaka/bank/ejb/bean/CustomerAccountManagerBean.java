package lk.pasanhansaka.bank.ejb.bean;

import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import lk.pasanhansaka.bank.core.annotation.LoggedException;
import lk.pasanhansaka.bank.core.entity.CustomerAccount;
import lk.pasanhansaka.bank.core.model.CustomerAccountStatus;
import lk.pasanhansaka.bank.core.service.CustomerAccountService;

@Stateless
@LoggedException
public class CustomerAccountManagerBean implements CustomerAccountService {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public CustomerAccount getCustomerByUsername(String username) {
        try {
            return entityManager.createNamedQuery("CustomerAccount.findByUserName", CustomerAccount.class)
                    .setParameter("username", username).getSingleResult();

        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public CustomerAccount getCustomerByNIC(String nic) {
        try {
            return entityManager.createNamedQuery("CustomerAccount.findByNIC", CustomerAccount.class)
                    .setParameter("nic", nic).getSingleResult();

        } catch (Exception e) {
            return null;
        }
    }

    @PermitAll
    @Override
    public void registerCustomerAccount(CustomerAccount customerAccount) {
        //Create Customer Account
        entityManager.persist(customerAccount);
        System.out.println("New Customer Account Created Successfully");
    }

    @RolesAllowed({"CUSTOMER", "ADMIN"})
    @Override
    public void updateCustomerAccount(CustomerAccount customerAccount) {
        //Update Customer Account
        entityManager.merge(customerAccount);
        System.out.println("Customer Account Updated Successfully");
    }

    @RolesAllowed({"CUSTOMER", "ADMIN"})
    @Override
    public void disableCustomerAccount(CustomerAccount customerAccount) {
        //Disable Customer Account
        customerAccount.setCustomerAccountStatus(CustomerAccountStatus.DISABLED);

        entityManager.merge(customerAccount);
        System.out.println("Customer Account Disabled Successfully");
    }

    @RolesAllowed({"CUSTOMER", "ADMIN"})
    @Override
    public void enableCustomerAccount(CustomerAccount customerAccount) {
        //Enable Customer Account
        customerAccount.setCustomerAccountStatus(CustomerAccountStatus.ACTIVE);

        entityManager.merge(customerAccount);
        System.out.println("Customer Account Enabled Successfully");
    }

    @RolesAllowed({"ADMIN"})
    @Override
    public void deleteCustomerAccount(CustomerAccount customerAccount) {
        //Delete Customer Account
        entityManager.remove(customerAccount);
        System.out.println("Customer Account Deleted Successfully");
    }

    @PermitAll
    @Override
    public boolean findCustomerAccount(String username, String password) {
        try {

            CustomerAccount customerAccount = entityManager.createNamedQuery("CustomerAccount.findByUserNameAndPassword", CustomerAccount.class)
                    .setParameter("username", username)
                    .setParameter("password", password)
                    .getSingleResult();

            return customerAccount != null;

        } catch (NoResultException e) {
            return false;

        }
    }
}
