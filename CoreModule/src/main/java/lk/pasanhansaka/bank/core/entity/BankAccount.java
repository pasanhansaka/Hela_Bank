package lk.pasanhansaka.bank.core.entity;

import jakarta.persistence.*;
import lk.pasanhansaka.bank.core.model.BankAccountStatus;
import lk.pasanhansaka.bank.core.model.BankAccountType;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "bank_account")
@NamedQueries({
        @NamedQuery(name = "BankAccount.findByCustomerNIC", query = "SELECT b FROM BankAccount b WHERE b.ownerNic.nic=:nic"),
        @NamedQuery(name = "BankAccount.findByAccountNumber", query = "SELECT b FROM BankAccount b WHERE b.accountNumber=:accountNumber")
})
public class BankAccount implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, length = 10, nullable = false)
    private String accountNumber;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "owner_nic", referencedColumnName = "nic", nullable = false)
    private CustomerAccount ownerNic;

    private double balance = 0;

    @Enumerated(EnumType.STRING)
    private BankAccountType bankAccountType = BankAccountType.SAVINGS;

    @Enumerated(EnumType.STRING)
    private BankAccountStatus bankAccountStatus = BankAccountStatus.ACTIVE;

    @Column(nullable = false)
    private Date creationDate;

    public BankAccount() {
    }

    public BankAccount(String accountNumber, CustomerAccount ownerNic, BankAccountType bankAccountType, BankAccountStatus bankAccountStatus, Date creationDate) {
        this.accountNumber = accountNumber;
        this.ownerNic = ownerNic;
        this.bankAccountType = bankAccountType;
        this.bankAccountStatus = bankAccountStatus;
        this.creationDate = creationDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public CustomerAccount getOwnerNic() {
        return ownerNic;
    }

    public void setOwnerNic(CustomerAccount ownerNic) {
        this.ownerNic = ownerNic;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public BankAccountType getBankAccountType() {
        return bankAccountType;
    }

    public void setBankAccountType(BankAccountType bankAccountType) {
        this.bankAccountType = bankAccountType;
    }

    public BankAccountStatus getBankAccountStatus() {
        return bankAccountStatus;
    }

    public void setBankAccountStatus(BankAccountStatus bankAccountStatus) {
        this.bankAccountStatus = bankAccountStatus;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
}