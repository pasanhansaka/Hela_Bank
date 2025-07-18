package lk.pasanhansaka.bank.web.servlet;

import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.pasanhansaka.bank.core.service.TransactionService;

import java.io.IOException;

@WebServlet("/customer/withdraw")
public class Withdraw extends HttpServlet {

    @EJB
    private TransactionService transactionService;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String accountNumber = request.getParameter("accountNumber");
        String amountString = request.getParameter("amount");
        String description = request.getParameter("description");

        double amount = 0.0;

        try {
            amount = Double.parseDouble(amountString);

        } catch (NumberFormatException e) {
            request.setAttribute("error", "Invalid amount");
            request.getRequestDispatcher(request.getContextPath() + "/error/error.jsp").forward(request, response);
            return;
        }

        transactionService.withdrawFrom(accountNumber,amount,description);
        response.sendRedirect(request.getContextPath() + "/index.jsp");

    }
}
