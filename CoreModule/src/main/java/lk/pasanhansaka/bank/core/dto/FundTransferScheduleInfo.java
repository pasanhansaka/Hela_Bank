package lk.pasanhansaka.bank.core.dto;

import java.io.Serializable;
import java.util.Date;

public class FundTransferScheduleInfo implements Serializable {
    private String fromAccountNumber;
    private String toAccountNumber;
    private double amount;
    private String description;
    private Date scheduledTime;

    public FundTransferScheduleInfo() {
    }

    public FundTransferScheduleInfo(String fromAccountNumber, String toAccountNumber, double amount, String description, Date scheduledTime) {
        this.fromAccountNumber = fromAccountNumber;
        this.toAccountNumber = toAccountNumber;
        this.amount = amount;
        this.description = description;
        this.scheduledTime = scheduledTime;
    }

    public String getFromAccountNumber() {
        return fromAccountNumber;
    }

    public void setFromAccountNumber(String fromAccountNumber) {
        this.fromAccountNumber = fromAccountNumber;
    }

    public String getToAccountNumber() {
        return toAccountNumber;
    }

    public void setToAccountNumber(String toAccountNumber) {
        this.toAccountNumber = toAccountNumber;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getScheduledTime() {
        return scheduledTime;
    }

    public void setScheduledTime(Date scheduledTime) {
        this.scheduledTime = scheduledTime;
    }
}
