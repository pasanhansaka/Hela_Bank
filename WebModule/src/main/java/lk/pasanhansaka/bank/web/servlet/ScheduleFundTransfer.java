package lk.pasanhansaka.bank.web.servlet;

import jakarta.ejb.EJB;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.pasanhansaka.bank.core.dto.FundTransferScheduleInfo;
import lk.pasanhansaka.bank.core.entity.BankAccount;
import lk.pasanhansaka.bank.core.service.BankAccountService;
import lk.pasanhansaka.bank.core.service.FundTransferSchedulerService;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@WebServlet("/customer/scheduleFundTransfer")
public class ScheduleFundTransfer extends HttpServlet {

    @EJB
    private BankAccountService bankAccountService;

    @EJB
    private FundTransferSchedulerService fundTransferSchedulerService;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String fromAccountNumber = request.getParameter("accountNumber");
        String receiverAccountNumber = request.getParameter("receiverAccountNumber");
        String amountString = request.getParameter("amount");
        String description = request.getParameter("description");
        String datetime = request.getParameter("datetime");

        BankAccount toAccountNumber;

        try {
            toAccountNumber = bankAccountService.getBankAccountByAccountNumber(receiverAccountNumber);

        } catch (Exception e) {
            request.setAttribute("error", "Invalid Account Number");
            request.getRequestDispatcher(request.getContextPath() + "/error/error.jsp").forward(request, response);
            return;
        }

        double amount = 0.0;

        try {
            amount = Double.parseDouble(amountString);

        } catch (NumberFormatException e) {
            request.setAttribute("error", "Invalid amount");
            request.getRequestDispatcher(request.getContextPath() + "/error/error.jsp").forward(request, response);
            return;
        }

        Date scheduledDate;
        try {
            LocalDateTime localDateTime = LocalDateTime.parse(datetime);
            scheduledDate = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());

        } catch (Exception e) {
            request.setAttribute("error", "Invalid Date Format");
            request.getRequestDispatcher(request.getContextPath() + "/error/error.jsp").forward(request, response);
            return;
        }

        FundTransferScheduleInfo info = new FundTransferScheduleInfo(fromAccountNumber, toAccountNumber.getAccountNumber(), amount, description, scheduledDate);
        fundTransferSchedulerService.scheduleFundTransfer(info);
        response.sendRedirect(request.getContextPath() + "/index.jsp");

    }
}
