package lk.pasanhansaka.bank.core.entity;

import jakarta.persistence.*;
import lk.pasanhansaka.bank.core.model.TransactionType;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "transactions")
@NamedQueries({
        @NamedQuery(name = "Transaction.findByFromBankAccount", query = "SELECT t FROM Transaction t WHERE t.fromBankAccount = :account"),
        @NamedQuery(name = "Transaction.findTransactionOwnerByCustomerNIC", query = "SELECT t FROM Transaction t WHERE t.fromBankAccount.ownerNic.nic = :nic"),
        @NamedQuery(name = "Transaction.findTransactionsByCustomerNIC", query = "SELECT t FROM Transaction t WHERE t.fromBankAccount.ownerNic.nic = :nic OR t.toBankAccount.ownerNic.nic=:nic"),
})
public class Transaction implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "from_account_number", referencedColumnName = "accountNumber")
    private BankAccount fromBankAccount;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "to_account_number", referencedColumnName = "accountNumber")
    private BankAccount toBankAccount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionType transactionType;

    @Column(nullable = false)
    private double amount;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date date;

    @Column(length = 500)
    private String description;

    public Transaction() {
    }

    public Transaction(BankAccount fromBankAccount, BankAccount toBankAccount, TransactionType transactionType, double amount, Date date, String description) {
        this.fromBankAccount = fromBankAccount;
        this.toBankAccount = toBankAccount;
        this.transactionType = transactionType;
        this.amount = amount;
        this.date = date;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BankAccount getFromBankAccount() {
        return fromBankAccount;
    }

    public void setFromBankAccount(BankAccount fromBankAccount) {
        this.fromBankAccount = fromBankAccount;
    }

    public BankAccount getToBankAccount() {
        return toBankAccount;
    }

    public void setToBankAccount(BankAccount toBankAccount) {
        this.toBankAccount = toBankAccount;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
