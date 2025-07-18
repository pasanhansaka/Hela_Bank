package lk.pasanhansaka.bank.web.servlet;

import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.pasanhansaka.bank.core.entity.BankAccount;
import lk.pasanhansaka.bank.core.service.BankAccountService;
import lk.pasanhansaka.bank.core.service.TransactionService;

import java.io.IOException;

@WebServlet("/customer/fundTransfer")
public class FundTransfer extends HttpServlet {

    @EJB
    private TransactionService transactionService;

    @EJB
    private BankAccountService bankAccountService;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String fromAccountNumber = request.getParameter("accountNumber");
        String receiverAccountNumber = request.getParameter("receiverAccountNumber");
        String amountString = request.getParameter("amount");
        String description = request.getParameter("description");

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

        transactionService.transferFunds(toAccountNumber.getAccountNumber(), fromAccountNumber, amount, description);
        response.sendRedirect(request.getContextPath() + "/index.jsp");

    }
}
