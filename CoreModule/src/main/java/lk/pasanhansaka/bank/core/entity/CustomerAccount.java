package lk.pasanhansaka.bank.core.entity;

import jakarta.persistence.*;
import lk.pasanhansaka.bank.core.model.CustomerAccountStatus;
import lk.pasanhansaka.bank.core.model.CustomerAccountType;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "customer_account")
@NamedQueries({
        @NamedQuery(name = "CustomerAccount.findByUserName", query = "SELECT u FROM CustomerAccount u WHERE u.username=:username"),
        @NamedQuery(name = "CustomerAccount.findByNIC", query = "SELECT u FROM CustomerAccount u WHERE u.nic=:nic"),
        @NamedQuery(name = "CustomerAccount.findByUserNameAndPassword" , query = "SELECT u FROM CustomerAccount u WHERE u.username=:username AND u.password=:password")
})
@Cacheable(value = false)
public class CustomerAccount implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstname;

    private String lastname;

    @Column(unique = true)
    private String username;

    @Column(unique = true, nullable = false)
    private String nic;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String mobile;

    private String password;

    private String verificationCode;

    @Enumerated(EnumType.STRING)
    private CustomerAccountStatus customerAccountStatus = CustomerAccountStatus.INACTIVE;

    @Enumerated(EnumType.STRING)
    private CustomerAccountType customerAccountType = CustomerAccountType.CUSTOMER;

    @OneToMany(mappedBy = "ownerNic", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<BankAccount> bankAccounts = new HashSet<>();

    @Column(nullable = false)
    private Date creationDate;

    public CustomerAccount() {
    }

    public CustomerAccount(String firstname, String lastname, String username, String nic, String email, String mobile, String password) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.nic = nic;
        this.email = email;
        this.mobile = mobile;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    public CustomerAccountStatus getCustomerAccountStatus() {
        return customerAccountStatus;
    }

    public void setCustomerAccountStatus(CustomerAccountStatus customerAccountStatus) {
        this.customerAccountStatus = customerAccountStatus;
    }

    public CustomerAccountType getCustomerAccountType() {
        return customerAccountType;
    }

    public void setCustomerAccountType(CustomerAccountType customerAccountType) {
        this.customerAccountType = customerAccountType;
    }

    public Set<BankAccount> getBankAccounts() {
        return bankAccounts;
    }

    public void setBankAccounts(Set<BankAccount> bankAccounts) {
        this.bankAccounts = bankAccounts;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public void addBankAccount(BankAccount bankAccount) {
        this.bankAccounts.add(bankAccount);
        bankAccount.setOwnerNic(this);
    }

    public void removeBankAccount(BankAccount bankAccount) {
        this.bankAccounts.remove(bankAccount);
        bankAccount.setOwnerNic(null);
    }
}
