package lk.pasanhansaka.bank.web.security;

import jakarta.ejb.EJB;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.security.enterprise.credential.Credential;
import jakarta.security.enterprise.credential.UsernamePasswordCredential;
import jakarta.security.enterprise.identitystore.CredentialValidationResult;
import jakarta.security.enterprise.identitystore.IdentityStore;
import lk.pasanhansaka.bank.core.entity.CustomerAccount;
import lk.pasanhansaka.bank.core.service.CustomerAccountService;

import java.util.Set;

@ApplicationScoped
public class AppIdentityStore implements IdentityStore {

    @EJB
    private CustomerAccountService customerAccountService;

    @Override
    public CredentialValidationResult validate(Credential credential) {

        if (credential instanceof UsernamePasswordCredential) {
            UsernamePasswordCredential upc = (UsernamePasswordCredential) credential;

            if (customerAccountService.findCustomerAccount(upc.getCaller(), upc.getPasswordAsString())) {
                CustomerAccount customer = customerAccountService.getCustomerByUsername(upc.getCaller());

                return new CredentialValidationResult(customer.getUsername(), Set.of(customer.getCustomerAccountType().name()));
            }
        }

        return CredentialValidationResult.INVALID_RESULT;
    }
}
