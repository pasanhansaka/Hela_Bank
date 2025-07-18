package lk.pasanhansaka.bank.core.service;

import jakarta.ejb.Remote;
import jakarta.ejb.Timer;
import lk.pasanhansaka.bank.core.dto.FundTransferScheduleInfo;

@Remote
public interface FundTransferSchedulerService {
    void scheduleFundTransfer(FundTransferScheduleInfo fundTransferScheduleInfo);
}
