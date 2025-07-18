package lk.pasanhansaka.bank.ejb.bean;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lk.pasanhansaka.bank.core.entity.CustomerAccount;
import lk.pasanhansaka.bank.core.model.CustomerAccountStatus;
import lk.pasanhansaka.bank.core.model.CustomerAccountType;

import java.util.Date;

@Singleton
@Startup
public class ApplicationStartUpBean {

    @PersistenceContext
    private EntityManager entityManager;

    @PostConstruct
    @Transactional
    public void createDefaultAdminUser() {
        try {
            //Register Main Admin User
            Long adminCount = (Long) entityManager.createQuery("SELECT COUNT(u) FROM CustomerAccount u WHERE u.customerAccountType = :role")
                    .setParameter("role", CustomerAccountType.ADMIN)
                    .getSingleResult();

            if (adminCount > 0) {
                System.out.println("Admin user already exists. Skipping default creation.");
                return;
            }

            CustomerAccount adminAccount = new CustomerAccount("admin", "admin", "admin", "admin", "admin", "admin", "admin");
            adminAccount.setVerificationCode("admin");
            adminAccount.setCustomerAccountStatus(CustomerAccountStatus.ACTIVE);
            adminAccount.setCustomerAccountType(CustomerAccountType.ADMIN);
            adminAccount.setCreationDate(new Date());

            entityManager.persist(adminAccount);
            System.out.println("Default admin user 'admin' created.");

        } catch (Exception e) {
            throw new RuntimeException("Failed to create default admin user: ", e);
        }
    }

}
