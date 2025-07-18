package lk.pasanhansaka.bank.ejb.bean;

import jakarta.annotation.Resource;
import jakarta.ejb.*;
import lk.pasanhansaka.bank.core.annotation.LoggedException;
import lk.pasanhansaka.bank.core.dto.FundTransferScheduleInfo;
import lk.pasanhansaka.bank.core.service.FundTransferSchedulerService;
import lk.pasanhansaka.bank.core.service.TransactionService;

import java.util.Date;

@Stateless
@LoggedException
public class FundTransferSchedulerBean implements FundTransferSchedulerService {
    @Resource
    private TimerService timerService;

    @EJB
    private TransactionService transactionService;

    @Override
    public void scheduleFundTransfer(FundTransferScheduleInfo fundTransferScheduleInfo) {
        if (fundTransferScheduleInfo == null || fundTransferScheduleInfo.getScheduledTime() == null) {
            throw new IllegalArgumentException("FundTransferInfo or schedule time cannot be null");
        }

        TimerConfig timerConfig = new TimerConfig(fundTransferScheduleInfo, true);
        timerService.createSingleActionTimer(fundTransferScheduleInfo.getScheduledTime(), timerConfig);

    }

    @Timeout
    public void startFundTransferring(Timer timer) {

        FundTransferScheduleInfo transferScheduleInfo = (FundTransferScheduleInfo) timer.getInfo();

        if (transferScheduleInfo == null) {
            return;
        }

        try {
            transactionService.transferFunds(
                    transferScheduleInfo.getToAccountNumber(),
                    transferScheduleInfo.getFromAccountNumber(),
                    transferScheduleInfo.getAmount(),
                    transferScheduleInfo.getDescription()
            );

            System.out.println("Fund transfer success.");
            System.out.println("Scheduled fund transfer at: " + transferScheduleInfo.getScheduledTime());


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void cancelScheduledFundTransfer(FundTransferScheduleInfo transferScheduleInfo) {
        for (Timer timer : timerService.getAllTimers()) {
            if (timer.getInfo() instanceof FundTransferScheduleInfo) {

                FundTransferScheduleInfo fundTransferScheduleInfo = (FundTransferScheduleInfo) timer.getInfo();

                if (fundTransferScheduleInfo.equals(transferScheduleInfo)) {
                    timer.cancel();
                    return;
                }

            }
        }
    }
}
