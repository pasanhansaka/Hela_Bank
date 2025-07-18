package lk.pasanhansaka.bank.ejb.bean;

import jakarta.ejb.EJB;
import jakarta.ejb.Schedule;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import lk.pasanhansaka.bank.core.annotation.LoggedException;
import lk.pasanhansaka.bank.core.service.BankAccountService;

import java.util.Date;

@Singleton
@Startup
@LoggedException
public class SystemTaskSchedulerBean {

    @EJB
    private BankAccountService bankAccountService;

    @Schedule(dayOfMonth = "1", hour = "0", minute = "0", second = "0", persistent = false)
    public void calculateMonthlyInterest() {
        try {
            bankAccountService.calculateMonthlyInterest();

            System.out.println("Running Monthly Interest Calculation at " + new Date());
        } catch (Exception e) {
            return;
        }
    }
}
