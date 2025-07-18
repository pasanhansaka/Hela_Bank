package lk.pasanhansaka.bank.core.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "transactions_history")
@NamedQueries({
        @NamedQuery(name = "TransactionHistory.findByCustomerNIC", query = "SELECT h FROM TransactionHistory h WHERE h.customer.nic = :nic")
})
public class TransactionHistory implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "transaction_id", nullable = false)
    private Transaction transaction;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_nic", referencedColumnName = "nic", nullable = false)
    private CustomerAccount customer;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date recordedAt;

    public TransactionHistory() {
    }

    public TransactionHistory(Transaction transaction, CustomerAccount customer, Date recordedAt) {
        this.transaction = transaction;
        this.customer = customer;
        this.recordedAt = recordedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public CustomerAccount getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerAccount customer) {
        this.customer = customer;
    }

    public Date getRecordedAt() {
        return recordedAt;
    }

    public void setRecordedAt(Date recordedAt) {
        this.recordedAt = recordedAt;
    }
}
